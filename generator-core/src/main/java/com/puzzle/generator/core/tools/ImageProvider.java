package com.puzzle.generator.core.tools;

import java.awt.image.BufferedImage;

public interface ImageProvider {

	public BufferedImage getImage(int code);
	
	public void clean();
	
}
