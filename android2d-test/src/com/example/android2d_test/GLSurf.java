package com.example.android2d_test;
 
import java.util.Timer;
import java.util.TimerTask;

import com.example.android2d_test.test.Test;
import com.example.view.MyRenderer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.DisplayMetrics;
 
public class GLSurf extends GLSurfaceView {
 
    private final Renderer renderer;
    private final Context context;
    
 
    public GLSurf(Context context,Renderer renderer) {
        super(context);
        this.renderer = renderer;
        this.context = context;

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);
 
        // Set the Renderer for drawing on the GLSurfaceView
//        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//        float largeur = metrics.widthPixels;
//        float hauteur = metrics.heightPixels;
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
    
    

 
}