package com.tracker.lantimat.cartracker.quantor.pack;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Nikolay Zlotnikov on 25.10.2017.
 */

public class sendreqreport {
    static String TAG = "sendreport";
    public byte[] imei = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; //16
    public byte[] hash = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; //16
    public String title = ""; //50
    public String msg = "";   //255
    public byte[] toBytes() throws IOException {
        int i = 0;
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            b.write(0);
            b.write(imei);
            b.write(hash);
            if (title == null || title.length() <= 0) {
                throw new RuntimeException("Login is empty");
            } else if (title.length() > 50) {
                title = title.substring(0, 49);
            }
            b.write(title.getBytes("UTF-8"));
            i = (50 - title.length());
            while (i > 0) {
                if (i > 0) {
                    b.write(0);
                    i--;
                }
            }
            if (msg == null || msg.length() <= 0) {
                throw new RuntimeException("Login is empty");
            } else if (msg.length() > 255) {
                msg = msg.substring(0, 254);
            }
            b.write(msg.getBytes("UTF-8"));
            i = (255 - msg.length());
            while (i > 0) {
                if (i > 0) {
                    b.write(0);
                    i--;
                }
            }
            return b.toByteArray();
        } catch (Exception e) {
            Log.e(TAG, "toBytes: ", e);
            return null;
        }

    }

}
