package com.example.flight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import android.content.res.*;
import android.util.Log;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class StartGame extends Game {
	
	public AssetManager mgr;
	public android.content.res.AssetManager manager;
	public BaseApp base;
    public BitmapFont font;

    StartGame(android.content.res.AssetManager m,BaseApp b) {
		base = b;
		manager = m;
		
	}
	
    public void create() {
    //	base.setA(manager);
        Log.w("LifeCycle", "StartGameCreate");

      //  font = generator.generateFont(param); // Генерируем шрифт
    	mgr = new AssetManager();
        setScreen(new Splash(this));
   }





    

}
