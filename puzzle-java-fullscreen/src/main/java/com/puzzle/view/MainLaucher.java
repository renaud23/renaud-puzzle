package com.puzzle.view;

import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.io.File;
import com.puzzle.model.Tapis;
import com.puzzle.view.core.Activater;
import com.puzzle.view.core.GameLoop;
import com.puzzle.view.core.Renderer;
import com.puzzle.view.core.TapisConverteur;
import com.puzzle.view.tool.Game;
import com.puzzle.view.tool.ImageLoadException;
import com.puzzle.view.tool.JavaDrawer;
import com.puzzle.view.tool.JavaRenderer;
import com.puzzle.view.tool.SimpleImageLoader;


public class MainLaucher {

	public static void main(String[] args) throws ImageLoadException {
		
		String rootPath = "/home/renaud/workspace/pieces-puzzle";
		int largeur = (int)(36000.0*0.5);
		int hauteur = (int)(12000.0*0.5);
		
		Tapis tapis = new Tapis(largeur, hauteur);
		
		
		
		Image background = new SimpleImageLoader().getImage(rootPath+File.separator+"background"+File.separator+"wood_tapis3.jpg");
		Fenetre f = new Fenetre();
		
		
		
		JavaDrawer drw = new JavaDrawer(f.getBuffer());
		
		TapisConverteur converter = new TapisConverteur(tapis, drw.getLargeur(), drw.getHauteur());
		
		Renderer renderer = new JavaRenderer(tapis,converter, drw,f.getStrategy(),background);
		Activater game = new Game(tapis,converter);
		
		
		f.getWindow().addMouseListener((MouseListener) game);
		f.getWindow().addMouseMotionListener((MouseMotionListener) game);
		f.getWindow().addMouseWheelListener((MouseWheelListener) game);
		
		
		GameLoop loop = new GameLoop(renderer, game);

	}

}
