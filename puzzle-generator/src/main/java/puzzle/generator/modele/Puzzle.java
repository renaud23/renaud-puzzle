package puzzle.generator.modele;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Map;
import puzzle.generator.descriptor.PieceDescripteur;
import puzzle.generator.modele.frame.FrameEx;

/**
 * Le puzzle à batire ;).
 * @author kqhlz2
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
	
	

	private FrameEx frame;



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
		// calcul du nombre de piéces.
		
		this.nbPiecesHeight = 1+(int)(this.nbPiecesWidth * (1.0/this.ratio) );
		this.nbPieces = this.nbPiecesWidth * this.nbPiecesHeight;
		
		
//		System.out.println(this.nbPiecesWidth+" "+this.nbPiecesHeight);
	}
	
	/**
	 * 
	 * @param nbPieceLargeur
	 */
	public void calculNbPieces(int nbPieceLargeur){
		// calcul du nombre de piéces.
		this.nbPiecesWidth = nbPieceLargeur;
		this.calculNbPieces();
	}
	
	/**
	 * 
	 */
	public void genereFrame(){
		this.frame = new FrameEx(this.nbPiecesWidth, this.nbPiecesHeight);
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
	public synchronized void genereAndSave(String buildPath){
		GeneratorEx gen = new GeneratorEx(this.frame,this.descriptor,this.image,this.scalePiece);
		gen.genereAndSave(buildPath);
	}
	
	/**
	 * 
	 * @param scale
	 * @return
	 */
	public synchronized Map<Integer, BufferedImage> genere(String buildPath,double scale){
		GeneratorEx gen = new GeneratorEx(this.frame,this.descriptor,this.image,scale);
		return gen.genere();
	}
	
	/**
	 * Génére un aperçu dans la mémoire.
	 * @param scale
	 * @return
	 */
	public synchronized BufferedImage genereAperçu(String buildPath,double scalePiece,double scaleApercu){
		GeneratorEx gen = new GeneratorEx(this.frame,this.descriptor,this.image,scalePiece);
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
	public synchronized BufferedImage genereVue(String piecePath, double scaleApercu){
		GeneratorEx gen = new GeneratorEx(this.frame,this.descriptor,this.image,this.scalePiece);
		BufferedImage vue = gen.makeVue(piecePath, scaleApercu);
		
		return vue;
	}
	
	/**
	 * Reconstruit la frame selon la sélection.
	 * @param selection
	 */
	public synchronized void validateSelection(Collection<Integer> selection){
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

	public FrameEx getFrame() {
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
