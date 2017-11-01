package com.tracker.lantimat.cartracker;

/**
 * Created by GabdrakhmanovII on 13.10.2017.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.tracker.lantimat.cartracker.mapActivity.models.Track;
import com.tracker.lantimat.cartracker.utils.ActionReceiver;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by roberto on 9/29/16.
 */

public class MyLocationService extends Service {
    final static int ID = 2;

    private static final String TAG = "MyLocationService";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final int LOCATION_SEND_TO_FIREBASE_INTERVAL = 5000;
    private static final float LOCATION_DISTANCE = 10f;
    private static int sJobId = 1;
    Location mLastLocation;
    long lastTimestamp = 0;
    FirebaseFirestore db;

    String carKey;


    private class LocationListener implements android.location.LocationListener {

        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);


        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);

            createNotification(getApplicationContext(), false, location);
            writeToFire(location);


            // }


            //scheduleJob(getApplicationContext());

            //}
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }


    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };


    /*LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.PASSIVE_PROVIDER)
    };*/

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {

        Log.e(TAG, "onCreate");
        FirebaseFirestore.getInstance();

        db = FirebaseFirestore.getInstance();

        loadCollectionKey();

        createNotification(getApplicationContext(), true, mLastLocation);


        initializeLocationManager();

        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    mLocationListeners[0]
            );
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }

        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    mLocationListeners[1]
            );
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                    stopSelf();
                    cancelNotification();
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listener, ignore", ex);
                }
            }
        }
    }

    public void loadCollectionKey() {
        db.collection("cars")
                .whereEqualTo("id", ID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                carKey = document.getId();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager - LOCATION_INTERVAL: " + LOCATION_INTERVAL + " LOCATION_DISTANCE: " + LOCATION_DISTANCE);
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    private void scheduleJob(Context context) {
        ComponentName jobService = new ComponentName(context, ExerciseJobService.class);
        JobInfo.Builder exerciseJobBuilder = new JobInfo.Builder(sJobId++, jobService);
        exerciseJobBuilder.setMinimumLatency(TimeUnit.SECONDS.toMillis(1));
        exerciseJobBuilder.setOverrideDeadline(TimeUnit.SECONDS.toMillis(5));
        exerciseJobBuilder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        exerciseJobBuilder.setRequiresDeviceIdle(false);
        exerciseJobBuilder.setRequiresCharging(false);
        exerciseJobBuilder.setBackoffCriteria(TimeUnit.SECONDS.toMillis(10), JobInfo.BACKOFF_POLICY_LINEAR);
        PersistableBundle bundle = new PersistableBundle();
        bundle.putDouble("lat", mLastLocation.getLatitude());
        bundle.putDouble("lng", mLastLocation.getLongitude());
        bundle.putDouble("speed", mLastLocation.getSpeed());
        exerciseJobBuilder.setExtras(bundle);

        Log.i(TAG, "scheduleJob: adding job to scheduler");

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(exerciseJobBuilder.build());
    }

    private void createNotification(Context context, boolean isStartNotification, Location location) {

        //This is the intent of PendingIntent
        Intent intentAction = new Intent(context, ActionReceiver.class);

        //This is optional if you have more than one buttons and want to differentiate between two
        intentAction.putExtra("action", "action1");
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 1, intentAction, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setAutoCancel(true)
                        .addAction(new NotificationCompat.Action(R.drawable.ic_adjust_white_24dp, "Остановить", pIntent))
                        .setSmallIcon(R.drawable.ic_menu_mapmode);
        if (!isStartNotification) {
            mBuilder.setContentTitle(String.valueOf(location.getSpeed() * 3.6));
            mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(location.toString()));
        } else {
            mBuilder.setContentTitle("CarTracker");
            mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText("Сервис запущен!"));
        }


        // Sets an ID for the notification
        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    private void cancelNotification() {
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        if (mNotifyMgr != null) {
            mNotifyMgr.cancel(001);
            mNotifyMgr.cancel(002);

        }
    }

    private void createdocumentAddNotification(Context context, String str) {

        //This is the intent of PendingIntent
        /*Intent intentAction = new Intent(context,ActionReceiver.class);

        //This is optional if you have more than one buttons and want to differentiate between two
        intentAction.putExtra("action","actionName");
        PendingIntent pIntent = PendingIntent.getBroadcast(context,1,intentAction,PendingIntent.FLAG_UPDATE_CURRENT);*/

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setAutoCancel(true)
                        //.addAction(new NotificationCompat.Action(R.drawable.ic_adjust_white_24dp, "Остановить", pIntent))
                        .setSmallIcon(R.drawable.ic_menu_mylocation);
        mBuilder.setContentTitle("Добавление документа в FB");
        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(str));


        // Sets an ID for the notification
        int mNotificationId = 002;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    private void writeToFire(Location location) {
        final Date date = new Date();
        //if(date.getTime() > lastTimestamp + LOCATION_SEND_TO_FIREBASE_INTERVAL) {


        //if(mLastLocation.getAccuracy()>60) {
        Track track = new Track(ID, true, new GeoPoint(location.getLatitude(), location.getLongitude()), location.getSpeed(), new Date());
        if (db != null & location.getAccuracy() < 100) {
            // Add a new document with a generated ID
            db.collection("tracks")
                    .add(track)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            createdocumentAddNotification(getApplicationContext(), "DocumentSnapshot added with ID: " + documentReference.getId());
                            lastTimestamp = date.getTime();
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }


        //Set the "isCapital" field of the city 'DC'

        if(carKey!=null) {
            db.collection("cars").document(carKey)
                    .update(
                            "track.geoPoint", new GeoPoint(location.getLatitude(), location.getLongitude()),
                            "track.speed", location.getSpeed(),
                            "track.timestamp", new Date())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "GeoPoint update");
                        }
                    });
        }
    }


}
