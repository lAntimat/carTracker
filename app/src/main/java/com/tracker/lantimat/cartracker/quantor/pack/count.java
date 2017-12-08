package com.tracker.lantimat.cartracker.quantor.pack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by Pre77 on 30.10.2017.
 */

public class count {
    public int count = 0;
    public byte[] toBytes() throws IOException {
        ByteBuffer b = ByteBuffer.allocate(4).putInt(count);
        return b.array();
    }
    //
    public void fromBytes(byte[] bytes, int offset, int len) {
        count = ((bytes[offset + 3] << 24) & 0xFF000000) ^ ((bytes[offset + 2] << 16) & 0xFF0000) ^((bytes[offset + 1] << 8) & 0xFF00) ^ (bytes[offset + 0] & 0xFF);
    }
}
