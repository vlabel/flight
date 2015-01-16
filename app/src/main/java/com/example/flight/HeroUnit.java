package com.example.flight;

import android.R.string;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

import java.util.ArrayList;

public class HeroUnit extends FlyingObject{
	private SimpleMotion _motion;
	HeroUnit(AssetManager mgr, String modelPath, String plane,boolean auto) {
		
		super(mgr, modelPath, plane);
			//initMotionrovider();
		_motion = new SimpleMotion();
		ModelInstance m = model();
		_motion.setAuto(auto);

        ArrayList<Vector3> rr = new ArrayList<Vector3>();
        rr.add(new Vector3(4000,0,500));
        rr.add(new Vector3(-8000,0,400));
        rr.add(new Vector3(12000,2000,2000));
        rr.add(new Vector3(20000,-300,500));
        rr.add(new Vector3(10,3310,200));
        rr.add(new Vector3(3110,10,700));
        rr.add(new Vector3(10,3310,100));
        rr.add(new Vector3(-3110,10,0));
        rr.add(new Vector3(10,-3310,0));
        rr.add(new Vector3(0,0,0));


//      rr.add(new Vector3(3110,10,3333));
//      rr.add(new Vector3(3110,10,333));
//      rr.add(new Vector3(3110,10,3333));
//        rr.add(new Vector3(3110,10,333));
//        rr.add(new Vector3(3110,10,3333));
//        rr.add(new Vector3(3110,10,333));
//        rr.add(new Vector3(3110,10,3333));
//        rr.add(new Vector3(3110,10,333));
//        rr.add(new Vector3(3110,10,3333));
              _motion.setRoute(rr);
			  // TODO Auto-generated constructor stub
	}

	
	
	
 public Vector3 position() {
	return _motion.position();
}

 public Quaternion orientation () {
	 return _motion.orientation();
 }

 public Quaternion resultOrientation () {
	 return _motion.resultOrientation();
 }
 
 public Ray ray() {
		return _motion.directionRay();
	}


public void update(double seconds,float sliderH,float sliderV,float sliderR) {
  _motion.setHEnforce(sliderH);
  _motion.setVEnforce(sliderV);
  _motion.setREnforce(sliderR);
  _motion.update(seconds); 
  ModelInstance m = model();
  float x = _motion.position().x;
  float y = _motion.position().y;
  float z = _motion.position().z;
  // m.transform.set(_motion.position(), _motion.orientation());
  m.transform.set(_motion.position(), _motion.resultOrientation());
}
}
