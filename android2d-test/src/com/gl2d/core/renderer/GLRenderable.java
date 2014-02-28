package com.gl2d.core.renderer;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public interface GLRenderable extends Comparable<GLRenderable>{
//	public FloatBuffer getVertexBuffer();
//	public FloatBuffer getUvBuffer();
//	public short[] getIndices();
//	public ShortBuffer getDrawListBuffer();
//	public int getTextureIndice();
	
	public void render(float[] mtrxProjectionAndView);
	
	public float getX();
	public float getY();
	
	public void setTextCoord(float[] coord);
}
