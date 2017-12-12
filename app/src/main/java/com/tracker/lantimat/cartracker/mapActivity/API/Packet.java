package com.tracker.lantimat.cartracker.mapActivity.API;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Packet implements Parcelable {
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
    private float lon;
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
    private Integer speed;
    @SerializedName("ex_dig_out_7")
    @Expose
    private Integer ex_dig_out_7;
    @SerializedName("bat_voltage")
    @Expose
    private Integer bat_voltage;
    @SerializedName("ex_dig_out_6")
    @Expose
    private Integer ex_dig_out_6;
    @SerializedName("gps_odometer")
    @Expose
    private Integer gps_odometer;
    @SerializedName("real-time")
    @Expose
    private float real_time;
    @SerializedName("angle")
    @Expose
    private Integer angle;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lat")
    @Expose
    private float lat;
    @SerializedName("ex_dig_in_2")
    @Expose
    private Integer ex_dig_in_2;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("ex_dig_in_1")
    @Expose
    private Integer ex_dig_in_1;
    @SerializedName("mileage")
    @Expose
    private Integer mileage;
    @SerializedName("glonass_inview")
    @Expose
    private Integer glonass_inview;
    @SerializedName("ex_dig_out_10")
    @Expose
    private Integer ex_dig_out_10;
    @SerializedName("ex_dig_out_11")
    @Expose
    private Integer ex_dig_out_11;
    @SerializedName("voltage")
    @Expose
    private Integer voltage;
    @SerializedName("gps_inview")
    @Expose
    private Integer gps_inview;
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("time")
    @Expose
    private double time;

    public void setDriver_message(String driver_message) {
        this.driver_message = driver_message;
    }

    public String getDriver_message() {
        return driver_message;
    }

    public void setEx_dig_out_1(Integer ex_dig_out_1) {
        this.ex_dig_out_1 = ex_dig_out_1;
    }

    public Integer getEx_dig_out_1() {
        return ex_dig_out_1;
    }

    public void setFuel(Integer fuel) {
        this.fuel = fuel;
    }

    public Integer getFuel() {
        return fuel;
    }

    public void setEx_dig_out_5(Integer ex_dig_out_5) {
        this.ex_dig_out_5 = ex_dig_out_5;
    }

    public Integer getEx_dig_out_5() {
        return ex_dig_out_5;
    }

    public void setEx_dig_out_4(Integer ex_dig_out_4) {
        this.ex_dig_out_4 = ex_dig_out_4;
    }

    public Integer getEx_dig_out_4() {
        return ex_dig_out_4;
    }

    public void setLon(Integer lon) {
        this.lon = lon;
    }

    public float getLon() {
        return lon;
    }

    public void setEx_dig_out_3(Integer ex_dig_out_3) {
        this.ex_dig_out_3 = ex_dig_out_3;
    }

    public Integer getEx_dig_out_3() {
        return ex_dig_out_3;
    }

    public void setEx_dig_out_2(Integer ex_dig_out_2) {
        this.ex_dig_out_2 = ex_dig_out_2;
    }

    public Integer getEx_dig_out_2() {
        return ex_dig_out_2;
    }

    public void setEx_dig_out_9(Integer ex_dig_out_9) {
        this.ex_dig_out_9 = ex_dig_out_9;
    }

    public Integer getEx_dig_out_9() {
        return ex_dig_out_9;
    }

    public void setEx_dig_out_8(Integer ex_dig_out_8) {
        this.ex_dig_out_8 = ex_dig_out_8;
    }

    public Integer getEx_dig_out_8() {
        return ex_dig_out_8;
    }

    public void setEgts(Boolean egts) {
        this.egts = egts;
    }

    public Boolean getEgts() {
        return egts;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setEx_dig_out_7(Integer ex_dig_out_7) {
        this.ex_dig_out_7 = ex_dig_out_7;
    }

    public Integer getEx_dig_out_7() {
        return ex_dig_out_7;
    }

    public void setEx_dig_out_6(Integer ex_dig_out_6) {
        this.ex_dig_out_6 = ex_dig_out_6;
    }

    public Integer getEx_dig_out_6() {
        return ex_dig_out_6;
    }

    public void setGps_odometer(Integer gps_odometer) {
        this.gps_odometer = gps_odometer;
    }

    public Integer getGps_odometer() {
        return gps_odometer;
    }

    public Integer getBat_voltage() {
        return bat_voltage;
    }

    public void setBat_voltage(Integer bat_voltage) {
        this.bat_voltage = bat_voltage;
    }

    public float getReal_time() {
        return real_time;
    }

    public void setReal_time(Integer real_time) {
        this.real_time = real_time;
    }

    public void setAngle(Integer angle) {
        this.angle = angle;
    }

    public Integer getAngle() {
        return angle;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public float getLat() {
        return lat;
    }

    public void setEx_dig_in_2(Integer ex_dig_in_2) {
        this.ex_dig_in_2 = ex_dig_in_2;
    }

    public Integer getEx_dig_in_2() {
        return ex_dig_in_2;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getHeight() {
        return height;
    }

    public void setEx_dig_in_1(Integer ex_dig_in_1) {
        this.ex_dig_in_1 = ex_dig_in_1;
    }

    public Integer getEx_dig_in_1() {
        return ex_dig_in_1;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setGlonass_inview(Integer glonass_inview) {
        this.glonass_inview = glonass_inview;
    }

    public Integer getGlonass_inview() {
        return glonass_inview;
    }

    public void setEx_dig_out_10(Integer ex_dig_out_10) {
        this.ex_dig_out_10 = ex_dig_out_10;
    }

    public Integer getEx_dig_out_10() {
        return ex_dig_out_10;
    }

    public void setEx_dig_out_11(Integer ex_dig_out_11) {
        this.ex_dig_out_11 = ex_dig_out_11;
    }

    public Integer getEx_dig_out_11() {
        return ex_dig_out_11;
    }

    public void setVoltage(Integer voltage) {
        this.voltage = voltage;
    }

    public Integer getVoltage() {
        return voltage;
    }

    public void setGps_inview(Integer gps_inview) {
        this.gps_inview = gps_inview;
    }

    public Integer getGps_inview() {
        return gps_inview;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }

    public void setTime(Integer time) {
        this.time = time;
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

    public Packet() {
    }

    protected Packet(Parcel in) {
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

    public static final Parcelable.Creator<Packet> CREATOR = new Parcelable.Creator<Packet>() {
        @Override
        public Packet createFromParcel(Parcel source) {
            return new Packet(source);
        }

        @Override
        public Packet[] newArray(int size) {
            return new Packet[size];
        }
    };
}