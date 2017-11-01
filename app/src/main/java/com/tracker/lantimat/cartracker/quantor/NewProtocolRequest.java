package com.tracker.lantimat.cartracker.quantor;

import android.os.AsyncTask;
import android.util.Log;

import com.tracker.lantimat.cartracker.quantor.pack.head;
import com.tracker.lantimat.cartracker.quantor.pack.login;

import java.io.BufferedInputStream;

import java.io.BufferedOutputStream;
import java.io.InputStream;

import java.net.Socket;


/**
 * Created by Pre77 on 25.10.2017.
 */

public class NewProtocolRequest extends AsyncTask<String, String, String>  {
//        public ru.quantor.pre77.phone.classes.RequestReportTrack.AsyncResponse delegate = null;
        String TAG = "reporttrack thread";

        @Override
        protected String doInBackground(String... params) {
            String Time = "";
            byte[] buffer = new byte[1000];    //If you handle larger data use a bigger buffer size
            int read;
            String _request = "";
            head H = new head();
            login L = new login();
            H.tag = 0;
            L.login = "test";
            L.pwd = "test";
            L.imei = "1234567891234567".getBytes();
            L.ver = new byte[]{7,7,8};

            try {
//                InetAddress serverAddr = InetAddress.getByName(serverIpAddress);
                Socket socket;
                BufferedOutputStream out;
                InputStream inBS;
                BufferedInputStream inputS;

                try {
                    socket = new Socket("support.geostron.ru",20200);
                    publishProgress(Integer.toString(0), "Соединение установлено");
                    boolean connected = true;
                    out = new BufferedOutputStream(socket.getOutputStream());
                    inBS = socket.getInputStream();
                    inputS = new BufferedInputStream(inBS);
                    buffer = protocolGS.packetLogin(H, L);
                    out.write(buffer);
                    out.flush();
                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: " + e.getMessage());
                }
            } catch (Exception e) {
                Log.d(TAG, "TaskExecute: " + e.getMessage());
            }
            return _request;
        }

        @Override
        protected void onPostExecute(String result) {
//            delegate.processFinish(result);
        }

        @Override
        protected void onProgressUpdate(String... s) {
            super.onProgressUpdate(s);
//            progress.setValue(Integer.parseInt(s[0]), s[1]);
        }

        public interface AsyncResponse {
//            void processFinish(String output);
        }
    }