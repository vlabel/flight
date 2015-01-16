package com.example.flight;
import android.util.Log;
import com.badlogic.gdx.math.Vector3;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;



public class JSBSender implements Runnable {
	
	  private String serverMessage;
	    public static final String SERVERIP = "localhost"; //your computer IP address
	    public static final int SERVERPORT = 1113;
	    private OnMessageReceived mMessageListener = null;
	    private boolean mRun = false;
	 
	    PrintWriter out;
	    BufferedReader in;
	 
	    /**
	     *  Constructor of the class. OnMessagedReceived listens for the messages received from server
	     */
	    JSBSender() {
	    	po = 1;
			// TODO Auto-generated constructor stub
		}
	       
	    
	 
	    /**
	     * Sends the message entered by client to the server
	     * @param message text entered by client
	     */
	    public void sendMessage(String message){
	        if (out != null && !out.checkError()) {
	            out.println(message);
	            Log.e("TCP Client", "Send message " + message);
	            out.flush();
	        }
	    }
	 
	    
	    public void sendRudder(float rud) {
	    	String str = "set fcs/rudder-cmd-norm " + Float.toString(rud) + "\r\n";
	    	sendMessage(str);
	    }
	    
	    private int po;
	   public void sendNewPoint(Vector3 vec) {
		   
	    	String str = "set guidance/target_wp_latitude_rad " + Float.toString(vec.y) + "\r\n";
	    	sendMessage(str);
	    	String str1 = "set guidance/target_wp_longitude_rad " + Float.toString(vec.x) + "\r\n";
	    	sendMessage(str1);
	    	String str2 = "set ap/altitude_setpoint " + Float.toString(vec.z/PlanePosition.FEET) + "\r\n";
	    	sendMessage(str2);
	    	int pod=++po;
	   /* 	String str5 = "set ap/heading-setpoint-select " + Float.toString(pod) + "\r\n";
	    	sendMessage(str5);*/
	    	
	    	
	    	String str3 = "set ap/active-waypoint " + Integer.toString(pod) + "\r\n";
	    	sendMessage(str3);
	    	String str4 = "set ap/altitude_hold " + Integer.toString(pod) + "\r\n";
	    	sendMessage(str4);
	    }
	    
	    
	    public void sendAileron(float rud) {
	    	String str = "set fcs/aileron-cmd-norm " + Float.toString(rud) + "\r\n";
	    	sendMessage(str);
	    }
	    public void sendEleator(float eleator) {
	    	String str = "set fcs/elevator-cmd-norm " + Float.toString(eleator) + "\r\n";
	    	sendMessage(str);
	    }
	    
	    public void stopClient(){
	        mRun = false;
	    }
	 
	    public void run() {
	 
	        mRun = true;
	 
	        try {
	            //here you must put your computer's IP address.
	            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
	 
	            Log.e("TCP Client", "C: Connecting..." + serverAddr.toString());
	 
	            //create a socket to make the connection with the server
	            Socket socket = new Socket(serverAddr, SERVERPORT);
	 
	            try {
	 
	                //send the message to the server
	                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
	 
	                Log.e("TCP Client", "C: Sent.");
	 
	                Log.e("TCP Client", "C: Done.");
	 
	                //receive the message which the server sends back
	                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	 
	                //in this while the client listens for the messages sent by the server
	                while (mRun) {
	                    serverMessage = in.readLine();
	 
	                    
	                   
	                    Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + serverMessage + "'");
	                    serverMessage = null;
	                }
	 
	 
	                Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + serverMessage + "'");
	 
	 
	            } catch (Exception e) {
	 
	                Log.e("TCP", "S: Error", e);
	 
	            } finally {
	                //the socket must be closed. It is not possible to reconnect to this socket
	                // after it is closed, which means a new socket instance has to be created.
	                socket.close();
	            }
	 
	        } catch (Exception e) {
	 
	            Log.e("TCP", "C: Error", e);
	 
	        }
	 
	    }
	 
	    //Declare the interface. The method messageReceived(String message) will must be implemented in the MyActivity
	    //class at on asynckTask doInBackground
	    public interface OnMessageReceived {
	        public void messageReceived(String message);
	    }
	
	
	
	

}
