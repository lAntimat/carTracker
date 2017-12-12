package com.tracker.lantimat.cartracker.mapActivity.API;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Models implements Parcelable{
  @SerializedName("id")
  @Expose
  private String id;
  public void setId(String id){
   this.id=id;
  }
  public String getId(){
   return id;
  }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
    }

    public Models() {
    }

    protected Models(Parcel in) {
        this.id = in.readString();
    }

    public static final Creator<Models> CREATOR = new Creator<Models>() {
        @Override
        public Models createFromParcel(Parcel source) {
            return new Models(source);
        }

        @Override
        public Models[] newArray(int size) {
            return new Models[size];
        }
    };
}