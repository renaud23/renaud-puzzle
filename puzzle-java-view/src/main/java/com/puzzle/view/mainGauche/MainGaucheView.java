package com.puzzle.view.mainGauche;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import com.puzzle.view.ImageBuffer;
import com.puzzle.view.Offscreen;



public class MainGaucheView {

	private Offscreen offscreen;
	private int largeur;
	private int hauteur;
	private List<ImageBuffer> buffers;
	
	public MainGaucheView (int largeur,int hauteur){
		this.buffers = new ArrayList<>(2);
		this.offscreen = new Offscreen(this.buffers);
		
		
		this.create(largeur, hauteur);
	}
	
	
	public void create(int largeur,int hauteur){
		this.buffers.clear();
		ImageBuffer im1 = new ImageBuffer(new Color(50,100,100,255), largeur, hauteur);
		im1.clean();
//		ImageBuffer im2 = new ImageBuffer(new Color(0,0,0,0), largeur, hauteur);
//		im2.transparentClean();
		this.buffers.add(0,im1);
//		this.buffers.add(0,im2);
		
		this.offscreen.setPreferredSize(new Dimension(largeur,hauteur));
		this.largeur = largeur;
		this.hauteur = hauteur;
	}
	
	public ImageBuffer getBuffer(int i){
		return this.buffers.get(i);
	}
	
	public Offscreen getOffscreen(){
		return this.offscreen;
	}
	
	
}
