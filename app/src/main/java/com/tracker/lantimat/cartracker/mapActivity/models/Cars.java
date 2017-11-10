package com.tracker.lantimat.cartracker.mapActivity.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.GeoPoint;

/**
 * Created by GabdrakhmanovII on 27.10.2017.
 */

public class Cars implements Parcelable {

    private int id;
    private int driverId;
    private String name;
    private String carNumber;
    private String ptsNumber;
    private Track track;

    public Cars() {
    }

    public Cars(int id, int driverId, String name, String carNumber, String ptsNumber) {
        this.id = id;
        this.driverId = driverId;
        this.name = name;
        this.carNumber = carNumber;
        this.ptsNumber = ptsNumber;
    }

    protected Cars(Parcel in) {
        id = in.readInt();
        driverId = in.readInt();
        name = in.readString();
        carNumber = in.readString();
        ptsNumber = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getPtsNumber() {
        return ptsNumber;
    }

    public void setPtsNumber(String ptsNumber) {
        this.ptsNumber = ptsNumber;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public static final Creator<Cars> CREATOR = new Creator<Cars>() {
        @Override
        public Cars createFromParcel(Parcel in) {
            return new Cars(in);
        }

        @Override
        public Cars[] newArray(int size) {
            return new Cars[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(driverId);
        parcel.writeString(name);
        parcel.writeString(carNumber);
        parcel.writeString(ptsNumber);
    }
}
