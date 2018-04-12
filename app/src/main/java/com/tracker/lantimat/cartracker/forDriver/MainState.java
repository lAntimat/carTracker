package com.tracker.lantimat.cartracker.forDriver;

import android.graphics.drawable.Drawable;

/**
 * Created by GabdrakhmanovII on 27.03.2018.
 */

public class MainState {
    String title;
    String text;
    int img;
    int percent = -1;

    public MainState(String title, String text, int img, int percent) {
        this.title = title;
        this.text = text;
        this.img = img;
        this.percent = percent;
    }

    public MainState(String title, String text, int img) {
        this.title = title;
        this.text = text;
        this.img = img;
    }

    public MainState(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
