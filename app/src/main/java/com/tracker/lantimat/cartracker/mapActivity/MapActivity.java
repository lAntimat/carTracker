package com.tracker.lantimat.cartracker.mapActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tracker.lantimat.cartracker.Constants;
import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.bottomSheetsTimeline.TrackFragment;
import com.tracker.lantimat.cartracker.mapActivity.models.Cars;
import com.tracker.lantimat.cartracker.mapActivity.models.Mode;
import com.tracker.lantimat.cartracker.mapActivity.models.Track;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Polyline;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MapActivity extends AppCompatActivity implements MapView {

    static final String TAG = "MapActivity";
    static final int PAGE_COUNT = 1;

    Toolbar toolbar;
    MapFragment mapFragment;

    ViewPager pager;
    PagerAdapter pagerAdapter;

    BottomSheetBehavior bottomSheetBehavior;
    BottomSheetsTrack bottomSheetsTrack;
    BottomSheetsCar bottomSheetsCar;

    Calendar dateAndTime = Calendar.getInstance();

    public MapPresenter mapPresenter;

    private List<MapFragmentUpdateListener> mListeners;

    MapFragmentUpdateListener mapFragmentUpdateListener;

    Mode mode = Mode.NORMAL;

    public interface MapFragmentUpdateListener {
        void onDataUpdate();

        void showPath(ArrayList<Track> ar);

        void clearPath();

        void addMarker(int position, Track track, String title, String subtitle);

        void showTrackingCarPositionMarker(Track track);

        void showCars(ArrayList<Cars> cars, int selectedPosition);
    }

    TrackFragmentListener trackFragmentListener;

    public interface TrackFragmentListener {
        void addTracks(ArrayList<Track> tracks);
    }

    public synchronized void registerDataUpdateListener(MapFragmentUpdateListener listener) {
        //mListeners.add(listener);
        mapFragmentUpdateListener = listener;
    }

    public synchronized void registerTrackFragmentListener(TrackFragmentListener listener) {
        //mListeners.add(listener);
        trackFragmentListener = listener;
    }


    public synchronized void unregisterDataUpdateListener(MapFragmentUpdateListener listener) {
        //mListeners.remove(listener);
    }

    public synchronized void dataUpdated() {
        for (MapFragmentUpdateListener listener : mListeners) {
            listener.onDataUpdate();
        }
    }

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
        mapPresenter.loadCars();

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
        LinearLayout llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);

        // init the bottom sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

    }

    private void initPagerAdapter() {
        //Инициализация ViewPager
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MapActivity.MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void showBottomSheets(View view) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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
    public void showCarInfo(Cars car) {
        bottomSheetsCar.setCarName(car.getName());
        bottomSheetsCar.setCarNumber(car.getCarNumber());

        bottomSheetsCar.setDate(car.getTrack().getTimestamp());
        bottomSheetsCar.setSpeed(String.valueOf(car.getTrack().getSpeed()));
    }

    @Override
    public void showCars(ArrayList<Cars> ar, int selectedPosition) {
        mapFragmentUpdateListener.showCars(ar, selectedPosition);

    }

    @Override
    public void showPath(ArrayList<Track> ar) {
        mapFragmentUpdateListener.showPath(ar);
    }

    @Override
    public void showTrackInfo() {

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
    public void onBackPressed() {
        if (mode == Mode.NORMAL) super.onBackPressed();
        else mapPresenter.onBackPressed();
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TrackFragment();
                case 1:
                    return new TrackFragment();
                //return new BottomSheetsInfoFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

    }

}
