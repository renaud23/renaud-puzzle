package com.gl2d.core.renderer;



public interface GLRenderable extends Comparable<GLRenderable>{
	
	public void render(float[] mtrxProjectionAndView);
	public int getZIndex();
	public void setZIndex(int index);
	

}
