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
    float _rEnforce; // roll
    
    float _hEnforce_dis;
    float _vEnforce_dis;
    float _aEnforce_dis;
    float _rEnforce_dis;
    float _dis;

    float _distanceMin = 310;
    
    
	private long _step;
	private Ray    _ray;
	private Vector3 _direction;
	private Vector3 _planeDirection;
	private Quaternion oldOrientation_;
	private Vector3 tracePosition_;
	private Quaternion rollOrientation_;
	private Quaternion resultOrientation_;
	private Quaternion oldRollOrientation_;
    private Quaternion _bufferOrient;
        
    /* auto pilot mode */
    private boolean _auto;
    private Vector3 _point;
    // container for next point
    private float   _pointArea;
    private ArrayList<Vector3> _route;
    private double _pcount;
    private  double _time;

	
	Queue<Vector3> trace;
	
	public SimpleMotion () {
		orientation_ = new Quaternion(0,0,0,0);
		oldOrientation_ = new Quaternion(new Vector3(0,0,1),-90);
		rollOrientation_ = new Quaternion(0,0,0,1);
		oldRollOrientation_ = new Quaternion(0,0,0,1);
		resultOrientation_ = new Quaternion(0,0,0,1);
        _bufferOrient = new Quaternion(0,0,0,1);
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

    public void setREnforce( float r) {
        if (_auto) return;
        _rEnforce = r;
    }

    public void setRoute( ArrayList<Vector3> route) {
        _route = route;
        _point = route.get(0);
       // _route.remove(0);
    }


    private void checkForPoint() {
        if (_route.isEmpty()) return;
        if (!_auto) return;
        float distance = _route.get(0).dst(_position);
        Log.w("Route","Distance to point  " +Double.toString(distance));
        if (distance < _distanceMin) {
            Log.w("Route1","Got checkpoint: position:  " +Double.toString(_position.x) + " "+Double.toString(_position.y) + " "+Double.toString(_position.z) + " ");
            Log.w("Route1","Distance to point:  " +Double.toString(distance));
            Log.w("Route1","Old point:  " +Double.toString(_point.x) + " "+Double.toString(_point.y) + " "+Double.toString(_point.z) + " ");
            if (_route.size() < 2) {
                Log.w("Route1","Last point  ");
                return;
            }
            Log.w("Route1","New point:  " +Double.toString(_route.get(1).x) + " "+Double.toString(_route.get(1).y) + " "+Double.toString(_route.get(1).z) + " ");
            setPoint(_route.get(1));
            _route.remove(0);

        }
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
        double lag = 1;
     	if (!_auto) return;
        _pcount+=sec;
      //  _pcount = 100;
        _time+=sec;
     //  if (_time < 14) return;

    	Vector3 toNext = new Vector3();
    	toNext.set(_point);
    	toNext.sub(_position);
    	toNext.nor();
        if (_pcount > lag) {
            Log.w("PointA", " position  " + Double.toString(_position.x) + " " + Double.toString(_position.y) + " " + Double.toString(_position.z) + " ");
            Log.w("PointA", " next  " + Double.toString(_point.x) + " " + Double.toString(_point.y) + " " + Double.toString(_point.z) + " ");
        }
        if (_pcount > lag)Log.w("PointA","To next Y " +Double.toString(toNext.x) + " "+Double.toString(toNext.y) + " "+Double.toString(toNext.z) + " ");

        Vector3 yaxis = new Vector3(0,0,1);
        yaxis.crs(toNext);
        yaxis.nor();

        if (_pcount > lag)Log.w("PointA","Yaxis  " +Double.toString(yaxis.x) + " "+Double.toString(yaxis.y) + " "+Double.toString(yaxis.z) + " ");
        
       // Vector3 zaxis = new Vector3(0,0,-1);
        Vector3 zaxis = new Vector3(toNext);
        zaxis =zaxis.crs(yaxis);
        zaxis.nor();
        if (_pcount > lag)Log.w("PointA","Zaxis  " +Double.toString(zaxis.x) + " "+Double.toString(zaxis.y) + " "+Double.toString(zaxis.z) + " ");

        Quaternion qtoNew = new Quaternion(0,0,0,1);
        qtoNew = qtoNew.setFromAxes(
                            toNext.x,toNext.y,toNext.z,
                            yaxis.x, yaxis.y, yaxis.z,
                            zaxis.x, zaxis.y, zaxis.z);
        qtoNew.nor();






        Vector3 selfx = _direction;
        selfx.nor();
        Vector3 selfy = new Vector3(0,0,1);
        selfy.crs(selfx);
        selfy.nor();
        Vector3 selfz = new Vector3(selfx);
        selfz =selfz.crs(selfy);
        selfz.nor();
        Quaternion qSelf = new Quaternion(0,0,0,1);
        qSelf = qSelf.setFromAxes(
                selfx.x,selfx.y,selfx.z,
                selfy.x, selfy.y, selfy.z,
                selfz.x, selfz.y, selfz.z);
        qSelf.nor();



     ///   qtoNew = qtoNew.conjugate();
       Quaternion unrollNew = new Quaternion(qSelf);

       Quaternion tt = new Quaternion(new Vector3(1,0, 0),-90);
       tt = tt.mul(qtoNew);
        _bufferOrient.set(qtoNew);

        float roll = _bufferOrient.getRoll();
        if (Float.isNaN(roll)) roll = 0;
        float pitch = resultOrientation_.getPitch();
        if (Float.isNaN(pitch)) pitch = 0;
        float yaw = _bufferOrient.getYaw();
        if (Float.isNaN(yaw)) yaw = 0;



        float roll1 = unrollNew.getRoll();
        if (Float.isNaN(roll1)) roll1=0;
        float pitch1 = unrollNew.getAngleAround(0,1,0);
        if (Float.isNaN(pitch1)) pitch1=0;
        float yaw1 = unrollNew.getYaw();
        if (Float.isNaN(yaw1)) yaw1 = 0;
        if (_pcount > lag) {
            Log.w("PointA","angles Self " +Float.toString(roll1) + " "+Float.toString(pitch1) + " "+Float.toString(yaw1) + " ");
            Log.w("PointA","angles New "  +Float.toString(roll) + " "+Float.toString(pitch) + " "+Float.toString(yaw) + " ");
        }

        float turn ;
        float turn1 ;
        float sign = 1;

        if (roll < 0) roll = 180 + (180+roll);
        if (roll1 < 0) roll1 = 180 + (180+roll1);

        turn =  roll - roll1;
      //  turn1 =  roll1 - roll;
        turn1 =  Float.parseFloat(Double.toString(360 - Math.sqrt(turn*turn)));

        double v1 = Math.sqrt(turn*turn);
        double v2 = Math.sqrt(turn1*turn1);


        if (v2 < v1)  {
               turn = turn1;
               sign =-1;
        } else {
            //if (roll < roll1) sign = -1;
        }

        float dyaw = yaw - yaw1;
        _vEnforce = turn/60*sign;
//        _rEnforce = pitch/180;
        _hEnforce = dyaw/40;
           //     _vEnforce = 0;
        _rEnforce = pitch/40;
        //_hEnforce = 0;
        if (_pcount > lag) {
            Log.w("PointA","angles " +Float.toString(turn) + " "+Float.toString(pitch) + " "+Float.toString(yaw) + " ");
            Log.w("PointA", "Enforces " + Float.toString(_vEnforce) + " " + Float.toString(_rEnforce) + " " + Float.toString(_hEnforce) );
            Log.w("PointA", "_________________ ");
            _pcount = 0;
        }
    }



	public void update(double sec) {
        checkForPoint();
        updateAutopilotForces(sec);
		_dis = (float) 1;
        String TAG = "Point";
        if (_auto) TAG = "PointAuto";
		Log.w(TAG,"vEnforce " + Float.toString(_vEnforce));
	    float vDelta = (float) ((_vEnforce - _vEnforce_dis)*_dis*sec);
		float vEnf = (float) (_vEnforce_dis - (vDelta));
		 Log.w(TAG,"Delt " + Float.toString(vDelta) + " " + Float.toString(_vEnforce) + " " + Float.toString(_vEnforce_dis));
		 
		_vEnforce_dis += vDelta;
		
		float hDelta = (float) ((_hEnforce - _hEnforce_dis)*_dis*sec);
		float hEnf = (float) (_hEnforce_dis - (hDelta)); 	
		_hEnforce_dis += hDelta;

        float rDelta = (float) ((_rEnforce - _rEnforce_dis)*_dis*sec);
        float rEnf = (float) (_rEnforce_dis - (rDelta));
        _rEnforce_dis += rDelta;

         Quaternion hq = new Quaternion(new Vector3(0,0,1), -vEnf);
         Quaternion vq = new Quaternion(new Vector3(1,0,0),hEnf );
         Quaternion rq = new Quaternion(new Vector3(0,1,0),vEnf + rEnf);
       //  Quaternion rq = new Quaternion(new Vector3(0,1,0),vEnf );
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

         orientation_ = orientation_.slerp(oldOrientation_, 1-(float) sec*38);
         rollOrientation_.mul(rq);
         rollOrientation_ = rollOrientation_.slerp(oldRollOrientation_, 1-(float) sec*38);
         Quaternion tmp = new Quaternion(orientation_);
         
         resultOrientation_ = tmp.mul(rollOrientation_);
         Log.w(TAG,"sec " +Double.toString(sec));
         Vector3 v = new Vector3(0,1,0);
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
		Log.w(TAG,"Position " +Double.toString(_position.x) + " "+Double.toString(_position.y) + " "+Double.toString(_position.z) + " ");
		
	}
	

}
