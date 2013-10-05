package puzzle.generator.vue.menu;


import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import puzzle.generator.vue.MainScreen;




public class GeneratorMenu extends JMenuBar implements ActionListener,ItemListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1694094764013540349L;
	
	MainScreen fenetre;
	JMenu menuFichier;
	JMenuItem itemLoadImage;
	
	public GeneratorMenu(MainScreen fenetre){
		this.fenetre = fenetre;
		this.menuFichier = new JMenu("Fichier");
		this.itemLoadImage = new JMenuItem("Charger une image");
		this.itemLoadImage.addActionListener(this);
		
		this.menuFichier.add(itemLoadImage);
		
		
		//
		this.add(this.menuFichier);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==this.itemLoadImage) this.loadImage();
		
	}

	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	/*
	 * 
	 */
	/**
	 * Charge une image.
	 */
	private void loadImage(){
		FileDialog fileBox = new FileDialog(this.fenetre,"Ouvrir...");
		fileBox.setFile("*.jpg");
		fileBox.setDirectory(".//");
		fileBox.setVisible(true);

		try{
			String path = fileBox.getDirectory()+fileBox.getFile();
			if(path != null){
				this.fenetre.loadImage(path);
			}
		}catch(NullPointerException e){

		}
	}

}
