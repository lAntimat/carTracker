package com.tracker.lantimat.cartracker.mapActivity.API;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class CarsR implements Parcelable {
    @SerializedName("models")
    @Expose
    private Models models;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("state")
    @Expose
    private State state;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("packet")
    @Expose
    private Packet packet;
    @SerializedName("status")
    @Expose
    private String status;

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

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.models, flags);
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeParcelable(this.state, flags);
        dest.writeString(this.type);
        dest.writeParcelable(this.packet, flags);
        dest.writeString(this.status);
    }

    public CarsR() {
    }

    protected CarsR(Parcel in) {
        this.models = in.readParcelable(Models.class.getClassLoader());
        this.name = in.readString();
        this.id = in.readString();
        this.state = in.readParcelable(State.class.getClassLoader());
        this.type = in.readString();
        this.packet = in.readParcelable(Packet.class.getClassLoader());
        this.status = in.readString();
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