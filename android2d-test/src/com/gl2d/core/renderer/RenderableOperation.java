package com.gl2d.core.renderer;

public interface RenderableOperation {
	public void moveTo(float x,float y);
	public void translate(float vx,float vy);
	public void turn(float delta);
}
