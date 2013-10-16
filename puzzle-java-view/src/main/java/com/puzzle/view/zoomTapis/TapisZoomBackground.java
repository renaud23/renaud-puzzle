
package com.puzzle.view.zoomTapis;

import java.awt.Image;

import com.puzzle.model.Tapis;
import com.puzzle.view.drawer.IBackgroundDrawer;
import com.puzzle.view.tool.ImageBuffer;
import com.puzzle.view.tool.SimpleImageLoader;

public class TapisZoomBackground implements IBackgroundDrawer {
	
	private Image background; 
	
	private TapisZoomConverteur converter;
	private Tapis tapis;
	private ImageBuffer buffer;
	

	

	public TapisZoomBackground(TapisZoomConverteur converter, Tapis tapis,
			ImageBuffer buffer) {
		this.converter = converter;
		this.tapis = tapis;
		this.buffer = buffer;
		
		this.background = new SimpleImageLoader().getImage("/home/renaud/git/renaud-puzzle/puzzle-java-view/src/main/resources/background/tapis.png");
	}

	@Override
	public void draw() {
		buffer.drawImage(this.background, 0, 0, 0, 0, 0, 1.0, 1.0f);

	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub

	}

}
