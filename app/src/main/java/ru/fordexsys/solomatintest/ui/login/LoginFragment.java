package ru.fordexsys.solomatintest.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ru.fordexsys.solomatintest.R;
import ru.fordexsys.solomatintest.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Altair on 01-Feb-17.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "LoginFragment";

    @BindView(R.id.btn_login)
    Button btnLogin;

    private Unbinder unbinder;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);

        ((BaseActivity) getActivity()).activityComponent().inject(this);

        init();

        return view;
    }

    private void init() {
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                btnLogin.setEnabled(false);
                ((LoginActivity) getActivity()).changeFragment(WebFragment.TAG);
                break;
        }
    }


    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
