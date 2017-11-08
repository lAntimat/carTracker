package com.tracker.lantimat.cartracker.mapActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.mapActivity.models.Cars;
import com.tracker.lantimat.cartracker.mapActivity.models.Mode;
import com.tracker.lantimat.cartracker.mapActivity.models.Track;
import com.tracker.lantimat.cartracker.utils.CarItemizedOverlay;
import com.tracker.lantimat.cartracker.utils.KindleGeoPointHelper;
import com.tracker.lantimat.cartracker.utils.MyOwnItemizedOverlay;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.osmdroid.views.overlay.mylocation.SimpleLocationOverlay;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by GabdrakhmanovII on 28.07.2017.
 */

public class MapFragment extends Fragment implements LocationListener, MapActivity.MapFragmentUpdateListener{


    final static String TAG = "MapFragment";
    final static String SAVE_LAT = "saveLat";
    final static String SAVE_LONG = "saveLong";
    final static int LOCATION_IS = 0;
    final static int LOCATION_TO = 1;


    MapView mMapView;

    private MyLocationNewOverlay mLocationOverlay;
    private CompassOverlay mCompassOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private RotationGestureOverlay mRotationGestureOverlay;
    protected ImageButton btZoomIn;
    protected ImageButton btZoomOut;
    private LocationManager lm;
    private Location currentLocation = null;
    private boolean followLocation = false;
    Menu menu;

    Double latLng[] = new Double[4];

    private SimpleLocationOverlay locationOverlay;
    ArrayList<Marker> arCarMarkers;
    ArrayList<OverlayItem> overlayItemArray = new ArrayList<OverlayItem>();
    ArrayList<OverlayItem> overlayCarsArray = new ArrayList<OverlayItem>();
    ArrayList<OverlayItem> overlayCarsNowPositionArray = new ArrayList<OverlayItem>();

    MyOwnItemizedOverlay showTrackCarOverlay;
    CarItemizedOverlay carNowPositionOverlay;

    MyOwnItemizedOverlay pathMarkersOverlay;
    Polyline pathOverlay;

    Overlay touchOverlay = null;
    ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay = null;
    public ArrayList<Track> arTrackForMarker = new ArrayList<Track>();

    GeoPoint lastGeopoint;
    int state;

    //ArrayList<OverlayItem> carItems = new ArrayList<OverlayItem>();


