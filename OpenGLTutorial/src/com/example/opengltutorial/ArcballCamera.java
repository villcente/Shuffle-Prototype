package com.example.opengltutorial;

import android.opengl.Matrix;

public class ArcballCamera {
	public float[] rotation = new float[16];
	public Vector3 position;	
	public Vector3 targetPosition;
	public Vector3 upVector;
	
	public float[] viewMatrix = new float[16];
	public float[] projectionMatrix = new float[16];
	
	private final float verticalAngleMin = 0;
	private final float verticalAngleMax = 75;
	private final float zoomMin = 1.5f;
	private final float zoomMax = 3.0f;
	
	private float zoom = 2;
	
	public void setZoom(float newZoom) {
        zoom = Vector3.Clamp(newZoom, zoomMin, zoomMax);
    }
 
    public float getZoom() {
        return zoom;
    }
    
    private float horizontalAngle = 180;
    private float verticalAngle = 0.0f;
    
    
    public ArcballCamera(float FOV, int w, int h, float near, float far)
    {     
    	position = new Vector3(0, 0, -zoom);
    	targetPosition = new Vector3(0, 0, 0);
    	upVector = new Vector3(0, 1f, 0);
    	
    	float ratio = (float) w / h;
    	float size = (float) (near * Math.tan(Math.toRadians(FOV) /2.0f));
	    
	    Matrix.frustumM(projectionMatrix, 0, -size, size, -size/ratio, size/ratio, near, far);	
	    
    	Matrix.setIdentityM(rotation, 0);
    	float[] pos = {0,0,-2.0f,1};
    	Matrix.rotateM(rotation, 0, verticalAngle, 1f, 0, 0);
    	Matrix.rotateM(rotation, 0, horizontalAngle, 0, 1f, 0);    		
		Matrix.multiplyMV(pos, 0, rotation, 0, pos, 0);
		position = new Vector3(pos[0],pos[1],pos[2]);
	    LookAt();
	    
    	Matrix.setLookAtM(	viewMatrix, 0, position.x, position.y, position.z, 
    						targetPosition.x, targetPosition.y, targetPosition.z, 
    						upVector.x, upVector.y, upVector.z);
    }
    
	public void Update(float dx, float dy)
	{     
		float[] pos = {0,0,-zoom,1};
		Matrix.setIdentityM(rotation, 0);
	    
	    // Rotate vertically
		if ((verticalAngle+dy)<verticalAngleMax && (verticalAngle+dy)>verticalAngleMin)
			verticalAngle += dy;
		Matrix.rotateM(rotation, 0, verticalAngle, 1f, 0, 0);
		
		// Rotate horizontally	 
		horizontalAngle += dx;
		horizontalAngle %= 360;
		float[] tempm = new float[16];
		
    	//Matrix.rotateM(rotation, 0, horizontalAngle, 0, 1f, 0);
		Matrix.setIdentityM(tempm, 0);
		Matrix.rotateM(tempm, 0, horizontalAngle, 0, 1f, 0); 
		Matrix.multiplyMM(rotation, 0, tempm, 0, rotation, 0);
		Matrix.multiplyMV(pos, 0, rotation, 0, pos, 0);
	    position = new Vector3(pos[0],pos[1],pos[2]);
	    	 
	    LookAt();
	 
	    Matrix.setLookAtM(	viewMatrix, 0, position.x, position.y, position.z, 
				targetPosition.x, targetPosition.y, targetPosition.z, 
				upVector.x, upVector.y, upVector.z);
	}	
		
	public void LookAt()
	{
	    Vector3 newForward = Vector3.Neg(position);
	    newForward.Normalize();
	 
	    Vector3 referenceVector = new Vector3(0,1,0);
	 
	    // On the slim chance that the camera is pointer perfectly parallel with the Y Axis, we cannot
	    // use cross product with a parallel axis, so we change the reference vector to the forward axis (Z).
	    if (newForward.y == referenceVector.y || newForward.y == -referenceVector.y)
	    {
	        referenceVector = new Vector3(0,0,1);
	    }
	 
	    Vector3 Right = Vector3.Cross(newForward, referenceVector);
	    Right.Normalize();
	    upVector = Vector3.Cross(Right, newForward);  
	    upVector.Normalize();
	}
    
    
}
