package com.puzzle.view.tool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

import com.puzzle.view.core.IDrawer;

public class JavaDrawer implements IDrawer{
	private Graphics2D graphics;
	private Rectangle clipArea;
	private int largeur;
	private int hauteur;
	
	
	public JavaDrawer(Graphics2D graphics) {
		this.graphics = graphics;
	
		this.clipArea = graphics.getDeviceConfiguration().getBounds();
		this.largeur = (int) this.clipArea.getWidth();
		this.hauteur = (int) this.clipArea.getHeight();
	}



	
	
	
	@Override
	public void dispose() {
		this.graphics.dispose();
	}

	@Override
	public void drawRect(Color color, int x, int y, int largeur, int hauteur) {
		graphics.setColor(color);
		graphics.drawRect(x, y, largeur, hauteur);
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
