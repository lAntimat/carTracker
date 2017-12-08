package com.tracker.lantimat.cartracker.quantor.pack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Pre77 on 30.10.2017.
 */

public class error {
    public int spn = 0;
    public byte fmi = 0;
    public byte[] toBytes() throws IOException {
        int i = 0;
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(spn);
        b.write(fmi);
        return b.toByteArray();
    }
}
