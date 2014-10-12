package com.example.flight;
import com.example.flight.GroupLoader;

import com.example.flight.PlanePosition;
import com.example.flight.JSBSender;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Vector;

import android.renderscript.Type;
import android.text.method.Touch;
import android.util.Log;
//import static com.example.flight.R.	
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.*;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Texture;
enum ModelType{ JSBMODEL,SIMPLEMODEL };
public class GamePlay implements Screen {
	public Environment environment;
	public PerspectiveCamera cam;
	public CameraInputController camController;
	private ModelsManager models_;
	public ModelBatch modelBatch;
	public Model runway;
	public ModelInstance runwayInstance;
	public Model skydome;
	public ModelInstance skyDomeInstance;
	public StartGame game;
	public GroupLoader loader;
	private static final String TAG = "GamePlay";
	private JSBMotionProvider m_jsbProvider;
		
	private ArrayList<EnemyUnit> enemies_;
	private HeroUnit hero_;
	
    private Stage stage;
    private SpriteBatch batch;
    private Touchpad touchpad;

    private TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    
    private Slider slider;
    private Slider sliderAiler;
    private SliderStyle sliderStyle;
    private Skin sliderSkin;
    
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Drawable sliderBackground;
    private Drawable sliderKnob;
    @Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
    
    public void setEnemies(int num) {
    	enemies_.clear();
    	for (int i = 0 ; i < num; i++) {
    		EnemyUnit unit = new EnemyUnit(game.mgr,"data/Su-27_Flanker.g3db","Su-27",m_jsbProvider);
    		unit.initMotionProvider();
    		enemies_.add(unit);
    	}
    }
    

    private void createHero() {
    	hero_ = new HeroUnit(game.mgr,"data/Su-27_Flanker.g3db" , "Su-27");
    }

    
	public GamePlay(StartGame gm) {
		enemies_ = new ArrayList<EnemyUnit>();
		game = gm;
 		loader = new GroupLoader();
        loader.setMaxs(0, 0);
	    loader.setName("1");
	    loader.setManager(game.mgr);
	    loader.init();
	    m_jsbProvider = new JSBMotionProvider();
	    /* Test Create */
	    environment = new Environment();
	    environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 0f, 0f, -1f));
		modelBatch = new ModelBatch();
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.near = 0.1f;
		cam.far = 7500f;
    	Vector3 v1 = new Vector3(1,0,0);
		cam.rotate(v1, 90);
		cam.update();
		models_.Instance().setAssetManager(game.mgr);
		models_.Instance().addModel("data/Su-27_Flanker.g3db");
		models_.Instance().addModel("data/sky/sky.g3db");
		models_.Instance().addModel("runway/runway.g3db");
		setEnemies(3);
        createHero();
		
			    runwayInstance = models_.Instance().getModelInstance("runway/runway.g3db");
		        Quaternion qua2 = new Quaternion();
				qua2.setEulerAngles(90, 0,90);
				Vector3 net = new Vector3(-1000,0,0);
				Vector3 sc = new Vector3(120f,120f, 120f);
				runwayInstance.transform.set(net,qua2, sc);
				
		        skyDomeInstance = models_.Instance().getModelInstance("data/sky/sky.g3db");
		        skyDomeInstance.transform.setToScaling(14.0f, 14.0f, 4.0f);
		        Gdx.input.setInputProcessor(camController);
		        createSlider();
		        createJoy();
		        createSliderAiler();
		    }

	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Log.w("Render ","lla " +Float.toString(delta));
	    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        modelBatch.begin(cam);
        validatePosition();
        //modelBatch.render(skyDomeInstance, environment);
        modelBatch.render(loader.m_instances,environment);
        modelBatch.render(hero_.model(),environment);
       // modelBatch.render(models),environment);// get all models from 
        // modelsManager
        updateCamera();
        hero_.update(delta,slider.getValue(),sliderAiler.getValue());
