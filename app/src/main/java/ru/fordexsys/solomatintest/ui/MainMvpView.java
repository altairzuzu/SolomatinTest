package ru.fordexsys.solomatintest.ui;

import java.util.List;

import ru.fordexsys.solomatintest.data.model.Photo;
import ru.fordexsys.solomatintest.ui.base.MvpView;

/**
 * Created by Altair on 11-Apr-17.
 */

public interface MainMvpView extends MvpView {

    void onGetPhotosSuccess(List<Photo> photoList);
    void onGetPhotosError(String error);
    void onGetMorePhotosSuccess(List<Photo> photoList);
    void onGetMorePhotosError(String error);
}
