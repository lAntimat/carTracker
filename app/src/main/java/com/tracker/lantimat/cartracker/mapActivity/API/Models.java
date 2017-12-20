package com.tracker.lantimat.cartracker.mapActivity.API;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Models{
  @SerializedName("id")
  @Expose
  private String id;
  public void setId(String id){
   this.id=id;
  }
  public String getId(){
   return id;
  }
}