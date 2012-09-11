package com.example.opengltutorial;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class ShuffleView extends GLSurfaceView {
	private static final String LOG_TAG = ShuffleView.class.getSimpleName();
	private ShuffleRenderer _renderer;
	
	public ShuffleView(Context context) {
		super(context);
		_renderer = new ShuffleRenderer();
		setRenderer(_renderer);
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
	}

	private float _x = 0;
	private float _y = 0;
	
	private static final int INVALID_POINTER_ID = -1;
	private int mActivePointerId = INVALID_POINTER_ID;
	
	public boolean onTouchEvent(final MotionEvent event) {
		mScaleDetector.onTouchEvent(event);
		
		final int action = event.getAction();

		switch(action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN: {
	        _x = event.getX();
	        _y = event.getY();
	        
	        mActivePointerId = event.getPointerId(0);
	        break;
	    }
		case MotionEvent.ACTION_MOVE: {
			final int pointerIndex = event.findPointerIndex(mActivePointerId);
			
			if (!mScaleDetector.isInProgress()) {
	        final float xdiff = (_x - event.getX(pointerIndex));
	        final float ydiff = (_y - event.getY(pointerIndex));
	        queueEvent(new Runnable() {
	            public void run() {
	                //_renderer.shuffleCamera.setVerticalAngle(_renderer.shuffleCamera.getVerticalAngle() + ydiff/100);
	                //_renderer.shuffleCamera.setHorizontalAngle(_renderer.shuffleCamera.getHorizontalAngle() + xdiff/100);
	            	
	            	_renderer.shuffleCamera.Update(-xdiff/5.0f, ydiff/5.0f);
	            }
	        });
			}
	        _x = event.getX(pointerIndex);
	        _y = event.getY(pointerIndex);
	        break;
		}
		case MotionEvent.ACTION_UP: {
			mActivePointerId = INVALID_POINTER_ID;
			break;
		}
		case MotionEvent.ACTION_CANCEL: {
			mActivePointerId = INVALID_POINTER_ID;
			break;
		}
		case MotionEvent.ACTION_POINTER_UP: {
	        // Extract the index of the pointer that left the touch sensor
	        final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) 
	                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
	        final int pointerId = event.getPointerId(pointerIndex);
	        if (pointerId == mActivePointerId) {
	            // This was our active pointer going up. Choose a new
	            // active pointer and adjust accordingly.
	            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
	            _x = event.getX(newPointerIndex);
	            _y = event.getY(newPointerIndex);
	            mActivePointerId = event.getPointerId(newPointerIndex);
	        }
	        break;
	    }

		}
	    return true;
	}
	
	private ScaleGestureDetector mScaleDetector;
	
	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
	    @Override
	    public boolean onScale(ScaleGestureDetector detector) {
	        _renderer.shuffleCamera.setZoom(_renderer.shuffleCamera.getZoom()*detector.getScaleFactor());
	        _renderer.shuffleCamera.Update(0, 0);
	        invalidate();
	        return true;
	    }
	}

}