//        slider.setValue(0);
//        sliderAiler.setValue(0);
        modelBatch.end();
        stage.act(Gdx.graphics.getDeltaTime());            
        stage.draw();
    }

	private void updateCamera() {
		
		
		//Log.w("Positon","Position " +Float.toString(hero_.position().x) + " " + Float.toString(hero_.position().y)+ " " + Float.toString(hero_.position().z));
		Vector3 end  = new Vector3(0,0,0); 
		hero_.ray().getEndPoint(end, 50);
	//	Log.w("Point","End " +Float.toString(end.x) + " " + Float.toString(end.y)+ " " + Float.toString(end.z));
	//	Log.w("Point","Vector " +Float.toString(hero_.ray().direction.x) + " " + Float.toString(hero_.ray().direction.y)+ " " + Float.toString(hero_.ray().direction.z));
	//	Log.w("Point","Vector " +Float.toString(hero_.ray().origin.x) + " " + Float.toString(hero_.ray().origin.y)+ " " + Float.toString(hero_.ray().origin.z));
		Vector3 p = cam.position;
	    cam.position.set(end.x, end.y, end.z); /* use transform..... */
	    cam.update();
	    cam.lookAt(hero_.position().x,
				hero_.position().y,
				hero_.position().z);
	    cam.update();
	}
	
	
	public void validatePosition() {
			/*float x = PlanePosition.x(); 
			float y = PlanePosition.y();
			float z = PlanePosition.z();
		
			float phi = PlanePosition.phi();
			float tht = PlanePosition.tht();
			float psi = PlanePosition.psi();	

			Quaternion qua1 = new Quaternion();
			
			 Log.w(TAG,"Angle  phi " +Float.toString(phi));
			 Log.w(TAG,"Angle  tht " +Float.toString(tht));
			 Log.w(TAG,"Angle  psi " +Float.toString(psi));

			
			Vector3 targ = target(qua1, new Vector3(x,y,z));
			Vector3 lla = PlanePosition.getLongLatAlt(targ);
			Log.w("Point","lla " +Float.toString(lla.x) + " " + Float.toString(lla.y)+ " " + Float.toString(lla.z));
			skyDomeInstance.transform.setToTranslation(targ).scale(0.088f, 0.088f, 0.088f);
			Vector3 net = new Vector3(x,y,z);
			Vector3 sc = new Vector3(0.5f,0.5f, 0.5f);
			double hui = (Math.sqrt(x*x + y*y)/y);
					double angle = Math.acos(y/Math.sqrt(x*x + y*y));
					angle =angle*180/Math.PI;
					//instance.transform.setToTranslation(x,y,z).scale(0.5f,0.5f, 0.5f).rotate(Vector3.X,phi+180).rotate(Vector3.Y,tht).rotate(Vector3.Z,psi - 90 );
					float delta = 22f;
				    double xx = x - delta*Math.cos((psi)*Math.PI/180);
				    double yy = y - delta*Math.sin((180-psi)*Math.PI/180); */

					
	}
	
	@Override
	public void dispose() {
		modelBatch.dispose();
		//model.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	private void createSlider() {
		batch = new SpriteBatch();
            
        //Create a touchpad skin        
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("data/touchBackground.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("data/touchKnob.png"));
        //Create TouchPad Style
        touchpadStyle = new TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 200, 200);
        //Create a Stage and add TouchPad
      //   stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() ),true,batch);
        stage  = new Stage();
        stage.addActor(touchpad);                        
        Gdx.input.setInputProcessor(stage);
        
        //Create block sprite
       
 		
	
	}
	
	private void createJoy() {
	    sliderSkin = new Skin();
       
	    sliderSkin.add("background", new Texture("data/touchBackground.png"));
        //Set knob image
	    sliderSkin.add("knob", new Texture("data/touchKnob.png"));
	    Texture rx =  new Texture("data/touchKnob.png");
	      
         sliderStyle = new SliderStyle();
        //Create Drawable's from TouchPad skin
        
        sliderBackground = sliderSkin.getDrawable("background");
        sliderBackground.setMinWidth(8);
        sliderBackground.setMinHeight(8);
        sliderKnob = sliderSkin.getDrawable("knob");
        sliderKnob.setMinWidth(18);
        sliderKnob.setMinHeight(18);
        //Apply the Drawables to the TouchPad Style
        sliderStyle.background = sliderBackground;
        sliderStyle.knob = sliderKnob;
        //Create new TouchPad with the created style
        slider = new Slider(-10,10,0.2f,true,sliderStyle);
        slider.setValue(0);
        
        slider.setBounds(Gdx.graphics.getWidth() -60 , 10, 40, Gdx.graphics.getHeight()-20);
        stage.addActor(slider);                        
    }
	
	private void createSliderAiler() {
	    
      sliderAiler = new Slider(-10,10,0.2f,false,sliderStyle); /*renew max y and step */
      sliderAiler.setBounds(360 , 40, Gdx.graphics.getHeight()-20, 40);
      sliderAiler.setValue(0);
      stage.addActor(sliderAiler);                        
   	}
	
	private Vector3 target(Quaternion q,Vector3 vecI)
	{
		Log.w("Point","target veci " +Float.toString(vecI.x) + " " + Float.toString(vecI.y)+ " " + Float.toString(vecI.z));
		Vector3 vec = new Vector3(0,1,0);
		Log.w("Point","target before " +Float.toString(vec.x) + " " + Float.toString(vec.y)+ " " + Float.toString(vec.z));
		Vector3 v2 = q.transform(vec);
		Log.w("Point","target after " +Float.toString(vec.x) + " " + Float.toString(vec.y)+ " " + Float.toString(vec.z));
		Log.w("Point","target after2 " +Float.toString(v2.x) + " " + Float.toString(v2.y)+ " " + Float.toString(v2.z));
		Ray r = new Ray(vecI,v2);
		Vector3 fina = new Vector3();
		r.getEndPoint(fina, 2043);
		Log.w("Point","target final" +Float.toString(fina.x) + " " + Float.toString(fina.y)+ " " + Float.toString(fina.z));
		return fina;
	}
}


