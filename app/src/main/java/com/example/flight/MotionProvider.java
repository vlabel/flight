package com.example.flight;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public abstract class MotionProvider {
	private Vector3 position_;
	private Vector3 startingPosition_;
	private Quaternion orientation_;
	private long millisecond_;
	
	public abstract boolean init(String name);
	
	public Vector3 startingPosition() {
		return startingPosition_;
	}
	
	public void setStartingPosition(Vector3 pos) {
		startingPosition_ = pos;
	}
	
	public  Vector3 position() {
		return position_;
	}
	public Quaternion orientation() {
		return orientation_;
	}
	
	public long millisecond() {
		return millisecond_;
	}
	
	public void update(long millisecond) {
		millisecond_ = millisecond;
	}
	
	

}
