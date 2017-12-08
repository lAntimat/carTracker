package com.tracker.lantimat.cartracker.Client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


public class TCPCommunicator {
	private static TCPCommunicator uniqInstance;
	private static String serverHost;
	private static int serverPort;
	private static List<TCPListener> allListeners;
	private static BufferedOutputStream out;
	private static BufferedInputStream in;
	private static Socket s;
	private static Handler UIHandler;
	private static Context appContext;


	private static byte[] buffer = new byte[1000];    //If you handle larger data use a bigger buffer size


	private TCPCommunicator()
	{
		allListeners = new ArrayList<TCPListener>();
	}
	public static TCPCommunicator getInstance()
	{
		if(uniqInstance==null)
		{
			uniqInstance = new TCPCommunicator();
		}
		return uniqInstance;
	}
	public  TCPWriterErrors init(String host, int port)
	{
		setServerHost(host);
		setServerPort(port);
		InitTCPClientTask task = new InitTCPClientTask();
		task.execute(new Void[0]);
		return TCPWriterErrors.OK;
	}
	public static  TCPWriterErrors writeToSocket(final List<byte[]> ar , Handler handle, Context context) {
		UIHandler=handle;
		appContext=context;

		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try
				{
			        //String outMsg = obj.toString() + System.getProperty("line.separator");
			        //out.write(str);
					//buffer = protocolGS.packetLogin(H, L);
					//buffer = bytes;
					for (byte[] b: ar
						 ) {
						out.write(b);
						Log.d("dump", bytesToHex(b, 0, b.length));
					}
			        out.flush();
			        //Log.i("TcpClient", "sent: " + out.toString());



				}
				catch(Exception e)
				{
					UIHandler.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(appContext ,"a problem has occured, the app might not be able to reach the server", Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
			
		};
		Thread thread = new Thread(runnable);
		thread.start();
		return TCPWriterErrors.OK;
		
	}
	
	public static void addListener(TCPListener listener) {
		allListeners.clear();
		allListeners.add(listener);
	}
	public static void removeAllListeners()
	{
		allListeners.clear();
	}
	public static void closeStreams() {
		try
		{
			s.close();
			in.close();
			out.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex( byte[] bytes, int offset, int length) {
		char[] hexChars = new char[length * 2];
		for ( int j = offset; j < length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);

	}
	

	public static String getServerHost() {
		return serverHost;
	}
	public static void setServerHost(String serverHost) {
		TCPCommunicator.serverHost = serverHost;
	}
	public static int getServerPort() {
		return serverPort;
	}
	public static void setServerPort(int serverPort) {
		TCPCommunicator.serverPort = serverPort;
	}


	public byte[] toByteArray(InputStream inputStream) throws IOException {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[1024];
		while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();
		//byte[] byteArray = buffer.toByteArray();
		//Log.d("dump", "received " + buffer.toByteArray());
		//Log.d("dump", "received string " + buffer.toString("UTF-8"));
		return buffer.toByteArray();
	}

	public class InitTCPClientTask extends AsyncTask<Void, Void, Void>
	{
		public InitTCPClientTask()
		{
			
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try
			{
				s = new Socket(getServerHost(), getServerPort());
		         in = new BufferedInputStream(new BufferedInputStream(s.getInputStream()));
		         out = new BufferedOutputStream(new BufferedOutputStream(s.getOutputStream()));
		         for(TCPListener listener:allListeners)
			        	listener.onTCPConnectionStatusChanged(true);
		        while(true)
		        {

					//String inMsg = new String(toByteArray(in))
					byte[] byteArray = toByteArray(in);
					String inMsg = new String(byteArray, "UTF-8");

		            /*if(temp!=null) {
		                byte[] bytes = temp.getBytes();
                        String inMsg = new String(bytes, "Windows-1251");*/

		        	if(!inMsg.isEmpty())
		        	{
						Log.d("dump", "received " + bytesToHex(byteArray, 0, byteArray.length));
						Log.i("TcpClient", "received: " + inMsg);
				        for(TCPListener listener:allListeners)
				        	listener.onTCPMessageReceived(bytesToHex(byteArray, 0, byteArray.length));
		        	}
		        }

		    } catch (UnknownHostException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } 
			
			return null;
			
		}
		
	}
	public enum TCPWriterErrors{UnknownHostException,IOException,otherProblem,OK}
}
