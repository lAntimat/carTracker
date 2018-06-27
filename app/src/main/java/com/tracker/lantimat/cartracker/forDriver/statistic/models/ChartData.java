package com.tracker.lantimat.cartracker.forDriver.statistic.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class ChartData implements Parcelable {
    public Date date;
    public Long value;

    public ChartData(Date date, Long value) {
        this.date = date;
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeValue(this.value);
    }

    protected ChartData(Parcel in) {
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.value = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<ChartData> CREATOR = new Parcelable.Creator<ChartData>() {
        @Override
        public ChartData createFromParcel(Parcel source) {
            return new ChartData(source);
        }

        @Override
        public ChartData[] newArray(int size) {
            return new ChartData[size];
        }
    };
}
