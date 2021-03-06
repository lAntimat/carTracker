package com.tracker.lantimat.cartracker.reportActivity;

import android.graphics.Bitmap;

import com.google.firebase.firestore.ServerTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by GabdrakhmanovII on 03.11.2017.
 */

public class Report {
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

    public void setImg(ArrayList<String> ar) {
        this.arUrl.addAll(ar);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
