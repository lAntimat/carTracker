package com.tracker.lantimat.cartracker.mapActivity;

import android.app.DatePickerDialog;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tracker.lantimat.cartracker.Constants;
import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.mapActivity.API.ApiUtils;
import com.tracker.lantimat.cartracker.mapActivity.API.CarsR;
import com.tracker.lantimat.cartracker.mapActivity.API.SOService;
import com.tracker.lantimat.cartracker.mapActivity.fragments.CarInfoFragment;
import com.tracker.lantimat.cartracker.mapActivity.fragments.CarInfoInTrackFragment;
import com.tracker.lantimat.cartracker.mapActivity.fragments.CarsListFragment;
import com.tracker.lantimat.cartracker.mapActivity.fragments.MapFragment;
import com.tracker.lantimat.cartracker.mapActivity.fragments.TrackFragment;
import com.tracker.lantimat.cartracker.mapActivity.fragments.TrackInfoFragment;
import com.tracker.lantimat.cartracker.mapActivity.fragments.UserInfoFragment;
import com.tracker.lantimat.cartracker.mapActivity.models.Cars;
import com.tracker.lantimat.cartracker.mapActivity.models.Mode;
import com.tracker.lantimat.cartracker.mapActivity.models.Track;
import com.tracker.lantimat.cartracker.mapActivity.models.TrackInfo;
import com.tracker.lantimat.cartracker.mapActivity.models.User;
import com.tracker.lantimat.cartracker.quantor.pack.response;
import com.tracker.lantimat.cartracker.utils.SharedPreferenceHelper;

