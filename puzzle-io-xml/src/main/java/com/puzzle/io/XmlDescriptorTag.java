package com.puzzle.io;

public enum XmlDescriptorTag {
	pieces("pieces"), 
	piece("piece"),
	id("id"),
	largeur("largeur"),
	hauteur("hauteur"),
	cx("cx"),
	cy("cy"),
	nom("nom"),
	angle("angle");
	
	
	private XmlDescriptorTag(String name){
		this.name = name;
	}
	
	private String name;
	
	public String toString(){
		return this.name;
	}
}
