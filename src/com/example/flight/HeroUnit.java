package com.example.flight;

import android.R.string;

import com.badlogic.gdx.assets.AssetManager;

public class HeroUnit extends FlyingObject{
	private SimpleMotion _motion;
	HeroUnit(AssetManager mgr, String modelPath, String plane) {
		
		super(mgr, modelPath, plane);
			//initMotionrovider();
			_motion = new SimpleMotion();
		// TODO Auto-generated constructor stub
	}

}


public void update(double seconds) {
   _motion.update(second); 
   ModelInstance m = model();
   double x = _motion.position().x();
   double y = _motion.position().y();
   double z = _motion.position().z();
   m.transform.setToTranslation(x,y,z);
}
