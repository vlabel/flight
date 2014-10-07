package com.example.flight;

import android.R.string;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class HeroUnit extends FlyingObject{
	private SimpleMotion _motion;
	HeroUnit(AssetManager mgr, String modelPath, String plane) {
		
		super(mgr, modelPath, plane);
			//initMotionrovider();
			_motion = new SimpleMotion();
		// TODO Auto-generated constructor stub
	}

 public Vector3 position() {
	return _motion.position();
}

 public Ray ray() {
		return _motion.directionRay();
	}


public void update(double seconds,float sliderH,float sliderV) {
  _motion.setHenforce(sliderH);
  _motion.setVEnforce(sliderV);
  _motion.update(seconds); 
  ModelInstance m = model();
  float x = _motion.position().x;
  float y = _motion.position().y;
  float z = _motion.position().z;
  m.transform.setToTranslation(x,y,z);
}
}
