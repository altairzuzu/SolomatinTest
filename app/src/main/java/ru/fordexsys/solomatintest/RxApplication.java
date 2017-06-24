package ru.fordexsys.solomatintest;

import android.app.Application;
import android.content.Context;

import ru.fordexsys.solomatintest.data.DataManager;
import ru.fordexsys.solomatintest.injection.component.ApplicationComponent;
import ru.fordexsys.solomatintest.injection.component.DaggerApplicationComponent;
import ru.fordexsys.solomatintest.injection.module.ApplicationModule;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RxApplication extends Application {

    @Inject
    DataManager dataManager;
    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
//        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
//                .schemaVersion(0)
//                .migration(new MyMigration())
//                .build();
//        Realm.setDefaultConfiguration(realmConfig);

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);

    }

    public static RxApplication get(Context context) {
        return (RxApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }


}

