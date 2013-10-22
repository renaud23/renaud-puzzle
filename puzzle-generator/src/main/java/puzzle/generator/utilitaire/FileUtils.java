package puzzle.generator.utilitaire;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FileUtils {
	
//	public final static String imagesPath = "D:/projet_java/personnel/puzzle-ex/data/";
	public final static String buildPath = "build/";
	
	
	/**
	 * Détruit tout les png à l'adresse mentionnée.
	 * @param path
	 */
	public static void cleanDirectory(String path){
		File directory = new File(path);
		if(directory.isDirectory()){
			File[] files = directory.listFiles();
	
			for(File file : files){
	
				String name = file.getName();
				String ext = name.substring(name.length()-3,name.length());
				if(ext.equals("png") && file.isFile()){
					while(file.exists())file.delete();
				}
			}
		}
	}
	
	/**
	 * 
	 * @param img
	 * @param path
	 * @param name
	 */
	public static void savePng(BufferedImage img,String path,String name){
		String pathComplet = path + name + ".png";
		File pathFile = new File(path);
		if(!pathFile.isDirectory()) pathFile.mkdir();
			
		try {
			ImageIO.setUseCache(false);
			ImageIO.write(img, "png", new File(pathComplet));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Charge un template depuis son code.
	 * @param value
	 * @return
	 */
	public static BufferedImage getPieceImage(int value,String path){
		String pathComplet = path + String.valueOf(value) + ".png";
		File file = new File(pathComplet);
		if(file.exists()){
			BufferedImage image = null;
			try {
				image = ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return image;
		}else return null;
	}
	
	

}
