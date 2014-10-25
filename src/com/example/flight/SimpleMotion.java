package com.example.flight;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.ui.List;

import android.util.Log;
public class SimpleMotion {
	
	private Vector3 _position;
	private Vector3 _norPosition;
	private Vector3 _priv_position;
	private Vector3 _priv_position_Camera;
	private Vector3 startingPosition_;
	private Quaternion orientation_;
	private double _second;
	private double _delta;
    float _hEnforce;
    float _vEnforce;
    float _aEnforce;
    
    float _hEnforce_dis;
    float _vEnforce_dis;
    float _aEnforce_dis;
    float _dis;
    
    
	private long _step;
	private Ray    _ray;
	private Vector3 _direction;
	private Vector3 _planeDirection;
	private Quaternion oldOrientation_;
	private Vector3 tracePosition_;
	private Quaternion rollOrientation_;
	private Quaternion resultOrientation_;
	private Quaternion oldRollOrientation_;
        
    /* auto pilot mode */
    private boolean _auto;
    private Vector3 _point;
    // container for next point
    private float   _pointArea;

	
	Queue<Vector3> trace;
	
	public SimpleMotion () {
		orientation_ = new Quaternion(0,0,0,0);
		oldOrientation_ = new Quaternion(new Vector3(0,0,1),-90);
		rollOrientation_ = new Quaternion(0,0,0,1);
		oldRollOrientation_ = new Quaternion(0,0,0,1);
		resultOrientation_ = new Quaternion(0,0,0,1);
		tracePosition_ = new  Vector3(0,0,1);
		trace = new LinkedList<Vector3>();
		_ray = new Ray(new Vector3(0,0,0),new Vector3(1,0,0));
		_priv_position = new Vector3(0,0,0);
        _priv_position_Camera = new Vector3(0,0,0); // for camera latency
		_direction = new Vector3(0,1,0);
		_planeDirection = new Vector3(1,0,0);
		_position = new Vector3(0,0,0);
		_norPosition = new Vector3(0,0,0);
		_step = 100;
		_second = 0;
        _point = new Vector3(0,0,0);
        _pointArea = 3; // distance to next checkpoint
        _auto = false;
	}
        
    public void setHEnforce( float h) {
    	 if (_auto) return;
        _hEnforce = h;
    }
    
    public void setVEnforce( float v) {
    	if (_auto) return;
    	_vEnforce = v;
    }

