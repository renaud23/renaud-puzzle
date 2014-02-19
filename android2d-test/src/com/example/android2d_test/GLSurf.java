package com.example.android2d_test;
 
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
 
public class GLSurf extends GLSurfaceView {
 
    private final GLRenderer mRenderer;
 
    public GLSurf(Context context) {
        super(context);
 
        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);
 
        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new GLRenderer(context);
        setRenderer(mRenderer);
 
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        
//        setEGLConfigChooser(8, 8, 8, 8, 0, 0); 
        getHolder().setFormat(PixelFormat.RGBA_8888); 
    }
 
    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mRenderer.onPause();
    }
 
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mRenderer.onResume();
    }
    
    
    private void loadTexture(Context context){
    	int[] texturenames = new int[1];
        GLES20.glGenTextures(1, texturenames, 0);
 
        // Retrieve our image from resources.
        int id = context.getResources().getIdentifier("drawable/ic_launcher", null, context.getPackageName());
 
        // Temporary create a bitmap
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), id);
 
        // Bind texture to texturename
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[0]);
 
        // Set filtering
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
 
        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);
 
        // We are done using the bitmap so we should recycle it.
        bmp.recycle();
    }
 
}