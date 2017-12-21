package com.tracker.lantimat.cartracker.mapActivity;

import com.tracker.lantimat.cartracker.mapActivity.API.CarsR;
import com.tracker.lantimat.cartracker.mapActivity.API.TrackR;
import com.tracker.lantimat.cartracker.mapActivity.models.CarState;
import com.tracker.lantimat.cartracker.mapActivity.models.Cars;
import com.tracker.lantimat.cartracker.mapActivity.models.Mode;
import com.tracker.lantimat.cartracker.mapActivity.models.Track;
import com.tracker.lantimat.cartracker.mapActivity.models.TrackInfo;
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

    void showPath(ArrayList<TrackR> ar);

    void clearPath();

    void addMarker(int position, TrackR track);

    void showTracksInFragment(ArrayList<TrackR> tracks);

    void setBsDateSpeed(Date time, Double speed, int position);

    void showTrackLengthInfo(String trackLength);

    void showTrackingCarPosition(TrackR track);

    void showCarInfo(CarsR car, ArrayList<CarState> carState);

    void showCarInfoInTrack(ArrayList<CarState> track);

    void showUserInfoLoading();

    void showUserInfo(User user);

    void showTrackInfo(TrackInfo trackInfo);

    void showCars(ArrayList<CarsR> ar, int selectedPosition);

    void setCenter(GeoPoint geoPoint);

    void showCarsListFragment(ArrayList<CarsR> ar, int selectedPosition);

    void hideCarsListFragment();

    void showDateTimeFragment();

    void hideDateTimeFragment();

    void onModeChange(Mode mode);

    void showError(String error);

}
