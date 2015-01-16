package com.example.flight;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

import android.util.Log;
public class GroupLoader {
	private static final String TAG = "GroupLoader";
	private AssetManager m_mgr;
	private String m_landName;
	private int m_x;
	private int m_y;
	private List<Model> m_models = new ArrayList<Model>();
	public List<ModelInstance> m_instances = new ArrayList<ModelInstance>();
	
	
	public void setManager(AssetManager m) {
		m_mgr = m;
	}
	
	public void  setMaxs(int x,int y) {
		m_x = x;
		m_y = y;
	}

	public void setName(String nm) {
		m_landName = nm;
	}
	
	public void  init () {
		for (int x = m_x;x >= 0;x--) {
			for(int y = m_y;y >= 0;y--) {
				String fileName = "data/1/" + m_landName + "_x"
			+ Integer.toString(x) + "y" + Integer.toString(y) + ".g3db";
							
				if ((x == 0) && (y == 0)) {
					fileName = "data/1/" + m_landName + ".g3db";
				}		
			   Log.w(TAG,fileName);
				m_mgr.load(fileName, Model.class);
			    m_mgr.finishLoading();
				Model mo = new Model();
				mo = m_mgr.get(fileName, Model.class);
                		
				m_models.add(mo);
				ModelInstance inst = new ModelInstance(mo);
				inst.transform.scale(10.0f, 10.0f, 10.0f);
				 Vector3 position = new Vector3();
				inst.transform.getTranslation(position);
				inst.transform.setToTranslation(0, 0, -1000).scale(5f, 5f, 1f);
				Log.w(TAG,Float.toString(position.y));
				Log.w(TAG,Float.toString(position.z));
				Log.w(TAG,"4");
				m_instances.add(inst);
 			}
		}
	}
	
	
	
	
		
}
