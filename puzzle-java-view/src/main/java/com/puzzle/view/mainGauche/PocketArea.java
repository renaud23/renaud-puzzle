package com.puzzle.view.mainGauche;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingUtilities;

import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.RepaintTask;
import com.puzzle.view.hud.Box;
import com.puzzle.view.hud.HudArea;
import com.puzzle.view.hud.HudTask;
import com.puzzle.view.tool.JImageBuffer;

public class PocketArea  extends HudArea implements Observer{
	
	
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
	
	private JImageBuffer buffer;
	private Fenetre fenetre;
	

	public PocketArea(Tapis tapis,Fenetre fenetre){
		this.fenetre = fenetre;
		this.buffer = fenetre.getBuffer(1);
		
		this.task = new HudTask() {
			
			@Override
			public void execute() {
				
				
			}
		};
		
		tapis.addObserver(this);
		
		this.margerVerticale = (int)(buffer.getLargeur() * 0.01);
		this.margeHorizontale = (int)(buffer.getHauteur() * 0.01);
		this.hauteur = (int)(buffer.getHauteur() * 0.1);
		this.largeur = buffer.getLargeur() - 2* this.margerVerticale;
		this.x = this.margerVerticale;
		this.y = buffer.getHauteur() - this.hauteur - this.margeHorizontale;
		
		double ratio = this.largeur / this.hauteur;
		this.limite = (int) Math.round((ratio - theta)/(1-theta));
		this.ecart = (int)(this.theta * this.hauteur);

		this.shape = new Box(x, y, largeur, hauteur);
	}


	public void clean(){
	
		
		Box b = (Box)this.shape;
		this.buffer.transparentClean(
				b.getX(), b.getY(), 
				b.getLargeur(), b.getHauteur());
	}
	
	@Override
	public void draw() {
		//dev
		Box b = (Box)this.shape;
		this.buffer.drawRect(
				Color.black, 
				b.getX(), b.getY(), 
				b.getLargeur(), b.getHauteur(), 1.0f);
		
		if(in)
			this.buffer.fillRect(
					Color.blue, 
					b.getX(), b.getY(), 
					b.getLargeur(), b.getHauteur(), 0.2f);
			
			
	}


	@Override
	public void mouseEntered() {
		this.in = true;
		this.clean();
		this.draw();
		SwingUtilities.invokeLater(new RepaintTask(this.fenetre));
	}


	@Override
	public void mouseExited() {
		this.in = false;
		this.clean();
		this.draw();
		SwingUtilities.invokeLater(new RepaintTask(this.fenetre));
	}


	@Override
	public void update(Observable o, Object arg) {
//		System.out.println(arg);
		
	}


	
	
	

}
