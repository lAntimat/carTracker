package com.tracker.lantimat.cartracker.bottomSheetsTimeline.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class TimeLineModel implements Parcelable {

    private String title;
    private String subTitle;
    private String stopTime;
    private String distance;
    private String driveTime;
    private OrderStatus mStatus;

    public TimeLineModel(String title, String subTitle, String stopTime, String distance, String driveTime, OrderStatus mStatus) {
        this.title = title;
        this.subTitle = subTitle;
        this.stopTime = stopTime;
        this.distance = distance;
        this.driveTime = driveTime;
        this.mStatus = mStatus;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public OrderStatus getmStatus() {
        return mStatus;
    }

    public String getStopTime() {
        return stopTime;
    }

    public String getDistance() {
        return distance;
    }

    public String getDriveTime() {
        return driveTime;
    }

    public String getTestText() {
        return stopTime + "\n\n" + distance + "\n" + driveTime;
    }

    public String getDate() {
        return subTitle;
    }

    public void setDate(String date) {
        this.subTitle = date;
    }

    public OrderStatus getStatus() {
        return mStatus;
    }

    public void setStatus(OrderStatus mStatus) {
        this.mStatus = mStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.subTitle);
        dest.writeInt(this.mStatus == null ? -1 : this.mStatus.ordinal());
    }

    protected TimeLineModel(Parcel in) {
        this.title = in.readString();
        this.subTitle = in.readString();
        int tmpMStatus = in.readInt();
        this.mStatus = tmpMStatus == -1 ? null : OrderStatus.values()[tmpMStatus];
    }

    public static final Creator<TimeLineModel> CREATOR = new Creator<TimeLineModel>() {
        @Override
        public TimeLineModel createFromParcel(Parcel source) {
            return new TimeLineModel(source);
        }

        @Override
        public TimeLineModel[] newArray(int size) {
            return new TimeLineModel[size];
        }
    };
}
