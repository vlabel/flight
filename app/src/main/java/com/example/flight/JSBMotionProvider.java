package com.example.flight;

import android.content.res.AssetManager;
import android.util.Log;

public class JSBMotionProvider  extends MotionProvider {
	private int m_planeID;
	static String  tag = "JSBMProvider";
	
/*	static {
		System.loadLibrary("gnustl_shared");
		System.loadLibrary("stlport_shared");
		System.loadLibrary("JSBSim");
        System.loadLibrary("JSBVova");
        System.loadLibrary("jsbj");
	}*/
	
	@Override
	public boolean init(String name) {
			
	 		// TODO Auto-generated method stub
	        m_planeID = createPlane(name);
	        Log.w(tag,"createPlane " + name + " " +Long.toString(m_planeID));
	        return false;
	}	
	
	
	
	
	

    public  native int createPlane(String name);
	
		
}
