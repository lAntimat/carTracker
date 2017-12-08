package com.tracker.lantimat.cartracker.quantor.pack;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Created by Николай on 09.11.2017.
 */

public class auth {
    public static byte[] imei = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; //16
    public static byte[] hash = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; //16
    public static String server = ""; //: array [0 .. 19] of AnsiChar;
    public static Date ltime;//: TDateTime;
    public static void fromBytes(byte[] bytes) {
        ByteBuffer b = ByteBuffer.allocate(16);
        b.put(bytes, 0, 16);
        imei = b.array().clone();
        b.clear();
        b.put(bytes, 16, 16).array();
        hash = b.array();
        b.clear();
        b = ByteBuffer.allocate(20);
        b.put(bytes, 32, 20).array();
        server = new String(b.array(), StandardCharsets.UTF_8);;
//        ltime
    }
}
