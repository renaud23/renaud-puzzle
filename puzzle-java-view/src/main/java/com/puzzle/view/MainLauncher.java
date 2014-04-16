package com.puzzle.view;

import java.awt.Image;
import java.awt.Point;
import java.io.File;

import com.puzzle.model.Tapis;
import com.puzzle.view.PuzzleCursor.CursorType;
import com.puzzle.view.controller.MyKeyListener;
import com.puzzle.view.controller.MyMouseListener;
import com.puzzle.view.controller.MyMouseMotionListener;
import com.puzzle.view.controller.MyMouseWheelListener;
import com.puzzle.view.drawer.DrawSelection;
import com.puzzle.view.hud.HudControler;
import com.puzzle.view.hud.HudDrawer;
import com.puzzle.view.hud.Loupe;
import com.puzzle.view.hud.LunetteArea;
import com.puzzle.view.menu.MenuController;
import com.puzzle.view.menu.MenuView;
import com.puzzle.view.pocket.Pocket;
import com.puzzle.view.tool.ImageLoadException;
import com.puzzle.view.tool.SimpleImageLoader;
import com.puzzle.view.zoomTapis.TapisZoomController;
import com.puzzle.view.zoomTapis.TapisZoomConverteur;
import com.puzzle.view.zoomTapis.TapisZoomDrawer;


public class MainLauncher {

	public static void main(String[] args) throws ImageLoadException {
		int largeurTapis = (int)(36000.0*0.5);
		int hauteurTapis = (int)(12000.0*0.5);
		int largeurScreen = 800;
		int hauteurScreen = 600;

//		String rootPath = System.getProperty("user.dir");
		String cursorPath = System.getProperty("user.dir")+"/src/main/resources/cursor/";
		String rootPath = "C:/Users/Renaud/git/renaud-puzzle/puzzle-pieces";

		Tapis tapis = new Tapis(largeurTapis,hauteurTapis);
	
		PuzzleCursor.getInstance().loadCursor(cursorPath+"mainPleine.png", new Point(2,2), CursorType.mainPleine);
		PuzzleCursor.getInstance().loadCursor(cursorPath+"mainVide.png", new Point(2,2), CursorType.mainVide);
		Fenetre f = new Fenetre(largeurScreen,hauteurScreen);
		
		Image background = new SimpleImageLoader().getImage(rootPath+File.separator+"background"+File.separator+"default.jpg");
		
		TapisZoomConverteur cv = new TapisZoomConverteur(largeurTapis,hauteurTapis,largeurScreen,hauteurScreen);
		
		// Drawer
		TapisZoomDrawer tapisDrawer = new TapisZoomDrawer(background,tapis,f.getBuffer(0),cv);
		DrawSelection selectionDrawer = new DrawSelection(f.getBuffer(1), cv);
		HudDrawer hudDrawer = new HudDrawer(selectionDrawer,f.getBuffer(1),cv);// decore le drawer de TapisZoomControllerEx
		
		// Controller
		TapisZoomController c = new TapisZoomController(tapis, cv, tapisDrawer,selectionDrawer,f.getOffscreen());
		HudControler hc = new HudControler(c , hudDrawer,tapis);
		c.setDrawerSelection(hudDrawer);
		
		// les éléments du hud
		Pocket pocket = new Pocket(hc, f.getBuffer(1));
		LunetteArea lunette = new LunetteArea(c,f.getBuffer(1));
		Loupe loupe = new Loupe(hc,cv,background,tapis,f.getBuffer(1),f.getBuffer(0));
		tapis.addObserver(pocket);
		hudDrawer.addDrawer(pocket);
		hudDrawer.addDrawer(lunette);
		hudDrawer.addDrawer(loupe);
		hc.addArea(lunette);
		hc.addArea(loupe);
		hudDrawer.draw();
		
		
		// les listeners
		f.getOffscreen().addMouseListener(new MyMouseListener(hc));
		f.getOffscreen().addMouseMotionListener(new MyMouseMotionListener(hc));
		f.getOffscreen().addMouseWheelListener(new MyMouseWheelListener(hc));
		f.getFrame().addKeyListener(new MyKeyListener(hc));
		
		// pour le redim de la fenêtre
		f.addObserver(tapisDrawer);
		f.addObserver(selectionDrawer);
		f.addObserver(cv);
		f.addObserver(lunette);
		f.addObserver(pocket);
		f.addObserver(loupe);

		// creation du menu.
		MenuController mc = new MenuController(tapis,rootPath+File.separator+"puzzle",f);
		MenuView menu = new MenuView(mc,f.getFrame());
		f.getFrame().setJMenuBar(menu.getMenu());
		mc.validate();
		
		f.getFrame().pack();

	}
	
	
	
}
