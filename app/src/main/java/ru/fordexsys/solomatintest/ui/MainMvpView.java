package ru.fordexsys.solomatintest.ui;

import ru.fordexsys.solomatintest.ui.base.MvpView;

/**
 * Created by Altair on 11-Apr-17.
 */

public interface MainMvpView extends MvpView {

    void getPhotosSuccess();
    void getPhotosError();
    void getMorePhotosSuccess();
    void getMorePhotosError();
}
