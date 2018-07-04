package com.tracker.lantimat.cartracker.forDriver.statistic.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by GabdrakhmanovII on 27.03.2018.
 */


public class MiniStatisticChart implements Parcelable {

    public static final int DISTANCE = 1;
    public static final int DRIVE_TIME = 2;
    public static final int FUEL_CONSUMPTION = 3;

    public int type = -1;
    public String title;
    public ArrayList<ChartData> chart;

    public MiniStatisticChart(int type, String title, ArrayList<ChartData> chart) {
        this.type = type;
        this.title = title;
        this.chart = chart;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.title);
        dest.writeList(this.chart);
    }

    protected MiniStatisticChart(Parcel in) {
        this.type = in.readInt();
        this.title = in.readString();
        this.chart = new ArrayList<ChartData>();
        in.readList(this.chart, ChartData.class.getClassLoader());
    }

    public static final Parcelable.Creator<MiniStatisticChart> CREATOR = new Parcelable.Creator<MiniStatisticChart>() {
        @Override
        public MiniStatisticChart createFromParcel(Parcel source) {
            return new MiniStatisticChart(source);
        }

        @Override
        public MiniStatisticChart[] newArray(int size) {
            return new MiniStatisticChart[size];
        }
    };
}
