package com.puzzle.bigTexture.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.puzzle.bigTexture.core.Piece;



public class PuzzleXml {

	private File input;
	private List<Piece> pieces;
	
	private Document document;
	private Element root;
	private OutputStream outputStream;
	
	
	
	
	
	
	public PuzzleXml(OutputStream outputStream, List<Piece> pieces) {
		this.outputStream = outputStream;
		this.pieces = pieces;
	}







	public void save(){
		this.root = new Element(XmlTag.puzzle.toString());
		this.document = new Document(this.root);
		Element pieces = new Element(XmlTag.pieces.toString());
		
		this.root.addContent(pieces);
		for(Piece p : this.pieces){
			Element piece = new Element(XmlTag.piece.toString());
			piece.addContent(new Element(XmlTag.id.toString()).setText(String.valueOf(p.getId())));
			piece.addContent(new Element(XmlTag.nameTex.toString()).setText(p.getNameTex()));
			piece.addContent(new Element(XmlTag.xTex.toString()).setText(String.valueOf(p.getxTex())));
			piece.addContent(new Element(XmlTag.yTex.toString()).setText(String.valueOf(p.getyTex())));
			piece.addContent(new Element(XmlTag.largeur.toString()).setText(String.valueOf(p.getLargeur())));
			piece.addContent(new Element(XmlTag.hauteur.toString()).setText(String.valueOf(p.getHauteur())));
			
			pieces.addContent(piece);
		}
		
		
		
		
		
		
	      XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	      try {
			sortie.output(document,System.out );//FileOutputStream
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//new FileOutputStream(this.file)
		  
	}
}
