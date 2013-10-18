package com.puzzle.view.mainGauche;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import com.puzzle.view.Offscreen;
import com.puzzle.view.tool.JImageBuffer;



public class MainGaucheView {

	private Offscreen offscreen;
	private int largeur;
	private int hauteur;
	private List<JImageBuffer> buffers;
	
	public MainGaucheView (int largeur,int hauteur){
		
		
		
		
		this.create(largeur, hauteur);
	}
	
	
	public void create(int largeur,int hauteur){
		this.buffers = new ArrayList<>(2);
		this.buffers.clear();
		JImageBuffer im1 = new JImageBuffer(new Color(200,200,200,255), largeur, hauteur);
		im1.clean();
//		ImageBuffer im2 = new ImageBuffer(new Color(0,0,0,0), largeur, hauteur);
//		im2.transparentClean();
		this.buffers.add(0,im1);
//		this.buffers.add(0,im2);
		this.offscreen = new Offscreen(this.buffers);
		this.offscreen.setPreferredSize(new Dimension(largeur,hauteur));
		this.largeur = largeur;
		this.hauteur = hauteur;
	}
	
	public JImageBuffer getBuffer(int i){
		return this.buffers.get(i);
	}
	
	public Offscreen getOffscreen(){
		return this.offscreen;
	}
	
	
}
