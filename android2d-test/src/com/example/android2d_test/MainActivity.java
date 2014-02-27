package com.example.android2d_test;

import java.util.Timer;
import java.util.TimerTask;

import com.example.android2d_test.test.Test;
import com.example.view.MyRenderer;

import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	
	private GLSurfaceView glSurfaceView;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
 
        // Turn off the window's title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
 
        // Super
        super.onCreate(savedInstanceState);
 
        // Fullscreen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
 
        // We create our Surfaceview for our OpenGL here.
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        float largeur = metrics.widthPixels;
        float hauteur = metrics.heightPixels;
        MyRenderer renderer =  new MyRenderer(this,largeur,hauteur);
        this.glSurfaceView = new GLSurf(this,renderer);
 
        // Set our view.
        setContentView(R.layout.activity_main);
 
        // Retrieve our Relative layout from our main layout we just set to our view.
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.gamelayout);
 
        // Attach our surfaceview to our relative layout from our main layout.
        RelativeLayout.LayoutParams glParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layout.addView(glSurfaceView, glParams);
        
        // for test
        Timer timer = new Timer();
        TimerTask task = new Test(renderer, largeur, hauteur);
		timer.scheduleAtFixedRate(task, 0, 10);
     
    }
	
	
 
    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

}
