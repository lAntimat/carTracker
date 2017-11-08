package com.tracker.lantimat.cartracker.quantor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * Created by Nikolay Zlotnikov on 24.10.2017.
 */

public class ProtocolGS {
    public final static int CRC_POLYNOM = 0x31;
    public final static int CRC_PRESET = 0xFF;
    public final static short SIZE_HEAD = 0x5;
    public final static short SIZE_LOGIN = 0x27;
    public final static short SIZE_MONITOR = 0x20;
    public final static short SIZE_NOOP = 0x1;
    public final static short SIZE_RESPONSE = 0x1;
    public final static short SIZE_ERROR = 0x5;
    public final static short SIZE_COUNT = 0x4;
    public final static short SIZE_SEND_REPORT = 0x151;
    public final static short SIZE_ROUTE_RECORD = 0x151;

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

    public response parseResponse(byte[] bytes, int offset, int count) {
        response RS = new response();
        RS.error = bytes[5];
        return RS;
    }

    public head parseHeader(byte[] bytes, int offset, int count) {
        head H = null;
        try {
            H = new head(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return H;
    }

    public static byte[] packetLogin(loginPack L) throws IOException {
        head H = new head();
        H.tag = 0;
        H.len = SIZE_HEAD + SIZE_LOGIN;
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        b.write(H.toBytes());
        b.write(L.toBytes());
        return b.toByteArray();
    }

    public byte[] packetSRR(sendreqreport SR, short Count, error[] E, byte[] Photo, short len) throws IOException {
        head H = new head();
        count C = new count();
        H.tag = 100;
        H.len = SIZE_HEAD + SIZE_SEND_REPORT + SIZE_COUNT;
        if (Count > 0) {
            C.count = Count;
            H.len = (short) (H.len + (short) (Count * SIZE_ERROR));
        }
        if (len > 0) {
            H.len = (short) (H.len + len);
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

    public class loginPack {
        public String login = ""; //10
        public String pwd = "";   //10
        public byte[] imei = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //16
        public byte[] ver = {0, 0, 0};                            // 3

        public byte[] toBytes() throws IOException {
            int i = 0;
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            if (login == null || login.length() <= 0) {
                throw new RuntimeException("Login is empty");
            } else if (login.length() > 10) {
                login = login.substring(0, 9);
            }
            if (pwd == null || pwd.length() <= 0) {
                throw new RuntimeException("Password is empty");
            } else if (pwd.length() > 10) {
                pwd = pwd.substring(0, 9);
            }
            if (imei.length < 15) {
                throw new RuntimeException("IMEI is empty");
            }
            b.write(login.getBytes("UTF-8"));
            i = (10 - login.length());
            while (i > 0) {
                if (i > 0) {
                    b.write(0);
                    i--;
                }
            }
            b.write(pwd.getBytes("UTF-8"));
            i = (10 - pwd.length());
            while (i > 0) {
                if (i > 0) {
                    b.write(0);
                    i--;
                }
            }
            b.write(0);
            b.write(imei);
            b.write(ver);
            return b.toByteArray();
        }
    }

    public static class sendreqreport {



        public byte[] imei = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //16
        public byte[] hash = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //16
        public String title = "TestTitle"; //50
        public String msg = "TestMsg";   //255

        public byte[] toBytes() throws IOException {
            int i = 0;
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            b.write(0);
            b.write(imei);
            b.write(hash);
            if(title.length() > 49)
            title = title.substring(0, 49);
            b.write(title.getBytes("UTF-8"));
            i = (50 - title.length());
            while (i > 0) {
                if (i > 0) {
                    b.write(0);
                    i--;
                }
            }
            if(msg.length() > 254)
            msg = msg.substring(0, 254);
            b.write(msg.getBytes("UTF-8"));
            i = (255 - msg.length());
            while (i > 0) {
                if (i > 0) {
                    b.write(0);
                    i--;
                }
            }
            return b.toByteArray();
        }

    }

    public static class head {
        public head(byte[] bytes) throws IOException {
            tag = bytes[0];
            len = (short) ((bytes[2] << 8) ^ bytes[1]);
            crc = bytes[3];
            reserv = bytes[4];
            if (crc != ProtocolGS.crc8ccitt(bytes, 3)) {
                throw new IOException("corrupt CRC");
            }
        }

        public head(byte tag, int len, byte reserv) {
            this.tag = tag;
            this.reserv = reserv;
            this.len = (short) (len & 0xFFFF);
        }

        public head() {
            tag = 0;
            reserv = 0;
            len = 0;
            crc = 0;
        }

        public byte tag;
        public short len;
        byte crc;
        byte reserv = 0;

        public byte[] toBytes() {
            byte[] res = new byte[5];
            res[0] = tag;
            res[1] = (byte) (len & 0xFF);
            res[2] = (byte) ((len >> 8) & 0xFF);
            res[3] = ProtocolGS.crc8ccitt(res, 3);
            res[4] = reserv;
            return res;
        }
    }

    public static class error {
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

    public static class count {
        public int count = 0;

        public byte[] toBytes() throws IOException {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            b.write(count);
            return b.toByteArray();
        }
    }

    public class response {
        public byte error = 0;

        public byte[] toBytes() throws IOException {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            b.write(error);
            return b.toByteArray();
        }
    }
}