package com.puzzle.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.puzzle.model.Piece;
import com.puzzle.model.Puzzle;
import com.puzzle.model.Tapis;

public class XmlLoader implements PuzzleLoader{
	
	private File file;
	
	private Puzzle puzzle;
	private List<Piece> pieces;
	
	private SAXBuilder sxb;
	private Document document;
	private Element root;
	

	public XmlLoader(File file) {
		this.file = file;
		this.pieces = new ArrayList<Piece>();
	}

	
	
	public void load() throws PuzzleIOException{
		this.sxb = new SAXBuilder();
		try {
			this.document = this.sxb.build(this.file);
			this.root = this.document.getRootElement();	
			
			List<Element> rootPieces = this.root.getChildren(XmlDescriptorTag.pieces.toString());
			int puzzl = Integer.valueOf(this.root.getChild(XmlDescriptorTag.largeur.toString()).getText());
			int puzzh = Integer.valueOf(this.root.getChild(XmlDescriptorTag.hauteur.toString()).getText());
			String nom = this.root.getChild(XmlDescriptorTag.nom.toString()).getText();
			this.puzzle = new Puzzle(nom, puzzl, puzzh);
			
			
			for(Element eps : rootPieces){
				List<Element> pieces = eps.getChildren(XmlDescriptorTag.piece.toString());
				for(Element ep : pieces){
					int id = Integer.valueOf(ep.getChild(XmlDescriptorTag.id.toString()).getText());
					double puzzleX = Double.valueOf(ep.getChild(XmlDescriptorTag.cx.toString()).getText());
					double puzzleY = Double.valueOf(ep.getChild(XmlDescriptorTag.cy.toString()).getText());
					double largeur = Double.valueOf(ep.getChild(XmlDescriptorTag.largeur.toString()).getText());
					double hauteur = Double.valueOf(ep.getChild(XmlDescriptorTag.hauteur.toString()).getText());
					double angle = 0.0;
					if(ep.getChild(XmlDescriptorTag.angle.toString()) != null) angle = Double.valueOf(ep.getChild(XmlDescriptorTag.angle.toString()).getText());
					
					Piece p = new Piece(id, puzzleX, puzzleY, largeur, hauteur);
					p.setAngle(angle);
					this.pieces.add(p);
				}// for
			}// for
			
		} catch (JDOMException | IOException e) {
			throw new PuzzleIOException("Impossible de charger un fichier de description.", e);
		}
	}
	
	
	@Override
	public List<Piece> getPieces() {
		return this.pieces;
	}

	@Override
	public Puzzle getPuzzle() {
		return this.puzzle;
	}



	@Override
	public void save(Tapis tapis)  throws PuzzleIOException{
		this.root = new Element(XmlSaveTag.tapis.getName());
		
		for(Puzzle p : tapis.getPuzzles()){
			this.save(p);
		}
		
	}



	
	private void save(Puzzle puzzle) {
		System.out.println(puzzle.getId());
		
	}

	void affiche() throws IOException{
	   try{
	      XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	      sortie.output(document, System.out);
	   }catch (java.io.IOException e){
		   
	   }
	}
}
