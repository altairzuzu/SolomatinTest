package ru.fordexsys.solomatintest.ui.base;

import android.support.v7.app.AppCompatActivity;

import ru.fordexsys.solomatintest.RxApplication;
import ru.fordexsys.solomatintest.injection.component.ActivityComponent;
import ru.fordexsys.solomatintest.injection.component.DaggerActivityComponent;
import ru.fordexsys.solomatintest.injection.module.ActivityModule;


public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    public ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(RxApplication.get(this).getComponent())
                    .activityModule(new ActivityModule(this))
                    .build();
        }
        return mActivityComponent;
    }

}
