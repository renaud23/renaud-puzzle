package com.puzzle.view;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

public class PuzzleCursor {
	public enum CursorType{
		mainPleine("mainPleine"),mainVide("mainVide");
		private String name;

		public String getName() {
			return name;
		}

		private CursorType(String name) {
			this.name = name;
		}
	}
	private Map<CursorType, Cursor> curseurs;
	
	private static PuzzleCursor instance;
	
	
	public static PuzzleCursor getInstance(){
		if(instance == null) instance = new PuzzleCursor();
		return instance;
	}
	
	private PuzzleCursor(){
		curseurs = new HashMap<CursorType, Cursor> ();
		curseurs.put(CursorType.mainPleine,Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		curseurs.put(CursorType.mainVide,Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	
	public void put(CursorType type,Cursor cursor){
		curseurs.put(type, cursor);
	}
	
	public Cursor get(CursorType type){
		return this.curseurs.get(type);
	}
	
	
	public void loadCursor(String path,Point hotspot,CursorType type){
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image image = kit.getImage(path); 
		Cursor cursor = kit.createCustomCursor(image, hotspot, type.getName());
		
		curseurs.put(type,cursor);
	}
}
