package com.tracker.lantimat.cartracker.quantor.pack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Nikolay Zlotnikov on 25.10.2017.
 */

public class monitor {
    public byte[] imei = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; //16
    public byte[] hash = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; //16
    public byte[] toBytes() throws IOException {
        int i = 0;
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(0);
        b.write(imei);
        b.write(hash);
        return b.toByteArray();
    }
}
