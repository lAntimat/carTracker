package com.tracker.lantimat.cartracker.mapActivity.API;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class State{
  @SerializedName("cons_fuel_p")
  @Expose
  private Double cons_fuel_p;
  @SerializedName("hand_break")
  @Expose
  private Double hand_break;
  @SerializedName("cons_fuel_l")
  @Expose
  private Double cons_fuel_l;
  @SerializedName("int_temp")
  @Expose
  private Double int_temp;
  @SerializedName("dynamic_ign")
  @Expose
  private Integer dynamic_ign;
  @SerializedName("bat-voltage")
  @Expose
  private Double batVoltage;
  @SerializedName("egts")
  @Expose
  private Boolean egts;
  @SerializedName("oper_mode")
  @Expose
  private Integer oper_mode;
  @SerializedName("lac")
  @Expose
  private Integer lac;
  @SerializedName("gps_odometer")
  @Expose
  private Double gps_odometer;
  @SerializedName("ign")
  @Expose
  private Integer ign;
  @SerializedName("calculatedFuelLevel")
  @Expose
  private Double calculatedFuelLevel;
  @SerializedName("real-time")
  @Expose
  private Double realTime;
  @SerializedName("pedal_break")
  @Expose
  private Integer pedal_break;
  @SerializedName("eng_uptime")
  @Expose
  private Integer eng_uptime;
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("signal")
  @Expose
  private Integer signal;
  @SerializedName("lat")
  @Expose
  private Double lat;
  @SerializedName("cell_id")
  @Expose
  private Integer cell_id;
  @SerializedName("height")
  @Expose
  private Integer height;
  @SerializedName("mileage")
  @Expose
  private Double mileage;
  @SerializedName("_ts")
  @Expose
  private Double _ts;
  @SerializedName("accelerator")
  @Expose
  private Integer accelerator;
  @SerializedName("glonass_inview")
  @Expose
  private Integer glonass_inview;
  @SerializedName("_oid")
  @Expose
  private String _oid;
  @SerializedName("ext_voltage")
  @Expose
  private Double ext_voltage;
  @SerializedName("sat_used")
  @Expose
  private Integer sat_used;
  @SerializedName("fuel_lev_p")
  @Expose
  private Integer fuel_lev_p;
  @SerializedName("_id")
  @Expose
  private String _id;
  @SerializedName("sat-count")
  @Expose
  private Integer satCount;
  @SerializedName("fuel_lev_l")
  @Expose
  private Integer fuel_lev_l;
  @SerializedName("temp-inside")
  @Expose
  private Double tempInside;
  @SerializedName("driver_message")
  @Expose
  private String driver_message;
  @SerializedName("altitude")
  @Expose
  private Double altitude;
  @SerializedName("can_speed")
  @Expose
  private Integer can_speed;
  @SerializedName("ex_dig_out_1")
  @Expose
  private Boolean ex_dig_out_1;
  @SerializedName("eng_uptime_p")
  @Expose
  private Integer eng_uptime_p;
  @SerializedName("gsm_sig_level")
  @Expose
  private Integer gsm_sig_level;
  @SerializedName("ex_dig_out_5")
  @Expose
  private Boolean ex_dig_out_5;
  @SerializedName("lon")
  @Expose
  private Double lon;
  @SerializedName("ex_dig_out_3")
  @Expose
  private Boolean ex_dig_out_3;
  @SerializedName("ex_dig_out_2")
  @Expose
  private Boolean ex_dig_out_2;
  @SerializedName("glonass_sat")
  @Expose
  private Integer glonass_sat;
  @SerializedName("speed")
  @Expose
  private Integer speed;
  @SerializedName("ex_dig_out_6")
  @Expose
  private Boolean ex_dig_out_6;
  @SerializedName("mcu_fw")
  @Expose
  private String mcu_fw;
  @SerializedName("angle")
  @Expose
  private Integer angle;
  @SerializedName("ignition")
  @Expose
  private Integer ignition;
  @SerializedName("ex_dig_in_2")
  @Expose
  private Boolean ex_dig_in_2;
  @SerializedName("direction")
  @Expose
  private Double direction;
  @SerializedName("ex_dig_in_1")
  @Expose
  private Boolean ex_dig_in_1;
  @SerializedName("can_odo_p")
  @Expose
  private Double can_odo_p;
  @SerializedName("mnc")
  @Expose
  private Integer mnc;
  @SerializedName("acc_voltage")
  @Expose
  private Double acc_voltage;
  @SerializedName("eng_rpm")
  @Expose
  private Integer eng_rpm;
  @SerializedName("eng_temp")
  @Expose
  private Double eng_temp;
  @SerializedName("hdop")
  @Expose
  private Double hdop;
  @SerializedName("tamper_2")
  @Expose
  private Integer tamper_2;
  @SerializedName("voltage")
  @Expose
  private Double voltage;
  @SerializedName("uptime")
  @Expose
  private Integer uptime;
  @SerializedName("trip_counter")
  @Expose
  private Integer trip_counter;
  @SerializedName("tamper_1")
  @Expose
  private Integer tamper_1;
  @SerializedName("can_odo_km")
  @Expose
  private Double can_odo_km;
  @SerializedName("can_in_sleep")
  @Expose
  private Integer can_in_sleep;
  @SerializedName("gps_inview")
  @Expose
  private Integer gps_inview;
  @SerializedName("mess_count_1")
  @Expose
  private Integer mess_count_1;
  @SerializedName("time")
  @Expose
  private long time;
  @SerializedName("engine_is_on")
  @Expose
  private Integer engine_is_on;

  public Double getCons_fuel_p() {
    return cons_fuel_p;
  }

  public Double getHand_break() {
    return hand_break;
  }

  public Double getCons_fuel_l() {
    return cons_fuel_l;
  }

  public Double getInt_temp() {
    return int_temp;
  }

  public Integer getDynamic_ign() {
    return dynamic_ign;
  }

  public Double getBatVoltage() {
    return batVoltage;
  }

  public Boolean getEgts() {
    return egts;
  }

  public Integer getOper_mode() {
    return oper_mode;
  }

  public Integer getLac() {
    return lac;
  }

  public Double getGps_odometer() {
    return gps_odometer;
  }

  public Integer getIgn() {
    return ign;
  }

  public Double getCalculatedFuelLevel() {
    return calculatedFuelLevel;
  }

  public Double getRealTime() {
    return realTime;
  }

  public Integer getPedal_break() {
    return pedal_break;
  }

  public Integer getEng_uptime() {
    return eng_uptime;
  }

  public String getId() {
    return id;
  }

  public Integer getSignal() {
    return signal;
  }

  public Double getLat() {
    return lat;
  }

  public Integer getCell_id() {
    return cell_id;
  }

  public Integer getHeight() {
    return height;
  }

  public Double getMileage() {
    return mileage;
  }

  public Double get_ts() {
    return _ts;
  }

  public Integer getAccelerator() {
    return accelerator;
  }

  public Integer getGlonass_inview() {
    return glonass_inview;
  }

  public String get_oid() {
    return _oid;
  }

  public Double getExt_voltage() {
    return ext_voltage;
  }

  public Integer getSat_used() {
    return sat_used;
  }

  public Integer getFuel_lev_p() {
    return fuel_lev_p;
  }

  public String get_id() {
    return _id;
  }

  public Integer getSatCount() {
    return satCount;
  }

  public Integer getFuel_lev_l() {
    return fuel_lev_l;
  }

  public Double getTempInside() {
    return tempInside;
  }

  public String getDriver_message() {
    return driver_message;
  }

  public Double getAltitude() {
    return altitude;
  }

  public Integer getCan_speed() {
    return can_speed;
  }

  public Boolean getEx_dig_out_1() {
    return ex_dig_out_1;
  }

  public Integer getEng_uptime_p() {
    return eng_uptime_p;
  }

  public Integer getGsm_sig_level() {
    return gsm_sig_level;
  }

  public Boolean getEx_dig_out_5() {
    return ex_dig_out_5;
  }

  public Double getLon() {
    return lon;
  }

  public Boolean getEx_dig_out_3() {
    return ex_dig_out_3;
  }

  public Boolean getEx_dig_out_2() {
    return ex_dig_out_2;
  }

  public Integer getGlonass_sat() {
    return glonass_sat;
  }

  public Integer getSpeed() {
    return speed;
  }

  public Boolean getEx_dig_out_6() {
    return ex_dig_out_6;
  }

  public String getMcu_fw() {
    return mcu_fw;
  }

  public Integer getAngle() {
    return angle;
  }

  public Integer getIgnition() {
    return ignition;
  }

  public Boolean getEx_dig_in_2() {
    return ex_dig_in_2;
  }

  public Double getDirection() {
    return direction;
  }

  public Boolean getEx_dig_in_1() {
    return ex_dig_in_1;
  }

  public Double getCan_odo_p() {
    return can_odo_p;
  }

  public Integer getMnc() {
    return mnc;
  }

  public Double getAcc_voltage() {
    return acc_voltage;
  }

  public Integer getEng_rpm() {
    return eng_rpm;
  }

  public Double getEng_temp() {
    return eng_temp;
  }

  public Double getHdop() {
    return hdop;
  }

  public Integer getTamper_2() {
    return tamper_2;
  }

  public Double getVoltage() {
    return voltage;
  }

  public Integer getUptime() {
    return uptime;
  }

  public Integer getTrip_counter() {
    return trip_counter;
  }

  public Integer getTamper_1() {
    return tamper_1;
  }

  public Double getCan_odo_km() {
    return can_odo_km;
  }

  public Integer getCan_in_sleep() {
    return can_in_sleep;
  }

  public Integer getGps_inview() {
    return gps_inview;
  }

  public Integer getMess_count_1() {
    return mess_count_1;
  }

  public long getTime() {
    return time;
  }

  public Integer getEngine_is_on() {
    return engine_is_on;
  }
}