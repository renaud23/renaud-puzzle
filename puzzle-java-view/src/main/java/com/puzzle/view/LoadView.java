package com.puzzle.view;

import java.awt.Component;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.puzzle.io.PuzzleIOException;
import com.puzzle.io.XmlLoader;
import com.puzzle.model.Puzzle;
import com.puzzle.model.Tapis;
import com.puzzle.view.tool.ImageMemoryManager;
import com.puzzle.view.tool.provider.PieceImageProvider;

public class LoadView {
	private Tapis tapis;
	private Component pere;
	
	
	public LoadView(Tapis tapis,Component pere) {
		this.tapis = tapis;
		this.pere = pere;
	}



	public void load(){
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers xml de sauvegarde","xml");
		fc.setFileFilter(filter);
	
		int value = fc.showOpenDialog(this.pere);
		
		if(JFileChooser.APPROVE_OPTION == value){
		
		
			XmlLoader ld = new XmlLoader(fc.getSelectedFile());
			try {
				List<Puzzle> puzzles = ld.loadSave(this.tapis);
				// les images
				for(Puzzle puzzle :puzzles){
					ImageMemoryManager.getInstance().put(puzzle.getId(), new PieceImageProvider(puzzle.getPath()+File.separator+"images"));
					this.tapis.poser(puzzle);
				}
				
				
			} catch (PuzzleIOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
}
