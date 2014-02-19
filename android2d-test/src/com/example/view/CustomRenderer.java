package com.example.view;

import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.android2d_test.riGraphicTools;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class CustomRenderer implements Renderer{
	
	private List<IDrawable> drawable;
	
	
	 // Our matrices
    private final float[] mtrxProjection = new float[16];
    private final float[] mtrxView = new float[16];
    private final float[] mtrxProjectionAndView = new float[16];
	
	private float screenLargeur = 1280;
	private float screenHauteur = 768;
 
	private Context context;
	private long lastTime;
	private int program;
 
    public CustomRenderer(Context c,float largeur,float hauteur){
        this.context = c;
        this.screenLargeur = largeur;
        this.screenHauteur = hauteur;
        this.lastTime = System.currentTimeMillis() + 100;
    }
    
    public void onPause(){
        /* Do stuff to pause the renderer */
    }
 
    public void onResume() {
        /* Do stuff to resume the renderer */
        this.lastTime = System.currentTimeMillis();
    }

	@Override
	public void onDrawFrame(GL10 arg0) {
		// Get the current time
        long now = System.currentTimeMillis();
 
        // We should make sure we are valid and sane
        if (this.lastTime > now) return;
 
        // Get the amount of time the last frame took.
        long elapsed = now - this.lastTime;

        this.render();
 
        // Save the current time to see how long it took <img src="http://androidblog.reindustries.com/wp-includes/images/smilies/icon_smile.gif" alt=":)" class="wp-smiley"> .
        this.lastTime = now;
		
	}
	
	
	private void render(){
		// clear Screen and Depth Buffer, we have set the clear color as black.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
 
       
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int largeur, int hauteur) {
		 // We need to know the current width and height.
        this.screenLargeur = largeur;
        this.screenHauteur = hauteur;
 
        // Redo the Viewport, making it fullscreen.
        GLES20.glViewport(0, 0, (int)this.screenLargeur, (int)this.screenHauteur);
 
        // Clear our matrices
        for(int i=0;i<16;i++){
            mtrxProjection[i] = 0.0f;
            mtrxView[i] = 0.0f;
            mtrxProjectionAndView[i] = 0.0f;
        }
 
        // Setup our screen width and height for normal sprite translation.
        Matrix.orthoM(mtrxProjection, 0, 0f, this.screenLargeur, 0.0f, this.screenHauteur, 0, 50);
 
        // Set the camera position (View matrix)
        Matrix.setLookAtM(mtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
 
        // Calculate the projection and view transformation
        Matrix.multiplyMM(mtrxProjectionAndView, 0, mtrxProjection, 0, mtrxView, 0);
		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		 // Set the clear color to black
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1);
        
        gl.glEnable(GL10.GL_BLEND); 
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA); 
 
        // Create the shaders, solid color
        int vertexShader = riGraphicTools.loadShader(GLES20.GL_VERTEX_SHADER, riGraphicTools.vs_SolidColor);
        int fragmentShader = riGraphicTools.loadShader(GLES20.GL_FRAGMENT_SHADER, riGraphicTools.fs_SolidColor);
 
        riGraphicTools.sp_SolidColor = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(riGraphicTools.sp_SolidColor, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(riGraphicTools.sp_SolidColor, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(riGraphicTools.sp_SolidColor);                  // creates OpenGL ES program executables
 
        // Create the shaders, images
        vertexShader = riGraphicTools.loadShader(GLES20.GL_VERTEX_SHADER, riGraphicTools.vs_Image);
        fragmentShader = riGraphicTools.loadShader(GLES20.GL_FRAGMENT_SHADER, riGraphicTools.fs_Image);
 
        riGraphicTools.sp_Image = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(riGraphicTools.sp_Image, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(riGraphicTools.sp_Image, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(riGraphicTools.sp_Image);                  // creates OpenGL ES program executables
 
        // Set our shader programm
        GLES20.glUseProgram(riGraphicTools.sp_Image);
	}

}
