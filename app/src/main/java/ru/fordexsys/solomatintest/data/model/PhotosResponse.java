package ru.fordexsys.solomatintest.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Altair on 24-Jun-17.
 */

public class PhotosResponse {

    @SerializedName(value="response")
    private PhotosItems photosItems;

    public PhotosItems getPhotosItems() {
        return photosItems;
    }

    public void setPhotosItems(PhotosItems photosItems) {
        this.photosItems = photosItems;
    }
}
