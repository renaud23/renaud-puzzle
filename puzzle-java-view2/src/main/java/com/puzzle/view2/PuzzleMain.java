package com.puzzle.view2;

import java.awt.Image;
import java.io.File;
import java.util.List;
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
import com.puzzle.view2.widget.MiniMap;

public class PuzzleMain {

	public static void main(String[] args) throws ImageLoadException, PuzzleIOException {
		String pathResources = "E:/git/renaud-puzzle/puzzle-java-view2/src/main/resources";
		String rootPuzzlePath = "E:/git/renaud-puzzle/puzzle-pieces/puzzle/renaudEtMimie";
		ImageProvider.getInstance().setPath(rootPuzzlePath);
		
		
		int screenLargeur = 800;
		int screenHauteur = 600;
		
		double tapisLargeur = 36000.0;
		double tapisHauteur = 12000.0;
		
		Image backgroundImage = new SimpleImageLoader().getImage(pathResources+File.separator+"background"+File.separator+"default-background.jpg"); 
		Image redCrossImage = new SimpleImageLoader().getImage(pathResources+File.separator+"red-cross.png"); 
		ImageProvider.getInstance().setImageWaiting(redCrossImage);
		
		Tapis tapis = new Tapis(tapisLargeur, tapisHauteur);
		PuzzleMain.load(rootPuzzlePath,tapis);
		
		
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
		Pocket pocket = new Pocket(tapis, hud, controller, 10, screenHauteur - 10 - 100,screenLargeur - 2*10,100);
		
		// injection des controllers au root
		controller.addController(tapisLayer);
		controller.addController(map);
		
		// injection des drawables au hud
		hud.addWidget(map);
		
		// ajout des layers à la fenetre
		f.addDrawable(backgroundLayer);
		f.addDrawable(tapisLayer);
		f.addDrawable(hud);
		f.addDrawable(pocket);
		
		f.getComponent().addMouseListener(controller);
		f.getComponent().addMouseMotionListener(controller);
		f.getComponent().addMouseWheelListener(controller);
		f.getComponent().addKeyListener(controller);

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
		int tx = (int) (tapis.getLargeur() - 200);
		int ty = (int) (tapis.getHauteur() - 200);
		int i = 0;
		for(Piece p : pieces){
			RectGridPiece grid = new RectGridPiece(p);
			p.setRect(grid);
			p.setX(rnd.nextInt(tx)-tx/2);
			p.setY(rnd.nextInt(ty)-ty/2);
//			p.setX(0);
//			p.setY(0);
			p.setAngle(new Angle());
//			p.setAngle(0.0);
			
			// liage des pieces au puzzle
			p.setPuzzle(puzzle);
			puzzle.put(p.getId(), p);
			
			i++;
		}
		
		tapis.poser(puzzle);
	}

}
