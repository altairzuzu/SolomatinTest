package ru.fordexsys.solomatintest.ui.detail;

import ru.fordexsys.solomatintest.data.model.Photo;
import ru.fordexsys.solomatintest.ui.base.MvpView;

/**
 * Created by Altair on 24-Jun-17.
 */

public interface DetailMvpView extends MvpView {

    void onGetPhotoSuccess(Photo photo);
    void onGetPhotoError();
}
