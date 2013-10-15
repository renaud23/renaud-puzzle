package com.renaud.manager;

public class TasStatistique {
	private int nbNode;
	private int nbLeaf;
	private double largeurLeaf;
	private double hauteurLeaf;
	private double meanElement;
	private int nbMin;
	private int nbMax;
	private double variance;
	
	public String toString(){
		return "[TasStatistique nbNode="+nbNode+" nbLeaf="+nbLeaf+" largeurLeaf="+largeurLeaf+" hauteurLeaf="+hauteurLeaf+" meanElement="+meanElement+" nbMin="+nbMin+" nbMax="+nbMax+" variance="+variance+"]";
	}
	
	
	
	
	
	
	public double getVariance() {
		return variance;
	}
	public void setVariance(double variance) {
		this.variance = variance;
	}
	public int getNbMin() {
		return nbMin;
	}
	public void setNbMin(int nbMin) {
		this.nbMin = nbMin;
	}
	public int getNbMax() {
		return nbMax;
	}
	public void setNbMax(int nbMax) {
		this.nbMax = nbMax;
	}

	public double getMeanElement() {
		return meanElement;
	}
	public void setMeanElement(double meanElement) {
		this.meanElement = meanElement;
	}
	
	public int getNbNode() {
		return nbNode;
	}
	public void setNbNode(int nbNode) {
		this.nbNode = nbNode;
	}
	public int getNbLeaf() {
		return nbLeaf;
	}
	public void setNbLeaf(int nbLeaf) {
		this.nbLeaf = nbLeaf;
	}
	public double getLargeurLeaf() {
		return largeurLeaf;
	}
	public void setLargeurLeaf(double largeurLeaf) {
		this.largeurLeaf = largeurLeaf;
	}
	public double getHauteurLeaf() {
		return hauteurLeaf;
	}
	public void setHauteurLeaf(double hauteurLeaf) {
		this.hauteurLeaf = hauteurLeaf;
	}
	
	
}
