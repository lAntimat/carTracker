package com.tracker.lantimat.cartracker.reportActivity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.ServerTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by GabdrakhmanovII on 03.11.2017.
 */

public class Report implements Parcelable {
    String title;
    String msg;
    ArrayList<String> arUrl = new ArrayList<>();
    @ServerTimestamp
    Date timestamp;

    public Report() {
    }

    public Report(String title, String msg, Date timestamp) {
        this.title = title;
        this.msg = msg;
        this.timestamp = timestamp;
    }

    protected Report(Parcel in) {
        title = in.readString();
        msg = in.readString();
        arUrl = in.createStringArrayList();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getImg(int position) {
        if(arUrl.size()==0) return null;
        return arUrl.get(position);
    }

    public ArrayList<String> getArImg() {
        return arUrl;
    }

    public void setImg(ArrayList<String> ar) {
        this.arUrl.addAll(ar);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(msg);
        parcel.writeStringList(arUrl);
    }

    public static final Creator<Report> CREATOR = new Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel in) {
            return new Report(in);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
        }
    };
}
