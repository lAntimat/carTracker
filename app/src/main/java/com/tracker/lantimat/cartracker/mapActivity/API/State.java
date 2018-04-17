package com.tracker.lantimat.cartracker.mapActivity.API;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class State{
  @SerializedName("cons_fuel_p")
  @Expose
  private float cons_fuel_p;
  @SerializedName("hand_break")
  @Expose
  private float hand_break;
  @SerializedName("cons_fuel_l")
  @Expose
  private float cons_fuel_l;
  @SerializedName("int_temp")
  @Expose
  private float int_temp;
  @SerializedName("dynamic_ign")
  @Expose
  private float dynamic_ign;
  @SerializedName("bat-voltage")
  @Expose
  private float bat_voltage;
  @SerializedName("egts")
  @Expose
  private boolean egts;
  @SerializedName("oper_mode")
  @Expose
  private float oper_mode;
  @SerializedName("lac")
  @Expose
  private float lac;
  @SerializedName("gps_odometer")
  @Expose
  private float gps_odometer;
  @SerializedName("ign")
  @Expose
  private float ign;
  @SerializedName("real-time")
  @Expose
  private float real_time;
  @SerializedName("pedal_break")
  @Expose
  private float pedal_break;
  @SerializedName("eng_uptime")
  @Expose
  private float eng_uptime;
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("signal")
  @Expose
  private float signal;
  @SerializedName("lat")
  @Expose
  private float lat;
  @SerializedName("cell_id")
  @Expose
  private float cell_id;
  @SerializedName("height")
  @Expose
  private float height;
  @SerializedName("mileage")
  @Expose
  private float mileage;
  @SerializedName("_ts")
  @Expose
  private float _ts;
  @SerializedName("accelerator")
  @Expose
  private float accelerator;
  @SerializedName("glonass_inview")
  @Expose
  private float glonass_inview;
  @SerializedName("_oid")
  @Expose
  private String _oid;
  @SerializedName("ext_voltage")
  @Expose
  private float ext_voltage;
  @SerializedName("sat_used")
  @Expose
  private float sat_used;
  @SerializedName("fuel_lev_p")
  @Expose
  private float fuel_lev_p;
  @SerializedName("sat-count")
  @Expose
  private float sat_count;
  @SerializedName("fuel_lev_l")
  @Expose
  private float fuel_lev_l;
  @SerializedName("temp-inside")
  @Expose
  private float temp_inside;
  @SerializedName("driver_message")
  @Expose
  private String driver_message;
  @SerializedName("altitude")
  @Expose
  private float altitude;
  @SerializedName("can_speed")
  @Expose
  private float can_speed;

  @SerializedName("eng_uptime_p")
  @Expose
  private float eng_uptime_p;
  @SerializedName("gsm_sig_level")
  @Expose
  private Integer gsm_sig_level;

  @SerializedName("lon")
  @Expose
  private float lon;
  @SerializedName("glonass_sat")
  @Expose
  private Integer glonass_sat;
  @SerializedName("speed")
  @Expose
  private float speed;
  @SerializedName("mcu_fw")
  @Expose
  private String mcu_fw;
  @SerializedName("angle")
  @Expose
  private float angle;
  @SerializedName("ignition")
  @Expose
  private float ignition;

  @SerializedName("direction")
  @Expose
  private float direction;
  @SerializedName("can_odo_p")
  @Expose
  private float can_odo_p;
  @SerializedName("mnc")
  @Expose
  private float mnc;
  @SerializedName("acc_voltage")
  @Expose
  private float acc_voltage;
  @SerializedName("eng_rpm")
  @Expose
  private float eng_rpm;
  @SerializedName("eng_temp")
  @Expose
  private float eng_temp;
  @SerializedName("hdop")
  @Expose
  private float hdop;
  @SerializedName("tamper_2")
  @Expose
  private float tamper_2;
  @SerializedName("voltage")
  @Expose
  private float voltage;
  @SerializedName("uptime")
  @Expose
  private float uptime;
  @SerializedName("trip_counter")
  @Expose
  private float trip_counter;
  @SerializedName("tamper_1")
  @Expose
  private float tamper_1;
  @SerializedName("can_odo_km")
  @Expose
  private float can_odo_km;
  @SerializedName("can_in_sleep")
  @Expose
  private float can_in_sleep;
  @SerializedName("gps_inview")
  @Expose
  private float gps_inview;
  @SerializedName("mess_count_1")
  @Expose
  private float mess_count_1;
  @SerializedName("time")
  @Expose
  private float time;
  @SerializedName("engine_is_on")
  @Expose
  private float engine_is_on;

    public float getCons_fuel_p() {
        return cons_fuel_p;
    }

    public float getHand_break() {
        return hand_break;
    }

    public float getCons_fuel_l() {
        return cons_fuel_l;
    }

    public float getInt_temp() {
        return int_temp;
    }

    public float getDynamic_ign() {
        return dynamic_ign;
    }

    public float getBat_voltage() {
        return bat_voltage;
    }

    public boolean isEgts() {
        return egts;
    }

    public float getOper_mode() {
        return oper_mode;
    }

    public float getLac() {
        return lac;
    }

    public float getGps_odometer() {
        return gps_odometer;
    }

    public float getIgn() {
        return ign;
    }

    public float getReal_time() {
        return real_time;
    }

    public float getPedal_break() {
        return pedal_break;
    }

    public float getEng_uptime() {
        return eng_uptime;
    }

    public String getId() {
        return id;
    }

    public float getSignal() {
        return signal;
    }

    public float getLat() {
        return lat;
    }

    public float getCell_id() {
        return cell_id;
    }

    public float getHeight() {
        return height;
    }

    public float getMileage() {
        return mileage;
    }

    public float get_ts() {
        return _ts;
    }

    public float getAccelerator() {
        return accelerator;
    }

    public float getGlonass_inview() {
        return glonass_inview;
    }

    public String get_oid() {
        return _oid;
    }

    public float getExt_voltage() {
        return ext_voltage;
    }

    public float getSat_used() {
        return sat_used;
    }

    public float getFuel_lev_p() {
        return fuel_lev_p;
    }

    public float getSat_count() {
        return sat_count;
    }

    public float getFuel_lev_l() {
        return fuel_lev_l;
    }

    public float getTemp_inside() {
        return temp_inside;
    }

    public String getDriver_message() {
        return driver_message;
    }

    public float getAltitude() {
        return altitude;
    }

    public float getCan_speed() {
        return can_speed;
    }

    public float getEng_uptime_p() {
        return eng_uptime_p;
    }

    public Integer getGsm_sig_level() {
        return gsm_sig_level;
    }

    public float getLon() {
        return lon;
    }

    public Integer getGlonass_sat() {
        return glonass_sat;
    }

    public float getSpeed() {
        return speed;
    }

    public String getMcu_fw() {
        return mcu_fw;
    }

    public float getAngle() {
        return angle;
    }

    public float getIgnition() {
        return ignition;
    }

    public float getDirection() {
        return direction;
    }

    public float getCan_odo_p() {
        return can_odo_p;
    }

    public float getMnc() {
        return mnc;
    }

    public float getAcc_voltage() {
        return acc_voltage;
    }

    public float getEng_rpm() {
        return eng_rpm;
    }

    public float getEng_temp() {
        return eng_temp;
    }

    public float getHdop() {
        return hdop;
    }

    public float getTamper_2() {
        return tamper_2;
    }

    public float getVoltage() {
        return voltage;
    }

    public float getUptime() {
        return uptime;
    }

    public float getTrip_counter() {
        return trip_counter;
    }

    public float getTamper_1() {
        return tamper_1;
    }

    public float getCan_odo_km() {
        return can_odo_km;
    }

    public float getCan_in_sleep() {
        return can_in_sleep;
    }

    public float getGps_inview() {
        return gps_inview;
    }

    public float getMess_count_1() {
        return mess_count_1;
    }

    public float getTime() {
        return time;
    }

    public float getEngine_is_on() {
        return engine_is_on;
    }
}