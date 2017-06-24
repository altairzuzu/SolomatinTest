package ru.fordexsys.solomatintest.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import ru.fordexsys.solomatintest.R;
import ru.fordexsys.solomatintest.data.DataManager;
import ru.fordexsys.solomatintest.ui.MainActivity;
import ru.fordexsys.solomatintest.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Altair on 04-Nov-16.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    @Inject
    DataManager dataManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        this.activityComponent().inject(this);

        String token = dataManager.getPreferencesHelper().getToken();
        if (!TextUtils.isEmpty(token)) {
            closeActivity();
        }

        changeFragment(LoginFragment.TAG);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }

    void closeActivity() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        this.startActivity(mainIntent);
        this.finish();
    }

    void changeFragment(@NonNull String fragTag) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;

        switch (fragTag) {
            case LoginFragment.TAG:
                fragment = LoginFragment.newInstance();
                break;
            case WebFragment.TAG:
                fragment = WebFragment.newInstance();
                ft.addToBackStack(null);
                break;
        }
        if (fragment != null) {
            ft.replace(R.id.container, fragment, fragTag);
            ft.commit();
        }
    }

}