    public  void setAuto(boolean auto) {
    	_auto = auto;
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
	
	public Quaternion resultOrientation() {
//		Quaternion vq = new Quaternion(new Vector3(0,0,1),90);
//		return vq.mul(resultOrientation_);
		return resultOrientation_;
	}
	
	public Quaternion oldOrientation() {
		return oldOrientation_;
	}
	

	public double second() {
		return _second;
	}
	
	public Ray directionRay() {
		Vector3 mm = new Vector3(0,0,0);
        
		mm.set(_priv_position);
		//mm.set(tracePosition_);
		_norPosition.set(mm.sub(_position));
		_ray.set(_position,_norPosition.nor());
		//_ray.set(_position,_norPosition.nor());
		return _ray;
	}
	
    /**
     * @brief set next checkpoint  
     *
     * @param point
     *
     * @return 
     */
    public void setPoint(Vector3 point)  {
        _point = point ;
    }

    private void updateAutopilotForces(double sec) {
        /*   needs to calc new v and h enforces  
         *  1 build vector to next point
         *  get angle between x in plane frame  - vforce
         *  build z axis - h force 
         *
          * */
    	if (!_auto) return;
   /* 	Vector3 toNext = new Vector3();
    	toNext.set(_position);
    	toNext.sub(_point);
   	
    	 Log.w("Point","To next  " +Double.toString(toNext.x) + " "+Double.toString(toNext.y) + " "+Double.toString(toNext.z) + " ");
    	 
    	 Vector3 toz = new Vector3(0,0,1);
    	 toz = orientation_.transform(toz);
    	 Log.w("Point","Toz  " +Double.toString(toz.x) + " "+Double.toString(toz.y) + " "+Double.toString(toz.z) + " ");
    	
    	 Vector3 tox = new Vector3(1,0,0);
    	 tox = orientation_.transform(tox);
    	 Log.w("Point","Tox  " +Double.toString(tox.x) + " "+Double.toString(tox.y) + " "+Double.toString(tox.z) + " ");
         toNext.nor();
         tox.nor();
    	 float dot = toNext.dot(tox);
    	 Log.w("Point","Dot  " +Double.toString(dot));
    	 float al = (float) Math.acos(dot);
    	 al = (float) Math.toDegrees(al);
    	 Log.w("Point","alpha  " +Double.toString(al));
    	_vEnforce = al; */

    	
    	
    	Vector3 toNext = new Vector3();
    	toNext.set(_point);
    	toNext.sub(_position);
    	toNext.nor();
    	Log.w("Point","To next Y " +Double.toString(toNext.x) + " "+Double.toString(toNext.y) + " "+Double.toString(toNext.z) + " ");
        
        
       
        Vector3 xaxis = new Vector3(0,0,1);
        xaxis.crs(toNext);
        xaxis.nor();
        Log.w("Point","Yaxis  " +Double.toString(xaxis.x) + " "+Double.toString(xaxis.y) + " "+Double.toString(xaxis.z) + " ");
        
        Vector3 zaxis = new Vector3(0,0,0);
        zaxis.set(toNext);
        zaxis.crs(xaxis);
        zaxis.nor();
        Log.w("Point","Zaxis  " +Double.toString(zaxis.x) + " "+Double.toString(zaxis.y) + " "+Double.toString(zaxis.z) + " ");

        Quaternion qtoNew = new Quaternion(0,0,0,1);
        qtoNew = qtoNew.setFromAxes(
                            
                            toNext.x,toNext.y,toNext.z,
                            xaxis.x,xaxis.y,xaxis.z,
                            zaxis.x,zaxis.y,zaxis.z);
        
        //Quaternion traver = new Quaternion(0, 0, 0, 1);
        qtoNew = qtoNew.conjugate();
        
        qtoNew.mul(orientation_);
        
        float roll = qtoNew.getAngleAround(new Vector3(0,0,1));
        float pitch = qtoNew.getAngleAround(new Vector3(1,0,0));
        if (Float.isNaN(pitch)) pitch=0;
        float yaw =qtoNew.getAngleAround(new Vector3(0,1,0));
        if (Float.isNaN(yaw)) yaw = 0;

    	Log.w("Point","AutoPilot Rotation " +Float.toString(roll) + " "+Float.toString(pitch) + " "+Float.toString(yaw) + " ");
        _vEnforce = roll/180;
        //_hEnforce = pitch/180;
        _hEnforce = yaw/180;
    }
    


	public void update(double sec) {
        updateAutopilotForces(sec);
		_dis = (float) 1;
		Log.w("Point","vEnforce " + Float.toString(_vEnforce));
	    float vDelta = (float) ((_vEnforce - _vEnforce_dis)*_dis*sec);
		float vEnf = (float) (_vEnforce_dis - (vDelta));
		 Log.w("Point","Delt " + Float.toString(vDelta) + " " + Float.toString(_vEnforce) + " " + Float.toString(_vEnforce_dis));
		 
		_vEnforce_dis += vDelta;
		
		float hDelta = (float) ((_hEnforce - _hEnforce_dis)*_dis*sec);
		float hEnf = (float) (_hEnforce_dis - (hDelta)); 	
		_hEnforce_dis += hDelta;
         Quaternion hq = new Quaternion(new Vector3(0,0,1), -vEnf);
         Quaternion vq = new Quaternion(new Vector3(1,0,0),hEnf);
         Quaternion rq = new Quaternion(new Vector3(0,1,0),vEnf);
         Quaternion aq = new Quaternion(new Vector3(0,1,0),0);
         if (_auto) {
        	 float aDelta = (float) ((_aEnforce - _aEnforce_dis)*_dis*sec);
     		float aEnf = (float) (_aEnforce_dis - (aDelta));
     		_aEnforce_dis += aDelta;
     		 aq.set(new Vector3(0,1,0),aEnf);
        	      	// hq.mul(aq);
         }
         vq.mul(hq);
         orientation_.mul(vq);
      //   if (_auto) orientation_.mul(aq);
         
         orientation_ = orientation_.slerp(oldOrientation_, 1-(float) sec*18);
      //   rollOrientation_.set(0, 0, 0,1);
         rollOrientation_.mul(rq);
         rollOrientation_ = rollOrientation_.slerp(oldRollOrientation_, 1-(float) sec*18);
         Quaternion tmp = new Quaternion(orientation_);
         
         resultOrientation_ = tmp.mul(rollOrientation_);
         Log.w("Point","sec " +Double.toString(sec));
         Vector3 v = new Vector3(0,10,0);
         oldRollOrientation_.set(rollOrientation_);         
         oldOrientation_.set(orientation_);
    	_direction.set(v.mul(orientation_));
		_second += sec;
		
		
		
		
		_delta=sec;
        _priv_position.set(_position);
		_position = _position.set(_priv_position.x+_direction.x,
		                		_priv_position.y+_direction.y,
			                	_priv_position.z+_direction.z
		);
		
		trace.add(_priv_position);
		if (trace.size() >= 1) {
			tracePosition_.set(trace.remove());
			
		}
		Log.w("Point","Position " +Double.toString(_position.x) + " "+Double.toString(_position.y) + " "+Double.toString(_position.z) + " ");
		
        // update model
	}
	

}
