package com.gl2d.core.renderer;



public class RenderableRect implements GLRenderable,RenderableOperation{

	private int z;
	
	@Override
	public int compareTo(GLRenderable o) {
		int val = 1;

		if(this.z < o.getZIndex()) val = -1;// attention on utilise un treeset, faut jamais dire == sinon l'objet n'entrera pas 
		return val;
	}

	@Override
	public int getZIndex() {
		return this.z;
	}

	@Override
	public void moveTo(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void translate(float vx, float vy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turn(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float[] mtrxProjectionAndView) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setZIndex(int index) {
		// TODO Auto-generated method stub
		
	}





}
