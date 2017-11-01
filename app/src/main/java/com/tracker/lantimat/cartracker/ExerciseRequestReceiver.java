package com.tracker.lantimat.cartracker;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * Created by GabdrakhmanovII on 13.10.2017.
 */

public class ExerciseRequestReceiver extends BroadcastReceiver {

    private static final String TAG = ExerciseRequestReceiver.class.getSimpleName();

    public static final String ACTION_PERFORM_EXERCISE = "ACTION_PERFORM_EXERCISE";

    private static int sJobId = 1;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "onReceive: action: " + intent.getAction());

        switch (intent.getAction()) {
            case ACTION_PERFORM_EXERCISE:
                scheduleJob(context);
                break;
            default:
                throw new IllegalArgumentException("Unknown action.");
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
        Bundle bundle = new Bundle();
        //bundle.putFloat("lot");
       // exerciseJobBuilder.setExtras()

        Log.i(TAG, "scheduleJob: adding job to scheduler");

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(exerciseJobBuilder.build());
    }
}
