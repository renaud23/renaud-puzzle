package com.puzzle.view;

import java.awt.Image;
import java.awt.Point;
import java.io.File;

import com.puzzle.model.Tapis;
import com.puzzle.view.PuzzleCursor.CursorType;
import com.puzzle.view.controller.IController;
import com.puzzle.view.controller.MyKeyListener;
import com.puzzle.view.controller.MyMouseListener;
import com.puzzle.view.controller.MyMouseMotionListener;
import com.puzzle.view.controller.MyMouseWheelListener;
import com.puzzle.view.drawer.DrawSelection;
import com.puzzle.view.drawer.IDrawerSelection;
import com.puzzle.view.hud.HudControler;
import com.puzzle.view.hud.HudDrawer;
import com.puzzle.view.menu.MenuController;
import com.puzzle.view.menu.MenuView;
import com.puzzle.view.tool.ImageLoadException;
import com.puzzle.view.tool.SimpleImageLoader;
import com.puzzle.view.zoomTapis.TapisZoomController;
import com.puzzle.view.zoomTapis.TapisZoomConverteur;


public class MainLauncher {

	public static void main(String[] args) throws ImageLoadException {
		int largeurTapis = (int)(36000.0*0.5);
		int hauteurTapis = (int)(12000.0*0.5);
		int largeurScreen = 800;
		int hauteurScreen = 600;

//		String rootPath = System.getProperty("user.dir");
		String cursorPath = System.getProperty("user.dir")+"/src/main/resources/cursor/";
		String rootPath = "E:/workspaceEclipse/puzzle-pieces";

		Tapis tapis = new Tapis(largeurTapis,hauteurTapis);
	
		PuzzleCursor.getInstance().loadCursor(cursorPath+"mainPleine.png", new Point(13,1), CursorType.mainPleine);
		PuzzleCursor.getInstance().loadCursor(cursorPath+"mainVide.png", new Point(13,1), CursorType.mainVide);
		Fenetre f = new Fenetre(largeurScreen,hauteurScreen);
		
		Image background = new SimpleImageLoader().getImage(rootPath+File.separator+"background"+File.separator+"wood_tapis3.jpg");
		
		// controller du tapis
		TapisZoomConverteur cv = new TapisZoomConverteur(largeurTapis,hauteurTapis,largeurScreen,hauteurScreen);
		IController c = new TapisZoomController(tapis, cv,background, f.getBuffer(0),f.getBuffer(1),f.getOffscreen());
		
		// controller du hud
		IDrawerSelection drw = new HudDrawer(
				(DrawSelection) ((TapisZoomController)c).getDrawerSelection(),
				f.getBuffer(1),
				cv);// decore le drawer de TapisZoomControllerEx
		HudControler hc = new HudControler(c , drw,tapis, f.getBuffer(1));
		((TapisZoomController)c).setDrawerSelection(drw);
		
		f.getOffscreen().addMouseListener(new MyMouseListener(hc));
		f.getOffscreen().addMouseMotionListener(new MyMouseMotionListener(hc));
		f.getOffscreen().addMouseWheelListener(new MyMouseWheelListener(hc));
		f.getFrame().addKeyListener(new MyKeyListener(hc));

		// création du menu.
		MenuController mc = new MenuController(tapis,rootPath+File.separator+"puzzle");
		MenuView menu = new MenuView(mc,f.getFrame());
		f.getFrame().setJMenuBar(menu.getMenu());
		mc.validate();
		
		f.getFrame().pack();
	}
	
	
	
}
