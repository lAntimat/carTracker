package com.tracker.lantimat.cartracker.quantor.pack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Pre77 on 30.10.2017.
 */

public class noop {
    public byte noop = 0;
    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(noop);
        return b.toByteArray();
    }
}
