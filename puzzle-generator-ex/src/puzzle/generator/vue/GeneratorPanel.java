package puzzle.generator.vue;

import java.awt.Choice;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Method;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalBorders;
import puzzle.generator.descriptor.DescriptotFactory;
import puzzle.generator.modele.Puzzle;
import puzzle.generator.observateur.IObservateur;
import puzzle.generator.task.GenerateApercuTask;


/**
 * 
 * @author kqhlz2
 *
 */
public class GeneratorPanel extends JPanel 
	implements ActionListener, ChangeListener,ItemListener,IObservateur<Puzzle>{
	
	private static GeneratorPanel instance = null;
	
	private Choice choixScale;
	private Choice choixSetPieces;
	private JSpinner widthSpinner;
	private TextField heightLabel;
	private TextField nbPiecesLabel;
	private TextField puzzleName;
	private JButton previewButton;
	private JButton genereButton;
	
	
	
	

	
	

	public static final GeneratorPanel getInstance(){
		if(GeneratorPanel.instance == null)
			GeneratorPanel.instance = new GeneratorPanel();
		return GeneratorPanel.instance;
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -122336764337791831L;
	
	
	

	private GeneratorPanel(){
		
		this.setLayout(new FlowLayout());
		this.setBorder(MetalBorders.getTextFieldBorder());

		SpinnerModel modeltau = new SpinnerNumberModel(10, //initial value
	            2, //min
	            50, //max
	            1);
		
		
		this.widthSpinner = new JSpinner(modeltau);
		this.widthSpinner.addChangeListener(this);
		this.heightLabel = new TextField("000");
		this.heightLabel.setColumns(3);
		this.heightLabel.setEnabled(false);
		this.nbPiecesLabel = new TextField("000");
		this.nbPiecesLabel.setColumns(3);
		this.nbPiecesLabel.setEnabled(false);
		this.previewButton = new JButton("Preview!");
		this.previewButton.addActionListener(this);
		this.genereButton = new JButton("Génerer!");
		this.genereButton.addActionListener(this);
		this.choixScale = new Choice();
		this.choixScale.addItemListener(this);
		this.choixSetPieces = new Choice();
		this.choixSetPieces.addItemListener(this);
		this.puzzleName = new TextField("sans nom");

		choixScale.add("0.5");
		choixScale.add("1.0");
		choixScale.add("1.5");
		choixScale.add("2.0");
		
		for(String idSet : DescriptotFactory.getPieceSetName()){
			this.choixSetPieces.add(idSet);
		}
	
		
		this.createZone("Nombre de piéces en largeur :", this.widthSpinner);
		this.createZone("Nombre de piéces en hauteur :",this.heightLabel);
		this.createZone("Nombre total de piéces :",this.nbPiecesLabel);
		this.createZone("Scale des piéces :",this.choixScale);
		this.createZone("Jeu de piéces utilisées :",this.choixSetPieces);
		this.createZone("Nom du puzzle :",this.puzzleName);
		
		this.add(this.previewButton);
		this.add(this.genereButton);
		

		
	}

	private void createZone(String name,Component composant){
		this.add(new JLabel(name));
		this.add(composant);
	}


	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source.equals(this.previewButton)){
			
		
			MainScreen.getInstance().getPuzzle().cleanFrame();
			ApercuPanel.getInstance().genereApercu();
			
		}else if(source.equals(this.genereButton)){
			int nbPieceLargeur = (Integer) this.widthSpinner.getValue();
			double scalePiece = Double.valueOf(this.choixScale.getSelectedItem());
			
			String jeu_piece = this.choixSetPieces.getSelectedItem();
					
			
			MainScreen.getInstance().genererPuzzle(nbPieceLargeur, scalePiece,jeu_piece);
			
		}
	}
	
	/*
	 * 
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		Object source = e.getSource();
		
		if(source.equals(this.widthSpinner)){
			MainScreen.getInstance().updateNbPieceWidth((Integer) this.widthSpinner.getValue());
		}
	}

	/**
	 * retourne le choix de largeur de l'utilisateur.
	 * @return
	 */
	public int getSelectedWidth(){
		Integer value = (Integer) this.widthSpinner.getValue();
		return value;
	}
	
	/*
	 * ItemListener
	 * (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == this.choixSetPieces){
			MainScreen.getInstance().setPuzzleDescriptor(this.choixSetPieces.getSelectedItem());
		}else if(e.getSource() == this.choixScale){
			MainScreen.getInstance().getPuzzle().setScalePiece(Double.valueOf(this.choixScale.getSelectedItem()));
		}
	}
	
	
	/*
	 * 
	 * 
	 * (non-Javadoc)
	 * @see puzzle.generator.observateur.IObservateur#notify(java.lang.reflect.Method, java.lang.Object)
	 */
	public void notify(Method method, Puzzle puzz) {
		String mtdName = method.getName();
		
		if("calculNbPieces".equals(mtdName)){
			this.heightLabel.setText(String.valueOf(puzz.getNbPiecesHeight()));
			this.nbPiecesLabel.setText(String.valueOf(puzz.getNbPieces()));
		}else if("setDescriptor".equals(mtdName)){
			this.logSize();
		}else if("setScalePiece".equals(mtdName)){
			this.logSize();
		}
	}

	private void logSize(){
		
	}
	
	public String getPuzzleName(){
		return this.puzzleName.getText();
	}
	
	public void initialize(){
		MainScreen.getInstance().setPuzzleDescriptor(this.choixSetPieces.getSelectedItem());
		MainScreen.getInstance().getPuzzle().setScalePiece(Double.valueOf(this.choixScale.getSelectedItem()));
		MainScreen.getInstance().setPuzzleDescriptor(this.choixSetPieces.getSelectedItem());
	}

	
}
