package com.tracker.lantimat.cartracker.mapActivity.API;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class CarsR implements Parcelable{
    @SerializedName("models")
    @Expose
    private Models models;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("state")
    @Expose
    private State state;
    @SerializedName("type")
    @Expose
    private String type;

    public void setModels(Models models) {
        this.models = models;
    }

    public Models getModels() {
        return models;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.models, flags);
        dest.writeString(this.name);
        dest.writeString(this._id);
        dest.writeString(this.id);
        dest.writeParcelable(this.state, flags);
        dest.writeString(this.type);
    }

    public CarsR() {
    }

    protected CarsR(Parcel in) {
        this.models = in.readParcelable(Models.class.getClassLoader());
        this.name = in.readString();
        this._id = in.readString();
        this.id = in.readString();
        this.state = in.readParcelable(State.class.getClassLoader());
        this.type = in.readString();
    }

    public static final Creator<CarsR> CREATOR = new Creator<CarsR>() {
        @Override
        public CarsR createFromParcel(Parcel source) {
            return new CarsR(source);
        }

        @Override
        public CarsR[] newArray(int size) {
            return new CarsR[size];
        }
    };
}