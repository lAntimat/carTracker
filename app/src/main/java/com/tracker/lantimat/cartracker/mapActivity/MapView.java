package com.tracker.lantimat.cartracker.mapActivity;

import com.tracker.lantimat.cartracker.mapActivity.models.Cars;
import com.tracker.lantimat.cartracker.mapActivity.models.Mode;
import com.tracker.lantimat.cartracker.mapActivity.models.Track;
import com.tracker.lantimat.cartracker.mapActivity.models.User;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by GabdrakhmanovII on 20.10.2017.
 */

public interface MapView {

    void showTrack(Date date);

    void showPath(ArrayList<Track> ar);

    void clearPath();

    void addMarker(int position, Track track);

    void showTracksInFragment(ArrayList<Track> tracks);

    void setBsDateSpeed(Date time, Double speed, int position);

    void showTrackLengthInfo(String trackLength);

    void showTrackingCarPosition(Track track);

    void showCarInfo(Cars car);

    void showCarInfoInTrack(Track track);

    void showUserInfoLoading();

    void showUserInfo(User user);

    void showCars(ArrayList<Cars> ar, int selectedPosition);

    void showTrackInfo();

    void onModeChange(Mode mode);

}
