package com.puzzle.view;

import java.awt.Image;
import java.io.File;

import com.puzzle.model.Tapis;
import com.puzzle.view.controller.IController;
import com.puzzle.view.controller.MyKeyListener;
import com.puzzle.view.controller.MyMouseListener;
import com.puzzle.view.controller.MyMouseMotionListener;
import com.puzzle.view.controller.MyMouseWheelListener;
import com.puzzle.view.drawer.DrawerDecorator;
import com.puzzle.view.drawer.IDrawerSelection;
import com.puzzle.view.hud.HudControler;
import com.puzzle.view.hud.HudDrawer;
import com.puzzle.view.menu.MenuController;
import com.puzzle.view.menu.MenuView;
import com.puzzle.view.tool.ImageLoadException;
import com.puzzle.view.tool.SimpleImageLoader;
import com.puzzle.view.zoomTapis.DrawZoomSelection;
import com.puzzle.view.zoomTapis.TapisZoomController;
import com.puzzle.view.zoomTapis.TapisZoomConverteur;
import com.puzzle.zoomTapis.state.TapisZoomControllerEx;


public class MainLauncher {

	public static void main(String[] args) throws ImageLoadException {
		int largeur = (int)(36000.0*0.5);
		int hauteur = (int)(12000.0*0.5);

//		String rootPath = System.getProperty("user.dir");
		String rootPath = "E:/workspaceEclipse/puzzle-pieces";

		Tapis tapis = new Tapis(largeur,hauteur);
	
		
		Fenetre f = new Fenetre(800,600);
		
		Image background = new SimpleImageLoader().getImage(rootPath+File.separator+"background"+File.separator+"wood_tapis3.jpg");
		
		// controller du tapis
		IController c = new TapisZoomControllerEx(tapis,background, f);
		
		// controller du hud
		IDrawerSelection drw = new HudDrawer((DrawZoomSelection) ((TapisZoomControllerEx)c).getDrawerSelection());// decore le drawer de TapisZoomControllerEx
		HudControler hc = new HudControler(c , drw,tapis, f);
		((TapisZoomControllerEx)c).setDrawerSelection(drw);
		
		
		f.getOffscreen().addMouseListener(new MyMouseListener(hc));
		f.getOffscreen().addMouseMotionListener(new MyMouseMotionListener(hc));
		f.getOffscreen().addMouseWheelListener(new MyMouseWheelListener(hc));
		f.getFrame().addKeyListener(new MyKeyListener(hc));
		
//		MainGaucheController mgc = new MainGaucheController(tapis, f);
//		f.getMainGauche().getOffscreen().addMouseWheelListener(new MyMouseWheelListener(mgc));
//		f.getMainGauche().getOffscreen().addMouseListener(new MyMouseListener(mgc));

		MenuView menu = new MenuView();
		f.getFrame().setJMenuBar(menu.getMenu());
		MenuController mc = new MenuController(tapis,rootPath+File.separator+"puzzle");
		mc.addObserver(menu);
		mc.validate();
		
		f.getFrame().pack();
	}

}
