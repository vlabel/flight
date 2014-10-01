package com.example.flight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import android.content.res.*;
public class StartGame extends Game {
	
	public AssetManager mgr;
	public android.content.res.AssetManager manager;
	public BaseApp base;
	
	StartGame(android.content.res.AssetManager m,BaseApp b) {
		base = b;
		manager = m;
		
	}
	
    public void create() {
    	base.setA(manager);
    	mgr = new AssetManager();
        setScreen(new GamePlay(this));
   }
    

}
