package com.example.flight;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.example.flight.HeroUnit;
/**
 * Created by user on 17.01.15.
 */
public class Missile extends  HeroUnit {
    private Integer xDelta_;
    private Integer yDelta_;
    private HeroUnit parent_;
    private boolean onboard_;
    public Missile(AssetManager mgr, String modelPath, String plane) {
        super(mgr, modelPath, plane, true);
        onboard_ = true;
    }
    public void setDeltas(Integer x, Integer y) {
        xDelta_ = x;
        yDelta_ = y;
    }
    public void setParent(HeroUnit h) {
        parent_ = h;
    }

    public void update(double seconds,float sliderH,float sliderV,float sliderR) {
        if (!onboard_) {
            super.update(seconds, sliderH, sliderV, sliderR);
        } else {

            ModelInstance m = model();
            Vector3 v = new Vector3(xDelta_,yDelta_,0);
            v = parent_.resultOrientation().transform(v);
            float x = parent_.position().x+v.x;
            float y = parent_.position().y+v.y;
            float z = parent_.position().z+v.z;
            m.transform.set(new Vector3(x,y,z), parent_.resultOrientation(),new Vector3(0.2f,0.2f,0.2f));



        }


    }


    public Integer x() {
        return xDelta_;
    }
    public Integer y() {
        return yDelta_;
    }
}
