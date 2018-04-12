package com.tracker.lantimat.cartracker.mapActivity.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class State {
    @SerializedName("driver_message")
    @Expose
    private String driver_message;
    @SerializedName("lon")
    @Expose
    private double lon;
    @SerializedName("egts")
    @Expose
    private Boolean egts;
    @SerializedName("speed")
    @Expose
    private double speed;
    @SerializedName("gps_odometer")
    @Expose
    private double gps_odometer;
    @SerializedName("real-time")
    @Expose
    private double realTime;
    @SerializedName("angle")
    @Expose
    private Long angle;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("mileage")
    @Expose
    private double mileage;
    @SerializedName("glonass_inview")
    @Expose
    private double glonass_inview;
    @SerializedName("voltage")
    @Expose
    private double voltage;
    @SerializedName("fuel-level")
    @Expose
    private double fuelLevel;
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

    public double getLon() {
        return lon;
    }

    public Boolean getEgts() {
        return egts;
    }

    public double getSpeed() {
        return speed;
    }

    public double getGps_odometer() {
        return gps_odometer;
    }

    public double getRealTime() {
        return realTime;
    }

    public Long getAngle() {
        return angle;
    }

    public String getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getMileage() {
        return mileage;
    }

    public double getGlonass_inview() {
        return glonass_inview;
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

    public double getFuelLevel() {
        return fuelLevel;
    }
}