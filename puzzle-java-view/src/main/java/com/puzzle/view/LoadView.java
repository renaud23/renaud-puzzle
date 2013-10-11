package com.puzzle.view;

import java.io.File;

import com.puzzle.io.PuzzleIOException;
import com.puzzle.io.XmlLoader;
import com.puzzle.model.Puzzle;
import com.puzzle.model.Tapis;
import com.puzzle.view.tool.BasicImageProvider;
import com.puzzle.view.tool.ImageMemoryManager;

public class LoadView {
	private Tapis tapis;
	
	
	
	public LoadView(Tapis tapis) {
		this.tapis = tapis;
	}



	public void load(){
		StringBuffer buff = new StringBuffer(System.getProperty(PuzzleProperties.savePath.getName()));
		buff.append(File.separator).append("saveTest.xml");
		File f = new File(buff.toString());
		
		XmlLoader ld = new XmlLoader(f);
		try {
			ld.loadSave(this.tapis);
			// les images
			for(Puzzle puzz : this.tapis.getPuzzles()){
				File file = new File(puzz.getPath());
				ImageMemoryManager.getInstance().put(puzz.getId(), new BasicImageProvider(file.getParent()+File.separator+"images"));
				
			}
			
		} catch (PuzzleIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
