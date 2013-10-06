package com.puzzle.view;

import java.io.File;
import java.util.List;
import java.util.Random;

import com.puzzle.loader.XmlLoader;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Puzzle;
import com.puzzle.model.Tapis;
import com.puzzle.view.TapisStateContoller.TapisZoomControllerEx;
import com.puzzle.view.basicTapis.TapisBasicController;
import com.puzzle.view.controller.IController;
import com.puzzle.view.controller.MyKeyListener;
import com.puzzle.view.controller.MyMouseListener;
import com.puzzle.view.controller.MyMouseMotionListener;
import com.puzzle.view.controller.MyMouseWheelListener;
import com.puzzle.view.drawer.ImageMemoryManager;
import com.puzzle.view.zoomTapis.TapisZoomController;

public class MainZoom {

	public static void main(String[] args) {
		File file = new File("E:/workspaceEclipse/puzzle-pieces/floflo/puzzle.xml");
		ImageMemoryManager.getInstance().setPath("E:/workspaceEclipse/puzzle-pieces/floflo/images/");
		XmlLoader ld = new XmlLoader(file);
		
		
		int largeur = 4000;
		int hauteur = 4000;
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
			
			
			p.setPuzzle(puzzle);
			puzzle.put(p.getId(), p);
			
			
			p.updateRect();
			p.poser(tapis);
			cmp.addComponent(p);
		}
		cmp.poser(tapis);
		
		Fenetre f = new Fenetre(800,800);
		f.start();
		IController c = new TapisZoomController(f, tapis);
		f.getOffscreen().addMouseListener(new MyMouseListener(c));
		f.getOffscreen().addMouseMotionListener(new MyMouseMotionListener(c));
		f.getOffscreen().addMouseWheelListener(new MyMouseWheelListener(c));
		f.getFrame().addKeyListener(new MyKeyListener(c));

	}

}
