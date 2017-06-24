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

/**
 * Created by Altair on 12-Nov-16.
 */
@RealmClass
public class Photo implements RealmModel, Parcelable {

//public class Photo extends RealmObject {

//              "id": 430425125,
//            "album_id": -7,
//            "owner_id": 1,
//            "photo_75": "https://pp.userap...490/r_Te_ODfXHo.jpg",
//            "photo_130": "https://pp.userap...491/2x-nxFeFElU.jpg",
//            "photo_604": "https://pp.userap...492/Rf1ZluR1Svg.jpg",
//            "photo_807": "https://pp.userap...493/OMbaZVhe9BA.jpg",
//            "photo_1280": "https://pp.userap...494/EiOADmRQ7sk.jpg",
//            "photo_2560": "https://pp.userap...495/btx0IfkZejU.jpg",
//            "width": 1600,
//            "height": 679,
//            "text": "4. При просмотре фотографий утрачен фокус на самой картинке. Фотографии смещены влево, а яркое пятно белой колонки комментариев, появившейся справа, перетягивает на себя фокус внимания. Это решение было скопировано c Facebook, который в свое время ввел правую колонку для размещения дополнительных рекламных блоков.",
//            "date": 1471444581,
//            "likes": {
//              "user_likes": 0,
//                "count": 16779
//              },
//            "reposts": {
//                 "count": 37
//    }

    @PrimaryKey
    private String id;

    private String photoSmall;
    private String photoBig;

    private String text;
    private int likes;

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


    public Photo() {
    }

    public Photo(String id) {
        this._id = id;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = false;
        if (object != null && object instanceof Photo) {
            isEqual = this._id.equals(((Photo) object).get_id());
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return this._id.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(name);
        parcel.writeString(ava);
        parcel.writeString(email);
        parcel.writeString(token);
        parcel.writeString(bike);
//        parcel.writeString(payload);
        parcel.writeByte((byte) (premium ? 1 : 0));

//        if (created != null) {
//            parcel.writeLong(created.getTime());
//        }
//        parcel.writeDouble(rating);
        parcel.writeInt(ratingCame);
        parcel.writeInt(ratingNotCame);
        parcel.writeInt(ratingNotOwner);
        parcel.writeInt(ratingOwner);
        parcel.writeInt(age);
        parcel.writeInt(rating);
        parcel.writeByte((byte) (isLoadedFull ? 1 : 0));
        ArrayList<Photo> friendsList = new ArrayList<>();
        if (friends != null) {
            for (Photo photo : friends) {
                friendsList.add(photo);
            }
        }
        parcel.writeTypedList(friendsList);
        ArrayList<Photo> friendToList = new ArrayList<>();
        if (friendTo != null) {
            for (Photo photo : friendTo) {
                friendToList.add(photo);
            }
        }
        parcel.writeTypedList(friendToList);

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
        _id = in.readString();
        name = in.readString();
        ava = in.readString();
        email = in.readString();
        token = in.readString();
        bike = in.readString();
//        payload = in.readString();
        premium = in.readByte() != 0;

//        created = new Date(in.readLong());
//        rating = in.readDouble();
        ratingCame = in.readInt();
        ratingNotCame = in.readInt();
        ratingNotOwner = in.readInt();
        ratingOwner = in.readInt();
        age = in.readInt();
        rating = in.readInt();
        isLoadedFull = in.readByte() != 0;
        ArrayList<Photo> friendsList = new ArrayList<>();
        in.readTypedList(friendsList, Photo.CREATOR);
        friends = new RealmList<>(friendsList.toArray(new Photo[friendsList.size()]));
        ArrayList<Photo> friendToList = new ArrayList<>();
        in.readTypedList(friendToList, Photo.CREATOR);
        friendTo = new RealmList<>(friendToList.toArray(new Photo[friendToList.size()]));

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAva() {
        return ava;
    }

    public void setAva(String ava) {
        this.ava = ava;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

//    public double getRating() {
//        return rating;
//    }
//
//    public void setRating(double rating) {
//        this.rating = rating;
//    }

    public RealmList<Photo> getFriends() {
        return friends;
    }

    public void setFriends(RealmList<Photo> friends) {
        this.friends = friends;
    }

    public RealmList<Photo> getFriendTo() {
        return friendTo;
    }

    public void setFriendTo(RealmList<Photo> friendTo) {
        this.friendTo = friendTo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public int getRatingCame() {
        return ratingCame;
    }

    public void setRatingCame(int ratingCame) {
        this.ratingCame = ratingCame;
    }

    public int getRatingNotCame() {
        return ratingNotCame;
    }

    public void setRatingNotCame(int ratingNotCame) {
        this.ratingNotCame = ratingNotCame;
    }

    public int getRatingNotOwner() {
        return ratingNotOwner;
    }

    public void setRatingNotOwner(int ratingNotOwner) {
        this.ratingNotOwner = ratingNotOwner;
    }

    public int getRatingOwner() {
        return ratingOwner;
    }

    public void setRatingOwner(int ratingOwner) {
        this.ratingOwner = ratingOwner;
    }

    public boolean isLoadedFull() {
        return isLoadedFull;
    }

    public void setLoadedFull(boolean loadedFull) {
        isLoadedFull = loadedFull;
    }

//    public String getPayload() {
//        return payload;
//    }
//
//    public void setPayload(String payload) {
//        this.payload = payload;
//    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBike() {
        return bike;
    }

    public void setBike(String bike) {
        this.bike = bike;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
