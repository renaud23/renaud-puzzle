package com.puzzle.view;

public enum PuzzleProperties {
	
	savePath("PUZZLE_SAVE_PATH");
	
	private String name;

	private PuzzleProperties(String name) {
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
}
