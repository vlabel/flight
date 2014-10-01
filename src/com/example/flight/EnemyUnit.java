package com.example.flight;



import com.badlogic.gdx.assets.AssetManager;

public class EnemyUnit extends FlyingObject {

	EnemyUnit(AssetManager mgr, String modelPath, String plane,JSBMotionProvider jsbMotionProvider) {
		super(mgr, modelPath, plane);
		m_motionProvider = jsbMotionProvider;
		initMotionProvider();
		// TODO Auto-generated constructor stub
	}
	

}
