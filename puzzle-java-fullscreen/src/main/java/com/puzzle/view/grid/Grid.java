package com.puzzle.view.grid;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.PrintStream;






/**
 * Crée une représentation en tableau de l'image.
 * @author kqhlz2
 *
 */
public class Grid {
	
	private int[][] matrice;
	
	private int lImg;
	private int hImg;
	
	private int lMat;
	private int hMat;
	
	private int lCarreau;
	private int hCarreau;
	
	private VolatileImage image;
	

	
	public Grid(VolatileImage image){
		this.image = image;
		this.lImg = image.getWidth(null);
		this.hImg = image.getHeight(null);
		
		// en largeur
		double rl = 0.05;
		this.lCarreau = (int) (lImg * rl);
		lMat = lImg / lCarreau;
		
//		System.out.println(lCarreau*lMat+" "+lImg);
		
		double rImg = (double)hImg/(double)lImg;
		hMat = (int) ((double)lMat * rImg);
		double rh = (double)hMat / (double)hImg;
		this.hCarreau = (int) (hImg / hMat);
		
//		System.out.println(hCarreau*hMat+" "+hImg);
		
//		
		this.matrice = new int[lMat][hMat];
		
		int nbCarreaux = lMat * hMat;
		int pixPerC = lCarreau * hCarreau;
		
		BufferedImage buff = image.getSnapshot();
		
		for(int i=0;i<hMat;i++){
			for(int j=0;j<lMat;j++){
				int xi = j * lCarreau;
				int yi = i * hCarreau;
				int count = 0;
				for(int x=0;x<lCarreau;x++){
					for(int y=0;y<hCarreau;y++){
						
						int c = buff.getRGB(xi+x, yi+y);
						if(c == 0) count++;
					}
				}
				if( count > (pixPerC/2)){
					this.matrice[j][i] = 0;
				}else this.matrice[j][i] = 1;
				
			}
		}
		
		
	}
	
	
	public boolean isIn(double x,double y){
		boolean state = true;
		int xi = (int) (x / this.lCarreau);
		int yi = (int) (y / this.hCarreau);
		
		int v = this.matrice[xi][yi];
		
		if(v == 0) state = false;
		
		return state;
	}
	
	
	public void print(PrintStream out){
		for(int i=0;i<hMat;i++){
			for(int j=0;j<lMat;j++){
				out.print(this.matrice[j][i]+" ");
			}
			out.println();
		}
	}
	
	
	
	
	
	
	
	
	
	
}
