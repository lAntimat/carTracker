package com.tracker.lantimat.cartracker.quantor.pack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Pre77 on 30.10.2017.
 */

public class response {
    public byte error = 0;
    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(error);
        return b.toByteArray();
    }
}
