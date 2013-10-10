package com.puzzle.view.tool;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

import com.puzzle.model.Puzzle;




public class ImageMemoryManager extends Thread {
	
	
	private static ImageMemoryManager instance;
	

	
	
	private Map<Integer, ImageProvider> providers;

	
	public static ImageMemoryManager getInstance(){
		if(ImageMemoryManager.instance == null){
			ImageMemoryManager.instance = new ImageMemoryManager();
		}
		return ImageMemoryManager.instance;
	}

	private ImageMemoryManager(){
		this.providers = new HashMap<Integer, ImageProvider>();
	}


	

	
	
	public void put(int idPuzzle,ImageProvider provider){
		this.providers.put(idPuzzle, provider);
	}
	
	public ImageProvider get(int idPuzzle){
		return this.providers.get(idPuzzle);
	}
	
	
	
	


	
}
