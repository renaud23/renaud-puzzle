package com.puzzle.view.java;


import java.awt.Color;
import java.awt.Image;


import com.puzzle.view.core.IDrawer;

public class JavaDrawer implements IDrawer{

	private JavaBuffer buffer;
	private int largeur;
	private int hauteur;
	
	
	public JavaDrawer(JavaBuffer buffer,int largeur,int hauteur) {
		this.buffer = buffer;
		this.largeur = largeur;
		this.hauteur = hauteur;
	}


	public void drawImage(Image image,double x,double y,double xRotation,double yRotation,double theta,double scaleX,double scaleY,float alpha){
		this.buffer.drawImage(image, x, y, xRotation, yRotation, theta, scaleX, scaleY,alpha);
	}
	
	public void fillRect(Color color,int x,int y,int width,int height,float alpha){
		this.buffer.fillRect(color, x, y, width, height, alpha);
		
	}
	
	public void transparentClean(){
		this.buffer.transparentClean();
	}
	
	public void clean(){
		this.buffer.clean();
	}
	
	@Override
	public void dispose() {
		
	}

	public void drawImageMask(Image image,double x,double y,double xRotation,double yRotation,double theta,double scaleX,double scaleY,Color color){
		this.buffer.drawImageMask(image, x, y, xRotation, yRotation, theta, scaleX, scaleY, color);
	}
	
	@Override
	public void drawRect(Color color, int x, int y, int largeur, int hauteur,float alpha) {
		this.buffer.drawRect(color, x, y, largeur, hauteur, alpha);
	}

	@Override
	public int getLargeur() {
		return this.largeur;
	}

	@Override
	public int getHauteur() {
		return this.hauteur;
	}
}
