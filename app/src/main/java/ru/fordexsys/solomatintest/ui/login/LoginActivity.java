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

    private String currentFrag;

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

//        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
//                if (fragment instanceof LoginFragment) {
////                    toolbarTitle.setText(R.string.authorization);
//                    btnBack.setVisibility(View.GONE);
//                    btnReload.setVisibility(View.GONE);
//                } else if (fragment instanceof WebFragment) {
//                    btnBack.setVisibility(View.VISIBLE);
//                    btnReload.setVisibility(View.VISIBLE);
////                    toolbarTitle.setText(R.string.register);
//                }
//            }
//        });
//
//        btnBack.setOnClickListener(this);

        changeFragment(LoginFragment.TAG);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//    }

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
        currentFrag = fragTag;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;
//        Bundle args = new Bundle();
        switch (fragTag) {
            case LoginFragment.TAG:
                fragment = LoginFragment.newInstance();
//                args = new Bundle();
//                if (category != null) {
//                    fragment.setArguments(args);
//                }
//                ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case WebFragment.TAG:
                fragment = WebFragment.newInstance();
//                ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                ft.addToBackStack(null);
                break;
        }
        if (fragment != null) {
            ft.replace(R.id.container, fragment, fragTag);
            ft.commit();
        }
    }

}
