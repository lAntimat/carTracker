package com.tracker.lantimat.cartracker.mapActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.tracker.lantimat.cartracker.mapActivity.API.ApiUtils;
import com.tracker.lantimat.cartracker.mapActivity.API.CarsR;
import com.tracker.lantimat.cartracker.mapActivity.API.SOService;
import com.tracker.lantimat.cartracker.mapActivity.API.State;
import com.tracker.lantimat.cartracker.mapActivity.API.TrackR;
import com.tracker.lantimat.cartracker.mapActivity.models.CarState;
import com.tracker.lantimat.cartracker.mapActivity.models.Cars;
import com.tracker.lantimat.cartracker.mapActivity.models.Mode;
import com.tracker.lantimat.cartracker.mapActivity.models.Track;
import com.tracker.lantimat.cartracker.mapActivity.models.TrackInfo;
import com.tracker.lantimat.cartracker.mapActivity.models.User;
import com.tracker.lantimat.cartracker.utils.DayUtil;
import com.tracker.lantimat.cartracker.utils.FbConstants;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by GabdrakhmanovII on 20.10.2017.
 */

public class MapPresenter {

    final static String TAG = "MapPresenter";

    private Disposable disposable;
    private Observable observable;


    private MapView mapView;
    private Context context;
    private BottomSheetsTrack bottomSheetsTrack;

    private ArrayList<TrackR> arTrack = new ArrayList<>();
    private ArrayList<Cars> arCars = new ArrayList<Cars>();
    private ArrayList<CarsR> arCarsR = new ArrayList<>();

    private int carSelectedPosition = -1;

    private User user;

    Mode mode = Mode.NORMAL;

    SOService mService;

    public MapPresenter() {
        mService = ApiUtils.getSOService();
    }

    public void attachView(MapView mapView, BottomSheetsTrack bottomSheetsTrack) {
        this.bottomSheetsTrack = bottomSheetsTrack;
        this.mapView = mapView;
    }

    public void detachView() {
        stopDateUpdate();
        this.mapView = null;
    }

    void stopDateUpdate() {
        if (disposable != null && !disposable.isDisposed()) {
            System.out.println("stopped");
            disposable.dispose();
        }
    }

    private void setMode(Mode mode) {
        this.mode = mode;
        mapView.onModeChange(mode);

    }


