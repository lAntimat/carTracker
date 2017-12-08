package com.tracker.lantimat.cartracker;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.tracker.lantimat.cartracker.Kotlin.MainKotlinActivity;
import com.tracker.lantimat.cartracker.mapActivity.MapActivity;
import com.tracker.lantimat.cartracker.mapActivity.models.Cars;
import com.tracker.lantimat.cartracker.mapActivity.models.Track;
import com.tracker.lantimat.cartracker.reportActivity.ReportActivity;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String MESSENGER_INTENT_KEY
            = BuildConfig.APPLICATION_ID + ".MESSENGER_INTENT_KEY";
    final static String TAG = "MainActivity";
    final static int MY_PERMISSIONS_ACCES_FINE_LOCATION = 1;



    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.textView);
        FirebaseApp.initializeApp(getApplicationContext());
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        /*db.collection("cars")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                textView.setText(document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });*/

/*        Track track = new Track(1, true, new GeoPoint(55.6951674, 52.2984999), 0, new Date());
        Cars car = new Cars(1, 1, "Renault Symbol", "X663AO116RUS", "123456789");
        car.setTrack(track);
            // Add a new document with a generated ID
            db.collection("cars")
                    .add(car)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });*/
        }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_ACCES_FINE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.
                    startService(new Intent(this, MyLocationService.class));

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }

    public void btnClick(View view) {
        startActivity(new Intent(this, MapActivity.class));
    }

    public void btnClickStartTracking(View view) {
        startService(new Intent(this, MyLocationService.class));
    }

    public void btnClick2(View view) {
        startActivity(new Intent(this, ReportActivity.class));
    }

    public void clientTest(View view) {
        startActivity(new Intent(this, TrackerEmulatorActivity.class));
    }

    public void openKotlinActivity(View view) {
        startActivity(new Intent(this, MainKotlinActivity.class));
    }

}
