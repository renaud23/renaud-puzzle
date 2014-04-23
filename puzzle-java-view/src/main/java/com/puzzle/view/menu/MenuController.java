package com.puzzle.view.menu;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;

import javax.swing.JMenuItem;

import com.puzzle.io.PuzzleIOException;
import com.puzzle.io.XmlLoader;
import com.puzzle.model.Angle;
import com.puzzle.model.MainDroite;
import com.puzzle.model.MainGauche;
import com.puzzle.model.Piece;
import com.puzzle.model.Puzzle;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.LoadView;
import com.puzzle.view.SaveView;
import com.puzzle.view.grid.RectGridPiece;
import com.puzzle.view.menu.MenuView.MenuAction;
import com.puzzle.view.tool.ImageMemoryManager;
import com.puzzle.view.tool.provider.PieceImageProvider;


public class MenuController extends Observable {
	
	/**
	 * 
	 */
	private String puzzlePath;
	
	/**
	 * 
	 */
	private Tapis tapis;
	
	private Fenetre fenetre;
	
	/**
	 * nom et chemin de chaque puzzle.
	 */
	private Map<String, String> puzzles;



	public MenuController(Tapis tapis,String puzzlePath,Fenetre fenetre) {
		this.tapis = tapis;
		this.puzzlePath = puzzlePath;
		this.puzzles = new HashMap<String, String>();
		this.fenetre = fenetre;
	}
	
	public void validate(){
		this.checkPuzzlePath();
	}
	
	private void checkPuzzlePath(){
		File file = new File(this.puzzlePath);	
		
		if(file.isDirectory()){
			for(File fld : file.listFiles()){
				String path = fld.getAbsolutePath();
				String name = fld.getName();
				this.puzzles.put(name, path);
				
				MenuMessage<String> msg = new MenuMessage<String>(MenuAction.addPuzzle, name);
		
				this.setChanged();
				this.notifyObservers(msg);
			}
		}
	}
	
	
	
	public class MenuMessage<U>{
		private MenuAction message;
		private U param;
		
		public MenuMessage(MenuAction message, U param) {
			this.message = message;
			this.param = param;
		}
		public MenuAction getMessage() {
			return message;
		}
		public void setMessage(MenuAction message) {
			this.message = message;
		}
		public U getParam() {
			return param;
		}
		public void setParam(U param) {
			this.param = param;
		}
	}
	
	public void fermerPuzzle(Puzzle p,JMenuItem item){
		if(MainGauche.getInstance().isEmpty() && MainDroite.getInstance().isEmpty()){
			this.tapis.oter(p);
			
			MenuMessage<JMenuItem> msg = new MenuMessage<JMenuItem>(MenuAction.closePuzzle, item);
			this.setChanged();
			this.notifyObservers(msg);
		}else{
			this.setChanged();
			this.notifyObservers(new MenuMessage<String>(MenuAction.afficherMsg, "Reposer toutes les pièces sur le tapis svp."));
		}
	}
	

	/**
	 * chargement d'un puzzle et posage aléatoire sur le tapis.
	 * @param name
	 */
	public void loadPuzzle(String name){
		try {
			String rootPuzzlePath = this.puzzles.get(name);
			File file = new File(rootPuzzlePath+File.separator+"puzzle.xml");
			XmlLoader ld = new XmlLoader(file);
			
			ld.loadDescriptor();
			
			List<Piece> pieces = ld.getPieces();
			Puzzle puzzle = ld.getPuzzle();
			puzzle.setPath(rootPuzzlePath);
			puzzle.setName(name);
			
			// placement aletoire des pieces
			Random rnd = new Random();
			int tx = (int) (tapis.getLargeur() - 200);
			int ty = (int) (tapis.getHauteur() - 200);
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
			
			ImageMemoryManager.getInstance().put(puzzle.getId(), new PieceImageProvider(rootPuzzlePath+File.separator+"images"));
			this.tapis.poser(puzzle);
			
			this.setChanged();
			MenuMessage<Puzzle> msg = new MenuMessage<Puzzle>(MenuAction.openPuzzle, puzzle);
			this.notifyObservers(msg);
			
		} catch (PuzzleIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void importerPartie(){
		LoadView view = new LoadView(this.tapis,null);
//		MainDroite.getInstance().libere();
//		MainGauche.getInstance().libere();
		try {
			List<Puzzle> puzzles = view.load();
			for(Puzzle puzzle : puzzles){
				this.setChanged();
				MenuMessage<Puzzle> msg = new MenuMessage<Puzzle>(MenuAction.openPuzzle, puzzle);
				this.notifyObservers(msg);
			}
		} catch (PuzzleIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void resize(int largeur,int hauteur){
		if(MainGauche.getInstance().isEmpty() && MainDroite.getInstance().isEmpty()){
			this.fenetre.resize(largeur, hauteur);
		}else{
			this.setChanged();
			this.notifyObservers(new MenuMessage<String>(MenuAction.afficherMsg, "Reposer toutes les pièces sur le tapis svp."));
		}
	}
	
	
	public void exporterPartie(){
		if(MainGauche.getInstance().isEmpty() && MainDroite.getInstance().isEmpty()){
			SaveView sv = new SaveView(this.tapis,null);
			sv.save();
		}else{
			this.setChanged();
			this.notifyObservers(new MenuMessage<String>(MenuAction.afficherMsg, "Reposer toutes les pièces sur le tapis svp."));
		}
		
	}

}
