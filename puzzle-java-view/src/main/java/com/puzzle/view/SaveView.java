package com.puzzle.view;

import java.io.File;
import com.puzzle.io.PuzzleIOException;
import com.puzzle.io.XmlLoader;
import com.puzzle.model.Tapis;

public class SaveView {
	
	private Tapis tapis;

	
	public SaveView(Tapis tapis) {
		this.tapis = tapis;
	}


	public void save(){
		StringBuffer buff = new StringBuffer(System.getProperty(PuzzleProperties.savePath.getName()));
		buff.append(File.separator).append("saveTest.xml");
		File f = new File(buff.toString());
		
		XmlLoader ld = new XmlLoader(f);
			
		try {
			ld.save(this.tapis);
		} catch (PuzzleIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
