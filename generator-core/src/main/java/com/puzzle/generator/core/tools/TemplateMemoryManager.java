package com.puzzle.generator.core.tools;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;



public class TemplateMemoryManager implements ImageProvider{
	
	private static TemplateMemoryManager instance;
	
	private Map<Integer, WeakReference<BufferedImage>> images;

	private String directory = "/";
	
	public static TemplateMemoryManager getInstance(){
		if(TemplateMemoryManager.instance == null){
			TemplateMemoryManager.instance = new TemplateMemoryManager();
		}
		return TemplateMemoryManager.instance;
	}
	
	private TemplateMemoryManager(){
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
		File file = new File(this.directory+File.separator+String.valueOf(code)+".png");
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
	
	/**
	 * 
	 * @param directory
	 */
	public void setDirectory(String directory){
		this.directory = directory;
		this.clean();
	}

}
