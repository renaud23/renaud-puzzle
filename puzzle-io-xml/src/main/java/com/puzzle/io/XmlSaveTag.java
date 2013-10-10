package com.puzzle.io;

public enum XmlSaveTag {

	puzzle("puzzle"), 
	path("path"),
	pieces("pieces"),
	piece("piece"),
	id("id"), x("x"),y("y"),angle("angle");
	
	private String name;
	
	private XmlSaveTag(String name){
		this.name = name;
	}

	public String toString(){
		return this.name;
	}
}
