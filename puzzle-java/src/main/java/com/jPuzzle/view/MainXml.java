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
import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Puzzle;
import com.puzzle.model.Tapis;

public class MainXml {

	public static void main(String[] args) {
		File file = new File("/home/renaud/puzzle/paris_84/puzzle.xml");
		ImageMemoryManager.getInstance().setPath("/home/renaud/puzzle/paris_84/images/");
		XmlLoader ld = new XmlLoader(file);
		
		
		// le m�tier
		int taille = 4000;
		int te = taille - 200;
		
		Tapis tapis = new Tapis(taille,taille);
		List<Piece> pieces = ld.getPieces();
		Puzzle puzzle = ld.getPuzzle();
		
		Random rnd = new Random();
		
		CompositePiece cmp = new CompositePiece(0,0);
		for(Piece p : pieces){
			p.setX(rnd.nextInt(te)-te/2);
			p.setY(rnd.nextInt(te)-te/2);
			
			p.setPuzzle(puzzle);
			puzzle.put(p.getId(), p);
			
			p.poser(tapis);
			p.updateRect();
//			cmp.addComponent(p);
		}
//		tapis.poserComposite(cmp);
		
		// la vue
		MainScreen m = new MainScreen(600,600);
		
		
		TapisBasicControler tc = new TapisBasicControler(tapis);
		TapisBasicDrawer td = new TapisBasicDrawer(tapis, m.getTapisOffscreen());
		tc.setDrawable(m);
		tc.setTapisDrawer(td);
		m.getOffscreen().addMouseListener(tc);
		m.getOffscreen().addMouseMotionListener(tc);
		m.getOffscreen().addMouseWheelListener(tc);
		m.getFenetre().addKeyListener(tc);
		m.setTapisDrawer(td);
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
