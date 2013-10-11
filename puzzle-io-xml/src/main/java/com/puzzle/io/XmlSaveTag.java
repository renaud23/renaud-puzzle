package com.puzzle.io;

public enum XmlSaveTag {

	tapis("tapis"),
	puzzle("puzzle"), 
	path("path"),
	composite("composite"),
	piece("piece"),
	largeur("largeur"),
	hauteur("hauteur"),
	taille("taille"),
	id("id"), 
	x("x"),
	y("y"),
	angle("angle");
	
	private String name;
	
	private XmlSaveTag(String name){
		this.name = name;
	}

	public String toString(){
		return this.name;
	}
	
	public String getName(){
		return this.name;
	}
}
