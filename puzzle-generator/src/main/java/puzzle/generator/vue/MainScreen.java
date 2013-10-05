package puzzle.generator.vue;



import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import puzzle.generator.descriptor.DescriptotFactory;
import puzzle.generator.modele.Puzzle;
import puzzle.generator.modele.Tapis;
import puzzle.generator.observateur.ObservableFabrique;
import puzzle.generator.observateur.Observer;
import puzzle.generator.task.GeneratePuzzleTask;

import puzzle.generator.vue.menu.GeneratorMenu;





/**
 * 
 * @author kqhlz2
 *
 */
public class MainScreen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2601791656287178100L;
	
	private static MainScreen instance;
	
	private Puzzle puzzle;
	
	private GeneratePuzzleTask generatePuzzleTask;
	
	/*
	 * 
	 */
	private JPanel viewImagePanel;
	
	public static final int viewImageWitdth = 400;
	
	public static final int viewImageHeight = 300;
	
	private OffScreen ofsViewImage;
	
	private boolean loadImage = false;
	
	private JTabbedPane tabPane;
	
	
	
	public static MainScreen getInstance(){
		if(MainScreen.instance == null)
			MainScreen.instance = new MainScreen();
		return MainScreen.instance;
	}
	
	
	
		
	
	
	
	

	

	
	
	/**
	 * 
	 */
	public MainScreen(){
		this.setTitle("Générator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.puzzle = new ObservableFabrique<Puzzle>(Puzzle.class).create();
        this.setJMenuBar(new GeneratorMenu(this));
        
        //
        this.tabPane = new JTabbedPane();
        this.viewImagePanel = new JPanel();
        this.viewImagePanel.setLayout(new FlowLayout());
       
        this.tabPane.addTab("Image", this.viewImagePanel);
        this.tabPane.addTab("Aperçu du puzzle", ApercuPanel.getInstance());
        this.tabPane.addTab("Propriétés du Puzzle", GeneratorPanel.getInstance());
        this.tabPane.addTab("Propriétés du Tapis de jeu",TapisPanel.getInstance());
        this.tabPane.addTab("Console", LoggerArea.getInstance());
        this.add(this.tabPane);
        
        //
        this.ofsViewImage = new OffScreen(viewImageWitdth, viewImageHeight);
        this.ofsViewImage.setBackgroundColor(Color.gray);
        this.ofsViewImage.clean();
        this.viewImagePanel.add(this.ofsViewImage);
        
        this.setPreferredSize(new Dimension(800,600));
        
       
        
        this.pack();
        this.setVisible(true);
        
        
        this.setAsActiveTab(LoggerArea.getInstance());
        LoggerArea.getInstance().info("Bonjour !!!");
        LoggerArea.getInstance().info("Commencer par charger une image.");
        
        
        //  
        Observer.getInstance().observe(GeneratorPanel.getInstance(), this.puzzle);

	}
	
	
	
	


	






	public static void main(String[] args){
		MainScreen.getInstance().setVisible(true);
		GeneratorPanel.getInstance().initialize();
	}
	
	
	
	/**
	 * Charge une image et met à jour le viewer.
	 * @param path
	 */
	public void loadImage(String path){
		File file = new File(path);
		if(file != null && file.isFile()){
			try{
				BufferedImage img = ImageIO.read(file);
				
				double l = img.getWidth(null);
				double h = img.getHeight(null);
				double r = l/h;
				
				double scale;
				if(r > (4.0/3.0)){
					scale = viewImageWitdth / l;
				}else{
					scale = viewImageHeight / h;
				}
			
				this.ofsViewImage.clean();
				this.ofsViewImage.drawImageScale(img, 0, 0, scale, 1.0f);
				this.puzzle.setImage(img);
				this.loadImage = true;
				
				this.puzzle.calculNbPieces(GeneratorPanel.getInstance().getSelectedWidth());
				
				this.setAsActiveTab(this.viewImagePanel);
				
				this.repaint();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Donne le focus à ce composant.
	 * @param c
	 */
	public void setAsActiveTab(Component c){
		this.tabPane.setSelectedIndex(
				this.tabPane.indexOfComponent(c));
	}
	
	
	/**
	 * Lancement de la génération.
	 * @param nbPieceLargeur
	 * @param scalePiece
	 */
	public void genererPuzzle(int nbPieceLargeur, double scalePiece, String descripteur){

		String name = GeneratorPanel.getInstance().getPuzzleName().trim();
		if(name.length() == 0) name = "default";
		else{
			name = name.replaceAll("[ ]", "_");
		}
		String outputPath = null;//FileUtils.imagesPath+File.separator+name.replaceAll("[ ]", "_")+File.separator; 
		JFileChooser chooser = new JFileChooser(); 
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("Emplacement de création du puzzle.");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);

	    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	    	outputPath = chooser.getSelectedFile().getPath()+File.separator+name+File.separator;
	
			this.setPuzzleDescriptor(descripteur);
			if (this.generatePuzzleTask == null || 
				this.generatePuzzleTask.getState() == Thread.State.TERMINATED){
				this.generatePuzzleTask = new GeneratePuzzleTask();
				this.generatePuzzleTask.setNbPieceLargeur(nbPieceLargeur);
				this.generatePuzzleTask.setPath(outputPath+File.separator);
				this.generatePuzzleTask.setPuzzle(this.puzzle);
				
				Tapis tapis = new Tapis();
				tapis.setLargeur(TapisPanel.getInstance().getLargeurTapis());
				tapis.setHauteur(TapisPanel.getInstance().getHauteurTapis());
				
				tapis.setR(TapisPanel.getInstance().getColorTapis().getRed());
				tapis.setG(TapisPanel.getInstance().getColorTapis().getGreen());
				tapis.setB(TapisPanel.getInstance().getColorTapis().getBlue());
				
				this.generatePuzzleTask.setTapis(tapis);
			
				
			}
			LoggerArea.getInstance().info("Début de la génération du puzzle");
			LoggerArea.getInstance().info(outputPath);
			this.generatePuzzleTask.start();

	    }else{
        	
        }
	}
		
	
	/**
	 * 
	 */
	public void generationTermine(){
		LoggerArea.getInstance().info("Génération terminée. Bon jeu.");
	}
	
	
	/**
	 * Met à jour le descripteur de piece du puzzle.
	 * @param witch
	 */
	public void setPuzzleDescriptor(String witch){
		this.puzzle.setDescriptor(DescriptotFactory.getDescripteur(witch));
	}
	
	/**
	 * Met à jour le nombre de piéce en largeur.
	 * @param nbPieceWidth
	 */
	public void updateNbPieceWidth(int nbPieceWidth){
		if(this.loadImage)
			this.puzzle.calculNbPieces(nbPieceWidth);
	}
	
	
	/*
	 * 
	 */

	public boolean isLoadImage() {
		return loadImage;
	}
	
	
	public Puzzle getPuzzle(){
		return this.puzzle;
	}


	
}
