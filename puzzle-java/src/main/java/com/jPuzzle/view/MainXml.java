package com.jPuzzle.view;

import java.io.File;
import java.util.List;
import java.util.Random;

import com.jPuzzle.view.basicTapis.TapisBasicControler;
import com.jPuzzle.view.basicTapis.TapisBasicConverter;
import com.jPuzzle.view.basicTapis.TapisBasicDrawer;
import com.jPuzzle.view.drawer.HeadUpDisplayDrawer;
import com.jPuzzle.view.image.ImageMemoryManager;
import com.puzzle.loader.XmlLoader;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.Puzzle;
import com.puzzle.model.Tapis;

public class MainXml {

	public static void main(String[] args) {
		File file = new File("E:/git/renaud-puzzle/puzzle-java/src/main/resources/floflo/puzzle.xml");
		
		XmlLoader ld = new XmlLoader(file);
		
		
		// le métier
		int taille = 3000;
		int te = taille - 200;
		
		Tapis tapis = new Tapis(taille,taille);
		List<Piece> pieces = ld.getPieces();
		Puzzle puzzle = ld.getPuzzle();
		
		Random rnd = new Random();
		for(Piece p : pieces){
			p.setX(rnd.nextInt(te)-te/2);
			p.setY(rnd.nextInt(te)-te/2);
			p.setAngle(Math.PI /8.0 *(1+rnd.nextInt(16)));
			
			p.setPuzzle(puzzle);
			puzzle.put(p.getId(), new Point(p.getPuzzleX(), p.getPuzzleY()));
			
			tapis.poserPiece(p);
		}
		
		
		// la vue
		MainScreen m = new MainScreen(800,800);
		ImageMemoryManager.getInstance().setPath("E:/git/renaud-puzzle/puzzle-java/src/main/resources/floflo/images/");
		
		TapisBasicControler tc = new TapisBasicControler(tapis);
		tc.setDrawable(m);
		m.getOffscreen().addMouseListener(tc);
		m.getOffscreen().addMouseMotionListener(tc);
		m.getOffscreen().addMouseWheelListener(tc);
		m.setTapisDrawer(new TapisBasicDrawer(tapis, m.getTapisOffscreen()));
		m.setHudDrawer(new HeadUpDisplayDrawer( m.getHudOffscreen() ));
		tapis.addObserver(tc);
		
		
		TapisBasicConverter.getInstance().setTapis(tapis);
		TapisBasicConverter.getInstance().setOffscreen(m.getOffscreen());
		TapisBasicConverter.getInstance().update();
		
		/* ** */
		m.drawTapis();
		m.drawHud();
		m.repaint();

	}

}
