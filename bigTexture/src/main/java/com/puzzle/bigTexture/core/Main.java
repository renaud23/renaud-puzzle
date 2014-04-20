package com.puzzle.bigTexture.core;

import java.io.File;

import com.puzzle.bigTexture.xml.PuzzleXml;

public class Main {

	public static void main(String[] args) {
		int powX = 11;
		int powY = 11;
		int largeur = (int) Math.pow(2, powX);
		int hauteur = (int) Math.pow(2, powY);
		File outputDir = new File("C:/Users/Renaud/git/renaud-puzzle/bigTexture/output");
		File inputDir = new File("C:/Users/Renaud/git/renaud-puzzle/puzzle-pieces/puzzle/florine_10x80_small/images");
		File xmlInput = new File("C:/Users/Renaud/git/renaud-puzzle/puzzle-pieces/puzzle/florine_10x80_small/puzzle.xml");
		
		
		BigTexture bt = new BigTexture(inputDir, outputDir, largeur, hauteur);
		bt.execute();
		
		PuzzleXml xml = new PuzzleXml(System.out, bt.getPieces());
		xml.save();
		
	}

}
