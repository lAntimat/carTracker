package com.tracker.lantimat.cartracker.quantor.pack;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;

import static com.tracker.lantimat.cartracker.quantor.protocolGS.SIZE_POINT;

/**
 * Created by Николай on 09.11.2017.
 */

public class point implements Cloneable {
    public static byte[] imei = new byte[16];
    private static Date time;
    private static double _time;
    public static float lat;
    public static float lon;
    public static short speed;
    public static short alt;
    public static short sat;

    public Date getTime() {
        return time;
    }

    public double getTimeD() {
        return _time;
    }
    public void setTime(Date t) {
        time = t;
    }

    public void setTimeD(double t) {
        _time = t;
    }
    public int in1;
    public int in2;
    public int in3;
    public int in4;
    public byte d1;
    public byte d2;
    public byte d3;
    public byte d4;
    public long RS0;
    public long RS1;
    public long can0;
    public long can1;
    public long can2;
    public long can3;
    public long can4;
    public long can5;
    public long can6;
    public long can7;
    public long can8;
    public long can9;
    public long can10;
    public long can11;
    public long can12;
    public long can13;
    public long can14;
    public long can15;
    public long can16;
    public long can17;
    public long can18;
    public long can19;
    public int rs_1; //word
    public int rs_2; //word
//    public insens in;
//    public imageData pict;
//    public extadc edc;

    @Override
    public point clone() {
//        try {
            point p = new point();
            p.can0 = this.can0;
            p.can1 = this.can1;
            p.can2 = this.can2;
            p.can3 = this.can3;
            p.can4 = this.can4;
            p.can5 = this.can5;
            p.can6 = this.can6;
            p.can7 = this.can7;
            p.can8 = this.can8;
            p.can9 = this.can9;
            p.can10 = this.can10;
            p.can11 = this.can11;
            p.can12 = this.can12;
            p.can13 = this.can13;
            p.can14 = this.can14;
            p.can15 = this.can15;
            p.can16 = this.can16;
            p.can17 = this.can17;
            p.can18 = this.can18;
            p.can19 = this.can19;
            p.in1 = this.in1;
            p.in2 = this.in2;
            p.in3 = this.in3;
            p.in4 = this.in4;
            p.d1 = this.d1;
            p.d2 = this.d2;
            p.d3 = this.d3;
            p.d4 = this.d4;
            p.setTimeD(this.getTimeD());
            p.setTime(this.getTime());

            return p;
//        } catch (CloneNotSupportedException ex) {
//            throw new InternalError();
//        }
    }

    public static float getAngle(float lat, float lon) {
        return 0f;
    }

    public static void fromBytes(byte[] bytes) {
        ByteBuffer b = ByteBuffer.allocate(16);
        b.put(bytes, 0, 16);
        imei = b.array().clone();
        b.clear();
        b = ByteBuffer.allocate(SIZE_POINT - 16).order(ByteOrder.LITTLE_ENDIAN);
        b.put(bytes, 16, SIZE_POINT - 16);
        b.position(0);
        _time = b.getDouble();
        //time = doubleDToDate(_time);
        lat = b.getFloat();
        lon = b.getFloat();
    }
//    public void setOut(pointoutstate state) {
//        if (state != null) {
//            this.o1 = state.isO1();
//            this.o2 = state.isO2();
//            this.o3 = state.isO3();
//            this.o4 = state.isO4();
//        } else {
//            this.o1 = this.d1 == 1;
//            this.o2 = this.d2 == 1;
//            this.o3 = this.d3 == 1;
//            this.o4 = this.d4 == 1;
//        }
//    }
//    public boolean isO1() {
//        return o1;
//    }
//
//    public boolean isO2() {
//        return o2;
//    }
//
//    public boolean isO3() {
//        return o3;
//    }
//
//    public boolean isO4() {
//        return o4;
//    }
//    public void setO1(int state) {
//        this.o1 = state == 1;
//    }
//    public void setO2(int state) {
//        this.o2 = state == 1;
//    }
}
