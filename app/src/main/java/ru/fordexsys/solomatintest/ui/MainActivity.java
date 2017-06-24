package ru.fordexsys.solomatintest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import ru.fordexsys.solomatintest.R;
import ru.fordexsys.solomatintest.ui.base.BaseActivity;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Altair on 29-Oct-16.
 */

public class MainActivity extends BaseActivity implements MainMvpView, View.OnClickListener {

    private static final String TAG = "MainActivity";

    @BindView(R.id.main_coordinator)
    CoordinatorLayout mainCoordinator;

    @Inject
    MainPresenter presenter;

    private String currentFrag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.activityComponent().inject(this);
        presenter.attachView(this);

        init();

        Intent intent = getIntent();
        if (intent.getBooleanExtra("login", false)) {
            Snackbar.make(mainCoordinator, getString(R.string.enter_done), Snackbar.LENGTH_SHORT).show();
        }

    }

    private void init() {


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar.make(mainCoordinator, R.string.press_more, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void getPhotosSuccess() {

    }

    @Override
    public void getPhotosError() {

    }

    @Override
    public void getMorePhotosSuccess() {

    }

    @Override
    public void getMorePhotosError() {

    }


    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (mapFragment != null) {
//            if (requestCode == REQUEST_CHECK_SETTINGS) {
//                mapFragment.onActivityResult(requestCode, resultCode, data);
//
//                //todo проверить
//            } else if (requestCode == REQUEST_DETAIL) {
//                mapFragment.onActivityResult(requestCode, resultCode, data);
//            }
//        }
//    }

}
