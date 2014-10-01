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
