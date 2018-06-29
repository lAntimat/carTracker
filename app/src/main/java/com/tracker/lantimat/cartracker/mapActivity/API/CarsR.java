package com.tracker.lantimat.cartracker.mapActivity.API;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class CarsR{
  @SerializedName("regNumber")
  @Expose
  private String regNumber;
  @SerializedName("color")
  @Expose
  private String color;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("description")
  @Expose
  private String description;
  @SerializedName("vehicleModel")
  @Expose
  private String vehicleModel;
  @SerializedName("model")
  @Expose
  private String model;
  @SerializedName("vin")
  @Expose
  private String vin;
  @SerializedName("_id")
  @Expose
  private String _id;
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("state")
  @Expose
  private State state;
  @SerializedName("status")
  @Expose
  private String status;

    public String getRegNumber() {
        return regNumber;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

  public String getDescription() {
    return description;
  }

  public String getVehicleModel() {
        return vehicleModel;
    }

    public String getModel() {
        return model;
    }

    public String getVin() {
        return vin;
    }

    public String get_id() {
        return _id;
    }

    public String getId() {
        return id;
    }

    public State getState() {
        return state;
    }

    public String getStatus() {
        return status;
    }
}