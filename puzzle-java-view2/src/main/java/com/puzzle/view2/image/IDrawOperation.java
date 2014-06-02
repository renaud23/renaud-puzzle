package com.puzzle.view2.image;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Image;

public interface IDrawOperation {
	public Image getImage();
	public void clean();
	public void drawImage(Image image,double x,double y,double xRotation,double yRotation,double theta,double scale,float alpha);
	public void drawImage(Image image,double x,double y,double xRotation,double yRotation,double theta,double scaleX, double scaleY,float alpha);
	public void drawPart(Image image,int dx1,int dy1,int dx2,int dy2,int sx1,int sy1,int sx2,int sy2);
	public void drawRect(Color color,int x,int y,int width,int height);
	public void fillRect(Color color,double x,double y,double width,double height,float alpha);
	public void drawImage(Image image,double x,double y,double xRotation,double yRotation,double theta,double scale,float alpha,Composite composite);
}
