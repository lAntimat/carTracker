package com.tracker.lantimat.cartracker.mapActivity.API;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class State implements Parcelable {
    @SerializedName("driver_message")
    @Expose
    private String driver_message;
    @SerializedName("ex_dig_out_1")
    @Expose
    private Integer ex_dig_out_1;
    @SerializedName("fuel")
    @Expose
    private Integer fuel;
    @SerializedName("ex_dig_out_5")
    @Expose
    private Integer ex_dig_out_5;
    @SerializedName("ex_dig_out_4")
    @Expose
    private Integer ex_dig_out_4;
    @SerializedName("lon")
    @Expose
    private double lon;
    @SerializedName("ex_dig_out_3")
    @Expose
    private Integer ex_dig_out_3;
    @SerializedName("ex_dig_out_2")
    @Expose
    private Integer ex_dig_out_2;
    @SerializedName("ex_dig_out_9")
    @Expose
    private Integer ex_dig_out_9;
    @SerializedName("ex_dig_out_8")
    @Expose
    private Integer ex_dig_out_8;
    @SerializedName("egts")
    @Expose
    private Boolean egts;
    @SerializedName("speed")
    @Expose
    private double speed;
    @SerializedName("ex_dig_out_7")
    @Expose
    private Integer ex_dig_out_7;
    @SerializedName("bat-voltage")
    @Expose
    private double bat_voltage;
    @SerializedName("ex_dig_out_6")
    @Expose
    private Integer ex_dig_out_6;
    @SerializedName("gps_odometer")
    @Expose
    private double gps_odometer;
    @SerializedName("real-time")
    @Expose
    private double real_time;
    @SerializedName("angle")
    @Expose
    private double angle;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("ex_dig_in_2")
    @Expose
    private Integer ex_dig_in_2;
    @SerializedName("height")
    @Expose
    private double height;
    @SerializedName("ex_dig_in_1")
    @Expose
    private Integer ex_dig_in_1;
    @SerializedName("mileage")
    @Expose
    private double mileage;
    @SerializedName("glonass_inview")
    @Expose
    private double glonass_inview;
    @SerializedName("ex_dig_out_10")
    @Expose
    private Integer ex_dig_out_10;
    @SerializedName("ex_dig_out_11")
    @Expose
    private Integer ex_dig_out_11;
    @SerializedName("voltage")
    @Expose
    private double voltage;
    @SerializedName("gps_inview")
    @Expose
    private double gps_inview;
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("time")
    @Expose
    private double time;

    public String getDriver_message() {
        return driver_message;
    }

    public Integer getEx_dig_out_1() {
        return ex_dig_out_1;
    }

    public Integer getFuel() {
        return fuel;
    }

    public Integer getEx_dig_out_5() {
        return ex_dig_out_5;
    }

    public Integer getEx_dig_out_4() {
        return ex_dig_out_4;
    }

    public double getLon() {
        return lon;
    }

    public Integer getEx_dig_out_3() {
        return ex_dig_out_3;
    }

    public Integer getEx_dig_out_2() {
        return ex_dig_out_2;
    }

    public Integer getEx_dig_out_9() {
        return ex_dig_out_9;
    }

    public Integer getEx_dig_out_8() {
        return ex_dig_out_8;
    }

    public Boolean getEgts() {
        return egts;
    }

    public double getSpeed() {
        return speed;
    }

    public Integer getEx_dig_out_7() {
        return ex_dig_out_7;
    }

    public double getBat_voltage() {
        return bat_voltage;
    }

    public Integer getEx_dig_out_6() {
        return ex_dig_out_6;
    }

    public double getGps_odometer() {
        return gps_odometer;
    }

    public double getReal_time() {
        return real_time;
    }

    public double getAngle() {
        return angle;
    }

    public String getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public Integer getEx_dig_in_2() {
        return ex_dig_in_2;
    }

    public double getHeight() {
        return height;
    }

    public Integer getEx_dig_in_1() {
        return ex_dig_in_1;
    }

    public double getMileage() {
        return mileage;
    }

    public double getGlonass_inview() {
        return glonass_inview;
    }

    public Integer getEx_dig_out_10() {
        return ex_dig_out_10;
    }

    public Integer getEx_dig_out_11() {
        return ex_dig_out_11;
    }

    public double getVoltage() {
        return voltage;
    }

    public double getGps_inview() {
        return gps_inview;
    }

    public String get_id() {
        return _id;
    }

    public double getTime() {
        return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.driver_message);
        dest.writeValue(this.ex_dig_out_1);
        dest.writeValue(this.fuel);
        dest.writeValue(this.ex_dig_out_5);
        dest.writeValue(this.ex_dig_out_4);
        dest.writeValue(this.lon);
        dest.writeValue(this.ex_dig_out_3);
        dest.writeValue(this.ex_dig_out_2);
        dest.writeValue(this.ex_dig_out_9);
        dest.writeValue(this.ex_dig_out_8);
        dest.writeValue(this.egts);
        dest.writeValue(this.speed);
        dest.writeValue(this.ex_dig_out_7);
        dest.writeValue(this.bat_voltage);
        dest.writeValue(this.ex_dig_out_6);
        dest.writeValue(this.gps_odometer);
        dest.writeValue(this.real_time);
        dest.writeValue(this.angle);
        dest.writeString(this.id);
        dest.writeValue(this.lat);
        dest.writeValue(this.ex_dig_in_2);
        dest.writeValue(this.height);
        dest.writeValue(this.ex_dig_in_1);
        dest.writeValue(this.mileage);
        dest.writeValue(this.glonass_inview);
        dest.writeValue(this.ex_dig_out_10);
        dest.writeValue(this.ex_dig_out_11);
        dest.writeValue(this.voltage);
        dest.writeValue(this.gps_inview);
        dest.writeString(this._id);
        dest.writeValue(this.time);
    }

    public State() {
    }

    protected State(Parcel in) {
        this.driver_message = in.readString();
        this.ex_dig_out_1 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.fuel = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ex_dig_out_5 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ex_dig_out_4 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lon = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ex_dig_out_3 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ex_dig_out_2 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ex_dig_out_9 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ex_dig_out_8 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.egts = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.speed = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ex_dig_out_7 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.bat_voltage = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ex_dig_out_6 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.gps_odometer = (Integer) in.readValue(Integer.class.getClassLoader());
        this.real_time = (Integer) in.readValue(Integer.class.getClassLoader());
        this.angle = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id = in.readString();
        this.lat = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ex_dig_in_2 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.height = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ex_dig_in_1 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mileage = (Integer) in.readValue(Integer.class.getClassLoader());
        this.glonass_inview = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ex_dig_out_10 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ex_dig_out_11 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.voltage = (Integer) in.readValue(Integer.class.getClassLoader());
        this.gps_inview = (Integer) in.readValue(Integer.class.getClassLoader());
        this._id = in.readString();
        this.time = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<State> CREATOR = new Parcelable.Creator<State>() {
        @Override
        public State createFromParcel(Parcel source) {
            return new State(source);
        }

        @Override
        public State[] newArray(int size) {
            return new State[size];
        }
    };
}