    public void getTrack(String id, long begin, long end) {
        if (carSelectedPosition == -1) return;
        setMode(Mode.TRACK_LOADING);

        begin = 1513677600000L;
        end = 1513688400000L;
        mService.getTrack(arCarsR.get(carSelectedPosition).get_id(), begin, end)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<TrackR>>() {
                    @Override
                    public void onNext(ArrayList<TrackR> trackRS) {
                        Log.d(TAG, "onComplete");

                        clearPath();
                        ArrayList<Track> arMarkedTrack = new ArrayList<Track>();
                        ArrayList<Track> arTrackTest = new ArrayList<Track>();

                        Double trackLength = 0D;
                        long trackTime = 0;
                        Double averageSpeed = 0D;

                        arTrack.addAll(trackRS); //Добавляем координаты в массив
                        Log.d(TAG, "array size " + arTrack.size());

                        //Вычисляем пройденный путь за трек
                        if (arTrack.size() > 0) {
                            //trackLength += distanceBetween(arTrack.get(arTrack.size() - 1).getState().getLat(), track.getGeoPoint()); //Расстояние между двумя точками
                            //averageSpeed += track.getSpeed();
                            Log.d(TAG, "trackLength " + trackLength);

                        }

                        //if (track.getSpeed() * 3.6 > 90) {
                        //  mapView.addMarker(arTrack.size() - 1, track); //Добавляю трек в короткий массив, с позицией в полном массиве
                        //}

                        //averageSpeed = averageSpeed/arTrack.size();

                        //trackTime = arTrack.get(arTrack.size() - 1).getTimestamp().getTime() - arTrack.get(0).getTimestamp().getTime();

                        TrackInfo trackInfo = new TrackInfo(trackLength, averageSpeed, trackTime);
                        mapView.showTrackInfo(trackInfo);

                        mapView.showPath(arTrack);
                        bottomSheetsTrack.setSeekBarMax(arTrack.size());
                        mapView.showTracksInFragment(arTrack);
                        mapView.showTrackLengthInfo(String.valueOf(trackLength));
                        Log.d(TAG, "track Length " + trackLength);
                        setMode(Mode.TRACK);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    public void startGetObjectSchedule() {
        //ArrayList<CarsR> carsR = new ArrayList<>();
        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        disposable = Observable.interval(5, TimeUnit.SECONDS)
                .flatMap(n ->
                        mService.getObjects()
                                .retry(3)
                                .subscribeOn(scheduler))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(carsRS -> {
                    //Log.d(TAG, "RxJava work ohuet`");
                    arCarsR.clear();
                    arCarsR.addAll(carsRS);
                    if(mapView!= null) {
                        mapView.showCars(arCarsR, carSelectedPosition); //Отображаем машины на карте
                        if (carSelectedPosition != -1)
                            mapView.showCarInfo(arCarsR.get(carSelectedPosition), stateFromApiToCarState(arCarsR.get(carSelectedPosition).getState())); //отображаем информацию о выделенной машине
                    }
                });
    }

    public ArrayList<CarState> stateFromApiToCarState(State state) {
        ArrayList<CarState> ar = new ArrayList<>();
        ar.add(new CarState("Угол", String.valueOf(state.getAngle())));
        ar.add(new CarState("Скорость", String.valueOf(state.getSpeed())));
        ar.add(new CarState("Напряжение батареи", String.valueOf(state.getBatVoltage())));
        ar.add(new CarState("Уровень топлива", String.valueOf(state.getDriver_message())));
        return ar;
    }
    public ArrayList<CarState> trackFromApiToCarState(TrackR state) {
        ArrayList<CarState> ar = new ArrayList<>();
        ar.add(new CarState("Угол", String.valueOf(state.getAngle())));
        ar.add(new CarState("Скорость", String.valueOf(state.getSpeed())));
        ar.add(new CarState("Напряжение батареи", String.valueOf(state.getBatVoltage())));
        ar.add(new CarState("Уровень топлива", String.valueOf(state.getDriver_message())));
        return ar;
    }

    public void loadUserInfo(int id) {
        mapView.showUserInfoLoading();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(FbConstants.USERS)
                .whereEqualTo("id", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                user = document.toObject(User.class);
                            }
                            mapView.showUserInfo(user);
                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                        }
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

    public long timeBetween(Date date, Date date2) {
        return date2.getTime() - date.getTime();
    }


    public void makePath(ArrayList<TrackR> ar) {

        mapView.showPath(ar);

    }

    private void clearPath() {
        if (arTrack != null) arTrack.clear();
        mapView.clearPath();
    }

    public void trackMarkerClick(int position) {
        mapView.setBsDateSpeed(new Date((long) arTrack.get(position).getTime()), arTrack.get(position).getSpeed(), position);
    }

    public void carMarkerClick(int position) {
        showCarInfo(position);
    }

    public void setCar(int position) { //выбираем машину, и она становитсья другого цвета + карта центрируется на ней
        showCarInfo(position);
        mapView.showCars(arCarsR, carSelectedPosition); //Обновляем машины, чтобы цвет сменился
        //Центрируем карту
        mapView.setCenter(new GeoPoint(arCarsR.get(carSelectedPosition).getState().getLat(),
                arCarsR.get(carSelectedPosition).getState().getLon()));

    }


    private void showCarInfo(int position) { //выбираем машину, и она становитсья другого цвета, но карта не центрируется на ней
        carSelectedPosition = position;
        if(carSelectedPosition == -1) carSelectedPosition = arCarsR.size();
        if(carSelectedPosition == arCarsR.size()) carSelectedPosition = 0;
        mapView.showCarInfo(arCarsR.get(carSelectedPosition), stateFromApiToCarState(arCarsR.get(carSelectedPosition).getState()));
        //loadUserInfo(arCarsR.get(position).getDriverId());
        mapView.showUserInfo(new User(0,0, "", arCarsR.get(carSelectedPosition).getName(), arCarsR.get(carSelectedPosition).getType(), ""));
        setMode(Mode.CAR_SELECTED);
    }

    public void showCars() {
        mapView.showCars(arCarsR, carSelectedPosition);
    }

    public void showCarsListFragment() {
        mapView.showCarsListFragment(arCarsR, carSelectedPosition);
    }

    public void hideCarsListFragment() {
        mapView.hideCarsListFragment();
    }

    public void showDateTimeFragment() {
        mapView.showDateTimeFragment();
    }

    public void hideDateTimeFragment() {
        mapView.hideDateTimeFragment();
    }

    public void nextCar() {  //при нажатии кнопки выбора след машины
        if (carSelectedPosition != -1) { //Если выбрана какая нибудь машина
            carSelectedPosition++;
            if (carSelectedPosition == arCars.size()) carSelectedPosition = 0; //если конец массива, обнуляем
        }

        setCar(carSelectedPosition);

    }

    public void prevCar() {
        if (carSelectedPosition != -1) {
            carSelectedPosition--;
            if (carSelectedPosition == -1) carSelectedPosition = arCars.size() - 1;
        }
        setCar(carSelectedPosition);
    }

    public void trackSeekBar(int position) {
        if (position != -1 & arTrack.size() > position) {
            mapView.setBsDateSpeed(new Date((long) arTrack.get(position).getTime()), arTrack.get(position).getSpeed(), position);
            mapView.showCarInfoInTrack(trackFromApiToCarState(arTrack.get(position)));

            mapView.showTrackingCarPosition(arTrack.get(position));
            //Log.d(TAG, arTrack.get(position).getGeoPoint().toString());
        }
    }

    public void onBackPressed() { //Обработка клика назад
        switch (mode) {
            case TRACK: //Если режим показа трека, то
                clearPath(); //чистим карту от трека
                if (carSelectedPosition != -1) {
                    setMode(Mode.CAR_SELECTED); //если маркер с машиной кликнут, то переходим в режим просмотра машины
                    mapView.showCars(arCarsR, carSelectedPosition);
                } else setMode(Mode.NORMAL); //иначе в нормальный режим
                break;
            case CAR_SELECTED: //Если режим клик по маркеру
                carSelectedPosition = -1;
                mapView.showCars(arCarsR, carSelectedPosition);
                setMode(Mode.NORMAL);
                break;
        }
    }

}
