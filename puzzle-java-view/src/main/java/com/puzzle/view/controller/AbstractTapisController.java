package com.puzzle.view.controller;


import java.util.Observable;
import java.util.Observer;

import com.puzzle.command.AttrapperMainDroite;
import com.puzzle.command.ClipserMainDroite;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.IsClipsable;
import com.puzzle.command.PasserDansMainGauche;
import com.puzzle.command.PoserMainDroite;
import com.puzzle.command.tournerMainDroite;
import com.puzzle.command.param.AttrapperMainDroiteParam;
import com.puzzle.command.param.ChangerDeMainParam;
import com.puzzle.command.param.ClipserParam;
import com.puzzle.command.param.IsClipsParam;
import com.puzzle.model.MainDroite;
import com.puzzle.model.MainGauche;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.LoadView;
import com.puzzle.view.SaveView;
import com.puzzle.view.drawer.DrawSelection;
import com.puzzle.view.drawer.DrawSelectionParam;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.drawer.IDrawerParametrable;
import com.puzzle.view.zoomTapis.TapisZoomConverteur;
import com.puzzle.view.zoomTapis.TapisZoomDrawer;

public abstract class AbstractTapisController implements IController, Observer{
	protected Tapis tapis;
	protected Fenetre fenetre;
	protected IDrawer tapisDrawer;
	protected TapisConverter converter;
	protected Point mousePosition;
	protected IDrawerParametrable<DrawSelectionParam> selectionDrawer;
	
	protected DrawSelectionParam selectionParam;
	protected IsClipsParam isClipsParam;
	protected AttrapperMainDroiteParam attraperParam;
	
	protected boolean mainDroiteVide;
	protected boolean mainGaucheVide;
	protected boolean rightClick;
	protected boolean shiftPressed;
	protected boolean tryClips;
	protected boolean clips;
	

	public AbstractTapisController(Fenetre fenetre, Tapis tapis) {
		this.tapis = tapis;
		this.fenetre = fenetre;
		this.converter = new TapisZoomConverteur(fenetre.getOffscreen(),this.tapis);
		this.tapisDrawer = new TapisZoomDrawer(this.tapis,this.fenetre.getBuffer(0),this.converter);
		this.selectionDrawer = new DrawSelection(this.fenetre.getBuffer(1), this.converter);
		
		this.selectionParam = new DrawSelectionParam();
		this.isClipsParam = new IsClipsParam();
		this.attraperParam = new AttrapperMainDroiteParam();
		
		this.selectionDrawer.setParam(this.selectionParam);
		
		this.mainDroiteVide = true;
		this.mainGaucheVide = true;
		this.rightClick = false;
		this.shiftPressed = false;
		this.tryClips = false;
		this.clips = false;
		
		
		this.tapis.addObserver(this);
		
		this.mousePosition = new Point();
		
	}
	
	private void resetSelection(){
		this.selectionParam.setAncre(MainDroite.getInstance().getAncre());
		this.selectionParam.setPosition(new Point(Double.MAX_VALUE,Double.MAX_VALUE));
		this.selectionParam.setComponent(MainDroite.getInstance().getPiece());
		this.mainGaucheVide = MainGauche.getInstance().isEmpty();
		
		((DrawSelection)this.selectionDrawer).setSelection(true);
		((DrawSelection)this.selectionDrawer).createbuffer();
		this.selectionDrawer.clean();
		this.selectionDrawer.draw();
		this.fenetre.repaint();
	}
	
	
	
	public void passerMainGauche(){
		ChangerDeMainParam param = new ChangerDeMainParam();
		CommandeArgument<ChangerDeMainParam> cmd = new PasserDansMainGauche(this.tapis);
		cmd.setArgument(param);
		cmd.execute();
		
		if(param.isReussi()){
			((DrawSelection)this.selectionDrawer).setSelection(false);
		}
		
		this.selectionDrawer.clean();
		this.selectionDrawer.draw();
		this.fenetre.repaint();
	}
	
