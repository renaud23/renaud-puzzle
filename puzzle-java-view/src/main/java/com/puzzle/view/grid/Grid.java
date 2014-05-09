package com.puzzle.view.grid;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.PrintStream;






/**
 * Crée une représentation en tableau de l'image.
 * @author kqhlz2
 *
 */
public class Grid {
	
	private byte[][] matrice;
	
	private int lImg;
	private int hImg;
	
	private int lMat;
	private int hMat;
	
	private int lCarreau;
	private int hCarreau;
	

	private int red;
	private int green;
	private int blue;
	

	
	public Grid(VolatileImage image){
		this.lImg = image.getWidth(null);
		this.hImg = image.getHeight(null);
		
		// en largeur
		double rl = 0.03;
		this.lCarreau = (int) (lImg * rl);
		lMat = lImg / lCarreau;
		
//		System.out.println(lCarreau*lMat+" "+lImg);
		
		double rImg = (double)hImg/(double)lImg;
		hMat = (int) ((double)lMat * rImg);
//		double rh = (double)hMat / (double)hImg;
		this.hCarreau = (int) (hImg / hMat);
		
//		System.out.println(hCarreau*hMat+" "+hImg);
		
//		
		this.matrice = new byte[lMat][hMat];
		int pixPerC = lCarreau * hCarreau;
		
		BufferedImage buff = image.getSnapshot();
		
		
		red = 0;
		green = 0;
		blue = 0;
		
		for(int i=0;i<hMat;i++){
			for(int j=0;j<lMat;j++){
				int xi = j * lCarreau;
				int yi = i * hCarreau;
				int count = 0;
				
				int limX = lCarreau;
				int limY = hCarreau;
				
				if(i == (hMat-1)) limY = hImg - hCarreau * (hMat-1);
				if(j == (lMat-1)) limX = lImg - lCarreau * (lMat-1);	
				
				 
				for(int x=0;x<limX;x++){
					for(int y=0;y<limY;y++){
						
						int c = buff.getRGB(xi+x, yi+y);
						if(c == 0) count++;
						else{
							Color col = new Color(c);
							red += col.getRed();
							blue += col.getBlue();
							green += col.getGreen();
						}
					}
				}
				if( count > ((double)pixPerC*0.5)){
					this.matrice[j][i] = 0;
				}else this.matrice[j][i] = 1;
				
			}
		}
		
		int t = lImg * hImg;
		red /= t;
		blue /= t;
		green /= t;
		
		
	}
	
	
	public boolean isIn(double x,double y){
		boolean state = true;
		int xi = (int) (x / this.lCarreau);
		int yi = (int) (y / this.hCarreau);
		
		if(xi >= lMat) xi = lMat - 1;
		if(yi >= hMat) yi = hMat - 1;
		
		int v = this.matrice[xi][yi];
		
		if(v == 0) state = false;
		
		return state;
	}
	
	
	public void print(PrintStream out){
		for(int i=0;i<hMat;i++){
			for(int j=0;j<lMat;j++){
				if(this.matrice[j][i] == 1)
					out.print("X ");
				else out.print("  ");
			}
			out.println();
		}
	}


	public int getRed() {
		return red;
	}


	public void setRed(int red) {
		this.red = red;
	}


	public int getGreen() {
		return green;
	}


	public void setGreen(int green) {
		this.green = green;
	}


	public int getBlue() {
		return blue;
	}


	public void setBlue(int blue) {
		this.blue = blue;
	}
	
	
}
