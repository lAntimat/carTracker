package com.tracker.lantimat.cartracker.quantor.pack;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.tracker.lantimat.cartracker.quantor.protocolGS.SIZE_ROUTE;

/**
 * Created by Pre77 on 30.10.2017.
 */

public class route {
    public Float lat;
    public Float lon;
    public short alt;
    public void fromBytes(byte[] bytes) {
        if ((bytes.length % SIZE_ROUTE) == 0) {
            ByteBuffer b = ByteBuffer.allocate(SIZE_ROUTE).order(ByteOrder.LITTLE_ENDIAN);
            b.put(bytes, 0, SIZE_ROUTE);
            b.position(0);
            lat = b.getFloat();
            lon = b.getFloat();
            alt = b.getShort();
//            return ;
        } else {
//            return null;
        }
    }
}
