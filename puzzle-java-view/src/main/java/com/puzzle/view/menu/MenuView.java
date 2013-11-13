package com.puzzle.view.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.puzzle.model.Puzzle;
import com.puzzle.view.menu.MenuController.MenuMessage;



public class MenuView implements Observer{
	
	private final MenuController controller;
	private Component root;
	private JMenuBar menu;
	private JMenu fichier;
	private JMenu affichage;
	private JMenu puzzle;
	private JMenu ouvert;
	
	
	private JMenuItem importer;
	private JMenuItem exporter;
	
	public MenuView(MenuController controller,Component root){
		this.root = root;
		this.menu = new JMenuBar();
		this.controller = controller;
		this.fichier = new JMenu("Fichier");
		this.affichage = new JMenu("Affichage");
		this.puzzle = new JMenu("Puzzle");
		this.ouvert = new JMenu("Ouvert");
		this.fichier.add(this.puzzle);
		this.fichier.add(this.ouvert);
		this.fichier.addSeparator();
		
		this.initImportExport();
		this.initQuit();
		
		
		this.menu.add(this.fichier);
		this.menu.add(this.affichage);
		
		this.controller.addObserver(this);
	}



	@Override
	public void update(Observable o, Object arg) {
		
		
		if(arg instanceof MenuMessage<?> && o instanceof MenuController){
			MenuMessage<?> mm = (MenuMessage<?>)arg;
			final MenuController controller = (MenuController) o;
			
			if(mm.getMessage() == MenuAction.addPuzzle){
					String name = (String) mm.getParam();
					this.addPuzzle(name,controller);
				
			}else if(mm.getMessage() == MenuAction.openPuzzle){
				// on ajoute une option de puzzle ouvert, qui permet de le refermer.
				final Puzzle  p = (Puzzle)mm.getParam();
				final JMenuItem puzz = new JMenuItem((String)p.getName());
				
				puzz.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						controller.fermerPuzzle(p,puzz);
					}
				});
				
				this.ouvert.add(puzz);
			
			}else if(mm.getMessage() == MenuAction.closePuzzle){
				// on retire l'option correspondante.
				this.ouvert.remove((JMenuItem) mm.getParam());
			}if(mm.getMessage() == MenuAction.afficherMsg){
				JOptionPane.showMessageDialog(this.root, (String)mm.getParam(), "Attention!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
	}
	
	
	public enum MenuAction{
		addPuzzle,closePuzzle,openPuzzle,afficherMsg;
	}
	
	
	private void initImportExport(){
		this.importer = new JMenuItem("Importer une partie");
		this.exporter = new JMenuItem("Exporter une partie");
		
		this.importer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.importerPartie();
			}
		});
		this.exporter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.exporterPartie();
			}
		});
		
		
		this.fichier.add(this.importer);
		this.fichier.add(this.exporter);
	}
	
	
	private void initQuit(){
		this.fichier.addSeparator();
		JMenuItem iq = new JMenuItem("Quitter");
		iq.addActionListener(new ActionListener() {
			
			@Override 
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(
					    root,
					    "Voulez-vous quitter l'application ?",
					    "Quitter",
					    JOptionPane.YES_NO_OPTION);
				
				if(n == 0) System.exit(0);
				
			}
		});
		
		this.fichier.add(iq);
	}
	
	
	
	public void addPuzzle(String name,MenuController observer){
		JMenuItem puzz = new JMenuItem(name);
		puzz.addActionListener(new PuzzleLoadListener(observer,name));
		
		this.puzzle.add(puzz);
	}
	
	
	

	public JMenuBar getMenu() {
		return menu;
	}

}
