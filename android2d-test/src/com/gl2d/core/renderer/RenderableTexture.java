package com.gl2d.core.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import com.gl2d.core.Shader;

import android.graphics.PointF;
import android.opengl.GLES20;


public class RenderableTexture implements GLRenderable,RenderableOperation{
	
	
	
	// Geometric variables
    private float vertices[];
    private short indices[];
    private float uvs[];
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;
    private FloatBuffer uvBuffer;
    
    private PointF p0;
    private PointF p1;
    private PointF p2;
    private PointF p3;
    private float x;
    private float y;
    private float z = 1;
  





	private float angle;
    private float largeur;
    private float hauteur;
    private int textureIndice;
    
    
    public RenderableTexture(int textureIndice,float x, float y, float largeur, float hauteur) {
		this.x = x;
		this.y = y;
		
		p0 = new PointF(x, y+hauteur);
		p1 = new PointF(x, y);
		p2 = new PointF(x+largeur, y);
		p3 = new PointF(x+largeur, y+hauteur);
		
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.textureIndice = textureIndice;
		
		
        indices = new short[] {0, 1, 2, 0, 2, 3}; // The order of vertexrendering.
 
 
        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(indices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(indices);
        drawListBuffer.position(0);
        
        // initialize texture coordonates
        uvs = new float[] {
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f
        };
 
        // The texture buffer
        ByteBuffer bb2 = ByteBuffer.allocateDirect(uvs.length * 4);
        bb2.order(ByteOrder.nativeOrder());
        uvBuffer = bb2.asFloatBuffer();
        uvBuffer.put(uvs);
        uvBuffer.position(0);   
	}




	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	/*
	 * RenderableOperation
	 */
	
	@Override
	public void moveTo(float x, float y) {		
		this.x = x;
		this.y = y;
	}

	@Override
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
	}

	@Override
	public void turn(float delta) {
		this.angle += delta;
		
	}

	private void checkTransform(){
		p0.x = this.x;
		p0.y = this.y; p0.y += this.hauteur;
		p1.x = this.x;
		p1.y = this.y;
		p2.x = this.x; p2.x += this.largeur;
		p2.y = this.y;
		p3.x = this.x; p3.x += this.largeur;
		p3.y = this.y; p3.y += this.hauteur;
		
		float cx = this.x; cx += this.largeur / 2.0f;
		float cy = this.y; cy += this.hauteur / 2.0f;
		float cos = (float) Math.cos(this.angle);
		float sin = (float) Math.sin(this.angle);
		this.turnPoint(p0, cos, sin, cx, cy);
		this.turnPoint(p1, cos, sin, cx, cy);
		this.turnPoint(p2, cos, sin, cx, cy);
		this.turnPoint(p3, cos, sin, cx, cy);
	}
	
	private void turnPoint(PointF p,float cos,float sin,float cx,float cy){
		float xi = p.x;
		float yi = p.y;
		
//		Rx = cos(a) * (Px-Ox) – sin(a) * (Py-Oy) + Ox
//		Ry = sin(a) * (Px-Ox) + cos(a) * (Py-Oy) + Oy
		
		float ex = p.x - cx;
		float ey = p.y - cy;
		xi = cos * ex - sin * ey + cx;
		yi = sin * ex + cos * ey + cy;
		
		p.x = xi;
		p.y = yi;
	}


	/*
	 * 
	 */
	private void makeVerticeBuffer(){
		this.vertices = new float[]{
	        	p0.x, p0.y, 0.0f,
	            p1.x, p1.y, 0.0f,
	            p2.x, p2.y, 0.0f,
	            p3.x, p3.y, 0.0f,
	         };
	
        // The vertex buffer.
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        this.vertexBuffer = bb.asFloatBuffer();
        this.vertexBuffer.put(vertices);
        this.vertexBuffer.position(0);
    }
	
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}

	public float getLargeur() {
		return largeur;
	}

	public void setLargeur(float largeur) {
		this.largeur = largeur;
	}

	public float getHauteur() {
		return hauteur;
	}

	public void setHauteur(float hauteur) {
		this.hauteur = hauteur;
	}


	@Override
	public void setTextCoord(float[] coord) {
		 ByteBuffer bb = ByteBuffer.allocateDirect(coord.length * 4);
        bb.order(ByteOrder.nativeOrder());
        this.uvBuffer = bb.asFloatBuffer();
        this.uvBuffer.put(coord);
        this.uvBuffer.position(0); 
	}


	public void render(float[] mtrxProjectionAndView){
		this.checkTransform();
		this.makeVerticeBuffer();
		
		
		// get handle to vertex shader's vPosition member
        int positionHandle = GLES20.glGetAttribLocation(Shader.sp_Image, "vPosition");
 
        // Enable generic vertex attribute array
        GLES20.glEnableVertexAttribArray(positionHandle);
 
        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(positionHandle, 3,
                                     GLES20.GL_FLOAT, false,
                                     0, vertexBuffer);
 
        // Get handle to texture coordinates location
        int mTexCoordLoc = GLES20.glGetAttribLocation(Shader.sp_Image, "a_texCoord" );
 
        // Enable generic vertex attribute array
        GLES20.glEnableVertexAttribArray (mTexCoordLoc);
 
        // Prepare the texturecoordinates
        GLES20.glVertexAttribPointer ( mTexCoordLoc, 2, GLES20.GL_FLOAT,
                false,
                0, uvBuffer);
        
       
        // alpha *****
        int mAlpha = GLES20.glGetUniformLocation(Shader.sp_Image, "alpha" );
        GLES20.glUniform1f(mAlpha, 1.0f);
        
        // Get handle to shape's transformation matrix
        int mtrxhandle = GLES20.glGetUniformLocation(Shader.sp_Image, "uMVPMatrix");
 
        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, mtrxProjectionAndView, 0);
 
        // Get handle to textures locations
        int mSamplerLoc = GLES20.glGetUniformLocation (Shader.sp_Image, "s_texture" );
 
        // Set the sampler texture unit to 0, where we have saved the texture.
        GLES20.glUniform1i ( mSamplerLoc, textureIndice);
 
        // Draw the triangle
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
 
        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(mTexCoordLoc);
	}



	@Override
	public int compareTo(GLRenderable o) {
		int val = 1;

		if(this.z < ((RenderableTexture)o).z) val = -1;// attention on utilise un treeset, faut jamais dire == sinon l'objet n'entrera pas 
		return val;
	}
    
}
