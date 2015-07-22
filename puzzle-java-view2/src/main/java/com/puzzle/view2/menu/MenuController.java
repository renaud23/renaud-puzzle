package com.puzzle.view2.menu;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
import com.puzzle.view2.grid.RectGridPiece;
import com.puzzle.view2.image.ImageProvider;

public class MenuController extends Observable{
	private Tapis tapis;
	private String rootPath;
	
	
	
	public MenuController(Tapis tapis,String rootPath) {
		this.tapis = tapis;
		this.rootPath = rootPath;
	}


	public List<Puzzle> importer(File file){
		XmlLoader ld = new XmlLoader(file);
		List<Puzzle> puzzles = new ArrayList<Puzzle>();
		try {
			puzzles = ld.loadSave(this.tapis);
			// les images
			for(Puzzle puzzle :puzzles){
//				ImageMemoryManager.getInstance().put(puzzle.getId(), new PieceImageProvider(puzzle.getPath()+File.separator+"images"));
				this.tapis.poser(puzzle);
			}
		} catch (PuzzleIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return puzzles;
	}
	
	
	public void exporter(File file){
		XmlLoader ld = new XmlLoader(file);
		// TODO valider l'extention
		try {
			ld.save(this.tapis);
		} catch (PuzzleIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public boolean canExport(){
		return MainGauche.getInstance().isEmpty() && MainDroite.getInstance().isEmpty();
	}
	
	
	public void open(Puzzle puzzle){
		Random rnd = new Random();
		double rangeX = 0.05;
		double rangeY = 0.1;
		int tx = (int) (tapis.getLargeur() - tapis.getLargeur() * rangeX * 2);
		int ty = (int) (tapis.getHauteur() - tapis.getHauteur() * rangeY * 2);
		 
		
//		int centerX = tx/2-rnd.nextInt(tx);
//		int centerY = ty/2-rnd.nextInt(ty);
//		int rayon = (int) Math.min(tapis.getLargeur() * rangeX, tapis.getHauteur() * rangeY);
//		
		Collection<Piece> pieces =  puzzle.getPieces();
		
		for(Piece piece : pieces){
			RectGridPiece grid = new RectGridPiece(piece);
			piece.setRect(grid);
			piece.setX(rnd.nextInt(tx)-tx/2);
			piece.setY(rnd.nextInt(ty)-ty/2);

			piece.setAngle(new Angle());
		}
		this.tapis.poser(puzzle);
	}
	
	public void oterPuzzle(Puzzle p){
		if(MainGauche.getInstance().isEmpty() && MainDroite.getInstance().isEmpty()){
			this.tapis.oter(p);
			Collection<Piece> pieces = p.getPieces();
			for(Piece piece : pieces){
				piece.setComposite(null);
			}
			ImageProvider.getInstance().clean(p);
			
//			MenuMessage<JMenuItem> msg = new MenuMessage<JMenuItem>(MenuAction.closePuzzle, item);
//			this.setChanged();
//			this.notifyObservers(msg);
		}else{
//			this.setChanged();
//			this.notifyObservers(new MenuMessage<String>(MenuAction.afficherMsg, "Reposer toutes les pièces sur le tapis svp."));
		}
	}
	
	public void load(){
		File root = new File(this.rootPath);
		
		for(File f :  root.listFiles()){
			if( f.isDirectory()){
				
				File fp = new File(f.getAbsoluteFile()+f.separator+"puzzle.xml");
				if(fp.exists()){
					XmlLoader ld = new XmlLoader(fp);
					
					
					try {
						ld.loadDescriptor();
						Puzzle puzzle = ld.getPuzzle();
					
//						puzzle.setName(f.getAbsolutePath());
						puzzle.setPath(f.getAbsolutePath());
						List<Piece> pieces = ld.getPieces();
						for(Piece pi : pieces) {
							pi.setPuzzle(puzzle);
							puzzle.put(pi.getId(), pi);
						}
						
						this.setChanged();
						this.notifyObservers(puzzle);
					} catch (PuzzleIOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			
			
		}
	}
}
