package puzzle.generator.task;


import java.io.File;
import java.io.FileFilter;

import puzzle.generator.modele.frame.FrameEx;
import puzzle.generator.vue.IRefreshable;





/**
 * Remet en place les éléments du puzzle, pour le réaligner vers le
 * bord suite à des modification utilisateur.
 * @author kqhlz2
 *
 */
public class RefactorTask extends Thread implements FileFilter{

	private FrameEx frame;
	private String imagesPath;
	private IRefreshable cible;
	
	private int nbFL;
	private int nbFC;
	private Thread creator;// thread qui créee les pieces : doit finir avant.
	
	public RefactorTask(IRefreshable cible){
		this.cible = cible;
	}
	
	
	
	public void run(){
		
		if(this.creator != null){
			while( !this.creator.getState().equals(Thread.State.TERMINATED)){
				// on attend que les piéces soient créees.
			}
		}
		
		this.nbFL = this.frame.compteFirstEmptyLigne();
		this.nbFC = this.frame.compteFirstEmptyColonne();
	
		this.renameFile();
		this.frame.refacte();
		

		
		this.cible.refresh();
	}
	
	



	/**
	 * 
	 */
	private void renameFile(){
		File path = new File(this.imagesPath);
		
		if(path.isDirectory()){
			for(File file : path.listFiles(this)){
				
				String[] tmp = file.getName().split("[.]");
				String name = tmp[0];
				
				try{
					int value = Integer.parseInt(name);
					value -= this.nbFC;
					value -= this.nbFL * this.frame.getLargeur();
					
					File nouveau = 
						new File(file.getPath().replace(file.getName(), "")+String.valueOf(value)+".png");
					
					System.out.println(file.getName()+" "+nouveau.getPath());
					file.renameTo(nouveau);
				}catch(NumberFormatException e){
					
				}
			}
		}
	}
	
	
	public void setFrame(FrameEx frame) {
		this.frame = frame;
	}


	public void setImagesPath(String imagesPath) {
		this.imagesPath = imagesPath;
	}

	public void setCreator(Thread creator) {
		this.creator = creator;
	}

	public boolean accept(File pathname) {
		if(pathname.getName().toLowerCase().endsWith("png"))
			return true;
		else return false;
	}

	
	
}
