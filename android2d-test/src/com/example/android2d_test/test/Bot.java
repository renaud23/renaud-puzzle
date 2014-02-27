package com.example.android2d_test.test;

import com.example.view.RenderableElement;
import com.example.view.GLRenderable;
import com.example.view.RenderableOperation;

public class Bot implements RenderableOperation{
	private GLRenderable drawble;
	private float x;
	private float y;
	private float largeur;
	private float hauteur;
	private float velocity;
	
	private float angle = (float) (Math.PI / 4.0);
	
	
	public Bot(float x, float y) {
		this.largeur = 50.0f;
		this.hauteur = 50.0f;
		this.velocity = 2.0f;
		this.x = x;
		this.y = y;
		
		this.drawble = new RenderableElement(0, 
				x-this.largeur/2.0f, 
				y-this.hauteur/2.0f, 
				this.largeur, this.hauteur);
	}


	
	public float getNextX(){
		float vx = (float) Math.cos(angle);
		vx *= this.velocity;
		float nx =  this.drawble.getX();
		nx += vx;
		
		return nx;
	}
	
	public float getNextY(){
		float vy = (float) Math.sin(angle);
		vy *= this.velocity;
		float ny =  this.drawble.getY();
		ny += vy;
		
		return ny;
	}
	
	
	public GLRenderable getDrawble() {
		return drawble;
	}


	public void setDrawble(GLRenderable drawble) {
		this.drawble = drawble;
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


	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}






	@Override
	public void moveTo(float x, float y) {
		((RenderableOperation)this.drawble).moveTo(x,y);
	}

	@Override
	public void translate(float vx, float vy) {
		((RenderableOperation)this.drawble).translate(vx, vy);
	}


	@Override
	public void turn(float delta) {
		((RenderableOperation)this.drawble).turn(delta);
	}
	
	
	
}
