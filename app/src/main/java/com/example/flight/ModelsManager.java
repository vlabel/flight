package com.example.flight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.assets.AssetManager;
public class ModelsManager {
	private Map<String,Model> models_;
	private AssetManager assetManager_;
	private static  ModelsManager instance_;
	private ModelsManager() {
		models_ = new HashMap<String,Model>();
	}
	
	public static final ModelsManager Instance() {
		if (instance_ == null) {
		instance_ = new ModelsManager(); }
		return instance_;
	}
	
	
	
	public void setAssetManager(AssetManager mgr) {
		assetManager_ = mgr;
	}
	
	
	
	public void addModel(String model) {
	    assetManager_.load(model, Model.class);
	    assetManager_.finishLoading();
	    models_.put(model, assetManager_.get(model,Model.class));
    }
	
	public void addModels(List<String> models) {
		for (String str : models) {
			assetManager_.load(str, Model.class);
		}
		assetManager_.finishLoading();
		for (String str : models) {
		    models_.put(str, assetManager_.get(str,Model.class));
		}
	}
	
	
	public ModelInstance getModelInstance(String str) {
		Model model = models_.get(str);
		return new ModelInstance(model);
	}
	
	

	
	
}
