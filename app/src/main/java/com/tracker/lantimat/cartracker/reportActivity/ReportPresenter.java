package com.tracker.lantimat.cartracker.reportActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tracker.lantimat.cartracker.Client.TCPCommunicator;
import com.tracker.lantimat.cartracker.Client.TCPListener;
import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.quantor.ProtocolGS;
import com.tracker.lantimat.cartracker.utils.FbConstants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
    Report mReport;

    private ArrayList<Report> arReports = new ArrayList<>();
    FirebaseFirestore db;
    // Create a storage reference from our app
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef;

    public ReportPresenter(ReportView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void connect() {
        //connectToServer();
        db = FirebaseFirestore.getInstance();
        storageRef = storage.getReference().child("reportImages");

        loadDate();
    }


    public void loadDate() {

        view.showLoading();

        if(arReports!=null) arReports.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(FbConstants.REPORTS)
                .orderBy("timestamp")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {

                                                   if (task.getResult().size() == 0) {
                                                       view.showToast("Нет данных");
                                                       view.hideLoading();
                                                       return;
                                                   }


                                                   for (DocumentSnapshot document : task.getResult()) {
                                                       Log.d(TAG, document.getId() + " => " + document.getData());
                                                       Report report = document.toObject(Report.class);
                                                       arReports.add(report);
                                                   }
                                                   view.hideLoading();
                                                   Collections.reverse(arReports);
                                                   view.showReports(arReports);
                                               }
                                           }
                                       });
    }

    public void sendData(Report report) {

        if (db != null) {
            // Add a new document with a generated ID
            db.collection(FbConstants.REPORTS)
                    .add(report)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            view.showToast("Отчет об ошибке отправлен!");
                            view.hideAddReportFragment();
                            loadDate();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }
    }

    public void uploadReport(Report report, byte[] data) {
        this.mReport = report;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DDMMYYHHMMSS");

        StorageReference spaceRef = storageRef.child(simpleDateFormat.format(new Date()) + ".jpg");

        UploadTask uploadTask = spaceRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                mReport.setImg(downloadUrl.toString());
                sendData(mReport);

            }
        });
    }

    public void showFragment() {
        view.showAddReportFragment();
    }

    public void hideFragment() {
        view.hideAddReportFragment();
    }











    /** TCP */
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
