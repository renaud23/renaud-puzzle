package com.puzzle.view2.menu;



import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.puzzle.model.Puzzle;



public class MenuView implements Observer{
	private JFrame root;
	private MenuController controller;
	private JMenuBar menu;
	private JMenu fichier;
	private JMenu ouverts;
	private JMenu puzzles;
	
	public MenuView(JFrame root,MenuController controller) {
		this.root = root;
		this.controller = controller;
		this.menu = new JMenuBar();
		this.fichier = new JMenu("Fichier");
		
		this.menu.add(this.fichier);
		
		this.initFichier();
		
		this.root.setJMenuBar(this.menu);
		this.root.pack();
	}


	public JMenuBar getMenu() {
		return menu;
	}
	
	
	private void initFichier(){
		this.puzzles = new JMenu("Puzzle");
		this.ouverts = new JMenu("Ouverts");
		JMenuItem exporter = new JMenuItem("Exporter");
		exporter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		exporter.setMnemonic(KeyEvent.VK_E);
		JMenuItem importer = new JMenuItem("Importer");
		importer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
		importer.setMnemonic(KeyEvent.VK_I);
		
		
		final MenuController mc = this.controller;
		final MenuView mv = this;
		exporter.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(mc.canExport()){
					File f = mv.getFileSave();
					if(f != null) mc.exporter(f);
				}
			}
		});
		importer.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				File f = mv.getFileLoad();
				if(f != null){
					List<Puzzle> puzzles = mc.importer(f);
					
					for(Puzzle puzz : puzzles){
						final Puzzle p = puzz;
						final JMenuItem mi = new JMenuItem(p.getName());
						mv.addOuvert(mi);
						mi.addActionListener(new ActionListener() {
							
							public void actionPerformed(ActionEvent e) {
								mc.oterPuzzle(p);
								mv.removeOuvert(mi);
							}
						});
						mv.addOuvert(mi);
					}
				}
			}
		});
		
		this.fichier.add(this.puzzles);
		this.fichier.add(this.ouverts);
		this.fichier.addSeparator();
		this.fichier.add(exporter);
		this.fichier.add(importer);
	}
	
	
	
	private File getFileSave(){
		File out = null;
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers xml de sauvegarde","xml");
		fc.setFileFilter(filter);
	
		int value = fc.showSaveDialog(this.root);
		
		if(JFileChooser.APPROVE_OPTION == value){
			out = fc.getSelectedFile();
		}
		
		return out;
	}
	
	private File getFileLoad(){
		File in = null;
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers xml de sauvegarde","xml");
		fc.setFileFilter(filter);
	
		int value = fc.showOpenDialog(this.root);
		
		if(JFileChooser.APPROVE_OPTION == value){
			in = fc.getSelectedFile();
		}
		
		return in;
	}
	
	public void addOuvert(JMenuItem it){
		this.ouverts.add(it);
	}
	
	public void removeOuvert(JMenuItem it){
		this.ouverts.remove(it);
	}
	
	public void removePuzzle(JMenuItem it){
		this.puzzles.remove(it);
	}
	
	public void addPuzzle(final Puzzle p){
		final MenuController mc = this.controller;
		final MenuView mv = this;
		final JMenuItem ip = new JMenuItem(p.getName());
		ip.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mc.open(p);
				mv.removePuzzle((JMenuItem) e.getSource());
				
				final JMenuItem mi = new JMenuItem(p.getName());
				mv.addOuvert(mi);
				mi.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						mc.oterPuzzle(p);
						mv.addPuzzle(p);
						mv.removeOuvert(mi);
					}
				});
				
				
			}
		});
		
		this.puzzles.add(ip);
	}

	public void update(Observable o, Object arg) {
		
		if(o == this.controller && arg instanceof Puzzle){
			final Puzzle p = (Puzzle) arg;
			this.addPuzzle(p);
		}
		
	}
}
