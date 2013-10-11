package com.puzzle.model;

import java.util.Random;

public class Angle {
	
	private static Random rnd = new Random();

	private double[] angles;
	private double theta;
	private int courant;
	private static int nbAngle = 16;
	

	public Angle(){
		this.update(nbAngle);
		this.courant = rnd.nextInt(nbAngle);
	}

	public void update(int nbStep){	
		this.angles = new double[nbStep];
		this.theta = 2.0 * Math.PI / nbStep;
		
		for(int i=0;i<nbStep;i++){
			this.angles[i] = i * this.theta;
		}
	}
	
	
	public double getAngle(){
		return this.angles[this.courant];
	}
	
	
	public void tournerGauche(){
		this.courant++;
		if(this.courant >= this.angles.length) this.courant = 0;
	}
	
	public void tournerDroite(){
		this.courant--;
		if(this.courant <0 ) this.courant = this.angles.length - 1;
	}
	
	public int getIndex(){
		return this.courant;
	}
	
	
	
	
	public void setIndex(int index) {
		this.courant = index;
	}

	public void regler(double angle){
		boolean find = false;
		int i = 0;
		while(!find && i<this.angles.length){
			if(this.angles[i] == angle){
				find = true;
				this.courant = i;	
			}
			i++;
		}
	}
}
