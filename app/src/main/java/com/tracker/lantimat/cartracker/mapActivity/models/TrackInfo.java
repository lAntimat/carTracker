package com.tracker.lantimat.cartracker.mapActivity.models;

import java.util.concurrent.TimeUnit;

/**
 * Created by GabdrakhmanovII on 08.11.2017.
 */

//Полноя информация о полученном треке
public class TrackInfo {
    double trackLength;
    double averageSpeed;
    long time;

    public TrackInfo(Double trackLength, Double averageSpeed, long time) {
        this.trackLength = trackLength;
        this.averageSpeed = averageSpeed;
        this.time = time;
    }

    public double getTrackLength() {
        return trackLength;
    }

    public void setTrackLength(double trackLength) {
        this.trackLength = trackLength;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public String getTime() {
        String driveTimeStr = String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(time),
                TimeUnit.MILLISECONDS.toSeconds(time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
        );
        return driveTimeStr;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
