package com.puzzle.view;

import java.awt.Component;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.puzzle.io.PuzzleIOException;
import com.puzzle.io.XmlLoader;
import com.puzzle.model.Tapis;



public class SaveView {
	
	private Tapis tapis;
	private Component pere;

	
	public SaveView(Tapis tapis,Component pere) {
		this.tapis = tapis;
		this.pere = pere;
	}


	public void save(){
	
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers xml de sauvegarde","xml");
		fc.setFileFilter(filter);
	
		int value = fc.showSaveDialog(this.pere);
		
		if(JFileChooser.APPROVE_OPTION == value){
			
//			int r = -1;
//			if(fc.getSelectedFile().exists()){
//			
//				Icon icon = UIManager.getIcon("OptionPane.warningIcon");
//				r =  JOptionPane.showConfirmDialog(fc,  "Voulez-vous écraser le fichier ?", "Confirm", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,icon);
//			}
			
			
			XmlLoader ld = new XmlLoader(fc.getSelectedFile());
			// TODO valider l'extention
			try {
				ld.save(this.tapis);
			} catch (PuzzleIOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
