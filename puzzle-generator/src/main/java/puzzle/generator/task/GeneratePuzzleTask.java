package puzzle.generator.task;



import java.awt.image.BufferedImage;
import java.io.File;
import puzzle.generator.modele.Puzzle;
import puzzle.generator.modele.Tapis;
import puzzle.generator.utilitaire.FileUtils;
import puzzle.generator.utilitaire.XMLGenerator;
import puzzle.generator.vue.LoggerArea;
import puzzle.generator.vue.MainScreen;




public class GeneratePuzzleTask extends Thread {
	private Puzzle puzzle;
	private int nbPieceLargeur;
	private double scaleApercu = 0.5;
	private String path;

	private Tapis tapis;
	
	

	public GeneratePuzzleTask(){
		// tapis par défaut.
		this.tapis = Tapis.DEFAULT_TAPIS;
	}
	
	public void run(){
		this.checkDirectory(this.path);
		this.puzzle.calculNbPieces(this.nbPieceLargeur);
		if(!this.puzzle.isFrame()) this.puzzle.genereFrame();
		this.puzzle.genereAndSave(this.path+"images/");
		
		BufferedImage vue = this.puzzle.genereVue(this.path+"images/", this.scaleApercu);
	
		FileUtils.savePng(vue, this.path, "final");
	
		XMLGenerator xml = new XMLGenerator(this.path,this.puzzle, this.scaleApercu);
		xml.setTapis(this.tapis);
		xml.genereFile();
		
		
		MainScreen.getInstance().generationTermine();
	}
	

	private void checkDirectory(String path){
		// création
		File dirRoot = new File(path);
		if(!dirRoot.isDirectory()){
			dirRoot.mkdir();
		}
		File imgRoot = new File(path+File.separator+"images");
		if(!imgRoot.isDirectory()){
			imgRoot.mkdir();
		}
		
//		this.clean(dirRoot);
	}
	
	
	private void clean(File file){
		if(file.isFile()) file.delete();
		else if(file.isDirectory()){
			for(File f : file.listFiles()){
				this.clean(f);
			}
		}
	}
	
	
	/*
	 * 
	 */
	public void setScaleApercu(double scaleApercu) {
		this.scaleApercu = scaleApercu;
	}
	
	public int getNbPieceLargeur() {
		return nbPieceLargeur;
	}

	public void setNbPieceLargeur(int nbPieceLargeur) {
		this.nbPieceLargeur = nbPieceLargeur;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
	}
	
	public Puzzle getPuzzle() {
		return puzzle;
	}

	public void setTapis(Tapis tapis) {
		this.tapis = tapis;
	}
}
