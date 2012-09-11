package com.example.opengltutorial;

public class Quaternion {
	float x;
	float y;
	float z;
	float w;
	
	public Quaternion(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Quaternion() {
		// TODO Auto-generated constructor stub
	}

	public static float[] CreateRotationMatrix(Quaternion q)
	{
		// This is the arithmetical formula optimized to work with unit quaternions.
	    // |1-2y²-2z²        2xy-2zw         2xz+2yw       0|
	    // | 2xy+2zw        1-2x²-2z²        2yz-2xw       0|
	    // | 2xz-2yw         2yz+2xw        1-2x²-2y²      0|
	    // |    0               0               0          1|
	 
	    // And this is the code.
	    // First Column
		float[] matrix = new float[16];
		
	    matrix[0] = 1 - 2 * (q.y * q.y + q.z * q.z);
	    matrix[1] = 2 * (q.x * q.y + q.z * q.w);
	    matrix[2] = 2 * (q.x * q.z - q.y * q.w);
	    matrix[3] = 0;
	 
	    // Second Column
	    matrix[4] = 2 * (q.x * q.y - q.z * q.w);
	    matrix[5] = 1 - 2 * (q.x * q.x + q.z * q.z);
	    matrix[6] = 2 * (q.z * q.y + q.x * q.w);
	    matrix[7] = 0;
	 
	    // Third Column
	    matrix[8] = 2 * (q.x * q.z + q.y * q.w);
	    matrix[9] = 2 * (q.y * q.z - q.x * q.w);
	    matrix[10] = 1 - 2 * (q.x * q.x + q.y * q.y);
	    matrix[11] = 0;
	 
	    // Fourth Column
	    matrix[12] = 0;
	    matrix[13] = 0;
	    matrix[14] = 0;
	    matrix[15] = 1;
	    
	    return matrix;
	}
	
	public static Quaternion Multiply(Quaternion q1, Quaternion q2) {
		Quaternion newQ = new Quaternion();
		
		newQ.w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
	    newQ.x = q1.w * q2.x + q1.x * q2.w + q1.y * q2.z - q1.z * q2.y;
	    newQ.y = q1.w * q2.y - q1.x * q2.z + q1.y * q2.w + q1.z * q2.x;
	    newQ.z = q1.w * q2.z + q1.x * q2.y - q1.y * q2.x + q1.z * q2.w;
	    
	    return newQ;
	}
	
	public static Quaternion CreateFromAxisAngle(Vector3 vec, float degrees)
	{
		// The new quaternion variable.
	    Quaternion q = new Quaternion();
	 
	    // Converts the angle in degrees to radians.
	    float radians = (float) Math.toRadians(degrees);
	 
	    // Finds the Sin and Cosin for the half angle.
	    float sin = (float) Math.sin(radians * 0.5);
	    float cos = (float) Math.cos(radians * 0.5);
	 
	    // Formula to construct a new Quaternion based on direction and angle.
	    q.w = cos;
	    q.x = vec.x * sin;
	    q.y = vec.y * sin;
	    q.z = vec.z * sin;
	    
	    return q;
	}
	
	public static Quaternion Identity() {
		return new Quaternion(0,0,0,1f);
	}
	
	public Quaternion Inverse(Quaternion q) {
		return new Quaternion(-q.x, -q.y, -q.z, q.w);
	}
}
