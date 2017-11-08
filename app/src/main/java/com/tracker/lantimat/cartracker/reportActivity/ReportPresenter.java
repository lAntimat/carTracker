package com.tracker.lantimat.cartracker.reportActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.tracker.lantimat.cartracker.Client.TCPCommunicator;
import com.tracker.lantimat.cartracker.Client.TCPListener;
import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.quantor.ProtocolGS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by GabdrakhmanovII on 07.11.2017.
 */

public class ReportPresenter implements TCPListener {

    final static String TAG = "ReportPresenter";

    public ReportView view;
    Context context;

    private TCPCommunicator tcpClient;
    private ProgressDialog dialog;
    public static String currentUserName;
    private Handler UIHandler = new Handler();
    private boolean isFirstLoad=true;
    String theMessage = "";

    public ReportPresenter(ReportView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void connect() {
        connectToServer();
    }

    public void sendDate() {


        try {

        ProtocolGS.sendreqreport report = new ProtocolGS.sendreqreport(); //Пакет с отчетом

            ProtocolGS protocolGS = new ProtocolGS();

        ProtocolGS.error er = new ProtocolGS.error();
        ProtocolGS.error[] error = new ProtocolGS.error[2];
        error[0] = er;

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.car);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();



        List<byte[]> list = new ArrayList<>();
/*        list.add(header.toBytes());
        list.add(report.toBytes());
        list.add(count.toBytes());
        list.add(error.toBytes());*/

        int length = report.toBytes().length + byteArray.length;
        list.add(protocolGS.packetSRR(report, (short) 0, error,  byteArray, (short)length ));

        TCPCommunicator.writeToSocket(list, UIHandler, context);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void connectToServer() {
        tcpClient = TCPCommunicator.getInstance();
        TCPCommunicator.addListener(this);
        tcpClient.init("support.geostron.ru",
                Integer.parseInt("20200"));
    }


    @Override
    public void onTCPMessageReceived(String message) {

    }

    @Override
    public void onTCPConnectionStatusChanged(boolean isConnectedNow) {

//        Toast.makeText(context, "connect" + isConnectedNow, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "connect status " + isConnectedNow);

    }
}
