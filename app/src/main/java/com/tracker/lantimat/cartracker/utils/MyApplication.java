package com.tracker.lantimat.cartracker.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by GabdrakhmanovII on 12.12.2017.
 */

public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
