package ru.fordexsys.solomatintest.ui.base;

public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
