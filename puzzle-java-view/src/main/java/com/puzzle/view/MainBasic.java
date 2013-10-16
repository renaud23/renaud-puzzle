package com.puzzle.view;

import java.io.File;
import java.util.List;
import java.util.Random;

import com.puzzle.io.PuzzleIOException;
import com.puzzle.io.XmlLoader;
import com.puzzle.model.Angle;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Puzzle;
import com.puzzle.model.Tapis;
import com.puzzle.view.basicTapis.TapisBasicController;
import com.puzzle.view.controller.MyKeyListener;
import com.puzzle.view.controller.MyMouseListener;
import com.puzzle.view.controller.MyMouseMotionListener;
import com.puzzle.view.controller.MyMouseWheelListener;
import com.puzzle.view.mainGauche.MainGaucheController;
import com.puzzle.view.tool.BasicImageProvider;
import com.puzzle.view.tool.ImageMemoryManager;

public class MainBasic {
	public static void main(String[] args) throws PuzzleIOException{
		File file = new File("C:/Documents and Settings/Administrateur/git/renaud-puzzle/puzzle-java-view/src/main/resources/floflo/puzzle.xml");
		XmlLoader ld = new XmlLoader(file);
		ld.loadDescriptor();
		
		int largeur = 2000;
		int hauteur = 2000;
		int tx = largeur - 200;
		int ty = hauteur - 200;
		
		Tapis tapis = new Tapis(largeur,hauteur);
		List<Piece> pieces = ld.getPieces();
		Puzzle puzzle = ld.getPuzzle();
		
		Random rnd = new Random();

		CompositePiece cmp = new CompositePiece(0, 0);
		for(Piece p : pieces){
			p.setX(rnd.nextInt(tx)-tx/2);
			p.setY(rnd.nextInt(ty)-ty/2);
			p.setAngle(new Angle());
			
			p.setPuzzle(puzzle);
			puzzle.put(p.getId(), p);
			
		
//			cmp.addComponent(p);
		}
//		cmp.poser(tapis);
		tapis.poser(puzzle);
		
		ImageMemoryManager.getInstance().put(puzzle.getId(), 
				new BasicImageProvider("C:/Documents and Settings/Administrateur/git/renaud-puzzle/puzzle-java-view/src/main/resources/floflo/images/"));
		
		Fenetre f = new Fenetre(750,600);
		TapisBasicController c = new TapisBasicController(f, tapis);
		f.getOffscreen().addMouseListener(new MyMouseListener(c));
		f.getOffscreen().addMouseMotionListener(new MyMouseMotionListener(c));
		f.getOffscreen().addMouseWheelListener(new MyMouseWheelListener(c));
		f.getFrame().addKeyListener(new MyKeyListener(c));
		
		MainGaucheController mgc = new MainGaucheController(tapis, f);
		f.getMainGauche().getOffscreen().addMouseWheelListener(new MyMouseWheelListener(mgc));
		f.getMainGauche().getOffscreen().addMouseListener(new MyMouseListener(mgc));
		
	}
}
