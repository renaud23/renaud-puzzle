package com.example.view;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;


public class DrawElement implements IDrawable{
	
	
	
	// Geometric variables
    private float vertices[];
    private short indices[];
    private float uvs[];
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;
    private FloatBuffer uvBuffer;
    
    private float x;
    private float y;
    private float largeur;
    private float hauteur;
    private int textureName;
    
    
    public DrawElement(float x, float y, float largeur, float hauteur) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		
		// We have to create the vertices of our triangle.
        vertices = new float[]{
        	x, y+hauteur, 0.0f,
            x, y, 0.0f,
            x+largeur, y, 0.0f,
            x+largeur, y+hauteur, 0.0f,
         };
 
        indices = new short[] {0, 1, 2, 0, 2, 3}; // The order of vertexrendering.
 
        // The vertex buffer.
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
 
        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(indices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(indices);
        drawListBuffer.position(0);
        
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


    
    
    
    
    
    
    
	public FloatBuffer getUvBuffer() {
		return uvBuffer;
	}

	public void setUvBuffer(FloatBuffer uvBuffer) {
		this.uvBuffer = uvBuffer;
	}


	public FloatBuffer getVertexBuffer() {
		return vertexBuffer;
	}

	public void setVertexBuffer(FloatBuffer vertexBuffer) {
		this.vertexBuffer = vertexBuffer;
	}

	public short[] getIndices() {
		return indices;
	}

	public void setIndices(short[] indices) {
		this.indices = indices;
	}

	public ShortBuffer getDrawListBuffer() {
		return drawListBuffer;
	}

	public void setDrawListBuffer(ShortBuffer drawListBuffer) {
		this.drawListBuffer = drawListBuffer;
	}


	
    
}
