package com.puzzle.view;

import java.io.File;
import java.util.List;
import java.util.Random;

import com.puzzle.io.XmlLoader;
import com.puzzle.model.Angle;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Puzzle;
import com.puzzle.model.Tapis;
import com.puzzle.view.controller.IController;
import com.puzzle.view.controller.MyKeyListener;
import com.puzzle.view.controller.MyMouseListener;
import com.puzzle.view.controller.MyMouseMotionListener;
import com.puzzle.view.controller.MyMouseWheelListener;
import com.puzzle.view.mainGauche.MainGaucheController;
import com.puzzle.view.tool.BasicImageProvider;
import com.puzzle.view.tool.ImageMemoryManager;
import com.puzzle.view.zoomTapis.TapisZoomController;





public class MainZoom {

	public static void main(String[] args) {
		int largeur = 10000;
		int hauteur = 5000;

		Tapis tapis = new Tapis(largeur,hauteur);


		Puzzle p1 = MainZoom.loadPuzzle("P:/workspace_java/puzzle-pieces/schtroumf_21/puzzle.xml", largeur, hauteur);
		tapis.poser(p1);
		Puzzle p2 = MainZoom.loadPuzzle("P:/workspace_java/puzzle-pieces/floflo/puzzle.xml", largeur, hauteur);
		tapis.poser(p2);
		
		
		ImageMemoryManager.getInstance().put(p1.getId(),
				new BasicImageProvider("P:/workspace_java/puzzle-pieces/schtroumf_21/images/"));
		ImageMemoryManager.getInstance().put(p2.getId(),
				new BasicImageProvider("P:/workspace_java/puzzle-pieces/floflo/images/"));
		
		
		Fenetre f = new Fenetre(800,600);
		f.start();
		
		IController c = new TapisZoomController(f, tapis);
		f.getOffscreen().addMouseListener(new MyMouseListener(c));
		f.getOffscreen().addMouseMotionListener(new MyMouseMotionListener(c));
		f.getOffscreen().addMouseWheelListener(new MyMouseWheelListener(c));
		f.getFrame().addKeyListener(new MyKeyListener(c));
		
		MainGaucheController mgc = new MainGaucheController(tapis, f);
		f.getMainGauche().getOffscreen().addMouseWheelListener(new MyMouseWheelListener(mgc));
		f.getMainGauche().getOffscreen().addMouseListener(new MyMouseListener(mgc));
		
		System.setProperty(PuzzleProperties.savePath.getName(), "P:/git/renaud-puzzle/puzzle-io-xml/src/main/resources");
		
	}
	
	
	public static Puzzle loadPuzzle(String path,int largeur,int hauteur){
		File file = new File(path);
		XmlLoader ld = new XmlLoader(file);
		List<Piece> pieces = ld.getPieces();
		Puzzle puzzle = ld.getPuzzle();
		
		Random rnd = new Random();
		int tx = largeur - 200;
		int ty = hauteur - 200;
		for(Piece p : pieces){
			p.setX(rnd.nextInt(tx)-tx/2);
			p.setY(rnd.nextInt(ty)-ty/2);
			p.setAngle(new Angle(16));
			
			p.setPuzzle(puzzle);
			puzzle.put(p.getId(), p);
		}
		return puzzle;
	}

}
