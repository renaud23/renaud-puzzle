package puzzle.generator.task;

import java.awt.image.BufferedImage;

import puzzle.generator.modele.Puzzle;
import puzzle.generator.utilitaire.FileUtils;
import puzzle.generator.vue.ApercuPanel;
import puzzle.generator.vue.MainScreen;




/**
 * tache de g�n�ration du puzzle.
 * thread� pour pas bloquer l'appli interactive.
 * @author kqhlz2
 *
 */
public class GenerateApercuTask extends Thread {
	
	private Puzzle puzzle;
	private double scaleApercu;
	private double initialScalePiece;



	
	public GenerateApercuTask(){
		
	}
	
	public void run(){
		if(!this.puzzle.isFrame()) this.puzzle.genereFrame();
		this.initialScalePiece = puzzle.getScalePiece();
		puzzle.setScalePiece(this.initialScalePiece*ApercuPanel.SCALE_APERCU);
		
		this.puzzle.genereAndSave(FileUtils.buildPath);
		
		
		
		BufferedImage vue = this.puzzle.genereVue(FileUtils.buildPath, this.scaleApercu/ApercuPanel.SCALE_APERCU);	
		ApercuPanel.getInstance().updateApercu(vue);
		MainScreen.getInstance().setAsActiveTab(ApercuPanel.getInstance());
		
		
		this.puzzle.setScalePiece(this.initialScalePiece);
	}
	
	
	

	/*
	 * 
	 */
	public void setScaleApercu(double scaleApercu) {
		this.scaleApercu = scaleApercu;
	}



	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
	}
}
