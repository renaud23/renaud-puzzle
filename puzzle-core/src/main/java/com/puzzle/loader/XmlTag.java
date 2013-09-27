package com.puzzle.loader;

public enum XmlTag {
	pieces("pieces"), piece("piece"),id("id"),largeur("largeur"),hauteur("hauteur"),cx("cx"),cy("cy"),nom("nom");
	
	
	private XmlTag(String name){
		this.name = name;
	}
	
	private String name;
	
	public String toString(){
		return this.name;
	}
}
