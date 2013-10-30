package com.puzzle.view;

import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.List;
import java.util.Random;

import com.puzzle.io.PuzzleIOException;
import com.puzzle.io.XmlLoader;
import com.puzzle.model.Angle;
import com.puzzle.model.MyRect;
import com.puzzle.model.Piece;
import com.puzzle.model.Puzzle;
import com.puzzle.model.Tapis;
import com.puzzle.view.core.Activater;
import com.puzzle.view.core.GameLoop;
import com.puzzle.view.core.Renderer;
import com.puzzle.view.core.TapisConverteur;
import com.puzzle.view.core.image.ImageMemoryManager;
import com.puzzle.view.core.image.PieceImageProvider;
import com.puzzle.view.java.Game;
import com.puzzle.view.java.ImageLoadException;
import com.puzzle.view.java.JavaDrawer;
import com.puzzle.view.java.JavaRenderer;
import com.puzzle.view.java.SimpleImageLoader;


public class MainLaucher {

	public static void main(String[] args) throws ImageLoadException, PuzzleIOException {
		
		String rootPath = "C:/Documents and Settings/Administrateur/workspace/puzzle-piece";
		String name = "floflo";
		// création du tapis
		int largeur = (int)(36000.0*0.5);
		int hauteur = (int)(12000.0*0.5);
		Tapis tapis = new Tapis(largeur, hauteur);
		
		Puzzle p1 = loadPuzzle(rootPath+File.separator+"puzzle"+File.separator+name, largeur, hauteur);
		tapis.poser(p1);
		ImageMemoryManager.getInstance().put(p1.getId(),
				new PieceImageProvider(rootPath+"/"+"puzzle"+File.separator+name+"/images/"));
		
		Image background = new SimpleImageLoader().getImage(rootPath+File.separator+"background"+File.separator+"wood_tapis3.jpg");
		
		int ls = 800;
		int hs = 600;
		Fenetre f = new Fenetre(ls,hs);
		
		
		
		JavaDrawer drw = new JavaDrawer(f.getStrategy(),f.getLargeur(),f.getHauteur());
		
		TapisConverteur converter = new TapisConverteur(tapis, drw.getLargeur(), drw.getHauteur());
		
		Renderer renderer = new JavaRenderer(tapis,converter, drw,f.getStrategy(),background);
		Activater game = new Game(tapis,converter);
		
		
		f.getWindow().addMouseListener((MouseListener) game);
		f.getWindow().addMouseMotionListener((MouseMotionListener) game);
		f.getWindow().addMouseWheelListener((MouseWheelListener) game);
		f.getWindow().addKeyListener((KeyListener) game);
		
		
		GameLoop loop = new GameLoop(renderer, game);

	}
	
	
	
	public static Puzzle loadPuzzle(String path,int largeur,int hauteur) throws PuzzleIOException{
		// chargement depuis le descripteur de puzzle
		File file = new File(path+File.separator+"puzzle.xml");
		XmlLoader ld = new XmlLoader(file);
		ld.loadDescriptor();
		List<Piece> pieces = ld.getPieces();
		Puzzle puzzle = ld.getPuzzle();
		puzzle.setPath(path);
		
		// placement al�atoire des pi�ces
		Random rnd = new Random();
		int tx = largeur - 400;
		int ty = hauteur - 400;
		for(Piece p : pieces){
			p.setX(rnd.nextInt(tx)-tx/2);
			p.setY(rnd.nextInt(ty)-ty/2);
			p.setAngle(new Angle());
			
			// pi�ces li�es au puzzle
			p.setPuzzle(puzzle);
			puzzle.put(p.getId(), p);
			
			((MyRect)p.getRect()).update();
		}
		
		
		return puzzle;
	}

}
