package com.puzzle.bigTexture.xml;

public enum XmlTag {
	puzzle("puzzle"),
	pieces("pieces"), 
	piece("piece"),
	id("id"),
	nameTex("nameTex"),
	largeur("largeur"),
	hauteur("hauteur"),
	xTex("xText"),
	yTex("yText"),
	cx("cx"),
	cy("cy");
	
	
	private XmlTag(String name){
		this.name = name;
	}
	
	private String name;
	
	public String toString(){
		return this.name;
	}
}
