package com.example.opengltutorial;

public class Vector3 {
	public float x, y, z;
	
	public Vector3(float X, float Y, float Z) {
		x = X;
		y = Y;
		z = Z;
	}
	
	public void Add(Vector3 a) {
		x+=a.x;
		y+=a.y;
		z+=a.z;
	}
	
	public static Vector3 Add(Vector3 a, Vector3 b) {
		return new Vector3(a.x+b.x, a.y+b.y, a.z+b.z);
	}
	
	public void Sub(Vector3 a) {
		x-=a.x;
		y-=a.y;
		z-=a.z;
	}
	
	public static Vector3 Sub(Vector3 a, Vector3 b) {
		return new Vector3(a.x-b.x, a.y-b.y, a.z-b.z);
	}
	
	public float Dot(Vector3 a) {		
		return x*a.x+y*a.y+z*a.z;
	}
	
	public static float Dot(Vector3 a, Vector3 b) {
		return b.x*a.x+b.y*a.y+b.z*a.z;
	}
	
	public float Length() {
		return (float) Math.sqrt(Dot(this));
	}
	
	public void Normalize() {
		x/=Length();
		y/=Length();
		z/=Length();
	}
	
	public void Cross(Vector3 a) {
		x = y*a.z - z*a.y;
		y = z*a.x - x*a.z;
		z = x*a.y - y*a.x;
	}
	
	public static Vector3 Cross(Vector3 a, Vector3 b) {
		return new Vector3(a.y*b.z - a.z*b.y, a.z*b.x - a.x*b.z, a.x*b.y - a.y*b.x );
	}
	
	public static float Clamp(float v, float min, float max) {
		if (v<min)
			return min;
		else if (v<max)
			return v;
		else return max;
	}
	
	public void Neg()
	{
		x *= -1;
		y *= -1;
		z *= -1;
	}
	
	public static Vector3 Neg(Vector3 a)
	{
		return new Vector3(-a.x, -a.y, -a.z);
	}
	
}
