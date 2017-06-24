package ru.fordexsys.solomatintest.ui.detail;

import java.util.List;

import ru.fordexsys.solomatintest.data.model.Photo;
import ru.fordexsys.solomatintest.ui.base.MvpView;

/**
 * Created by Altair on 24-Jun-17.
 */

public interface DetailMvpView extends MvpView {

    void onGetPhotosSuccess(List<Photo> photoList);
    void onGetPhotosError();
    void onGetMorePhotosSuccess(List<Photo> photoList);
    void onGetMorePhotosError();
}
