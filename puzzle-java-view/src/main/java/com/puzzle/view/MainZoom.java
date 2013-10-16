package com.puzzle.view;

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
import com.puzzle.view.controller.IController;
import com.puzzle.view.controller.MyKeyListener;
import com.puzzle.view.controller.MyMouseListener;
import com.puzzle.view.controller.MyMouseMotionListener;
import com.puzzle.view.controller.MyMouseWheelListener;
import com.puzzle.view.mainGauche.MainGaucheController;
import com.puzzle.view.tool.BasicImageProvider;
import com.puzzle.view.tool.ImageMemoryManager;
import com.puzzle.view.tool.SimpleImageLoader;
import com.puzzle.view.zoomTapis.TapisZoomController;
import com.renaud.manager.TasStatistique;





public class MainZoom {

	public static void main(String[] args) throws PuzzleIOException {
		int largeur = 24000;
		int hauteur = 8000;

		Tapis tapis = new Tapis(largeur,hauteur);

		String rootPath = "E:/workspaceEclipse/puzzle-pieces";
//		String rootPath = "/home/renaud/workspace/puzzle-pieces";
//		String rootPath = "C:/Documents and Settings/Administrateur/workspace/puzzle-piece";
		
		Puzzle p1 = MainZoom.loadPuzzle(rootPath+"/floflo/puzzle.xml", largeur, hauteur);
		tapis.poser(p1);
		ImageMemoryManager.getInstance().put(p1.getId(),
				new BasicImageProvider(rootPath+"/floflo/images/"));
		
		Puzzle p2 = MainZoom.loadPuzzle(rootPath+"/fabien_20/puzzle.xml", largeur, hauteur);
		tapis.poser(p2);
		ImageMemoryManager.getInstance().put(p2.getId(),
				new BasicImageProvider(rootPath+"/fabien_20/images/"));
//		
//		Puzzle p3 = MainZoom.loadPuzzle(rootPath+"/floflo/puzzle.xml", largeur, hauteur);
//		tapis.poser(p3);
//		ImageMemoryManager.getInstance().put(p3.getId(),
//				new BasicImageProvider(rootPath+"/floflo/images/"));
		
	
//		CompositePiece cmp = new CompositePiece(0,0);
//		for(Piece p : p1.getPieces()){
//			cmp.addComponent(p);
//		}
//
//		tapis.poser(cmp);
		
		
		TasStatistique stat = tapis.getStatistique();
		System.out.println(stat);
		
		Fenetre f = new Fenetre(800,600);
		
		Image background = new SimpleImageLoader().getImage(rootPath+"/background/wood_tapis3.jpg");
		IController c = new TapisZoomController(background,f, tapis);
		f.getOffscreen().addMouseListener(new MyMouseListener(c));
		f.getOffscreen().addMouseMotionListener(new MyMouseMotionListener(c));
		f.getOffscreen().addMouseWheelListener(new MyMouseWheelListener(c));
		f.getFrame().addKeyListener(new MyKeyListener(c));
		
		MainGaucheController mgc = new MainGaucheController(tapis, f);
		f.getMainGauche().getOffscreen().addMouseWheelListener(new MyMouseWheelListener(mgc));
		f.getMainGauche().getOffscreen().addMouseListener(new MyMouseListener(mgc));
		
		System.setProperty(PuzzleProperties.savePath.getName(), "C:/Users/Renaud/git/renaud-puzzle/puzzle-io-xml/src/main/resources/save");
		
	}
	
	
	public static Puzzle loadPuzzle(String path,int largeur,int hauteur) throws PuzzleIOException{
		// chargement depuis le descripteur de puzzle
		File file = new File(path);
		XmlLoader ld = new XmlLoader(file);
		ld.loadDescriptor();
		List<Piece> pieces = ld.getPieces();
		Puzzle puzzle = ld.getPuzzle();
		puzzle.setPath(path);
		
		// placement al�atoire des pi�ces
		Random rnd = new Random();
		int tx = largeur - 200;
		int ty = hauteur - 200;
		for(Piece p : pieces){
			p.setX(rnd.nextInt(tx)-tx/2);
			p.setY(rnd.nextInt(ty)-ty/2);
			p.setAngle(new Angle());
			
			// pi�ces li�es au puzzle
			p.setPuzzle(puzzle);
			puzzle.put(p.getId(), p);
		}
		return puzzle;
	}

}
