package com.tracker.lantimat.cartracker.quantor;

import com.tracker.lantimat.cartracker.quantor.pack.head;
import com.tracker.lantimat.cartracker.quantor.pack.login;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
/**
 * Created by Nikolay Zlotnikov on 24.10.2017.
 */

public class  protocolGS {
    public final static int CRC_POLYNOM = 0x31;

    public final static int CRC_PRESET = 0xFF;

    public static byte crc8ccitt(byte[] b, int len) {
        int crc_U = CRC_PRESET;
        for(int i = 0; i < len; i++) {
            crc_U ^= b[i];
            for(int j = 0; j < 8; j++) {
                if((crc_U & 0x80) != 0) {
                    crc_U = (crc_U << 1) ^ CRC_POLYNOM;
                } else {
                    crc_U = (crc_U << 1);
                }
            }
        }
        return ((byte) crc_U);
    }


    public static byte[] packetLogin(head H, login L) throws IOException {
        H.len = 5+39;
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(H.toBytes());
        b.write(L.toBytes());
        return b.toByteArray();
    }
}
