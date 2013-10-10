package com.puzzle.io;

public enum XmlTag {
	pieces("pieces"), 
	piece("piece"),
	id("id"),
	largeur("largeur"),
	hauteur("hauteur"),
	cx("cx"),
	cy("cy"),
	nom("nom"),
	angle("angle");
	
	
	private XmlTag(String name){
		this.name = name;
	}
	
	private String name;
	
	public String toString(){
		return this.name;
	}
}
