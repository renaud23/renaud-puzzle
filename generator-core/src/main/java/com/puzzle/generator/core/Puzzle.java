package com.puzzle.generator.core;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Map;

import com.puzzle.generator.core.descriptor.PieceDescripteur;
import com.puzzle.generator.core.frame.Frame;


/**
 * Le puzzle à batire ;).
 *
 */
public class Puzzle {
	public final static double QParT = 4.0/3.0;
	
	private BufferedImage image;
	private double imageLargeur;
	private double imageHauteur;
	private double ratio;
	
	private int nbPiecesWidth;
	private int nbPiecesHeight;
	private int nbPieces;
	private double scalePiece;
	
	

	private Frame frame;



	private PieceDescripteur descriptor;
	
	
	
	
	/**
	 * 
	 * @param image
	 */
	public void setImage(BufferedImage image){
		this.image = image;
		this.imageLargeur = image.getWidth();
		this.imageHauteur = image.getHeight();
		this.ratio = this.imageLargeur / this.imageHauteur;
	}
	
	/**
	 * 
	 * @param nbPieceLargeur
	 */
	public void calculNbPieces(){
		// calcul du nombre de piï¿½ces.
		
		this.nbPiecesHeight = 1+(int)(this.nbPiecesWidth * (1.0/this.ratio) );
		this.nbPieces = this.nbPiecesWidth * this.nbPiecesHeight;
		
		
//		System.out.println(this.nbPiecesWidth+" "+this.nbPiecesHeight);
	}
	
	/**
	 * 
	 * @param nbPieceLargeur
	 */
	public void calculNbPieces(int nbPieceLargeur){
		// calcul du nombre de piï¿½ces.
		this.nbPiecesWidth = nbPieceLargeur;
		this.calculNbPieces();
	}
	
	/**
	 * 
	 */
	public void genereFrame(){
		this.frame = new Frame(this.nbPiecesWidth, this.nbPiecesHeight);
		this.frame.genere();
	}

	public void cleanFrame(){
		this.frame = null;
	}
	
	public boolean isFrame(){
		return this.frame != null;
	}
	
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public void genereAndSave(String buildPath){
		GeneratorPuzzle gen = new GeneratorPuzzle(this.frame,this.descriptor,this.image,this.scalePiece);
		gen.genereAndSave(buildPath);
	}
	
	/**
	 * 
	 * @param scale
	 * @return
	 */
	public Map<Integer, BufferedImage> genere(String buildPath,double scale){
		GeneratorPuzzle gen = new GeneratorPuzzle(this.frame,this.descriptor,this.image,scale);
		return gen.genere();
	}
	
	/**
	 * Gï¿½nï¿½re un aperï¿½u dans la mï¿½moire.
	 * @param scale
	 * @return
	 */
	public BufferedImage genereApercu(String buildPath,double scalePiece,double scaleApercu){
		GeneratorPuzzle gen = new GeneratorPuzzle(this.frame,this.descriptor,this.image,scalePiece);
		Map<Integer,BufferedImage> images = gen.genere();
		return gen.makeApercu(images,scaleApercu);
	}
	
	/**
	 * 
	 * @param piecePath
	 * @param scalePiece
	 * @param scaleApercu
	 * @return
	 */
	public BufferedImage genereVue(String piecePath, double scaleApercu){
		GeneratorPuzzle gen = new GeneratorPuzzle(this.frame,this.descriptor,this.image,this.scalePiece);
		BufferedImage vue = gen.makeVue(piecePath, scaleApercu);
		
		return vue;
	}
	
	/**
	 * Reconstruit la frame selon la sï¿½lection.
	 * @param selection
	 */
	public void validateSelection(Collection<Integer> selection){
		this.frame.validateSelection(selection);
	}


	public Dimension gePuzzleDim(){
		if(this.descriptor != null){
			Dimension dim = new Dimension();
			dim.width = this.nbPiecesWidth;
			dim.width *= this.descriptor.getPieceCote();
			dim.height = this.nbPiecesHeight;
			dim.height *= this.descriptor.getPieceCote();
			
			
			return dim;
		}else return null;
	}
	
	
	
	public Dimension getImageDim(){
		if(this.image != null){
			Dimension dim = new Dimension();
			
			dim.width = this.image.getWidth();
			dim.height = this.image.getHeight();
			
			return dim;
		}else return null;
	}
	
	
	
	public int getPuzzleLargeur(){
		int l = this.descriptor.getPieceCote();
		l *= this.nbPiecesWidth;
//		l *= this.
		
		return l;
	}
	
	
	
	/*
	 * 
	 */
	public double getLargeurReel() {
		return imageLargeur;
	}


	public double getHauteurReel() {
		return imageHauteur;
	}


	public double getRatio() {
		return ratio;
	}


	public int getNbPiecesWidth() {
		return nbPiecesWidth;
	}


	public int getNbPiecesHeight() {
		return nbPiecesHeight;
	}


	public int getNbPieces() {
		return nbPieces;
	}

	public PieceDescripteur getDescriptor() {
		return descriptor;
	}

	public Frame getFrame() {
		return frame;
	}
	
	public void setDescriptor(PieceDescripteur descriptor) {
		this.descriptor = descriptor;
	}
	
	public double getScalePiece() {
		return scalePiece;
	}

	public void setScalePiece(double scalePiece) {
		this.scalePiece = scalePiece;
	}
}
