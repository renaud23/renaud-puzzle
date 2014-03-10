package com.gl2d.core;
 
import java.util.Timer;
import java.util.TimerTask;
import com.gl2d.core.renderer.MyRenderer;
import com.puzzle.android.controller.IController;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
 
public class GLSurface extends GLSurfaceView {
 
    private final Renderer renderer;
    private final Context context;
    private IController controller;
    
    
    private float screenHauteur;
 
    public GLSurface(Context context,Renderer renderer) {
        super(context);
        this.renderer = renderer;
        this.context = context;

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);
 
        // Set the Renderer for drawing on the GLSurfaceView
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float largeur = metrics.widthPixels;
        this.screenHauteur = metrics.heightPixels;
//        this.renderer = new CustomRenderer(context,largeur,hauteur);
        this.setRenderer(renderer);
 
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        
//        setEGLConfigChooser(8, 8, 8, 8, 0, 0); 
        getHolder().setFormat(PixelFormat.RGBA_8888); 
        
       
    }
 
    @Override
    public void onPause() {
        super.onPause();
//        renderer.onPause();
    }
 
    @Override
    public void onResume() {
        super.onResume();
//        renderer.onResume();
    }
    
    
    @Override
    public boolean onTouchEvent(MotionEvent e) {
    	float x = e.getX();
		float y = e.getY();
	
		
    	if(e.getAction() == MotionEvent.ACTION_DOWN){	
    		if(this.controller != null) this.controller.onTouchDown(x, this.screenHauteur - y);
    	}else if(e.getAction() == MotionEvent.ACTION_UP){
    		if(this.controller != null) this.controller.onTouchUp(x, this.screenHauteur - y);
    	}else if(e.getAction() == MotionEvent.ACTION_MOVE){
    		if(this.controller != null) this.controller.onTouchMove(x, this.screenHauteur - y);
    	}
   
        return true;
    }
    
    
    

	public IController getController() {
		return controller;
	}

	public void setController(IController controller) {
		this.controller = controller;
	}
}