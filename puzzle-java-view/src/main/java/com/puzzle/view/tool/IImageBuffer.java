package com.puzzle.view.tool;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

public interface IImageBuffer {
	public void transparentClean();
	public void clean();
	public void drawImage(Image image,double x,double y,double xRotation,double yRotation,double theta,double scale,float alpha);
	public void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor);
	public void drawRect(Color color,int x,int y,int width,int height);
	public void drawRect(Color color,int x,int y,int width,int height,float alpha);
	public void fillRect(Color color,int x,int y,int width,int height,float alpha);
	public void drawImage(Image image,double x,double y,double xRotation,double yRotation,double theta,double scaleX,double scaleY,float alpha);
	public void drawImageMask(Image image,double x,double y,double xRotation,double yRotation,double theta,double scaleX,double scaleY,Color color);
	public void drawString(String text,int x,int y,Font font, Color color);
	public int getHauteur();
	public int getLargeur();
}
