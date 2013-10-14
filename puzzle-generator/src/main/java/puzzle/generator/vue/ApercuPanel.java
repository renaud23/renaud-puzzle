package puzzle.generator.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import puzzle.generator.memory.MemoryManager;
import puzzle.generator.modele.Puzzle;
import puzzle.generator.modele.frame.Cell;
import puzzle.generator.task.GenerateApercuTask;




public class ApercuPanel extends JPanel implements MouseListener,
MouseMotionListener,ActionListener,IRefreshable{
	
	private static ApercuPanel instance;
	
	private GenerateApercuTask generateTask;

	public final static double SCALE_APERCU = 0.5;

	public final int offscreenWidth = 600;
	public final int offscreenHeight = 450;
	private OffScreen offscreen;
	private boolean created;
	private double scaleApercu;
	private BufferedImage apercu;

	
	private Map<Integer,String> selected;
	
	
	
	private int courantId;
	private int xPiece;
	private int yPiece;

	private JButton validate;
	
	private boolean userChange;


	public static ApercuPanel getInstance(){
		if(ApercuPanel.instance == null)
			ApercuPanel.instance = new ApercuPanel();
		return ApercuPanel.instance;	
	}
	
	/**
	 * 
	 */
	private ApercuPanel(){
		super(new FlowLayout());
		this.offscreen =
			new OffScreen(this.offscreenWidth, this.offscreenHeight);
		this.offscreen.setBackgroundColor(Color.gray);
		this.offscreen.clean();
		this.add(this.offscreen);
		this.offscreen.addMouseListener(this);
		this.offscreen.addMouseMotionListener(this);
		this.courantId = -1;
		this.created = false;
		
		this.selected = new HashMap<Integer, String>();
		
		this.validate = new JButton("Valider!");
		this.validate.addActionListener(this);
		this.add(this.validate);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3776339999883497580L;
	
	
	/**
	 * nouvelle aper�u g�n�r�.
	 * @param apercu
	 */
	public void updateApercu(BufferedImage apercu){
		this.apercu = apercu;
		
		this.offscreen.setTaille(new Dimension(apercu.getWidth(),apercu.getHeight()));
		this.offscreen.clean();
		this.offscreen.drawImage(apercu,1.0f);
		this.created = true;
		this.userChange = false;
		
		this.refreshApercu();
		this.repaint();
	}

	
	/**
	 * 
	 * @param nbPieceLargeur
	 * @param scalePiece
	 */
	public void genereApercu(){
		LoggerArea.getInstance().info("D�but de cr�ation de l'aper�u.");
		
		Puzzle puzzle = MainScreen.getInstance().getPuzzle();
		if(MainScreen.getInstance().isLoadImage()){
			// calcul de la taille de l'aper�u.
			double scaleApercu = 1.0;
			
			puzzle.calculNbPieces();
			double ratio = (double)puzzle.getNbPiecesWidth() / (double)puzzle.getNbPiecesHeight();	
			if(ratio > (4.0/3.0) ){
				scaleApercu = (double) this.offscreenWidth;
				scaleApercu /= (double) puzzle.getDescriptor().getPieceCote();
				scaleApercu /= puzzle.getScalePiece();
				scaleApercu /= (double) puzzle.getNbPiecesWidth();
			}else{
				scaleApercu = (double) this.offscreenHeight;
				scaleApercu /= (double) puzzle.getDescriptor().getPieceCote();
				scaleApercu /= puzzle.getScalePiece();
				scaleApercu /= (double) puzzle.getNbPiecesHeight();
			}
			
			ApercuPanel.getInstance().setScaleApercu(scaleApercu);
			
			if (this.generateTask == null || 
					this.generateTask.getState() == Thread.State.TERMINATED){
				this.generateTask = new GenerateApercuTask();
				this.generateTask.setPuzzle(puzzle);
				this.generateTask.setScaleApercu(scaleApercu);
			}
			
			this.generateTask.start();
		}
	}
	
	
		
	/**
	 * 
	 */
	public void refreshApercu(){
		MemoryManager.getInstance().clean();
		this.userChange = true;
		this.selected.clear();
		LoggerArea.getInstance().info("Fin de revalidation de l'aper�u.");
	}

	
	/**
	 * recalcul les coordonn�e et le nom de la pi�ce point� par
	 * le curseur.
	 * @param point
	 */
	private void changePiece(Point point){
		Puzzle puzz =  MainScreen.getInstance().getPuzzle();
		double cote = this.apercu.getWidth();
		cote /= puzz.getNbPiecesWidth();

		int xi = (int)(point.x/cote);
		int yi = (int)(point.y/cote);
		
		int id = 1+xi+yi * puzz.getNbPiecesWidth();
			
		this.courantId = id;
		
		Cell type = puzz.getFrame().getCell(xi, yi);
		this.xPiece = xi;
		this.xPiece *=  puzz.getDescriptor().getPieceCote();
		this.yPiece = yi;
		this.yPiece *=  puzz.getDescriptor().getPieceCote();
		
		int varX = puzz.getDescriptor().getPieceVarMin();
		int varY = puzz.getDescriptor().getPieceVarMin();
		if(type.isNord()){
			varY =puzz.getDescriptor().getPieceVarMax();
		}else if(type.isCoteNord()){
			varY = 0;
		}
		if(type.isOuest()){
			varX =puzz.getDescriptor().getPieceVarMax();
		}else if(type.isCoteOuest()){
			varX = 0;
		}
		
		double r = cote / puzz.getDescriptor().getPieceCote();

		
		this.xPiece -= varX;
		this.yPiece -= varY;
		
		this.xPiece *= r;
		this.yPiece *= r;
	}
	
	/**
	 * 
	 * @param point
	 * @return
	 */
	private boolean isChangingPiece(Point point){
		Puzzle puzz =  MainScreen.getInstance().getPuzzle();
		double cote = this.apercu.getWidth() / puzz.getNbPiecesWidth();

		int xi = (int)(point.x/cote);
		int yi = (int)(point.y/cote);
		
		int id = 1+xi+yi * puzz.getNbPiecesWidth();
//		System.out.println(this.courantId +" "+ id+" "+(this.courantId != id));
		return this.courantId != id;//&& xi<puzz.getNbPiecesWidth() && yi<puzz.getNbPiecesHeight();
	}
	
	/**
	 * 
	 * @param color
	 */
	private void drawPieceCourante(Color color){
		if(this.courantId != -1 ){
			BufferedImage img = MemoryManager.getInstance().getImage(this.courantId);
			this.offscreen.drawImageMask(img, this.xPiece,this.yPiece,0.0,0.0,0.0,scaleApercu/SCALE_APERCU,color);
		}
	}
	
	
	/*
	 * Event.
	 *
	 */
	
	public void mouseDragged(MouseEvent e) {
		
		if(this.isChangingPiece(e.getPoint())){
			if(this.selected.containsKey(this.courantId)){
				this.drawPieceCourante(Color.yellow);
			}else{
				this.drawPieceCourante(Color.white);
			}
			
			this.changePiece(e.getPoint());
			
			if(e.isShiftDown()){
				this.selected.remove(this.courantId);
				this.drawPieceCourante(Color.red);
			}else{
				this.selected.put(this.courantId,String.valueOf(this.courantId));
				this.drawPieceCourante(Color.green);
			}
			
			this.repaint();
		}	
	}


	public void mouseMoved(MouseEvent e) {
		if(this.created){
			if(this.isChangingPiece(e.getPoint())){
				if(this.selected.containsKey(this.courantId))
					this.drawPieceCourante(Color.yellow);
				else
					this.drawPieceCourante(Color.white);
				
				this.changePiece(e.getPoint());
				
				if(this.selected.containsKey(this.courantId))
					this.drawPieceCourante(Color.green);
				else
					this.drawPieceCourante(Color.red);
			}
			
			
			this.repaint();
		}
	}


	public void mouseClicked(MouseEvent e) {
		if(this.courantId != -1){
			String id = this.selected.get(this.courantId);
			if(id != null){
				this.selected.remove(this.courantId);
				this.drawPieceCourante(Color.red);
			}else{
				this.selected.put(this.courantId, String.valueOf(this.courantId));
				this.drawPieceCourante(Color.green);
			}
			
			this.repaint();
		}
		
	}


	public void mouseEntered(MouseEvent e) {
		
	}


	public void mouseExited(MouseEvent e) {
		
	}


	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void setScaleApercu(double scaleApercu) {
		this.scaleApercu = scaleApercu;
	}


	/**
	 * Pour �couter les bouton
	 */
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		// Prise en compte de la selection utilisateur
		if(o.equals(this.validate)){
			if(this.selected.size()>0){
				LoggerArea.getInstance().info("Prise en compte de la s�lection");
				MainScreen.getInstance().getPuzzle().validateSelection(this.selected.keySet());
				
				this.genereApercu();

			}
		}
		
	}
	

	
	
	public boolean isUserChange() {
		return userChange;
	}

	public void refresh() {
		this.refreshApercu();
	}

	

	/*
	 * 
	 * Observateur
	 * 
	 */
	

}
