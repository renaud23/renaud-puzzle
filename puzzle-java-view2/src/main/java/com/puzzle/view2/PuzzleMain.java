package com.puzzle.view2;

import java.awt.Image;
import java.io.File;
import com.puzzle.view2.controller.RootController;
import com.puzzle.view2.layer.BackgroundLayer;
import com.puzzle.view2.layer.HudLayer;
import com.puzzle.view2.layer.TapisLayer;
import com.puzzle.view2.tools.ImageLoadException;
import com.puzzle.view2.tools.SimpleImageLoader;
import com.puzzle.view2.widget.MiniMap;

public class PuzzleMain {

	public static void main(String[] args) throws ImageLoadException {
		String pathResources = "E:/git/renaud-puzzle/puzzle-java-view2/src/main/resources";
		
		
		int screenLargeur = 800;
		int screenHauteur = 600;
		
		double tapisLargeur = 36000.0;
		double tapisHauteur = 12000.0;
		
		Image backgroundImage = new SimpleImageLoader().getImage(pathResources+File.separator+"background"+File.separator+"default-background.jpg"); 
		
		
		
		
		
		
		
		
		
		
		Fenetre f = new Fenetre(screenLargeur, screenHauteur);
		BackgroundLayer gameSpace = new BackgroundLayer(backgroundImage, screenLargeur, screenHauteur, tapisLargeur, tapisHauteur, 0.1);
		gameSpace.setxVue(-tapisLargeur/2.0);
		gameSpace.setyVue(tapisHauteur/2.0);
		
		
		
		RootController controller = new RootController();
		TapisLayer tapisLayer = new TapisLayer(gameSpace, screenLargeur, screenHauteur);
		
		
		
		HudLayer hud = new HudLayer();
		MiniMap map = new MiniMap(gameSpace, backgroundImage, 10, 10, 0.006);
		
		// injection des controllers au root
		controller.addController(tapisLayer);
		controller.addController(map);
		
		// injection des drawables
		hud.addWidget(map);
		
		// ajout des layers à la fenetre
		f.addDrawable(gameSpace);
		f.addDrawable(hud);
		
		f.getOffscreen().addMouseListener(controller);
		f.getOffscreen().addMouseMotionListener(controller);
		f.getOffscreen().addMouseWheelListener(controller);
	}

}
