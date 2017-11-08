package com.tracker.lantimat.cartracker.reportActivity;

import android.graphics.Bitmap;

/**
 * Created by GabdrakhmanovII on 03.11.2017.
 */

public class Report {
    String title;
    String msg;
    Bitmap img;

    public Report(String title, String msg) {
        this.title = title;
        this.msg = msg;
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

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
