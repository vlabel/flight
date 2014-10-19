package com.example.flight;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import android.util.Log;
public class SimpleMotion {
	
	private Vector3 _position;
	private Vector3 _norPosition;
	private Vector3 _priv_position;
	private Vector3 startingPosition_;
	private Quaternion orientation_;
	private double _second;
	private double _delta;
    float _hEnforce;
    float _vEnforce;
    
    float _hEnforce_dis;
    float _vEnforce_dis;
    float _dis;
    
    
	private long _step;
	private Ray    _ray;
	private Vector3 _direction;
	private Vector3 _planeDirection;
	private Quaternion oldOrientation_;
	
	public SimpleMotion () {
		orientation_ = new Quaternion(new Vector3(0,0,1),-90);
		oldOrientation_ = new Quaternion(new Vector3(0,0,1),-90);
		_ray = new Ray(new Vector3(0,0,0),new Vector3(1,0,0));
		_priv_position = new Vector3(0,0,0);
		_direction = new Vector3(0,1,0);
		_planeDirection = new Vector3(1,0,0);
		_position = new Vector3(0,0,0);
		_norPosition = new Vector3(0,0,0);
		_step = 100;
		_second = 0;
	}
        
    public void setHEnforce( float h) {
        _hEnforce = h;
    }
    
    public void setVEnforce( float v) {
        _vEnforce = v;
    }


	public Vector3 startingPosition() {
		return startingPosition_;
	}
	
	public void setStartingPosition(Vector3 pos) {
		startingPosition_ = pos;
	}
	
	public  Vector3 position() {
		return _position;
	}

	public Quaternion orientation() {
//		Quaternion rot = new Quaternion(new Vector3(0,0,1),90);
//		orientation_.mul(rot);
		return orientation_;
	}
	
	public double second() {
		return _second;
	}
	
	public Ray directionRay() {
		//return new Ray(_position,_priv_position);
		Vector3 mm = new Vector3(0,0,0);
		mm.set(_priv_position);
		_norPosition.set(mm.sub(_position));
		//_ray.set(_priv_position,_norPosition.nor());
		_ray.set(_position,_norPosition.nor());
		return _ray;
	}
	
	
	public void update(double sec) {

        /* new Quater = (Old * Quter(horendf) * Quater(vert) ) approx (sec) */
        /* moving vector  = (1,0,0)* rotate BY quater  */
		_dis = (float) 1;
	    float vDelta = (float) ((_vEnforce - _vEnforce_dis)*_dis*sec);
		float vEnf = (float) (_vEnforce_dis - (vDelta));
		 Log.w("Point","Delt " +Float.toString(vDelta) + " " + Float.toString(_vEnforce) + " " + Float.toString(_vEnforce_dis));
		 
		_vEnforce_dis += vDelta;
	
		
		float hDelta = (float) ((_hEnforce - _hEnforce_dis)*_dis*sec);
		float hEnf = (float) (_hEnforce_dis - (hDelta)); 	
		_hEnforce_dis += hDelta;
         Quaternion hq = new Quaternion(new Vector3(0,0,1), -vEnf);
         Quaternion vq = new Quaternion(new Vector3(1,0,0),hEnf);
         vq.mul(hq);
         orientation_.mul(vq);
         orientation_ = orientation_.slerp(oldOrientation_, 1-(float) sec*18);
         Log.w("Point","sec " +Double.toString(sec));
         Vector3 v = new Vector3(0,1,0);
         oldOrientation_.set(orientation_);
    	_direction.set(v.mul(orientation_));
		_second += sec;
		
		_delta=sec;
        _priv_position.set(_position);
		_position = _position.set(_priv_position.x+_direction.x,
				_priv_position.y+_direction.y,
				_priv_position.z+_direction.z
		);
        // update model
	}
	

}
