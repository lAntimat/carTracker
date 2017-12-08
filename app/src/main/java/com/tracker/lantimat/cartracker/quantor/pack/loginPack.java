package com.tracker.lantimat.cartracker.quantor.pack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Pre77 on 25.10.2017.
 */

public class loginPack {
    public String login = ""; //10
    public String pwd = "";   //10
    public byte[] imei = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; //16
    public byte[] ver = {0,0,0};                            // 3
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