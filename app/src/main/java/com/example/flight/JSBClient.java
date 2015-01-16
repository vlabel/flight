package com.example.flight;
import com.example.flight.PlanePosition;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import android.util.Log;
public class JSBClient implements Runnable {
	  ServerSocket serverSocket;

	private static final String TAG = "JSBClient";
	@Override
	public void run() {
		// TODO Auto-generated method stub
	Log.w(TAG,"Server: Run"); 
	String incomingMsg;

    try {
    	Log.w(TAG,"Starting socket thread...");

        serverSocket = new ServerSocket(1112);

        Log.w(TAG,"ServerSocket created, waiting for incomming connections...");

        Socket socket = serverSocket.accept();
        Log.w(TAG,"ServerSocket after accept");
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                socket.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));

        while (true) {
            // System.out.println("Connection accepted, reading...");
        	
            while ((incomingMsg = in.readLine()) != null && socket.isConnected()) {
            	Log.w(TAG,"Message: " + incomingMsg);
            	parseMessage(incomingMsg);
            	
          }

            if (socket.isConnected()) Log.w(TAG,"Socket still connected");
            else Log.w(TAG,"Socket not connected");
        }

    } catch (Exception e) {
    	Log.w(TAG,"Error: " + e.getMessage());
        e.printStackTrace();
    }

			
		}
	
	
	private void parseMessage(String msg) {
		String[] parse = msg.split(",");
    	
    	Log.w(TAG,"Message: " + parse[1]);
    	float alt;
    	float phi;
    	float tht;
    	float psi;
    	float alpha;
    	float betta;
    	float lat;
    	float lon;
    	float xx;
    	float yy;
    	float zz;
    	float phi2;
    	float tht2;
    	float psi2;
    
    	try {
    		alt = Float.parseFloat(parse[1]);
    		phi = Float.parseFloat(parse[2]);
        	tht = Float.parseFloat(parse[3]);
        	
    	} catch (NumberFormatException E) {
    		Log.w(TAG,"Message: !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    		return;
    	}
    	try {
        	psi = Float.parseFloat(parse[4]);
        	alpha = Float.parseFloat(parse[5]);
        	betta = Float.parseFloat(parse[6]);
        	lat = Float.parseFloat(parse[7]);
        	lon = Float.parseFloat(parse[8]);
        	
        	xx = Float.parseFloat(parse[9]);
        	yy = Float.parseFloat(parse[10]);
        	zz = Float.parseFloat(parse[11]);
        	phi= Float.parseFloat(parse[12]);
        	tht= Float.parseFloat(parse[13]);
        	psi= Float.parseFloat(parse[14]);
        	
    	} catch (NumberFormatException E) {
    		Log.w(TAG,"Message: !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    		return;
    	}
    	PlanePosition.setPosition(xx, yy, zz);
    	PlanePosition.setAlgles(phi, tht, psi);
    	PlanePosition.setAB(alpha, betta);
		
		
	}

	

}
