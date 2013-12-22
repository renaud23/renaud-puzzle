package com.puzzle.generator.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class PuzzleGenerator {
	
	
	@Test
	public void generate(){
		
		try {
			// Given
			BufferedImage img = ImageIO.read(
					new File("D:/workspace/generator-core/src/test/resources/images/image.jpg"));
			
			Puzzle puzzle = new Puzzle();
			puzzle.setImage(img);
			puzzle.calculNbPieces(5);
			
			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
