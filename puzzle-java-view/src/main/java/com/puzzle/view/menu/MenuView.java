package com.puzzle.view.menu;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.puzzle.view.menu.MenuController.MenuMessage;

public class MenuView implements Observer{
	
	
	private JMenuBar menu;
	private JMenu file;
	private JMenu puzzle;
	
	public MenuView(){
		this.menu = new JMenuBar();
		
		this.file = new JMenu("Fichier");
		this.puzzle = new JMenu("Puzzle");
		this.file.add(this.puzzle);
		
		
		this.menu.add(this.file);
	}



	@Override
	public void update(Observable o, Object arg) {
		
		
		if(arg instanceof MenuMessage<?>){
			MenuMessage<?> mm = (MenuMessage<?>)arg;
			
			switch(mm.getMessage()){
				case  addPuzzle : 
					String name = (String) mm.getParam();
					this.addPuzzle(name,(MenuController) o);
					break;
			}
		}
		
	}
	
	
	public enum MenuAction{
		addPuzzle();
		
		
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
