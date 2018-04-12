package com.tracker.lantimat.cartracker.mapActivity.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.support.v4.app.FragmentManager;
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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.mapActivity.API.CarsR;
import com.tracker.lantimat.cartracker.mapActivity.API.TrackR;
import com.tracker.lantimat.cartracker.mapActivity.MapActivity;
import com.tracker.lantimat.cartracker.mapActivity.adapters.CarItemizedOverlay;
import com.tracker.lantimat.cartracker.mapActivity.adapters.MyOwnItemizedOverlay;
import com.tracker.lantimat.cartracker.mapActivity.models.Cars;
import com.tracker.lantimat.cartracker.mapActivity.models.Track;
import com.tracker.lantimat.cartracker.utils.KindleGeoPointHelper;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
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
import java.util.HashMap;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by GabdrakhmanovII on 28.07.2017.
 */

public class GoogleMapFragment extends Fragment implements LocationListener, MapActivity.MapFragmentUpdateListener {


    final static String TAG = "MapFragment";
    final static String SAVE_LAT = "saveLat";
    final static String SAVE_LONG = "saveLong";
    final static int LOCATION_IS = 0;
    final static int LOCATION_TO = 1;


    GoogleMap gMapView;
    StreetViewPanorama streetViewPanorama;
    private UiSettings mUiSettings;

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
    //ArrayList<Marker> arCarMarkers;
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
    private HashMap<String, Marker> gCarMarkers = new HashMap<String, Marker>();
    private ArrayList<String> markerPosition = new ArrayList();
    //ArrayList<OverlayItem> carItems = new ArrayList<OverlayItem>();


    public GoogleMapFragment() {
    }


    public static GoogleMapFragment newInstance() {
        GoogleMapFragment fragment = new GoogleMapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
        //arCarMarkers = new ArrayList<>();
        //setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.street_view_panorama_basic_demo, null);

        FragmentManager fragmentManager = getChildFragmentManager();
        SupportMapFragment mapFragment =
                (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMapView = googleMap;

                mUiSettings = googleMap.getUiSettings();
                // Keep the UI Settings state in sync with the checkboxes.
                mUiSettings.setZoomControlsEnabled(true);
                mUiSettings.setMyLocationButtonEnabled(true);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                gMapView.setMyLocationEnabled(true);
                mUiSettings.setScrollGesturesEnabled(true);
                mUiSettings.setZoomGesturesEnabled(true);
                mUiSettings.setTiltGesturesEnabled(true);
                mUiSettings.setRotateGesturesEnabled(true);


                gMapView.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        ((MapActivity)getActivity()).mapPresenter.carMarkerClick((Integer) marker.getTag());
                        return false;
                    }
                });
                gMapView.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        if(streetViewPanorama!=null) streetViewPanorama.setPosition(latLng);
                    }
                });
            }
        });

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment)
                        fragmentManager.findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(
                new OnStreetViewPanoramaReadyCallback() {
                    @Override
                    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                        // Only set the panorama to SYDNEY on startup (when no panoramas have been
                        // loaded which is when the savedInstanceState is null).
                        if (savedInstanceState == null) {
                            streetViewPanorama = panorama;
                        }
                    }
                });

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                //initMap(v);

            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            //initMap(v);
        }

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MapActivity) context).registerDataUpdateListener(this);
    }

    public void showMapsMenu(boolean showMenu) {
        if (menu == null)
            return;
        //menu.setGroupVisible(R.id.maps_menu_group, showMenu);
        //menu.findItem(R.id.action_settings).setVisible(showMenu);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void showPath(ArrayList<TrackR> ar) {

    }

    @Override
    public void clearPath() {


    }

    @Override
    public void addMarker(int position, TrackR track, String title, String subtitle) {
        /*Marker m = new Marker(mMapView);
        m.setPosition(new GeoPoint(track.getGeoPoint().getLatitude(), track.getGeoPoint().getLongitude()));
        m.setSnippet(title);
        m.setSubDescription(subtitle);
        m.setIcon(getResources().getDrawable(R.drawable.ic_adjust_black_18dp));
        arCarMarkers.add(m);*/
        //gMapView.addMarker(new MarkerOptions().position(new LatLng(track.getLat(), track.get)).title("Marker"));



    }

    @Override
    public void showTrackingCarPositionMarker(TrackR track) {
        if (showTrackCarOverlay.size() > 0) showTrackCarOverlay.removeItem(0);
        gMapView.addMarker(new MarkerOptions().position(new LatLng(track.getLat(), track.getLon())).title(""));
        Log.d(TAG, "showTrackingCarPositionMarker");
    }

    @Override
    public void showCars(ArrayList<CarsR> cars, int selectedPosition) {

        if(gCarMarkers.size() > 0) {
            for (Marker marker: gCarMarkers.values()
                 ) {
                marker.remove();
            }
        }
        gCarMarkers.clear();

        for (int i = 0; i < cars.size(); i++) {
            CarsR car = cars.get(i);
            Marker marker = gMapView.addMarker(new MarkerOptions()
                    .position(new LatLng(car.getState().getLat(), car.getState().getLon())));
            marker.setTag(gCarMarkers.size());
            if(selectedPosition!=-1 & i == selectedPosition) {
                marker.setIcon(bitmapDescriptorFromVector(getActivity(), R.drawable.car_red));
            }
            else marker.setIcon(bitmapDescriptorFromVector(getActivity(), R.drawable.car));
            gCarMarkers.put(marker.getId(), marker);
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onBottomSheetsStateChange(int state) {
        this.state = state;
        /*GeoPoint g = new GeoPoint(mMapView.getMapCenter().getLatitude(), mMapView.getMapCenter().getLongitude());
        if(state == BottomSheetBehavior.STATE_EXPANDED) {
            lastGeopoint = g;
            final GeoPoint adjustedCenter = adjustCentreByPadding(g.getLatitude(), g.getLongitude(), false);
            mMapView.getController().animateTo(adjustedCenter);
        } else if(state == BottomSheetBehavior.STATE_COLLAPSED) {
            if(lastGeopoint!=null) {
                final GeoPoint adjustedCenter = adjustCentreByPadding(g.getLatitude(), g.getLongitude(), true);
                mMapView.getController().animateTo(adjustedCenter);
                lastGeopoint = null;
            }*/

        //}
    }

    @Override
    public void setCenter(GeoPoint geoPoint) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude()))
                .zoom(10)
                .build();
        gMapView.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