	protected void tourner(boolean up){
		CommandeArgument<Boolean> cmd = new tournerMainDroite();
		cmd.setArgument(!up);
		cmd.execute();
		
		this.selectionDrawer.clean();
		this.selectionDrawer.draw();
		this.fenetre.repaint();
	}
	

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof State){
			State st = (State) arg;
			if(st == State.MainDroitePleine)this.mainDroiteVide = false;
			else if(st == State.MainDroiteVide)this.mainDroiteVide = true;
			else if(st == State.droiteToGauche){
				this.mainDroiteVide = true;
				this.mainGaucheVide = false;
			}
			else if(st == State.gaucheToDroite){
				this.mainDroiteVide = false;
				this.resetSelection();
			}else if(st == State.PuzzleFini){
				this.mainDroiteVide = true;
				System.out.println("Fini!!!");
			}
		}
	}

	
	protected void isClipsable(double x,double y){
		this.selectionParam.clearCandidats();
		Point p = new Point(x,y);
		this.converter.convertScreenToModel(p);
		
		this.isClipsParam.setCentre(p);
		CommandeArgument<IsClipsParam> cmd = new IsClipsable(this.tapis);
		cmd.setArgument(this.isClipsParam);
		cmd.execute();
		
		if(!this.isClipsParam.getCandidats().isEmpty()){
			this.selectionParam.addCandidats(this.isClipsParam.getCandidats());
			this.clips = true;
			
			this.selectionDrawer.clean();
			this.selectionParam.setPosition(new Point(x,y));
			this.selectionDrawer.draw();
			this.fenetre.repaint();
		} else{
			this.clips = false;
		}
	}
	
	protected void attraper(int x,int y){
		Point p = new Point(x, y);
		this.converter.convertScreenToModel(p);
		
		CommandeArgument<AttrapperMainDroiteParam> cmd = new AttrapperMainDroite(this.tapis);
		this.attraperParam.setPosition(p);
		cmd.setArgument(this.attraperParam);
		
		cmd.execute();
		
		if(!this.mainDroiteVide){
			this.selectionParam.setAncre(this.attraperParam.getAncre());
			this.selectionParam.setPosition(new Point(x,y));
			this.selectionParam.setComponent(MainDroite.getInstance().getPiece());
			
			((DrawSelection)this.selectionDrawer).setSelection(true);
			((DrawSelection)this.selectionDrawer).createbuffer();
			
			this.tapisDrawer.draw();
			this.selectionDrawer.draw();
			this.fenetre.repaint();
		}
	}
	
	
	protected void poser(int x,int y){
		Point p = new Point(x, y);
		
		this.converter.convertScreenToModel(p);
		CommandeArgument<Point> cmd = new PoserMainDroite(this.tapis);
		cmd.setArgument(p);
		
		cmd.execute();	
		
		((DrawSelection)this.selectionDrawer).setSelection(false);
		
		this.selectionDrawer.clean();
		this.selectionDrawer.draw();
		this.tapisDrawer.draw();
		this.fenetre.repaint();
	}
	
	@Override
	public void mouseLeftPressed(int x, int y) {
		this.mousePosition.setX(x);
		this.mousePosition.setY(y);
	
		if(this.mainDroiteVide){
			this.attraper(x, y);
		}else{
			if(this.clips){
				ClipserParam param = new ClipserParam();
				CommandeArgument<ClipserParam> cmd = new ClipserMainDroite(this.tapis);
				cmd.setArgument(param);
				
				((DrawSelection)this.selectionDrawer).setSelection(false);
				param.setCandidat(this.isClipsParam.getCandidats().get(0));
				cmd.execute();
				
				this.clips = false;

				this.selectionParam.clearCandidats();
				
				this.selectionDrawer.clean();
				this.selectionDrawer.draw();
				this.tapisDrawer.draw();
				this.fenetre.repaint();
			}else this.poser(x, y);
		}
	}
	
	private void save(){
		if(this.mainDroiteVide && this.mainGaucheVide){
			SaveView sv = new SaveView(this.tapis,this.fenetre.getFrame());
			sv.save();	
		}else{
			System.out.println("Videz vous les mains !");
		}
	}
	

	@Override
	public void mouseLeftReleased(int x, int y) {
		this.mousePosition.setX(x);
		this.mousePosition.setY(y);
	}
	
	@Override
	public void mouseMove(int x, int y, boolean isShiftDown) {
		this.mousePosition.setX(x);
		this.mousePosition.setY(y);
		
		if(!this.mainDroiteVide && !this.shiftPressed){
			
			this.selectionParam.setPosition(new Point(x,y));
			
			this.selectionDrawer.clean();
			this.selectionDrawer.draw();
			this.fenetre.repaint();
		}
	}

	@Override
	public void mouseWheel(boolean up) {
		if(!this.mainDroiteVide){
			this.tourner(up);
		}	
	}

	@Override
	public void keyShiftPressed() {
		this.shiftPressed = true;
		
		if(!this.tryClips && !this.mainDroiteVide){
			this.tryClips = true;
			this.isClipsable(this.mousePosition.getX(), this.mousePosition.getY());
		}
	}

	@Override
	public void keyShiftReleased() {
		this.shiftPressed = false;
		this.tryClips = false;
		
		if(this.clips){
			this.selectionParam.clearCandidats();
			this.clips = false;
		}
		if(!this.mainDroiteVide){
			
			this.selectionParam.setPosition(new Point(this.mousePosition.getX(),this.mousePosition.getY()));
			this.selectionDrawer.clean();
			this.selectionDrawer.draw();
			this.fenetre.repaint();
		}
	}

	@Override
	public void mouseDrag(int x, int y) {
		
	}

	@Override
	public void mouseRightPressed(int x, int y) {
		this.rightClick = true;	
	}

	@Override
	public void mouseRightReleased(int x, int y) {
		this.rightClick = false;
	}



	@Override
	public void keyControlPressed() {
		if(!this.mainDroiteVide && !clips){
			this.passerMainGauche();
		}// if
	}
	
	public void controlPlusS(){
		this.save();
	}
	

	public void mouseEntered(){
		// Nothing
	}
	
	public void mouseExited(){
		// Nothing
	}

	@Override
	public void keyControlReleased() {
		// Nothing
	}

	@Override
	public void controlPlusL() {
		LoadView view = new LoadView(this.tapis,this.fenetre.getFrame());
		MainDroite.getInstance().libere();
		MainGauche.getInstance().libere();
		view.load();
		
		if(this.selectionDrawer!=null) this.selectionDrawer.clean();
		this.tapisDrawer.clean();
		this.tapisDrawer.draw();
		this.fenetre.repaint();
	}
	
	
	
}
