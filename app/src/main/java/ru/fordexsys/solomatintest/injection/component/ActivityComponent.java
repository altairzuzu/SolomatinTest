package ru.fordexsys.solomatintest.injection.component;

import ru.fordexsys.solomatintest.injection.PerActivity;
import ru.fordexsys.solomatintest.injection.module.ActivityModule;

import dagger.Component;
import ru.fordexsys.solomatintest.ui.MainActivity;
import ru.fordexsys.solomatintest.ui.detail.DetailActivity;
import ru.fordexsys.solomatintest.ui.login.LoginActivity;
import ru.fordexsys.solomatintest.ui.login.LoginFragment;
import ru.fordexsys.solomatintest.ui.login.WebFragment;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LoginFragment loginFragment);
    void inject(WebFragment webFragment);
//
    void inject(LoginActivity mainActivity);
    void inject(MainActivity mainActivity);
    void inject(DetailActivity mainActivity);

}

