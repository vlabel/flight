package com.example.flight;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
public class SimpleMotion {
	
	private Vector3 _position;
	private Vector3 _priv_position;
	private Vector3 startingPosition_;
	private Quaternion orientation_;
	private double _second;
	private double _delta;
	private long _step;
	private Ray    _ray;
	private Vector3 _direction;
	
	public SimpleMotion () {
		orientation_ = new Quaternion(0, 0, 0, 1);
		_ray = new Ray(new Vector3(0,0,0),new Vector3(1,0,0));
		_priv_position = new Vector3(0,0,0);
		_direction = new Vector3(1,0,0);
		_step = 100;
		_second = 0;
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
		return orientation_;
	}
	
	public long millisecond() {
		return _millisecond;
	}
	
	
	public void  update()
	{
		_millisecond+=_step;
		_position = _priv_position.set(_priv_position.x+_direction.x,
				_priv_position.y+_direction.y,
				_priv_position.z+_direction.z
				);

	}
	
	public void update(double sec) {
		_second += sec;
		_delsta=sec;
        _priv_position = _position;
		_position = _priv_position.set(_priv_position.x+_direction.x,
				_priv_position.y+_direction.y,
				_priv_position.z+_direction.z
		);
        
        // update model
	}
	

}
