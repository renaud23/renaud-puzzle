package com.puzzle.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.puzzle.model.Puzzle;
import com.puzzle.view.menu.MenuController.MenuMessage;



public class MenuView implements Observer{
	
	private final MenuController controller;
	private JMenuBar menu;
	private JMenu file;
	private JMenu puzzle;
	private JMenu ouvert;
	private JMenuItem importer;
	private JMenuItem exporter;
	
	public MenuView(MenuController controller){
		this.menu = new JMenuBar();
		this.controller = controller;
		this.file = new JMenu("Fichier");
		this.puzzle = new JMenu("Puzzle");
		this.ouvert = new JMenu("Ouvert");
		this.file.add(this.puzzle);
		this.file.add(this.ouvert);
		this.file.addSeparator();
		
		this.initImportExport();
		
		
		this.menu.add(this.file);
		
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
			}
		}
		
	}
	
	
	public enum MenuAction{
		addPuzzle,closePuzzle,openPuzzle;
		
		
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
		
		
		this.file.add(this.importer);
		this.file.add(this.exporter);
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
