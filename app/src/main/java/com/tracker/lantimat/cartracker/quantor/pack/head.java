package com.tracker.lantimat.cartracker.quantor.pack;

import com.tracker.lantimat.cartracker.quantor.protocolGS;

/**
 * Created by Pre77 on 25.10.2017.
 */

public class head{
    public byte tag;
    public short len;
    byte crc;
    byte reserv;
    public byte[] toBytes(){
        byte[] res = new byte[5];
        res[0] = tag;
        res[1] = (byte) (len & 0xFF);
        res[2] = (byte) ((len >> 8) & 0xFF);
        res[3] = protocolGS.crc8ccitt(res,3);
        res[4] = 0;
        return res;
    }
}
