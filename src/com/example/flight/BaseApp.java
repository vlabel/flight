package com.example.flight;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.example.flight.StartGame;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import android.os.Bundle;
import android.content.res.AssetManager;
		
public class BaseApp extends  AndroidApplication {
	public AssetManager mgr;
	  static {
//	    	System.loadLibrary("gnustl_shared");
//	        System.loadLibrary("stlport_shared");
//	        System.loadLibrary("JSBSim");
//	        System.loadLibrary("JSBVova");
//	        System.loadLibrary("jsbj");
		  System.loadLibrary("gdx");
	    }
//	public native void setA(AssetManager magr)
//    {mgr = magr}
  
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useWakelock = true;
       // config.useGL20 = true;
              
        
      
        mgr =  this.getAssets();
      //  setA(mgr);
                   
        initialize(new StartGame(mgr,this), config);
    }
  
    
    
  //  public  native void init2();
  

}  


