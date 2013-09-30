package puzzle.generator.memory;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

import puzzle.generator.utilitaire.FileUtils;


public class MemoryManager extends Thread implements ImageProvider{
	
	
	private static MemoryManager instance;
	
	private Map<Integer, WeakReference<BufferedImage>> images;


	
	public static MemoryManager getInstance(){
		if(MemoryManager.instance == null){
			MemoryManager.instance = new MemoryManager();
		}
		return MemoryManager.instance;
	}

	private MemoryManager(){
		this.images = new HashMap<Integer, WeakReference<BufferedImage>>();
	}


	public BufferedImage getImage(int code) {
		if(this.images.get(code)== null ||
			this.images.get(code).get() == null)
			this.reload(code);
		if(this.images.containsKey(code))
			return this.images.get(code).get();
		else return null;
	}


	public void reload(int code){
		File file = new File(FileUtils.buildPath+File.separator+String.valueOf(code)+".png");
		try {
			if(file.exists()){
				BufferedImage buff = ImageIO.read(file);
				this.images.put(code,new WeakReference<BufferedImage>(buff));
			}else{
				this.images.remove(code);
			}
		} catch (IOException e) {
			this.images.remove(code);
			e.printStackTrace();
		} 
	}

	public void clean() {
		this.images.clear();
	}
	
}
