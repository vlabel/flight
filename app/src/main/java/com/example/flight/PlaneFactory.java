package com.example.flight;

import com.badlogic.gdx.assets.AssetManager;
import com.example.flight.HeroUnit;

/**
 * Created by user on 17.01.15.
 */
public class PlaneFactory {
    private AssetManager mgr_;
    public PlaneFactory(AssetManager mgr) {
        mgr_ = mgr;
    }

    public HeroUnit getHero(String hero,Boolean auto) {
        HeroUnit h;
      if (hero.equalsIgnoreCase("Su-27")) {
          h = new HeroUnit(mgr_,"data/Su-27_Flanker.g3db" , "Su-27",false);
          Missile missile1= new Missile(mgr_,"data/Su-27_Flanker.g3db" , "Su-27");
          missile1.scaleModel(Float.parseFloat("0.1"));
          missile1.setDeltas(7,-5);
          missile1.setParent(h);
          h.addMissile(missile1);

//          Missile missile2= new Missile(mgr_,"data/Su-27_Flanker.g3db" , "Su-27");
//          missile2.scaleModel(Float.parseFloat("0.1"));
//          missile2.setDeltas(5,10);
//          missile2.setParent(h);
//          h.addMissile(missile2);
//
//          Missile missile3= new Missile(mgr_,"data/Su-27_Flanker.g3db" , "Su-27");
//          missile3.scaleModel(Float.parseFloat("0.1"));
//          missile3.setDeltas(-5,10);
//          missile3.setParent(h);
//          h.addMissile(missile3);



          Missile missile4= new Missile(mgr_,"data/Su-27_Flanker.g3db" , "Su-27");
          missile4.scaleModel(Float.parseFloat("0.1"));
          missile4.setDeltas(-7,-5);
          missile4.setParent(h);
          h.addMissile(missile4);




     } else  {
          h = new HeroUnit(mgr_,"data/Su-27_Flanker.g3db" , "Su-27",false);
     };
        return h;
    };

}
