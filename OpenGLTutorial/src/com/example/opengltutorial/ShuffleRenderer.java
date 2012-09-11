package com.example.opengltutorial;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

public class ShuffleRenderer implements GLSurfaceView.Renderer {
	private static final String LOG_TAG = ShuffleRenderer.class.getSimpleName();
	
	public ArcballCamera shuffleCamera;
		
	public void onDrawFrame(GL10 gl) {
		// clear the color buffer and the depth buffer
	    gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	 
	    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
	    gl.glColorPointer(4, GL10.GL_FLOAT, 0, _colorBuffer);
	    
	    long time = SystemClock.uptimeMillis() % 10000L;
        float angleInDegrees = (360.0f / 10000.0f) * ((int) time);
        
	    Matrix.setIdentityM(modelMatrix, 0);
	    //Matrix.rotateM(modelMatrix, 0, angleInDegrees, 0.0f, 1.0f, 0.0f);
	    //Matrix.rotateM(modelMatrix, 0, _xAngle, 1.0f, 0.0f, 0.0f);
	    //Matrix.rotateM(modelMatrix, 0, _yAngle, 0.0f, 1.0f, 0.0f);
	    //gl.glLoadIdentity();
	    // set rotation
	    //gl.glTranslatef(0,0,-3f);
	    //gl.glRotatef(_xAngle, 1f, 0f, 0f);
	    //gl.glRotatef(_yAngle, 0f, 1f, 0f);
	    
	    //Matrix.multiplyMM(mMVPMatrix, 0, shuffleCamera.viewMatrix, 0, modelMatrix, 0);
	    //Matrix.multiplyMM(mMVPMatrix, 0, shuffleCamera.projectionMatrix, 0, mMVPMatrix, 0);
	    Matrix.multiplyMM(mMVPMatrix, 0, shuffleCamera.projectionMatrix, 0, shuffleCamera.viewMatrix, 0);
	    Matrix.multiplyMM(mMVPMatrix, 0, mMVPMatrix, 0, modelMatrix, 0);
	    
	    gl.glLoadMatrixf(mMVPMatrix, 0);
	    
	    gl.glDrawElements(GL10.GL_TRIANGLES, _nrOfVertices, GL10.GL_UNSIGNED_SHORT, _indexBuffer);
	    
	    //gl.glTranslatef(1, 0, 0);
	    //gl.glDrawElements(GL10.GL_TRIANGLES, _nrOfVertices, GL10.GL_UNSIGNED_SHORT, _indexBuffer);
	    
	}
	

	private float[] modelMatrix = new float[16];
	
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		gl.glViewport(0, 0, w, h);
		
		shuffleCamera = new ArcballCamera(45.0f, w, h, 0.1f, 10.0f);
		
	    gl.glMatrixMode(GL10.GL_MODELVIEW);
	    gl.glEnable(GL10.GL_DEPTH_TEST);
	    
	    // define the color we want to be displayed as the "clipping wall"
	    gl.glClearColor(0f, 0f, 0f, 1.0f);
	    
	    // enable the differentiation of which side may be visible 
	    gl.glEnable(GL10.GL_CULL_FACE);
	    // which is the front? the one which is drawn counter clockwise
	    gl.glFrontFace(GL10.GL_CCW);
	    // which one should NOT be drawn
	    gl.glCullFace(GL10.GL_BACK);
	 
	    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	 
	    DrawCube();
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.i(LOG_TAG, "onSurfaceCreated()");
	    gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
	    
	}
	
	// new object variables we need
	// a raw buffer to hold indices
	private ShortBuffer _indexBuffer;
	 
	// a raw buffer to hold the vertices
	private FloatBuffer _vertexBuffer;
	 
	private int _nrOfVertices = 0;
	 
	// a raw buffer to hold the colors
	private FloatBuffer _colorBuffer;
	
	// Tymczasowa metoda do wyświetlania bryły
	private void DrawCube() {
		float[] coords = {
	            -0.5f, -0.5f, 0.5f, // 0
	            0.5f, -0.5f, 0.5f, // 1
	            0.5f, -0.5f, -0.5f, // 2
	            -0.5f, -0.5f, -0.5f, // 3
	            -0.5f, 0.5f, 0.5f, // 4
	            0.5f, 0.5f, 0.5f, // 5
	            0.5f, 0.5f, -0.5f, // 6
	            -0.5f, 0.5f, -0.5f, // 7
	            
	    };
	    	 
	    float[] colors = {
	            1f, 0f, 0f, 1f, // point 0 red
	            0f, 1f, 0f, 1f, // point 1 green
	            0f, 0f, 1f, 1f, // point 2 blue
	            1f, 1f, 1f, 1f, // point 3 white
	            1f, 0f, 0f, 1f, // point 4 red
	            0f, 1f, 0f, 1f, // point 5 green
	            0f, 0f, 1f, 1f, // point 6 blue
	            1f, 1f, 1f, 1f, // point 7 white
	    };
	 
	    short[] indices = new short[] {
	            0, 1, 3, 
	            1, 2, 3,  	// podstawa
	            
	            0, 3, 4,
	            3, 7, 4,	// lewa
	            
	            3, 2, 7,
	            2, 6, 7,	// tył
	            
	            2, 1, 6,
	            1, 5, 6,	// prawa
	            
	            0, 4, 1,
	            4, 5, 1,	// przód
	            
	            4, 7, 5,
	            5, 7, 6		// góra
	    };
	    
	    _nrOfVertices = indices.length;
	 
	    // float has 4 bytes, coordinate * 4 bytes
	    ByteBuffer vbb = ByteBuffer.allocateDirect(coords.length * 4);
	    vbb.order(ByteOrder.nativeOrder());
	    _vertexBuffer = vbb.asFloatBuffer();
	 
	    // short has 2 bytes, indices * 2 bytes
	    ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
	    ibb.order(ByteOrder.nativeOrder());
	    _indexBuffer = ibb.asShortBuffer();
	 
	    // float has 4 bytes, colors (RGBA) * 4 bytes
	    ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
	    cbb.order(ByteOrder.nativeOrder());
	    _colorBuffer = cbb.asFloatBuffer();
	 
	    _vertexBuffer.put(coords);
	    _indexBuffer.put(indices);
	    _colorBuffer.put(colors);
	 
	    _vertexBuffer.position(0);
	    _indexBuffer.position(0);
	    _colorBuffer.position(0);    
	    
	}
	
	private float[] mMVPMatrix = new float[16];
	
	private float _xAngle;
    private float _yAngle;
	 
    public void setXAngle(float angle) {
        _xAngle = angle;
    }
 
    public float getXAngle() {
        return _xAngle;
    }
 
    public void setYAngle(float angle) {
        _yAngle = angle;
    }
 
    public float getYAngle() {
        return _yAngle;
    }	
}
