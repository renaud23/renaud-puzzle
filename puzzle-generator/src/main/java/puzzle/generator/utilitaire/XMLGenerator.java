package puzzle.generator.utilitaire;


import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import puzzle.generator.modele.Puzzle;
import puzzle.generator.modele.Tapis;
import puzzle.generator.modele.frame.Cell;
import puzzle.generator.modele.frame.FrameEx;

public class XMLGenerator {
//	public final static String OUTPUT_PATH = "build/description";
	public final static String OUTPUT_NAME = "puzzle.xml";
	
	private String path;
	private Puzzle puzzle;
	private double scaleApercu;
	private String nom;
	private Tapis tapis;
	
	int cote;
	int varMax;
	int varMin;
	
	private Document document;
	private Element rootElement;
	
	
	
	
	public XMLGenerator(String path,Puzzle puzzle,double scaleApercu){
		this.path = path;
		this.puzzle = puzzle;
		this.scaleApercu = scaleApercu;

		this.nom = "Puzzle";
		
		this.cote = (int) (this.puzzle.getScalePiece()* this.puzzle.getDescriptor().getPieceCote());
		this.varMax = (int) (this.puzzle.getScalePiece()* this.puzzle.getDescriptor().getPieceVarMax());
		this.varMin  = (int) (this.puzzle.getScalePiece()* this.puzzle.getDescriptor().getPieceVarMin());
	}	
	

	public void genereFile(){
		File outputFile = new File(this.path+File.separator+OUTPUT_NAME);
		
		this.document = new Document();
		this.rootElement = new Element("puzzle");
		this.document.setRootElement(this.rootElement);
		
		this.rootElement.addContent(new Element("nom").setText(this.nom));
		this.rootElement.addContent(new Element("largeur").setText(String.valueOf(this.puzzle.getNbPiecesWidth())));
		this.rootElement.addContent(new Element("hauteur").setText(String.valueOf(this.puzzle.getNbPiecesHeight())));
		this.rootElement.addContent(new Element("taille").setText(String.valueOf(this.puzzle.getNbPieces())));
		this.rootElement.addContent(new Element("scaleApercu").setText(String.valueOf(this.scaleApercu)));
		
		this.genereTapisSection();
		this.genereSetSection();
		this.generePiecesSection();
		
		XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
		
	    try {
			sortie.output(document, new PrintStream(outputFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	private void genereSetSection(){
		Element set = new Element("set");
		set.addContent(new Element("cote").setText(String.valueOf(this.cote)));
		set.addContent(new Element("varMin").setText(String.valueOf(this.varMin)));
		set.addContent(new Element("varMax").setText(String.valueOf(this.varMax)));
		set.addContent(new Element("scale").setText(String.valueOf(this.puzzle.getScalePiece())));
		
		this.rootElement.addContent(set);
	}
	
	
	private void generePiecesSection(){
		Element pieces = new Element("pieces");
		
		FrameEx frame = this.puzzle.getFrame();
		for(int j=0;j<frame.getHauteur();j++){
			for(int i=0;i<frame.getLargeur();i++){
				Rectangle r = this.getPieceRect(frame.getCell(i, j).getType(), i, j);
				
				Element piece = new Element("piece");
				piece.addContent(new Element("id").setText(String.valueOf(1+i+j*this.puzzle.getNbPiecesWidth())));
				piece.addContent(new Element("x").setText(String.valueOf(r.x)));
				piece.addContent(new Element("y").setText(String.valueOf(r.y)));
				piece.addContent(new Element("cx").setText(String.valueOf( Math.round(r.x + r.width / 2.0))));
				piece.addContent(new Element("cy").setText(String.valueOf(Math.round(r.y + r.height / 2.0))));
				piece.addContent(new Element("largeur").setText(String.valueOf(r.width)));
				piece.addContent(new Element("hauteur").setText(String.valueOf(r.height)));
				pieces.addContent(piece);
			}
		}
		
		
		this.rootElement.addContent(pieces);
	}
	
	/**
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	private Rectangle getPieceRect(int type,int i,int j){
		Rectangle rect = new Rectangle();
		rect.x = i * this.cote;
		rect.y = j * this.cote;
		rect.width = this.cote;
		rect.height = this.cote;
		
		Cell cell = this.puzzle.getFrame().getCell(i, j);
		
		if(cell.isNord()){
			rect.y -= this.varMax;
			rect.height += this.varMax;
		}else if(!cell.isCoteNord()){
			rect.y -= this.varMin;
			rect.height += this.varMin;
		}
		if(cell.isOuest()){
			rect.x -= this.varMax;
			rect.width += this.varMax;
		}else if(!cell.isCoteOuest()){
			rect.x -= this.varMin;
			rect.width += this.varMin;
		}
		if(cell.isSud()){
			rect.height += this.varMax;
		}else if(!cell.isCoteSud()){
			rect.height += this.varMin;
		}
		if(cell.isEst()){
			rect.width += this.varMax;
		}else if(!cell.isCoteEst()){
			rect.width += this.varMin;
		}
		
		return rect;
	}
	
	private void genereTapisSection(){
		Element tapisElm = new Element("tapis");
		tapisElm.addContent(new Element("largeur").setText(String.valueOf(this.tapis.getLargeur())));
		tapisElm.addContent(new Element("hauteur").setText(String.valueOf(this.tapis.getHauteur())));
		
		Element color = new Element("color");

		color.addContent(new Element("r").setText(String.valueOf(this.tapis.getR())));
		color.addContent(new Element("g").setText(String.valueOf(this.tapis.getG())));
		color.addContent(new Element("b").setText(String.valueOf(this.tapis.getB())));
		
		tapisElm.addContent(color);
		this.rootElement.addContent(tapisElm);
	}
	
	
	
	

	public void setTapis(Tapis tapis) {
		this.tapis = tapis;
		
	}
	
}
