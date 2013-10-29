package com.puzzle.view.core;

import java.awt.Color;
import java.awt.Image;


/**
 * interface pour les opérations de dessin à l'écran.
 * et des info necessaire à la gestion de l'affichage.
 * @author renaud
 *
 */
public interface IDrawer {
	
	public void drawRect(Color color,int x,int y,int largeur,int hauteur,float alpha);
	public void drawImage(Image image,double x,double y,double xRotation,double yRotation,double theta,double scaleX,double scaleY,float alpha);
	public void fillRect(Color color,int x,int y,int width,int height,float alpha);
	
	
	
	public int getLargeur();
	
	public int getHauteur();
	
	public void dispose();

}
