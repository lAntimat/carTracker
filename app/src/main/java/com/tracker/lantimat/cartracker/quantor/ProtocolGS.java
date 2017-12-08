package com.tracker.lantimat.cartracker.quantor;

        import com.tracker.lantimat.cartracker.quantor.pack.auth;
        import com.tracker.lantimat.cartracker.quantor.pack.count;
        import com.tracker.lantimat.cartracker.quantor.pack.error;
        import com.tracker.lantimat.cartracker.quantor.pack.head;
        import com.tracker.lantimat.cartracker.quantor.pack.loginPack;
        import com.tracker.lantimat.cartracker.quantor.pack.monitor;
        import com.tracker.lantimat.cartracker.quantor.pack.noop;
        import com.tracker.lantimat.cartracker.quantor.pack.point;
        import com.tracker.lantimat.cartracker.quantor.pack.response;
        import com.tracker.lantimat.cartracker.quantor.pack.route;
        import com.tracker.lantimat.cartracker.quantor.pack.sendreqreport;

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;
        import java.nio.ByteBuffer;

/**
 * Created by Nikolay Zlotnikov on 24.10.2017.
 */

public class protocolGS {
    public final static int CRC_POLYNOM = 0x31;
    public final static int CRC_PRESET = 0xFF;
    public final static short SIZE_HEAD = 0x6;
    public final static short SIZE_LOGIN = 0x27;
    public final static short SIZE_MONITOR = 0x20;
    public final static short SIZE_NOOP = 0x1;
    public final static short SIZE_RESPONSE = 0x1;
    public final static short SIZE_ERROR = 0x5;
    public final static short SIZE_COUNT = 0x4;
    public final static short SIZE_SEND_REPORT = 0x151;
    public final static short SIZE_ROUTE_RECORD = 0x151;
    public final static int SIZE_AUTH = 0x3C;
    public final static short SIZE_POINT = 0x148;
    public final static short SIZE_ROUTE = 0xA;
    public final static short SIZE_UTRACKERA = 0x11;


    public final static short PACKET_LOGIN = 0x0;
    public final static short PACKET_MONITOR = 0x1;
    public final static short PACKET_USER_IMEI = 0x2;
    public final static short PACKET_ROUTE = 0x3;

    public static byte crc8ccitt(byte[] b, int len) {
        int crc_U = CRC_PRESET;
        for (int i = 0; i < len; i++) {
            crc_U ^= b[i];
            for (int j = 0; j < 8; j++) {
                if ((crc_U & 0x80) != 0) {
                    crc_U = (crc_U << 1) ^ CRC_POLYNOM;
                } else {
                    crc_U = (crc_U << 1);
                }
            }
        }
        return ((byte) crc_U);
    }

    public static response parseResponse(byte[] bytes, int offset, int count) {
        response RS = new response();
//        try {
//            head H = new head(bytes);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        RS.error = bytes[offset];
        return RS;
    }

    public static head parseHeader(byte[] bytes, int offset, int count) {
        head H = null;
        try {
            H = new head(bytes, offset, count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return H;
    }
    public static byte[] packetLogin(loginPack L) throws IOException {
        return packetLogin(L, false);
    }
    public static byte[] packetLogin(loginPack L, boolean one) throws IOException {
        head H = new head();
        H.tag = 0;
        H.len = SIZE_HEAD + SIZE_LOGIN;
        if (!one) {
            L.imei = new byte[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        }
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(H.toBytes());
        b.write(L.toBytes());
        return b.toByteArray();
    }

    public static auth[] parseAuth(byte[] bytes, int offset, long len) {
        count c = new count();
        c.fromBytes(bytes, offset, SIZE_COUNT);
        auth[] aa = null;
        aa = new auth[c.count];
        for (int _i = 0; _i < c.count; _i++) {
            auth A = new auth();
            ByteBuffer b = ByteBuffer.allocate(SIZE_AUTH);
            b.put(bytes, offset + SIZE_COUNT + (int) (SIZE_AUTH * _i), SIZE_AUTH);
            A.fromBytes(b.array());
            aa[_i] = A;
            b.clear();
        }
        return aa;
    }

    public static point parsePoints(byte[] bytes, int offset, long len) {
//        count c = new count();
//        c.fromBytes(bytes, offset, SIZE_COUNT);
//        point[ aa = null;
        point aa = new point();
        ByteBuffer b = ByteBuffer.allocate(SIZE_POINT);
//        for (int _i = 0; _i < c.count; _i++) {
//            point A = new point();
        b.put(bytes, offset, SIZE_POINT);
        aa.fromBytes(b.array());
//            aa[_i] = A;
//        }
        return aa;
    }

    public static byte[] packetMonitoring(short Count, monitor[] M) throws IOException {
        head H = new head();
        count C = new count();
        H.tag = 1;
        H.len = (short) (SIZE_HEAD + SIZE_COUNT + (short) (SIZE_MONITOR * Count));
        C.count = Count;
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(H.toBytes());
        b.write(C.toBytes());
        for (int i = 0; i < Count; i++) {
            b.write(M[i].toBytes());
        }
        return b.toByteArray();
    }

    public static byte[] packetSRR(sendreqreport SR, short Count, error[] E, byte[] Photo, int len) throws IOException {
        head H = new head();
        count C = new count();
        H.tag = 100;
        H.len = SIZE_HEAD + SIZE_SEND_REPORT + SIZE_COUNT;
        if (Count > 0) {
            C.count = Count;
            H.len = (H.len + (Count * SIZE_ERROR));
        } else {
            C.count = 0;
        }
        if (len > 0) {
            H.len = (int) (H.len + len);
        }
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(H.toBytes());
        b.write(SR.toBytes());
        b.write(C.toBytes());
        if (Count > 0) {
            for (int i = 0; i < Count; i++) {
                b.write(E[i].toBytes());
            }
        }
        if (len > 0) {
            b.write(Photo);
        }
        return b.toByteArray();
    }

    public static byte[] packetNoop(noop NP) throws IOException {
        head H = new head();
        H.tag = (byte) 0xFF;
        H.len = SIZE_HEAD + SIZE_NOOP;
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(H.toBytes());
        b.write(NP.toBytes());
        return b.toByteArray();
    }
    public static route[] parseRoute(byte[] bytes, int offset, long length) {
        count c = new count();
        c.fromBytes(bytes, offset, SIZE_COUNT);
        route[] aa = null;
        aa = new route[c.count];
        for (int _i = 0; _i < c.count; _i++) {
            route A = new route();
            ByteBuffer b = ByteBuffer.allocate(SIZE_AUTH);
            b.put(bytes, offset + SIZE_COUNT + (int) (SIZE_ROUTE * _i), SIZE_ROUTE);
            A.fromBytes(b.array());
            aa[_i] = A;
            b.clear();
        }
        return aa;
    }
}
