package com.jPuzzle.view.drawer;

import com.jPuzzle.view.image.ImageBuffer;
import com.jPuzzle.view.image.ImageMemoryManager;
import com.puzzle.model.ComponentPiece;

public class HeadUpDisplayDrawer implements IDrawer{
	
	private ImageBuffer offscreen;
	private ComponentPiece mainDroite;

	public HeadUpDisplayDrawer(ImageBuffer offscreen) {
		super();
		this.offscreen = offscreen;
	}

	@Override
	public void draw() {
		this.offscreen.transparentClean();
		this.offscreen.drawImage(ImageMemoryManager.getInstance().getImage(1), 0, 0, 0, 0, 0, 1.0, 1.0f);
	}

	@Override
	public void setTransformation(Transformation transformation) {
		// TODO Auto-generated method stub
		
	}

	public ComponentPiece getMainDroite() {
		return mainDroite;
	}

	public void setMainDroite(ComponentPiece mainDroite) {
		this.mainDroite = mainDroite;
	}

	
	
	
}
