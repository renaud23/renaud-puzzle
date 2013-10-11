package com.puzzle.view;

import java.io.File;

import com.puzzle.io.PuzzleIOException;
import com.puzzle.io.XmlLoader;
import com.puzzle.model.Tapis;

public class LoadView {
	private Tapis tapis;
	
	
	
	public LoadView(Tapis tapis) {
		this.tapis = tapis;
	}



	public void load(){
		StringBuffer buff = new StringBuffer(System.getProperty(PuzzleProperties.savePath.getName()));
		buff.append(File.separator).append("save/saveTest.xml");
		File f = new File(buff.toString());
		
		XmlLoader ld = new XmlLoader(f);
		try {
			ld.loadSave(tapis);
		} catch (PuzzleIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
