package ru.fordexsys.solomatintest.injection.component;

import android.app.Application;
import android.content.Context;

import ru.fordexsys.solomatintest.RxApplication;
import ru.fordexsys.solomatintest.data.DataManager;
import ru.fordexsys.solomatintest.data.local.LocalDataSource;
import ru.fordexsys.solomatintest.data.local.PreferencesHelper;
import ru.fordexsys.solomatintest.data.remote.VKApi;
import ru.fordexsys.solomatintest.injection.ApplicationContext;
import ru.fordexsys.solomatintest.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(RxApplication RxApplication);

    @ApplicationContext
    Context context();
    Application application();
    VKApi bikeWithMeApi();
    LocalDataSource localDataSource();
    PreferencesHelper preferencesHelper();
    DataManager dataManager();
}
