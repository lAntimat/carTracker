package com.tracker.lantimat.cartracker.Client;

public interface TCPListener {
	public void onTCPMessageReceived(String message);
	public void onTCPConnectionStatusChanged(boolean isConnectedNow);
}
