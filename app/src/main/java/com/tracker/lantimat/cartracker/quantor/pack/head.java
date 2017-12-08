package com.tracker.lantimat.cartracker.quantor.pack;

import com.tracker.lantimat.cartracker.quantor.protocolGS;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.tracker.lantimat.cartracker.quantor.protocolGS.SIZE_HEAD;


/**
 * Created by Pre77 on 25.10.2017.
 */

public class head{
    public head(byte[] bytes, int offset, int length) throws IOException {
//        tag = bytes[0];
        ByteBuffer b = ByteBuffer.wrap(bytes, offset, length).order(ByteOrder.LITTLE_ENDIAN);
//        b;
        tag = b.get();
        len = b.getInt() & 0x00000000FFFFFFFFL; //!!!Cardinal
        crc = b.get();
        if (crc != protocolGS.crc8ccitt(bytes,SIZE_HEAD - 1)) {
            throw new IOException("corrupt CRC");
        }
    }
    public head(byte tag, int len, byte reserv) {
        this.tag = tag;
        this.reserv = reserv;
        this.len = (short)(len & 0xFFFF);
    }
    public head() {
        tag = 0;
        reserv = 0;
        len = 0;
        crc = 0;
    }
    public byte tag;
    public long len;
    byte crc;
    byte reserv = 0;
    public byte[] toBytes(){
        ByteBuffer b = ByteBuffer.allocate(SIZE_HEAD).order(ByteOrder.LITTLE_ENDIAN);
        b.put(tag);
        b.putInt((int)len);
        b.put(protocolGS.crc8ccitt(b.array(),SIZE_HEAD - 1));
        return b.array().clone();
    }
}
