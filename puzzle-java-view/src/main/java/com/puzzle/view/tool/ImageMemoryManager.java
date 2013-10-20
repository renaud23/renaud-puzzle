package com.puzzle.view.tool;




import java.util.HashMap;
import java.util.Map;





public class ImageMemoryManager {
	
	
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
