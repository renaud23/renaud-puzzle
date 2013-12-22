package com.puzzle.generator.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.puzzle.generator.core.descriptor.PieceDescripteur;
import com.puzzle.generator.core.tools.FileUtils;

public class PuzzleGenerator {
	
	
	@Test
	public void generate(){
		
		try {
			
//			System.out.println(System.getProperty("user.dir"));
			// Given
			BufferedImage img = ImageIO.read(
					new File("src/test/resources/images/image.jpg"));
			
			PieceDescripteur dsc = new PieceDescripteur();
			dsc.setPath("src/main/resources/template");
			dsc.setPieceCote(120);
			dsc.setPieceVarMax(32);
			dsc.setPieceVarMin(7);
			
			String buildPath = "src/test/resources/out/";
			FileUtils.cleanDirectory(buildPath);
			
			Puzzle puzzle = new Puzzle();
			puzzle.setImage(img);
			puzzle.setDescriptor(dsc);
			puzzle.calculNbPieces(5);
			puzzle.genereFrame();
			puzzle.setScalePiece(1.0);
			puzzle.genereAndSave(buildPath);
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
