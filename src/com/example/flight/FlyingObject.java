package com.example.flight;

import android.R.string;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import com.badlogic.gdx.assets.AssetManager;

public class FlyingObject implements RenderableProvider {
	MotionProvider m_motionProvider;
	String plane_;
	ModelInstance model_;
	FlyingObject(AssetManager mgr, String modelPath,String plane) {
		super();
		plane_ = plane;
		model_ = ModelsManager.Instance().getModelInstance(modelPath);
	//	m_motionProvider.init(plane);
			// TODO Auto-generated constructor stub
	}
	
	@Override
	public void getRenderables(Array<Renderable> arg0, Pool<Renderable> arg1) {
		// TODO Auto-generated method stub
		model_.getRenderables(arg0, arg1);
	}

	public void setStartingPosition(Vector3 pos) {
		m_motionProvider.setStartingPosition(pos);
	}
	
	public	 boolean initMotionProvider() {
		return m_motionProvider.init(plane_);
	}
    ModelInstance model() {
        return model_;
    }

}