import org.osmdroid.util.GeoPoint;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapActivity extends AppCompatActivity implements MapView {

    static final String TAG = "MapActivity";
    static final int PAGE_COUNT = 2;

    Toolbar toolbar;
    MapFragment mapFragment;

    ViewPager pagerTrackBs;
    ViewPager pagerCarInfoBs;
    PagerAdapter pagerAdapterCar;
    PagerAdapter pagerAdapterTrack;

    BottomSheetBehavior bottomSheetBehavior;
    BottomSheetsTrack bottomSheetsTrack;
    BottomSheetsCar bottomSheetsCar;

    Calendar dateAndTime = Calendar.getInstance();

    public MapPresenter mapPresenter;

    Mode mode = Mode.NORMAL;
    int lastState = -1;

    private List<MapFragmentUpdateListener> mListeners;

    MapFragmentUpdateListener mapFragmentUpdateListener;
    TrackFragmentListener trackFragmentListener;
    CarInfoFragmentListener carInfoFragmentListener;
    CarInfoInTrackFragmentListener carInfoInTrackFragmentListener;
    UserInfoFragmentListener userInfoFragmentListener;
    TrackInfoFragmentListener trackInfoFragmentListener;


    public interface MapFragmentUpdateListener {
        void onDataUpdate();

        void showPath(ArrayList<Track> ar);

        void clearPath();

        void addMarker(int position, Track track, String title, String subtitle);

        void showTrackingCarPositionMarker(Track track);

        void showCars(ArrayList<CarsR> cars, int selectedPosition);

        void onBottomSheetsStateChange(int state);

        void setCenter(GeoPoint geoPoint);
    }
    public interface TrackFragmentListener {
        void addTracks(ArrayList<Track> tracks);
    }
    public interface CarInfoFragmentListener{
        void addDate(CarsR car);
    }
    public interface CarInfoInTrackFragmentListener{
        void addDate(Track track);
    }
    public interface UserInfoFragmentListener{
        void addDate(User user);
        void showLoading();
    }
    public interface TrackInfoFragmentListener{
        void addDate(TrackInfo trackInfo);
        void showLoading();
    }

    public synchronized void registerDataUpdateListener(MapFragmentUpdateListener listener) {
        //mListeners.add(listener);
        mapFragmentUpdateListener = listener;
    }
    public synchronized void registerTrackFragmentListener(TrackFragmentListener listener) {
        //mListeners.add(listener);
        trackFragmentListener = listener;
    }
    public synchronized void registerCarInfoFragmentListener(CarInfoFragmentListener listener) {
        carInfoFragmentListener = listener;
    }
    public synchronized void registerCarInfoInTrackFragmentListener(CarInfoInTrackFragmentListener listener) {
        carInfoInTrackFragmentListener = listener;
    }
    public synchronized void registerUserInfoFragmentListener(UserInfoFragmentListener listener) {
        userInfoFragmentListener = listener;
    }
    public synchronized void registerTrackInfoFragmentListener(TrackInfoFragmentListener listener) {
        trackInfoFragmentListener = listener;
    }

    public synchronized void unregisterDataUpdateListener(MapFragmentUpdateListener listener) {
        //mListeners.remove(listener);
    }

    public synchronized void dataUpdated() {
        for (MapFragmentUpdateListener listener : mListeners) {
            listener.onDataUpdate();
        }
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            dateAndTime.set(Calendar.YEAR, i);
            dateAndTime.set(Calendar.MONTH, i1);
            dateAndTime.set(Calendar.DAY_OF_MONTH, i2);
            Date date = new Date(dateAndTime.getTimeInMillis());
            mapPresenter.loadTrack(date);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();

        setContentView(R.layout.activity_map);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomSheetsTrack = new BottomSheetsTrack(this);
        bottomSheetsCar = new BottomSheetsCar(this);

        mapPresenter = new MapPresenter(this, bottomSheetsTrack);
        //mapPresenter.loadCars();



        SOService mService;
        mService = ApiUtils.getSOService();
        Call<Gson> call = mService.login("kamaz-api", "Aa79372996433");
        call.enqueue(new Callback<Gson>() {
            @Override
            public void onResponse(Call<Gson> call, Response<Gson> response) {
                Log.d(TAG, "AuthSuccess " + response.toString());
                mapPresenter.getObjects();
            }

            @Override
            public void onFailure(Call<Gson> call, Throwable t) {
                Log.d(TAG, "AuthFail ");

            }
        });
        initBottomSheets();
        initPagerAdapter();
        initFragment();
    }

    public void onResume() {
        super.onResume();
    }

    private void initFragment() {
        mapFragment = new MapFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.map_activity_content_frame, mapFragment, Constants.MAP_FRAGMENT);
        ft.commit();
    }

    private void initBottomSheets() {
        // get the bottom sheet view
        /*LinearLayout llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        // init the bottom sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);*/


        bottomSheetsCar.bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(mode == Mode.CAR_SELECTED & newState == BottomSheetBehavior.STATE_HIDDEN) mapPresenter.onBackPressed(); //Если bs смахнули рукой в режиме выбранного авто
                if(newState == BottomSheetBehavior.STATE_EXPANDED & lastState == -1) {
                    mapFragmentUpdateListener.onBottomSheetsStateChange(newState);
                    lastState = newState;
                } else if(newState == BottomSheetBehavior.STATE_EXPANDED & lastState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mapFragmentUpdateListener.onBottomSheetsStateChange(newState);
                    lastState = newState;
                } else if(newState == BottomSheetBehavior.STATE_COLLAPSED & lastState == BottomSheetBehavior.STATE_EXPANDED) {
                    mapFragmentUpdateListener.onBottomSheetsStateChange(newState);
                    lastState = newState;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        bottomSheetsTrack.bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_EXPANDED & lastState == -1) {
                    mapFragmentUpdateListener.onBottomSheetsStateChange(newState);
                    lastState = newState;
                } else if(newState == BottomSheetBehavior.STATE_EXPANDED & lastState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mapFragmentUpdateListener.onBottomSheetsStateChange(newState);
                    lastState = newState;
                } else if(newState == BottomSheetBehavior.STATE_COLLAPSED & lastState == BottomSheetBehavior.STATE_EXPANDED) {
                    mapFragmentUpdateListener.onBottomSheetsStateChange(newState);
                    lastState = newState;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }

    private void initPagerAdapter() {
        //Инициализация ViewPager
        pagerTrackBs = (ViewPager) findViewById(R.id.pager_track);
        pagerAdapterTrack = new TrackBsheetsPagerAdapter(getSupportFragmentManager());
        pagerTrackBs.setAdapter(pagerAdapterTrack);
        pagerTrackBs.setOffscreenPageLimit(3);

        pagerCarInfoBs = (ViewPager) findViewById(R.id.pager_car);
        pagerAdapterCar = new CarInfoBsheetsPagerAdapter(getSupportFragmentManager());
        pagerCarInfoBs.setAdapter(pagerAdapterCar);
        pagerCarInfoBs.setOffscreenPageLimit(3);

    }

    public void showFragment(View view) {
       mapPresenter.showCarsList();
    }

    public void btnClick2(View view) {
        mapPresenter.loadCars();
    }

    private void showPopupMenu(View v, final int locationIsTo) {
        PopupMenu popupMenu = new PopupMenu(this, v, Gravity.CENTER);
        popupMenu.inflate(R.menu.popupmenu); // Для Android 4.0

        setForceShowIcon(popupMenu);
        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = null;
                        switch (item.getItemId()) {  //Мое местоположение

                            case R.id.menu1:

                                return true;
                            case R.id.menu2:
                                Toast.makeText(getApplicationContext(),
                                        "Избранное в разработке",
                                        Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.menu3:

                                return true;
                            case R.id.menu4:

                                return true;
                            default:
                                return false;
                        }
                    }
                });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {

            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(getApplicationContext(), "onDismiss",
                        Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }

    public static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(MapActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }


    @Override
    public void showTrack(Date date) {

    }

    @Override
    public void clearPath() {
        mapFragmentUpdateListener.clearPath();
    }

    @Override
    public void addMarker(int position, Track track) {
        SimpleDateFormat sf = new SimpleDateFormat("hh:mm");
        String formattedDate = sf.format(track.getTimestamp());
        String subTitle = "Скорость " + track.getSpeed() * 3.6;

        mapFragmentUpdateListener.addMarker(position, track, formattedDate, subTitle);
    }

    @Override
    public void showTracksInFragment(ArrayList<Track> tracks) {
        trackFragmentListener.addTracks(tracks);
    }

    @Override
    public void setBsDateSpeed(Date time, Double speed, int position) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

        bottomSheetsTrack.setSeekBarProgress(position);
        bottomSheetsTrack.setTime(df.format(time));
        bottomSheetsTrack.setSpeed(speed);
    }


    @Override
    public void showTrackLengthInfo(String trackLength) {

    }

    @Override
    public void showTrackingCarPosition(Track track) {
        mapFragmentUpdateListener.showTrackingCarPositionMarker(track);
    }

    @Override
    public void showCarInfo(CarsR car) {
        bottomSheetsCar.setCarName(car.getName());
        bottomSheetsCar.setCarNumber(car.getType());
        bottomSheetsCar.setDate(new Date((long) car.getState().getTime()));
        bottomSheetsCar.setSpeed(String.valueOf(car.getState().getSpeed()));

        carInfoFragmentListener.addDate(car); //Показываем подробную инфу во фрагменте
    }

    @Override
    public void showCarInfoInTrack(Track track) {
        carInfoInTrackFragmentListener.addDate(track); //Показываем подробную инфу во фрагменте
    }

    @Override
    public void showUserInfoLoading() {
        userInfoFragmentListener.showLoading();
    }

    @Override
    public void showUserInfo(User user) {
        userInfoFragmentListener.addDate(user);

    }

    @Override
    public void showTrackInfo(TrackInfo trackInfo) {
        trackInfoFragmentListener.addDate(trackInfo);
    }

    @Override
    public void showCars(ArrayList<CarsR> ar, int selectedPosition) {
        mapFragmentUpdateListener.showCars(ar, selectedPosition);
    }

    @Override
    public void setCenter(GeoPoint geoPoint) {
        mapFragmentUpdateListener.setCenter(geoPoint);
    }

    @Override
    public void showCarsListFragment(ArrayList<CarsR> ar, int selectedPosition) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("1" , ar);/**/

        CarsListFragment carsListFragment = CarsListFragment.newInstance(ar);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .add(R.id.map_activity_content_with_toolbar_frame, carsListFragment)
                .addToBackStack("list")
                .commit();

        //Log.d(TAG, "showCarsListFragment");
    }

    @Override
    public void hideCarsListFragment() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() != 0) {
            manager.popBackStack();
        }
    }

    @Override
    public void showPath(ArrayList<Track> ar) {
        mapFragmentUpdateListener.showPath(ar);
    }

    @Override
    public void onModeChange(Mode mode) {
        this.mode = mode;
        switch (mode) {
            case NORMAL: //если это обычный режим
                bottomSheetsTrack.setState(BottomSheetBehavior.STATE_HIDDEN);
                bottomSheetsCar.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case CAR_SELECTED:
                if (bottomSheetsCar.bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_HIDDEN) //Переводим в состояние Collapse, только если BS был скрыт
                bottomSheetsCar.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetsTrack.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case TRACK_LOADING:
                bottomSheetsCar.setState(BottomSheetBehavior.STATE_HIDDEN);
                bottomSheetsTrack.showProgress();
                bottomSheetsTrack.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
            case TRACK:
                bottomSheetsTrack.hideProgress();
                bottomSheetsTrack.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
         if (manager.getBackStackEntryCount() != 0) {
            manager.popBackStack();
        } else if (mode == Mode.NORMAL) super.onBackPressed();
        else mapPresenter.onBackPressed();
    }



    private class TrackBsheetsPagerAdapter extends FragmentPagerAdapter {

        public TrackBsheetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TrackInfoFragment();
                case 1:
                    return new TrackFragment();
                case 2:
                    return new CarInfoInTrackFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }
    private class CarInfoBsheetsPagerAdapter extends FragmentPagerAdapter {

        public CarInfoBsheetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new CarInfoFragment();
                case 1:
                    return new UserInfoFragment();

            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

    }

}
