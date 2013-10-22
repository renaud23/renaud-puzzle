package com.puzzle.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;

import javax.swing.SwingUtilities;

import com.puzzle.io.PuzzleIOException;
import com.puzzle.io.XmlLoader;
import com.puzzle.model.Angle;
import com.puzzle.model.Piece;
import com.puzzle.model.Puzzle;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.RepaintTask;
import com.puzzle.view.menu.MenuView.MenuAction;
import com.puzzle.view.tool.ImageMemoryManager;
import com.puzzle.view.tool.provider.PieceImageProvider;

public class MenuController extends Observable {
	
	private String puzzlePath;
	private Tapis tapis;
	private Map<String, String> puzzles;



	public MenuController(Tapis tapis,String puzzlePath) {
		this.tapis = tapis;
		this.puzzlePath = puzzlePath;
		this.puzzles = new HashMap<String, String>();
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
			
			// placement aletoire des pieces
			Random rnd = new Random();
			int tx = (int) (tapis.getLargeur() - 200);
			int ty = (int) (tapis.getHauteur() - 200);
			for(Piece p : pieces){
				p.setX(rnd.nextInt(tx)-tx/2);
				p.setY(rnd.nextInt(ty)-ty/2);
				p.setAngle(new Angle());
				
				// liage des pieces au puzzle
				p.setPuzzle(puzzle);
				puzzle.put(p.getId(), p);
			}
			
			
			
			
			this.tapis.poser(puzzle);
			ImageMemoryManager.getInstance().put(puzzle.getId(), new PieceImageProvider(rootPuzzlePath+File.separator+"images"));
			
			this.tapis.change();
			this.tapis.notifyObservers();
			
		} catch (PuzzleIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
//		tapis.poser(p1);
//		ImageMemoryManager.getInstance().put(p1.getId(),
//				new PieceImageProvider(rootPath+"/"+name+"/images/"));
	}


}
