package com.tracker.lantimat.cartracker.quantor.pack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Pre77 on 20.11.2017.
 */

public class utrackera {
    public byte len = 0;
    public byte[] imei = new byte[16];
    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(len);
        b.write(imei);
        return b.toByteArray();
    }
}
