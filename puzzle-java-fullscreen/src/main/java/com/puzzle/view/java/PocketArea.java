package com.puzzle.view.java;

import java.awt.Color;
import java.awt.event.MouseEvent;
import com.puzzle.view.core.IDrawer;
import com.puzzle.view.hud.Box;
import com.puzzle.view.hud.HudArea;
import com.puzzle.view.hud.HudTask;

public class PocketArea  extends HudArea{
	
	private IDrawer drawer;
	private boolean in;
	
	private int margerVerticale;
	private int margeHorizontale;
	private int x;
	private int y;
	private int largeur;
	private int hauteur;
	private int limite;
	private int ecart;
	private double theta = 0.5;
	

	public PocketArea(IDrawer drawer){
		this.drawer = drawer;
		
		this.task = new HudTask() {
			
			@Override
			public void execute() {
				
				
			}
		};
		this.margerVerticale = (int)(drawer.getLargeur() * 0.01);
		this.margeHorizontale = (int)(drawer.getHauteur() * 0.01);
		this.hauteur = (int)(drawer.getHauteur() * 0.1);
		this.largeur = drawer.getLargeur() - 2* this.margerVerticale;
		this.x = this.margerVerticale;
		this.y = drawer.getHauteur() - this.hauteur - this.margeHorizontale;
		
		double ratio = this.largeur / this.hauteur;
		this.limite = (int) Math.round((ratio - theta)/(1-theta));
		this.ecart = (int)(this.theta * this.hauteur);
		System.out.println(ecart);
		this.shape = new Box(x, y, largeur, hauteur);
	}

	@Override
	public void Render() {
		
		//dev
		Box b = (Box)this.shape;
		this.drawer.drawRect(
				Color.black, 
				b.getX(), b.getY(), 
				b.getLargeur(), b.getHauteur(), 1.0f);
		
		if(in)
			this.drawer.fillRect(
					Color.blue, 
					b.getX(), b.getY(), 
					b.getLargeur(), b.getHauteur(), 0.2f);
		
		
		// pieces
		

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.in = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.in = false;
	}
	
	
	

}
