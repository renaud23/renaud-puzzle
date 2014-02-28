package com.gl2d.core.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.gl2d.core.Shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView.Renderer;





public class MyRenderer implements Renderer{
	
	private Set<GLRenderable> drawable;
	
	
	 // Our matrices
    private final float[] mtrxProjection = new float[16];
    private final float[] mtrxView = new float[16];
    private final float[] mtrxProjectionAndView = new float[16];
	
	private float screenLargeur;
	private float screenHauteur;
 
	private Context context;
	private long lastTime;

 
    public MyRenderer(Context c,float largeur,float hauteur){
        this.context = c;
        this.screenLargeur = largeur;
        this.screenHauteur = hauteur;
        this.lastTime = System.currentTimeMillis() + 100;
        
        
        this.drawable = new TreeSet<GLRenderable>();    
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

        this.render();
 
        // Save the current time to see how long it took <img src="http://androidblog.reindustries.com/wp-includes/images/smilies/icon_smile.gif" alt=":)" class="wp-smiley"> .
        this.lastTime = now;
		
	}
	
	
	private void render(){
		// clear Screen and Depth Buffer, we have set the clear color as black.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        
        // No culling of back faces
        GLES20.glDisable(GLES20.GL_CULL_FACE);
         
        // No depth testing
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
         
        // Enable blending
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        
        for(GLRenderable dr : this.drawable){
        	dr.render(mtrxProjectionAndView);
        }
       
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

		this.loadTexture(context);
		
		 // Set the clear color to black
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1);
        
        gl.glEnable(GL10.GL_BLEND); 
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA); 
 
        // Create the shaders, solid color
        int vertexShader = Shader.loadShader(GLES20.GL_VERTEX_SHADER, Shader.vs_SolidColor);
        int fragmentShader = Shader.loadShader(GLES20.GL_FRAGMENT_SHADER, Shader.fs_SolidColor);
 
        Shader.sp_SolidColor = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(Shader.sp_SolidColor, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(Shader.sp_SolidColor, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(Shader.sp_SolidColor);                  // creates OpenGL ES program executables
 
        // Create the shaders, images
        vertexShader = Shader.loadShader(GLES20.GL_VERTEX_SHADER, Shader.vs_Image);
        fragmentShader = Shader.loadShader(GLES20.GL_FRAGMENT_SHADER, Shader.fs_Image);
 
        Shader.sp_Image = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(Shader.sp_Image, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(Shader.sp_Image, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(Shader.sp_Image);                  // creates OpenGL ES program executables
 
        // Set our shader programm
        GLES20.glUseProgram(Shader.sp_Image);
	}

	
	
	
	private void loadTexture(Context context){
    	int[] texturenames = new int[1];
        GLES20.glGenTextures(1, texturenames, 0);
 
        // Retrieve our image from resources.
        int id = context.getResources().getIdentifier("drawable/background", null, context.getPackageName());
 
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
	
	
	public void addRenderable(GLRenderable drawable){
		this.drawable.add(drawable);
	}
	
	public void removeRenderable(GLRenderable drawable){
		this.drawable.remove(drawable);
	}
}
