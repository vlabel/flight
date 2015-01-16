package com.example.flight;
import java.lang.Math;
import com.badlogic.gdx.math.Vector3;
public class PlanePosition {
	private static float m_alt;
	private static float m_lat;
	private static float m_lon;
	private static float m_phi;
	private static float m_tht;
	private static float m_psi;
	private static float m_al;
	private static float m_betta;
	
	public final static float FEET = 0.3048f;
	private final static float R = 6378137.3142f;
	private static float m_x;
	private static float m_y;
	private static float m_z;
	

	final static Object mutex = new Object(); 

	public  static void setPosition(float x,float y,float z) {
		m_x =  x * FEET;
		m_y =  y * FEET;
		m_z = (z * FEET) - R;
	}
	
	public static void setAlgles(float ph,float tht,float psi) {
		m_phi = ph;
		m_tht = tht;
		m_psi = psi;
	}
	
	
public static Vector3 getLongLatAlt(Vector3 xyz)
{
	float lat = (float)Math.atan2(xyz.z+R,Math.sqrt(xyz.x*xyz.x+xyz.y*xyz.y));
	float lon = (float)Math.atan2(xyz.y,xyz.x);
	return (new Vector3(lon,lat,xyz.z));
}
	
	public static void setAB(float a,float b) {
		m_al = a;
		m_betta = b;
	}
	
	
	public static float x() {
	//	synchronized (mutex) {
			return m_x;
		//}
	}
	public static float y() {
		//synchronized (mutex) {
			return m_y;
		//}
	}
	public static float z() {
	//	synchronized (mutex) {
			return m_z;
	//	}
	}
	
	public static float phi() {
	//	synchronized (mutex) {
			return m_phi;
	//	}
	}
	public static float tht() {
	//	synchronized (mutex) {
			return m_tht;
	//	}
	}
	public static float psi() {
	//	synchronized (mutex) {
			return m_psi;
	//	}
	}
	
}
