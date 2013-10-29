package com.puzzle.view.core;

import java.awt.Color;


/**
 * interface pour les opérations de dessin à l'écran.
 * et des info necessaire à la gestion de l'affichage.
 * @author renaud
 *
 */
public interface IDrawer {
	
	public void drawRect(Color color,int x,int y,int largeur,int hauteur);
	
	public int getLargeur();
	
	public int getHauteur();
	
	public void dispose();

}
