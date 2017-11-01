package com.tracker.lantimat.cartracker.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tracker.lantimat.cartracker.MyLocationService;

/**
 * Created by GabdrakhmanovII on 18.10.2017.
 */

public class ActionReceiver extends BroadcastReceiver {

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("ActionReceiver", "brReceiver");


        this.context = context;
        //Toast.makeText(context,"recieved",Toast.LENGTH_SHORT).show();

        String action=intent.getStringExtra("action");
        if(action.equals("action1")){
            performAction1();
        }
        else if(action.equals("action2")){
            performAction2();

        }
        //This is used to close the notification tray
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
    }

    public void performAction1(){
        context.stopService(new Intent(context, MyLocationService.class));
    }

    public void performAction2(){

    }

}
