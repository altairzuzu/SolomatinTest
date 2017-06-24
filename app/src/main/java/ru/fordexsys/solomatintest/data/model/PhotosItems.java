package ru.fordexsys.solomatintest.data.model;

import java.util.List;

/**
 * Created by Altair on 24-Jun-17.
 */

public class PhotosItems {

    private int count;
    private List<Photo> photoList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }
}
