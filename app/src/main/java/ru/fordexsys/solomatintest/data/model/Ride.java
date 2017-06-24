package ru.fordexsys.solomatintest.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Altair on 10-Nov-16.
 */
@RealmClass
public class Ride implements RealmModel {

    @PrimaryKey
    private String _id;
    @SerializedName(value="ownr")
    private Photo owner;
//    @SerializedName(value="crtd")
//    private Date create;
    @SerializedName(value="strt")
    private Date start;
    @SerializedName(value="fin")
    private Date finish;
    @SerializedName(value="stat")
    private int state;
    @SerializedName(value="viz")
    private int visible;
    private int wait;
    private RealmList<Photo> will;
    private RealmList<Photo> came;
    @SerializedName(value="inv")
    private RealmList<Photo> invited;
//    @SerializedName(value="lat")
    private double lat;
//    @SerializedName(value="lon")
    private double lon;
    @SerializedName(value="poin")
    private String pointsStr;
    @SerializedName(value="desc")
    private String description;
    private boolean my; // 0 - не имеет ко мне отношения, 1 - относится ко мне (либо я ownr, либо в will)
    private boolean isInv; // 0 - не приглашение , 1 - приглашение (я в inv)
    private boolean newInv; // 0 - просмотренное приглашение, 1 - не просмотренное (новые)
    @SerializedName(value="tz")
    private double timeZone;
    private int temp; // tempo, 0 - slow, 1 - average, 2 - fast
//    @SerializedName(value="msgcnt")
//    private int msgCount;

    public Ride() {}


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Photo getOwner() {
        return owner;
    }

    public void setOwner(Photo owner) {
        this.owner = owner;
    }

//    public Date getCreate() {
//        return create;
//    }
//
//    public void setCreate(Date create) {
//        this.create = create;
//    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }

    public RealmList<Photo> getWill() {
        return will;
    }

    public void setWill(RealmList<Photo> will) {
        this.will = will;
    }

    public RealmList<Photo> getCame() {
        return came;
    }

    public void setCame(RealmList<Photo> came) {
        this.came = came;
    }

    public RealmList<Photo> getInvited() {
        return invited;
    }

    public void setInvited(RealmList<Photo> invited) {
        this.invited = invited;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getPointsStr() {
        return pointsStr;
    }

    public void setPointsStr(String pointsStr) {
        this.pointsStr = pointsStr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isMy() {
        return my;
    }

    public void setMy(boolean my) {
        this.my = my;
    }

    public boolean isInv() {
        return isInv;
    }

    public void setInv(boolean inv) {
        this.isInv = inv;
    }

    public boolean isNewInv() {
        return newInv;
    }

    public void setNewInv(boolean newInv) {
        this.newInv = newInv;
    }

    public double getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(double timeZone) {
        this.timeZone = timeZone;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

//    public int getMsgCount() {
//        return msgCount;
//    }
//
//    public void setMsgCount(int msgCount) {
//        this.msgCount = msgCount;
//    }
}
