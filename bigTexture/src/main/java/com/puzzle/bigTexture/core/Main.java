package com.puzzle.bigTexture.core;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		int factorX = 32;
		int factorY = 8;
		int largeur = 512 * factorX;
		int hauteur = 512 * factorY;
		File outputDir = new File("E:/workspaceEclipse/bigTexture/output");
		File inputDir = new File("C:/Users/Renaud/git/renaud-puzzle/puzzle-pieces/puzzle/renaudEtMimie/images");
		
		
		BigTexture bt = new BigTexture(inputDir, outputDir, largeur, hauteur);
		bt.execute();
		
		
		
	}

}
