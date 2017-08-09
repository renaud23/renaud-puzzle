package com.puzzle.view2;

import java.awt.Image;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import com.puzzle.io.PuzzleIOException;
import com.puzzle.io.XmlLoader;
import com.puzzle.model.Angle;
import com.puzzle.model.Piece;
import com.puzzle.model.Puzzle;
import com.puzzle.model.Tapis;
import com.puzzle.view2.controller.RootController;
import com.puzzle.view2.grid.RectGridPiece;
import com.puzzle.view2.image.ImageProvider;
import com.puzzle.view2.image.tool.ImageLoadException;
import com.puzzle.view2.image.tool.SimpleImageLoader;
import com.puzzle.view2.layer.BackgroundLayer;
import com.puzzle.view2.layer.HudLayer;
import com.puzzle.view2.layer.Pocket;
import com.puzzle.view2.layer.TapisLayer;
import com.puzzle.view2.layer.Vue;
import com.puzzle.view2.menu.MenuController;
import com.puzzle.view2.menu.MenuView;
import com.puzzle.view2.widget.MiniMap;

public class PuzzleMain {

	public static void main(String[] args) {
		String pathResources = "D:/git_repository/renaud-puzzle/puzzle-java-view2/src/main/resources";
//		String rootPuzzlePath = "E:/git/renaud-puzzle/puzzle-pieces/puzzle/k_160";
//		String rootPuzzlePath2 = "D:/projet_java/personnel/git/renaud-puzzle/puzzle-pieces/puzzle/mug_117";
		String puzzlePiecePath = "D:/git_repository/renaud-puzzle/puzzle-pieces/puzzle";

		
		
		int screenLargeur = 1024;
		int screenHauteur = 768;
		
		double tapisLargeur = 36000.0 * 0.8;
		double tapisHauteur = 12000.0 * 0.8;
		
		Image backgroundImage = null;
		try {
			backgroundImage = new SimpleImageLoader().getImage(pathResources+File.separator+"background"+File.separator+"default-background.jpg");
		}
		catch (ImageLoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Image redCrossImage = null;
		try {
			redCrossImage = new SimpleImageLoader().getImage(pathResources+File.separator+"red-cross.png");
		}
		catch (ImageLoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		ImageProvider.getInstance().setImageWaiting(redCrossImage);
		
		Tapis tapis = new Tapis(tapisLargeur, tapisHauteur);
//		PuzzleMain.load(rootPuzzlePath,tapis);
//		PuzzleMain.load(rootPuzzlePath2,tapis);
		
		
		Vue vue = new Vue();
		Fenetre f = new Fenetre(vue,screenLargeur, screenHauteur);
		
		RootController controller = new RootController();
		
		// layer
		BackgroundLayer backgroundLayer = new BackgroundLayer(vue,backgroundImage, screenLargeur, screenHauteur, tapisLargeur, tapisHauteur, 0.1);
//		backgroundLayer.setxVue(-tapisLargeur/2.0);
//		backgroundLayer.setyVue(tapisHauteur/2.0);
	
		TapisLayer tapisLayer = new TapisLayer(tapis,backgroundLayer, screenLargeur, screenHauteur);
		
	
		HudLayer hud = new HudLayer();
		MiniMap map = new MiniMap(backgroundLayer, backgroundImage, 10, 10, 0.006);
		Pocket pocket = new Pocket(tapis, hud, controller, 
				(int) (screenLargeur *0.05), (int) (screenHauteur - screenLargeur *0.1 - screenHauteur *0.1),
				(int) (screenLargeur - screenLargeur *0.1),(int) (screenHauteur *0.1));
		
		// injection des controllers au root
		controller.addController(tapisLayer);
		controller.addController(map);
		
		// injection des drawables au hud
		hud.addWidget(map);
		
		// ajout des layers � la fenetre
		f.addDrawable(backgroundLayer);
		f.addDrawable(tapisLayer);
		f.addDrawable(hud);
		f.addDrawable(pocket);
		
		f.getComponent().addMouseListener(controller);
		f.getComponent().addMouseMotionListener(controller);
		f.getComponent().addMouseWheelListener(controller);
		f.getComponent().addKeyListener(controller);
		
		// le menu
		MenuController mc = new MenuController(tapis,puzzlePiecePath);
		MenuView mv = new MenuView(f.getFrame(),mc);
		mc.addObserver(mv);
		
		mc.load();
	}
	
	
	
	public static void load(String rootPuzzlePath, Tapis tapis) throws PuzzleIOException{
		File file = new File(rootPuzzlePath+File.separator+"puzzle.xml");
		XmlLoader ld = new XmlLoader(file);
		
		
		ld.loadDescriptor();
		List<Piece> pieces = ld.getPieces();
		Puzzle puzzle = ld.getPuzzle();
		puzzle.setPath(rootPuzzlePath);
		
		// placement aletoire des pieces
		Random rnd = new Random();
		
		double rangeX = 0.05;
		double rangeY = 0.1;
		int tx = (int) (tapis.getLargeur() - tapis.getLargeur() * rangeX * 2);
		int ty = (int) (tapis.getHauteur() - tapis.getHauteur() * rangeY * 2);
		 
		
		int centerX = tx/2-rnd.nextInt(tx);
		int centerY = ty/2-rnd.nextInt(ty);
		int rayon = (int) Math.min(tapis.getLargeur() * rangeX, tapis.getHauteur() * rangeY);
		
		
//		for(Piece p : pieces){
//			RectGridPiece grid = new RectGridPiece(p);
//			p.setRect(grid);
//			
//			int x = 0; 
//			int y = 0;
//			
//			x = centerX + rayon - rnd.nextInt(2*rayon);
//			y = centerY + rayon - rnd.nextInt(2*rayon);
//			
//			p.setX(x);
//			p.setY(y);
//			p.setAngle(new Angle());
//
//			// liage des pieces au puzzle
//			p.setPuzzle(puzzle);
//			puzzle.put(p.getId(), p);
//		}
		
		for(Piece p : pieces){
			RectGridPiece grid = new RectGridPiece(p);
			p.setRect(grid);
			p.setX(rnd.nextInt(tx)-tx/2);
			p.setY(rnd.nextInt(ty)-ty/2);

			p.setAngle(new Angle());

			
			// liage des pieces au puzzle
			p.setPuzzle(puzzle);
			puzzle.put(p.getId(), p);
			

		}

		tapis.poser(puzzle);
	}




}
