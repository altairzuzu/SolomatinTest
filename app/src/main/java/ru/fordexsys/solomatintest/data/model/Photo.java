package ru.fordexsys.solomatintest.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Photo implements RealmModel, Parcelable {

    @PrimaryKey
    private long id;

    private String photoSmall;
    private String photoBig;
    private String text;
    private int likesCount;
    private int repostsCount;
    private Date uploaded;

    @Ignore
    private String photo_75;
    @Ignore
    private String photo_130;
    @Ignore
    private String photo_604;
    @Ignore
    private String photo_807;
    @Ignore
    private String photo_1280;
    @Ignore
    private String photo_2560;
    @Ignore
    private int width;
    @Ignore
    private int height;
    @Ignore
    private int date;
    @Ignore
    private Likes likes;
    @Ignore
    private Reposts reposts;

    public Photo() {
    }

    public Photo(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = false;
        if (object != null && object instanceof Photo) {
            isEqual = (this.id == ((Photo) object).getId());
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return (int) (this.id ^ (this.id >>> 32));
//        return this.id.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(photoSmall);
        parcel.writeString(photoBig);
        parcel.writeString(text);
        parcel.writeInt(likesCount);
        parcel.writeInt(repostsCount);
        parcel.writeLong(uploaded.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Photo> CREATOR
            = new Creator<Photo>() {
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    private Photo(Parcel in) {
        id = in.readLong();
        photoSmall = in.readString();
        photoBig = in.readString();
        text = in.readString();
        likesCount = in.readInt();
        repostsCount = in.readInt();
        uploaded = new Date(in.readLong());

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhotoSmall() {
        return photoSmall;
    }

    public void setPhotoSmall(String photoSmall) {
        this.photoSmall = photoSmall;
    }

    public String getPhotoBig() {
        return photoBig;
    }

    public void setPhotoBig(String photoBig) {
        this.photoBig = photoBig;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getRepostsCount() {
        return repostsCount;
    }

    public void setRepostsCount(int repostsCount) {
        this.repostsCount = repostsCount;
    }

    public Date getUploaded() {
        return uploaded;
    }

    public void setUploaded(Date uploaded) {
        this.uploaded = uploaded;
    }

    public String getPhoto_75() {
        return photo_75;
    }

    public void setPhoto_75(String photo_75) {
        this.photo_75 = photo_75;
    }

    public String getPhoto_130() {
        return photo_130;
    }

    public void setPhoto_130(String photo_130) {
        this.photo_130 = photo_130;
    }

    public String getPhoto_604() {
        return photo_604;
    }

    public void setPhoto_604(String photo_604) {
        this.photo_604 = photo_604;
    }

    public String getPhoto_807() {
        return photo_807;
    }

    public void setPhoto_807(String photo_807) {
        this.photo_807 = photo_807;
    }

    public String getPhoto_1280() {
        return photo_1280;
    }

    public void setPhoto_1280(String photo_1280) {
        this.photo_1280 = photo_1280;
    }

    public String getPhoto_2560() {
        return photo_2560;
    }

    public void setPhoto_2560(String photo_2560) {
        this.photo_2560 = photo_2560;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public Reposts getReposts() {
        return reposts;
    }

    public void setReposts(Reposts reposts) {
        this.reposts = reposts;
    }
}
