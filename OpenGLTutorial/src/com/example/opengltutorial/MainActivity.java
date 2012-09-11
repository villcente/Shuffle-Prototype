package com.example.opengltutorial;

import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {

	private static final String LOG_TAG = MainActivity.class.getSimpleName();
	private ShuffleView _shuffleView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _shuffleView = new ShuffleView(this);        
        setContentView(_shuffleView);
    }
    
    @Override
    protected void onResume()
    {
        // The activity must call the GL surface view's onResume() on activity onResume().
        super.onResume();
        _shuffleView.onResume();
    }
     
    @Override
    protected void onPause()
    {
        // The activity must call the GL surface view's onPause() on activity onPause().
        super.onPause();
        _shuffleView.onPause();
    }

}