    public MapFragment() {
    }


    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
        arCarMarkers = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_map, null);

        mMapView = (MapView) v.findViewById(R.id.mapview);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);



        /*IMapController mapController = mMapView.getController();
        mapController.setZoom(9);
        GeoPoint startPoint = new GeoPoint(55.45, 52.20);
        mapController.setCenter(startPoint);*/

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                initMap(v);

            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            initMap(v);
        }

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MapActivity) context).registerDataUpdateListener(this);
    }

    private void initMap(View view) {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //init locationManager
        LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            currentLocation = location;
            mMapView.getController().setZoom(12);
            mMapView.getController().setCenter(new GeoPoint(location.getLatitude(), location.getLongitude()));
            //addLocationMarker(new GeoPoint(location.getLatitude(), location.getLongitude()));
            //addLocationMarker(new GeoPoint(location.getLatitude(), location.getLongitude()));
        }


        final Context context = this.getActivity();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();

        //Добавляет на карту икноку с компасом
        this.mCompassOverlay = new CompassOverlay(context, new InternalCompassOrientationProvider(context),
                mMapView);


        GpsMyLocationProvider provider = new GpsMyLocationProvider(context);
        provider.addLocationSource(LocationManager.NETWORK_PROVIDER);

        //Добавляет на карту иконку с местоположением
        this.mLocationOverlay = new MyLocationNewOverlay(provider,
                mMapView);


        mScaleBarOverlay = new ScaleBarOverlay(mMapView);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);

        mRotationGestureOverlay = new RotationGestureOverlay(mMapView);
        mRotationGestureOverlay.setEnabled(true);

        //mMapView.getController().setZoom(15);
        mMapView.setTilesScaledToDpi(true);
        //mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);
        mMapView.setFlingEnabled(true);
        mMapView.getOverlays().add(this.mLocationOverlay);
        mMapView.getOverlays().add(this.mRotationGestureOverlay);
        //mMapView.getOverlays().add(this.mCompassOverlay);
        mMapView.getOverlays().add(this.mScaleBarOverlay);

        mLocationOverlay.enableMyLocation();
        mLocationOverlay.enableFollowLocation();
        mLocationOverlay.setOptionsMenuEnabled(true);
        //mCompassOverlay.enableCompass();

        btZoomIn = (ImageButton) view.findViewById(R.id.ic_center_map);

        //Кнопка для центрирование карты по местоположение
        btZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMapView.getController().zoomIn();
            }
        });

        btZoomOut = (ImageButton) view.findViewById(R.id.ic_follow_me);

        //Кнопка для вкл/выкл режима следования за местоположением
        btZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMapView.getController().zoomOut();
            }
        });

        initOverlays();
    }

    private void initOverlays() {
        showTrackCarOverlay = new MyOwnItemizedOverlay(getContext(), overlayCarsArray);
        mMapView.getOverlays().add(showTrackCarOverlay);

        carNowPositionOverlay = new CarItemizedOverlay(getContext(), overlayCarsNowPositionArray);
        mMapView.getOverlays().add(carNowPositionOverlay);

    }

    private void addLocationMarker(GeoPoint geoPoint) {
        //Ставим маркер по введенным значениям
        if (mMapView != null) {
            if (locationOverlay != null) mMapView.getOverlayManager().remove(locationOverlay);
            //Добавляем иконку к нашему местоположению

            BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.brightness1);
            locationOverlay = new SimpleLocationOverlay(drawable.getBitmap());
            locationOverlay.setLocation(geoPoint);
            mMapView.getOverlayManager().add(locationOverlay);
        }
    }


    private void addCars(ArrayList<Cars> cars, int selectedPosition) {


        //overlayCarsNowPositionArray.clear();
        if(carNowPositionOverlay!=null) carNowPositionOverlay.removeAllItems();
        //items
        for (int i = 0; i < cars.size(); i++) {
            OverlayItem item = new OverlayItem("Test", "Test", new GeoPoint(cars.get(i).getTrack().getGeoPoint().getLatitude(), cars.get(i).getTrack().getGeoPoint().getLongitude()));
            if (i == selectedPosition)
                item.setMarker(getResources().getDrawable(R.drawable.car_red));
            else item.setMarker(getResources().getDrawable(R.drawable.car));
            //overlayCarsNowPositionArray.add(item); // Lat/Lon decimal degrees
            carNowPositionOverlay.addItem(item);
        }
        //mMapView.getOverlays().add(carNowPositionOverlay);
        mMapView.invalidate();

    }

    public void moveCameraToCurrentLocation() {
        //перемещаем камеру
        GeoPoint g = new GeoPoint(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMapView.getController().setZoom(12);
        mMapView.getController().animateTo(g);

    }

    public void moveCameraToGeopointLocation(GeoPoint g) {
        //перемещаем камеру

        //mMapView.getController().setZoom(14);
        //mMapView.getController().animateTo(g);
        if(state == BottomSheetBehavior.STATE_EXPANDED) {
            final GeoPoint adjustedCenter = adjustCentreByPadding(g.getLatitude(), g.getLongitude(), false);
            mMapView.getController().setCenter(adjustedCenter);
        } else mMapView.getController().setCenter(g);


    }

    public void showMapsMenu(boolean showMenu) {
        if (menu == null)
            return;
        //menu.setGroupVisible(R.id.maps_menu_group, showMenu);
        //menu.findItem(R.id.action_settings).setVisible(showMenu);

    }


    public void getPositionOnTap(boolean getPosition, final int locationIsTo) {  //locationIsTo - для опредения как EditText был нажат
        if (getPosition) {
            touchOverlay = new Overlay() {

                @Override
                public void draw(Canvas arg0, MapView arg1, boolean arg2) {

                }

                @Override
                public boolean onSingleTapConfirmed(final MotionEvent e, final MapView mapView) {

                    final Drawable marker = getContext().getResources().getDrawable(R.drawable.ic_map_marker_grey600_18dp);
                    Projection proj = mapView.getProjection();
                    GeoPoint loc = (GeoPoint) proj.fromPixels((int) e.getX(), (int) e.getY());
                    String longitude = Double.toString(((double) loc.getLongitudeE6()) / 1000000);
                    String latitude = Double.toString(((double) loc.getLatitudeE6()) / 1000000);
                    /*if(locationIsTo==LOCATION_IS)
                        setLatLngIs(loc.getLatitude(), loc.getLongitude(), latitude + " " + longitude);
                    else if(locationIsTo==LOCATION_TO)
                        setLatLngTo(loc.getLatitude(), loc.getLongitude(), latitude + " " + longitude);*/

                    System.out.println("- Latitude = " + latitude + ", Longitude = " + longitude);
                    ArrayList<OverlayItem> overlayArray = new ArrayList<OverlayItem>();
                    OverlayItem mapItem = new OverlayItem("", "", new GeoPoint((((double) loc.getLatitudeE6()) / 1000000), (((double) loc.getLongitudeE6()) / 1000000)));
                    mapItem.setMarker(marker);
                    overlayArray.add(mapItem);
                    if (anotherItemizedIconOverlay == null) {
                        anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(getContext(), overlayArray, null);
                        mapView.getOverlays().add(anotherItemizedIconOverlay);
                        mapView.invalidate();
                    } else {
                        mapView.getOverlays().remove(anotherItemizedIconOverlay);
                        mapView.invalidate();
                        anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(getContext(), overlayArray, null);
                        mapView.getOverlays().add(anotherItemizedIconOverlay);
                    }
                    //      dlgThread();
                    return true;
                }
            };
            mMapView.getOverlays().add(touchOverlay);
        } else {
            if (touchOverlay != null & anotherItemizedIconOverlay != null) {
                mMapView.getOverlays().remove(anotherItemizedIconOverlay);
                mMapView.invalidate();
                mMapView.getOverlays().remove(touchOverlay);
            }
        }
    }

    private GeoPoint adjustCentreByPadding(final double latitude, final double longitude, boolean negate) {
        final int newY = negate ? (int) (mMapView.getHeight()/2  - mMapView.getHeight()/4) : (int) (mMapView.getHeight()/2 + mMapView.getHeight()/4);
        mMapView.getController().setCenter(new org.osmdroid.util.GeoPoint(latitude, longitude));
        final IGeoPoint offset = mMapView.getProjection().fromPixels(mMapView.getWidth()/2, newY);
        return KindleGeoPointHelper.fromIGeoPointToRoverGeoPoint(offset);
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            lm.removeUpdates(this);
        } catch (Exception ex) {
        }

        mCompassOverlay.disableCompass();
        mLocationOverlay.disableFollowLocation();
        mLocationOverlay.disableMyLocation();
        mScaleBarOverlay.enableScaleBar();

        showMapsMenu(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        //Проверка permission
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkLocationPermission();
            return;
        }
        try {
            //this fails on AVD 19s, even with the appcompat check, says no provided named gps is available
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0l, 0f, this);
        } catch (Exception ex) {
        }

        try {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0l, 0f, this);
        } catch (Exception ex) {
        }

        mLocationOverlay.enableFollowLocation();
        mLocationOverlay.enableMyLocation();
        mScaleBarOverlay.disableScaleBar();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        ((MapActivity) getActivity()).unregisterDataUpdateListener(this);
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        lm = null;
        currentLocation = null;

        mLocationOverlay = null;
        mCompassOverlay = null;
        mScaleBarOverlay = null;
        mRotationGestureOverlay = null;
        btZoomIn = null;
        btZoomOut = null;
    }


    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        //Log.d(TAG, "Current location " + location.toString());
        //addLocationMarker(new GeoPoint(location.getLatitude(), location.getLongitude()));
        //if (followLocation) moveCameraToCurrentLocation();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Требуется разрешение для доступа к местоположению")
                        .setMessage(R.string.location_permission_accept)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        /*if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);*/
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Отказано в доступе к местоположению", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onDataUpdate() {

    }

    @Override
    public void showPath(ArrayList<Track> ar) {

        if(carNowPositionOverlay!=null) carNowPositionOverlay.removeAllItems();

        Polyline line = new Polyline();
        line.setWidth(10f);
        line.setColor(R.color.md_green_900);
        List<GeoPoint> pts = new ArrayList<>();
        for (int i = 0; i < ar.size(); i++) {
            pts.add(new GeoPoint(ar.get(i).getGeoPoint().getLatitude(), ar.get(i).getGeoPoint().getLongitude()));
            //addMarker(cars.get(i));
        }
        if (pts.isEmpty()) {
            //Toast.makeText(, "Трека нет!", Toast.LENGTH_SHORT).show();
            return;
        }
        line.setPoints(pts);
        line.setGeodesic(true);


        if(pathOverlay!=null) mMapView.getOverlayManager().remove(pathOverlay);

        pathOverlay = line;

        mMapView.getController().setCenter(pts.get(0));
        mMapView.getOverlayManager().add(pathOverlay);
        mMapView.invalidate();

        //mMapView.getOverlayManager().addAll(arCarMarkers); //Добавляем маркеры
        pathMarkersOverlay = new MyOwnItemizedOverlay(getContext(), overlayItemArray);
        mMapView.getOverlays().add(pathMarkersOverlay);

    }

    @Override
    public void clearPath() {

        mMapView.getOverlayManager().remove(pathOverlay); //Удаляем трек
        if(pathMarkersOverlay !=null) mMapView.getOverlayManager().remove(pathMarkersOverlay); //Удаляем Оверлей маркеры
        if(overlayItemArray!= null) overlayItemArray.clear(); //Чистим массив с маркерами
        if (showTrackCarOverlay.size() > 0) showTrackCarOverlay.removeItem(0); //Удаляем иконку машины, которая в данный момент показывает путь

        mMapView.invalidate(); //Перерисовка

    }

    @Override
    public void addMarker(int position, Track track, String title, String subtitle) {
        /*Marker m = new Marker(mMapView);
        m.setPosition(new GeoPoint(track.getGeoPoint().getLatitude(), track.getGeoPoint().getLongitude()));
        m.setSnippet(title);
        m.setSubDescription(subtitle);
        m.setIcon(getResources().getDrawable(R.drawable.ic_adjust_black_18dp));
        arCarMarkers.add(m);*/

        OverlayItem olItem = new OverlayItem(String.valueOf(position), title, subtitle, new GeoPoint(track.getGeoPoint().getLatitude(), track.getGeoPoint().getLongitude()));//marker
        olItem.setMarker(getResources().getDrawable(R.drawable.ic_adjust_black_36dp));
        overlayItemArray.add(olItem);
        mMapView.invalidate();


    }

    @Override
    public void showTrackingCarPositionMarker(Track track) {
        if (showTrackCarOverlay.size() > 0) showTrackCarOverlay.removeItem(0);

        OverlayItem olItem = new OverlayItem("", "", new GeoPoint(track.getGeoPoint().getLatitude(), track.getGeoPoint().getLongitude()));//marker
        olItem.setMarker(getResources().getDrawable(R.drawable.car));
        showTrackCarOverlay.addItem(olItem);
        GeoPoint g = new GeoPoint(track.getGeoPoint().getLatitude(), track.getGeoPoint().getLongitude());
        moveCameraToGeopointLocation(g);
        mMapView.invalidate();

        Log.d(TAG, "showTrackingCarPositionMarker");
    }

    @Override
    public void showCars(ArrayList<Cars> cars, int selectedPosition) {
        addCars(cars, selectedPosition);
    }

    @Override
    public void onBottomSheetsStateChange(int state) {
        this.state = state;
        GeoPoint g = new GeoPoint(mMapView.getMapCenter().getLatitude(), mMapView.getMapCenter().getLongitude());
        if(state == BottomSheetBehavior.STATE_EXPANDED) {
            lastGeopoint = g;
            final GeoPoint adjustedCenter = adjustCentreByPadding(g.getLatitude(), g.getLongitude(), false);
            mMapView.getController().animateTo(adjustedCenter);
        } else if(state == BottomSheetBehavior.STATE_COLLAPSED) {
            if(lastGeopoint!=null) {
                final GeoPoint adjustedCenter = adjustCentreByPadding(g.getLatitude(), g.getLongitude(), true);
                mMapView.getController().animateTo(adjustedCenter);
                lastGeopoint = null;
            }

        }
    }
}
