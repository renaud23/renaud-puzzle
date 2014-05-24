package com.puzzle.view2;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import com.puzzle.view2.controller.RootController;
import com.puzzle.view2.image.IDrawOperation;
import com.puzzle.view2.image.IDrawable;
import com.puzzle.view2.layer.BackgroundLayer;
import com.puzzle.view2.layer.Vue;





public class Fenetre extends Observable implements Iterable<IDrawable>{
	
	

	private JFrame frame;
	
	private AWTImageBufferDecorator offscreen;
	
	private int largeur;
	private int hauteur;
	
	private List<IDrawable> drawables = new ArrayList<>();

	private Timer timer;	
	
	public Fenetre(int largeur,int hauteur){
		this.frame = new JFrame("JPuzzle");
		this.frame.setLayout(new FlowLayout());
		this.largeur = largeur;
		this.hauteur = hauteur;
		
		
	
		
		this.offscreen = new AWTImageBufferDecorator(largeur,hauteur);
		this.offscreen.setPreferredSize(new Dimension(this.largeur,this.hauteur));
		this.offscreen.validate();

		this.frame.add(this.offscreen);

		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.pack();
		
		this.frame.validate();
		
		this.frame.setVisible(true);
		this.frame.setResizable(false);
		
		// la boucle de jeu
		this.timer = new Timer();
		this.start();
		
		this.frame.repaint();
		
		
	}
	

	
	public void setIconImage(Image image){
		this.frame.setIconImage(image);
	}
	
	
	public void cleanListener(){
		this.offscreen.cleanListener();
	}
	
	public void resize(int largeur,int hauteur){
		this.largeur = largeur;
		this.hauteur = hauteur;
	}



	public JFrame getFrame() {
		return frame;
	}




	public Component getOffscreen(){
		return this.offscreen;
	}
	
	public void repaint(){
		this.frame.repaint();
	}



	public IDrawOperation getDrawOperation(){
		return this.offscreen;
	}
	








	private void start(){
		
		final Fenetre f = this;
	
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				
				f.getDrawOperation().clean();
				for(IDrawable drw : f){
					if(drw instanceof DrawOperationAware) 
						((DrawOperationAware)drw).setDrawOperation(f.getDrawOperation());
					drw.draw();
				}
				f.repaint();
			
				
			}
		};
		
		this.timer.schedule(task, 0, 5);
	}

	
	
	public int getLargeur() {
		return largeur;
	}


	public int getHauteur() {
		return hauteur;
	}
	
	
	public void addDrawable(IDrawable d){
		this.drawables.add(d);
	}

	
	




	@Override
	public Iterator<IDrawable> iterator() {
		return this.drawables.iterator();
	}
}
