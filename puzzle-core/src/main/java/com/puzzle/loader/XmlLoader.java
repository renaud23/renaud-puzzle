package com.puzzle.loader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.puzzle.model.Piece;
import com.puzzle.model.Puzzle;

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
		this.sxb = new SAXBuilder();
		
		this.load();
	}

	
	
	private void load(){
		try {
			this.document = this.sxb.build(this.file);
			this.root = this.document.getRootElement();	
			
			List<Element> rootPieces = this.root.getChildren(XmlTag.pieces.toString());
			
			for(Element eps : rootPieces){
				List<Element> pieces = eps.getChildren(XmlTag.piece.toString());
				
				for(Element ep : pieces){
					int id = Integer.valueOf(ep.getChild(XmlTag.id.toString()).getText());
					double puzzleX = Double.valueOf(ep.getChild(XmlTag.cx.toString()).getText());
					double puzzleY = Double.valueOf(ep.getChild(XmlTag.cy.toString()).getText());
					double largeur = Double.valueOf(ep.getChild(XmlTag.largeur.toString()).getText());
					double hauteur = Double.valueOf(ep.getChild(XmlTag.hauteur.toString()).getText());
					
					Piece p = new Piece(id, puzzleX, puzzleY, largeur, hauteur);
					this.pieces.add(p);
				}// for
			}// for
			
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public List<Piece> getPieces() {
		return this.pieces;
	}

	@Override
	public Puzzle getPuzzle() {
		// TODO Auto-generated method stub
		return null;
	}

}
