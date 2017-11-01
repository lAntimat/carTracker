package com.tracker.lantimat.cartracker.mapActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.mapActivity.models.Cars;
import com.tracker.lantimat.cartracker.mapActivity.models.Mode;
import com.tracker.lantimat.cartracker.mapActivity.models.Track;
import com.tracker.lantimat.cartracker.utils.DayUtil;
import com.tracker.lantimat.cartracker.utils.FbConstants;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by GabdrakhmanovII on 20.10.2017.
 */

public class MapPresenter {

    final static String TAG = "MapPresenter";

    private final MapView mapView;
    private Context context;
    private BottomSheetsTrack bottomSheetsTrack;

    private ArrayList<Track> arTrack = new ArrayList<Track>();
    private ArrayList<Cars> arCars = new ArrayList<Cars>();

    private int carSelectedPosition = -1;

    Mode mode = Mode.NORMAL;


    public MapPresenter(MapView mapView, BottomSheetsTrack bottomSheetsTrack) {
        this.mapView = mapView;
        this.bottomSheetsTrack = bottomSheetsTrack;
    }

    public MapView getView() {
        return mapView;
    }

    public void init() {

    }

    public void loadTrack(Date date) {
        //Date date = new Date();
        //date.setTime(1508986177000L);

        if (carSelectedPosition == -1) return;

        setMode(Mode.TRACK_LOADING);

        DayUtil dayUtil = new DayUtil();
        dayUtil.getStartOfDayInMillis(date);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(FbConstants.TRACKS)
                .whereEqualTo("carId", arCars.get(carSelectedPosition).getId())
                .whereGreaterThanOrEqualTo("timestamp", dayUtil.getStartOfDayInMillis(date))
                .whereLessThan("timestamp", dayUtil.getEndOfDayInMillis(date))
                .orderBy("timestamp")
                //.limit(100)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            clearPath();

                            ArrayList<Track> arMarkedTrack = new ArrayList<Track>();
                            Double trackLength = 0D;
                            Double trackTime = 0D;
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Track track = document.toObject(Track.class);

                                //if()

                                //Вычисляем пройденный путь за трек
                                if (arTrack.size() > 0) {
                                    trackLength += distanceBetween(arTrack.get(arTrack.size() - 1).getGeoPoint(), track.getGeoPoint()); //Расстояние между двумя точками
                                }

                                //Добавляем координаты в массив
                                arTrack.add(track);


                                if (track.getSpeed() * 3.6 > 90) {
                                    mapView.addMarker(arTrack.size() - 1, track); //Добавляю трек в короткий массив, с позицией в полном массиве
                                    arMarkedTrack.add(track);
                                }
                            }

                            mapView.showPath(arTrack);
                            bottomSheetsTrack.setSeekBarMax(arTrack.size());
                            mapView.showTracksInFragment(arTrack);
                            mapView.showTrackLengthInfo(String.valueOf(trackLength));
                            Log.d(TAG, "track Length " + trackLength);
                            setMode(Mode.TRACK);


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void setMode(Mode mode) {
        this.mode = mode;
        mapView.onModeChange(mode);

    }

    public void loadCars() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

/*        db.collection(FbConstants.CARS)
                .orderBy("id")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            arCars.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Cars cars = document.toObject(Cars.class);
                                arCars.add(cars);
                            }

                            mapView.showCars(arCars, carSelectedPosition);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });*/

        db.collection(FbConstants.CARS)
                .orderBy("id")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        arCars.clear();
                        for (DocumentSnapshot document : documentSnapshots) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            Cars cars = document.toObject(Cars.class);
                            arCars.add(cars);
                        }

                        mapView.showCars(arCars, carSelectedPosition); //Отображаем машины на карте
                        if(carSelectedPosition!=-1) mapView.showCarInfo(arCars.get(carSelectedPosition)); //отображаем информацию о выделенной машине
                    }
                });
    }

    public double distanceBetween(com.google.firebase.firestore.GeoPoint geoPoint, com.google.firebase.firestore.GeoPoint geoPoint2) {
        double lon, lat, lon2, lat2;
        lat = geoPoint.getLatitude();
        lon = geoPoint.getLongitude();
        lat2 = geoPoint2.getLatitude();
        lon2 = geoPoint2.getLongitude();
        return 111.2 * Math.sqrt((lon - lon2) * (lon - lon2) + (lat - lat2) * Math.cos(Math.PI * lon / 180) * (lat - lat2) * Math.cos(Math.PI * lon / 180));
    }

    public void makePath(ArrayList<Track> ar) {

        mapView.showPath(ar);

    }

    private void clearPath() {
        if (arTrack != null) arTrack.clear();
        mapView.clearPath();
    }

    public void trackMarkerClick(int position) {
        mapView.setBsDateSpeed(arTrack.get(position).getTimestamp(), arTrack.get(position).getSpeed(), position);
    }

    public void carMarkerClick(int position) {
        mapView.showCarInfo(arCars.get(position));
        carSelectedPosition = position;
        setMode(Mode.CAR_SELECTED);

    }



    public void trackSeekBar(int position) {
        if (position != -1 & arTrack.size() > position) {
            mapView.setBsDateSpeed(arTrack.get(position).getTimestamp(), arTrack.get(position).getSpeed(), position);
            mapView.showTrackingCarPosition(arTrack.get(position));
        }
    }

    public void onBackPressed() { //Обработка клика назад
        switch (mode) {
            case TRACK: //Если режим показа трека, то
                clearPath(); //чистим карту от трека
                if (carSelectedPosition != -1) {
                    setMode(Mode.CAR_SELECTED); //если маркер с машиной кликнут, то переходим в режим просмотра машины
                    mapView.showCars(arCars, carSelectedPosition);
                }
                else setMode(Mode.NORMAL); //иначе в нормальный режим
                break;
            case CAR_SELECTED: //Если режим клик по маркеру
                carSelectedPosition = -1;
                mapView.showCars(arCars, carSelectedPosition);
                setMode(Mode.NORMAL);
                break;
        }
    }

}
