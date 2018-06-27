package com.tracker.lantimat.cartracker.mapActivity.API;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class CarsR{
  @SerializedName("regNumber")
  @Expose
  private Integer regNumber;
  @SerializedName("color")
  @Expose
  private String color;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("description")
  @Expose
  private Long description;
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
  public void setRegNumber(Integer regNumber){
   this.regNumber=regNumber;
  }
  public Integer getRegNumber(){
   return regNumber;
  }
  public void setColor(String color){
   this.color=color;
  }
  public String getColor(){
   return color;
  }
  public void setName(String name){
   this.name=name;
  }
  public String getName(){
   return name;
  }
  public void setDescription(Long description){
   this.description=description;
  }
  public Long getDescription(){
   return description;
  }
  public void setVehicleModel(String vehicleModel){
   this.vehicleModel=vehicleModel;
  }
  public String getVehicleModel(){
   return vehicleModel;
  }
  public void setModel(String model){
   this.model=model;
  }
  public String getModel(){
   return model;
  }
  public void setVin(String vin){
   this.vin=vin;
  }
  public String getVin(){
   return vin;
  }
  public void set_id(String _id){
   this._id=_id;
  }
  public String get_id(){
   return _id;
  }
  public void setId(String id){
   this.id=id;
  }
  public String getId(){
   return id;
  }
  public void setState(State state){
   this.state=state;
  }
  public State getState(){
   return state;
  }
  public void setStatus(String status){
   this.status=status;
  }
  public String getStatus(){
   return status;
  }
}