package ru.fordexsys.solomatintest.injection.module;

import android.app.Application;
import android.content.Context;

import ru.fordexsys.solomatintest.data.local.LocalDataSource;
import ru.fordexsys.solomatintest.data.remote.VKApi;
import ru.fordexsys.solomatintest.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }


    @Provides
    @Singleton
    VKApi provideBikeWithMeApi() {
        return VKApi.Factory.makeBikeWithMeService(mApplication);
    }

    @Provides
    @Singleton
    LocalDataSource provideLocalDataSource() {
        return LocalDataSource.getInstance();
    }


}
