package com.puzzle.view.tool;




import java.util.HashMap;
import java.util.Map;

import com.puzzle.model.Piece;





public class ImageMemoryManager {
	
	
	private static ImageMemoryManager instance;
	private Map<Integer, ImageProvider<Piece>> providers;

	
	public static ImageMemoryManager getInstance(){
		if(ImageMemoryManager.instance == null){
			ImageMemoryManager.instance = new ImageMemoryManager();
		}
		return ImageMemoryManager.instance;
	}

	private ImageMemoryManager(){
		this.providers = new HashMap<Integer, ImageProvider<Piece>>();
	}


	

	
	
	public void put(int idPuzzle,ImageProvider<Piece> provider){
		this.providers.put(idPuzzle, provider);
	}
	
	public ImageProvider<Piece> get(int idPuzzle){
		return this.providers.get(idPuzzle);
	}
	
	
	
	


	
}
