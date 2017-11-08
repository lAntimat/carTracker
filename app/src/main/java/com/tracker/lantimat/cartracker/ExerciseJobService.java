package com.tracker.lantimat.cartracker;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.tracker.lantimat.cartracker.mapActivity.models.Track;

import java.util.Date;

/**
 * Created by GabdrakhmanovII on 13.10.2017.
 * start the test
 */

public class ExerciseJobService extends JobService {
    FirebaseFirestore db;
    private Messenger mActivityMessenger;

    private static final String TAG = ExerciseJobService.class.getSimpleName();

    public ExerciseJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob: starting job with id: " + params.getJobId());

        db = FirebaseFirestore.getInstance();

        GeoPoint geoPoint = new GeoPoint(params.getExtras().getDouble("lat"), params.getExtras().getDouble("lng"));
        double speed = params.getExtras().getDouble("speed");

        Track car = new Track(1, true, geoPoint, speed, new Date());

        if (db != null) {
            // Add a new document with a generated ID
            db.collection("tracks")
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
                    });
        }

        //ExerciseIntentService.startActionWriteExercise(getApplicationContext());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob: stopping job with id: " + params.getJobId());
        return true;
    }

}
