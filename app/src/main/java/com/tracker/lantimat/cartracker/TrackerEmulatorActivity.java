package com.tracker.lantimat.cartracker;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tracker.lantimat.cartracker.Client.EnumsAndStatics;
import com.tracker.lantimat.cartracker.Client.SettingsActivity;
import com.tracker.lantimat.cartracker.Client.TCPCommunicator;
import com.tracker.lantimat.cartracker.Client.TCPListener;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class TrackerEmulatorActivity extends Activity implements TCPListener {

    private TCPCommunicator tcpClient;
    private ProgressDialog dialog;
    public static String currentUserName;
    private Handler UIHandler = new Handler();
    private boolean isFirstLoad=true;
    String theMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker_emulator);

        ConnectToServer();
    }

    private void ConnectToServer() {
        setupDialog();
        tcpClient = TCPCommunicator.getInstance();
        TCPCommunicator.addListener(this);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        tcpClient.init(settings.getString(EnumsAndStatics.SERVER_IP_PREF, "support.geostron.ru"),
                Integer.parseInt(settings.getString(EnumsAndStatics.SERVER_PORT_PREF, "20200")));
    }



    private void setupDialog() {
        dialog = new ProgressDialog(this,ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

    @Override
    protected void onStop()
    {
        super.onStop();

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        setContentView(R.layout.activity_tracker_emulator);
        if(!isFirstLoad)
        {
            //TCPCommunicator.closeStreams();
            //ConnectToServer();
        }
        else
            isFirstLoad=false;
    }
    public void btnSendClick(View view)
    {
        JSONObject obj = new JSONObject();
        EditText txtName= (EditText)findViewById(R.id.txtUserName);
        if(txtName.getText().toString().length()==0)
        {
            Toast.makeText(this, "Please enter text", Toast.LENGTH_SHORT).show();
            return;
        }
        try
        {
            //obj.put(EnumsAndStatics.MESSAGE_TYPE_FOR_JSON, EnumsAndStatics.MessageTypes.MessageFromClient);
            //obj.put(EnumsAndStatics.MESSAGE_CONTENT_FOR_JSON, txtName.getText().toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //byte[] packet = createMsg("100", (short)15, (short)82, '1', "\r\n");
        TCPCommunicator.writeToSocket(txtName.getText().toString(), UIHandler,this);
        //dialog.show();

    }

    @Override
    public void onTCPMessageRecieved(String message) {
        // TODO Auto-generated method stub


            theMessage = message;

        //Log.d("msg from server", message);
       /* try {
            JSONObject obj = new JSONObject(message);
            String messageTypeString=obj.getString(EnumsAndStatics.MESSAGE_TYPE_FOR_JSON);
            EnumsAndStatics.MessageTypes messageType = EnumsAndStatics.getMessageTypeByString(messageTypeString);

            switch(messageType)
            {

                case MessageFromServer:
                {*/

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            EditText editTextFromServer =(EditText)findViewById(R.id.editTextFromServer);
                            editTextFromServer.setText(theMessage);
                        }
                    });

                    //break;
                //}


            //}
        //} catch (JSONException e) {
            // TODO Auto-generated catch block
           // e.printStackTrace();
       // }

    }

    @Override
    public void onTCPConnectionStatusChanged(boolean isConnectedNow) {
        // TODO Auto-generated method stub
        if(isConnectedNow)
        {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "Connected to server", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    public void btnSettingsClicked(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    public static byte[] createMsg(String login, String pass, short packetle, short cmd, char cErrorCode, String sEnd){
        byte[] buffer = new byte[13];


        byte[] header = new BigInteger("100",10).toByteArray();


        char[] sLoginArr = login.toCharArray();
        char[] sEndArr = sEnd.toCharArray();

        // header
        buffer[0] = (byte) sLoginArr[header[0]];
        buffer[1] = (byte) sLoginArr[1];
        buffer[2] = (byte) sLoginArr[2];
        buffer[3] = (byte) sLoginArr[3];

        //
        buffer[4] = (byte) sLoginArr[4];
        buffer[5] = (byte) sLoginArr[5];

        // packetle
        buffer[6] = (byte)(packetle & 0xFF);
        buffer[7] = (byte)((packetle >> 8) & 0xFF);

        // cmd
        buffer[8] = (byte)(cmd & 0xFF);
        buffer[9] = (byte)((cmd >> 8) & 0xFF);

        // cErrorCode
        buffer[10] = (byte)(cErrorCode);

        // sEnd
        buffer[11] = (byte)sEndArr[0];
        buffer[12] = (byte)sEndArr[1];

        return buffer;
    }
}
