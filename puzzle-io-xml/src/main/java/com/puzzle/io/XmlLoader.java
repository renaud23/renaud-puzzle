package com.puzzle.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.puzzle.model.CompositePiece;
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

	
	
	public void loadDescriptor() throws PuzzleIOException{
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
		this.document = new Document(this.root);
		
		this.root.addContent(new Element(XmlSaveTag.largeur.getName()).setText(String.valueOf(tapis.getLargeur())));
		this.root.addContent(new Element(XmlSaveTag.hauteur.getName()).setText(String.valueOf(tapis.getHauteur())));
		
		for(Puzzle p : tapis.getPuzzles()){
			this.save(p);
		}
		
		this.enregistre();
	}



	
	private void save(Puzzle puzzle) {
		Element puzzElmt = new Element(XmlSaveTag.puzzle.getName());
//		puzzElmt.addContent(new Element(XmlSaveTag.largeur.getName()).setText(String.valueOf(puzzle.getLargeur())));
//		puzzElmt.addContent(new Element(XmlSaveTag.hauteur.getName()).setText(String.valueOf(puzzle.getHauteur())));
//		puzzElmt.addContent(new Element(XmlSaveTag.taille.getName()).setText(String.valueOf(puzzle.getTaille())));
		puzzElmt.addContent(new Element(XmlSaveTag.path.getName()).setText(puzzle.getPath()));
		
		
		Map<CompositePiece, Element> composites = new HashMap<CompositePiece, Element>();
		for(Piece p : puzzle.getPieces()){
			
			Element pieceElmt = new Element(XmlSaveTag.piece.getName());
			pieceElmt.addContent(new Element(XmlSaveTag.id.getName()).setText(String.valueOf(p.getId())));
			pieceElmt.addContent(new Element(XmlSaveTag.x.getName()).setText(String.valueOf(p.getCentre().getX())));
			pieceElmt.addContent(new Element(XmlSaveTag.y.getName()).setText(String.valueOf(p.getCentre().getY())));
			pieceElmt.addContent(new Element(XmlSaveTag.angle.getName()).setText(String.valueOf(p.getAngleIndex())));
			
			
			
			if(p.getComposite()!=null){
				Element cmpElmt = composites.get(p.getComposite());
				if(cmpElmt == null) {
					cmpElmt = new Element(XmlSaveTag.composite.getName());
					cmpElmt.addContent(new Element(XmlSaveTag.x.getName()).setText(String.valueOf(p.getComposite().getCentre().getX())));
					cmpElmt.addContent(new Element(XmlSaveTag.y.getName()).setText(String.valueOf(p.getComposite().getCentre().getY())));
					composites.put(p.getComposite(),cmpElmt);
					puzzElmt.addContent(cmpElmt);
				}
				cmpElmt.addContent(pieceElmt);
			}else puzzElmt.addContent(pieceElmt);
		}
		
		this.root.addContent(puzzElmt);
		
	}

	private void affiche() throws PuzzleIOException{
	   try{
	      XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	      sortie.output(this.document, System.out);
	      
	   }catch (IOException e){
		   throw new PuzzleIOException("Impossible de constituer le xml de sauvegarde.",e);
	   }
	}
	
	void enregistre()  throws PuzzleIOException{
	   try{
	      XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	      sortie.output(document, new FileOutputStream(this.file));
	   }
	   catch (java.io.IOException e){
		   throw new PuzzleIOException("Impossible de constituer sauvegarder.",e);
	   }
	}



	@Override
	public void loadSave(Tapis tapis) throws PuzzleIOException {
		tapis.nettoyer();
		this.sxb = new SAXBuilder();
		try {
			this.document = this.sxb.build(this.file);
			this.root = this.document.getRootElement();	
			
			tapis.setLargeur(Double.valueOf(this.root.getChild(XmlSaveTag.largeur.getName()).getText()));
			tapis.setHauteur(Double.valueOf(this.root.getChild(XmlSaveTag.hauteur.getName()).getText()));
			
			List<Element> puzzElmt = this.root.getChildren(XmlSaveTag.puzzle.getName());
			
			for(Element puzz : puzzElmt){
				File file = new File(puzz.getChildText(XmlSaveTag.path.getName()));
				XmlLoader loader = new XmlLoader(file);
				loader.loadDescriptor();
				
				List<Piece> pieces = loader.getPieces();
				Puzzle puzzle = loader.getPuzzle();
				puzzle.setPath(puzz.getChildText(XmlSaveTag.path.getName()));
				
				// liage
				for(Piece p : pieces){
					p.setPuzzle(puzzle);
					puzzle.put(p.getId(), p);
				}
				
				// plaçage
				for(Element cmpElmt : puzz.getChildren(XmlSaveTag.composite.getName())){
					CompositePiece cmp = new CompositePiece(
							Double.valueOf(cmpElmt.getChildText(XmlSaveTag.x.getName())),
							Double.valueOf(cmpElmt.getChildText(XmlSaveTag.y.getName())));
					for(Element pe : cmpElmt.getChildren(XmlSaveTag.piece.getName())){
						Piece piece = puzzle.get(Integer.valueOf(pe.getChildText(XmlSaveTag.id.getName())));
						piece.getCentre().setX(Double.valueOf(pe.getChildText(XmlSaveTag.x.getName())));
						piece.getCentre().setY(Double.valueOf(pe.getChildText(XmlSaveTag.y.getName())));
						piece.setAngleIndex(Integer.valueOf(pe.getChildText(XmlSaveTag.angle.getName())));
						
						cmp.addComponent(piece);
					}
					
				}
				for(Element pe : puzz.getChildren(XmlSaveTag.piece.getName())){
					Piece piece = puzzle.get(Integer.valueOf(pe.getChildText(XmlSaveTag.id.getName())));
					piece.getCentre().setX(Double.valueOf(pe.getChildText(XmlSaveTag.x.getName())));
					piece.getCentre().setY(Double.valueOf(pe.getChildText(XmlSaveTag.y.getName())));
					piece.setAngleIndex(Integer.valueOf(pe.getChildText(XmlSaveTag.angle.getName())));
					
				}
				
				tapis.poser(puzzle);
			}	
			
		} catch (JDOMException | IOException e) {
			throw new PuzzleIOException("Impossible de charger une sauvegarde.", e);
		}
	}
	
	
	
	
}
