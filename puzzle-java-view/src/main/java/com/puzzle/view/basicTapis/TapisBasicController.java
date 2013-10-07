package com.puzzle.view.basicTapis;


import java.util.Observable;
import java.util.Observer;

import com.puzzle.command.AttrapperMainDroite;
import com.puzzle.command.ClipserMainDroite;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.IsClipsable;
import com.puzzle.command.PoserMainDroite;
import com.puzzle.command.tournerMainDroite;
import com.puzzle.command.param.AttrapperMainDroiteParam;
import com.puzzle.command.param.ClipserParam;
import com.puzzle.command.param.IsClipsParam;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.controller.IController;
import com.puzzle.view.controller.TapisConverter;
import com.puzzle.view.drawer.DrawSelection;
import com.puzzle.view.drawer.DrawSelectionParam;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.drawer.IDrawerParametrable;








public class TapisBasicController implements IController,Observer{
	
	private Fenetre fenetre;
	private TapisConverter converter;
	private IDrawer tapisDrawer;
	private IDrawerParametrable<DrawSelectionParam> selectionDrawer;
	
	private DrawSelectionParam selectionParam;
	private IsClipsParam isClipsParam;
	private AttrapperMainDroiteParam attraperParam;
	
	private Tapis tapis;
	private Point mousePosition;

	private boolean tryClips;
	private boolean rightClick;
	private boolean shiftPressed;
	private boolean mainVide;
	private boolean clips;

	
	
	public TapisBasicController(Fenetre fenetre,Tapis tapis){
		this.fenetre = fenetre;
		this.tapis = tapis;
		this.selectionParam = new DrawSelectionParam();
		this.isClipsParam = new IsClipsParam();
		this.attraperParam = new AttrapperMainDroiteParam();
		
		// IOC
		this.converter = new TapisBasicConverter(this.fenetre.getOffscreen(),tapis);
		this.converter.update();
		this.tapisDrawer = new TapisBasicDrawer(tapis, fenetre.getBuffer(0),this.converter);
		
		this.tapis.addObserver(this);
		
		
		this.tapisDrawer.draw();
		this.fenetre.repaint();
		
		this.mainVide = true;
		this.rightClick = false;
		this.shiftPressed = false;
		this.tryClips = false;
		this.mainVide = true;
		this.clips = false;
		
		this.mousePosition = new Point();
	}

	
	private void isClipsable(double x,double y){
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
			this.selectionDrawer.draw();
			this.selectionParam.setPosition(new Point(x,y));
			this.fenetre.repaint();
		} else{
			this.clips = false;
		}
	}
	
	
	private void attraper(int x,int y){
		Point p = new Point(x, y);
		this.converter.convertScreenToModel(p);
		
		CommandeArgument<AttrapperMainDroiteParam> cmd = new AttrapperMainDroite(this.tapis);
		this.attraperParam.setPosition(p);
		cmd.setArgument(this.attraperParam);
		
		cmd.execute();
		
		if(!this.mainVide){
			this.selectionDrawer = new DrawSelection(
					this.fenetre.getBuffer(1), 
					MainDroite.getInstance().getPiece(), 
					this.converter);
			this.selectionParam.setAncre(this.attraperParam.getAncre());
			this.selectionParam.setPosition(new Point(x,y));
			this.selectionDrawer.setParam(this.selectionParam);
			
			this.tapisDrawer.draw();
			this.selectionDrawer.draw();
			this.fenetre.repaint();
		}
	}
	
	private void tourner(boolean up){
		CommandeArgument<Boolean> cmd = new tournerMainDroite();
		cmd.setArgument(!up);
		cmd.execute();
		
		this.selectionDrawer.clean();
		this.selectionDrawer.draw();
		this.fenetre.repaint();
	}
	
	private void poser(int x,int y){
		Point p = new Point(x, y);
		
		this.converter.convertScreenToModel(p);
		CommandeArgument<Point> cmd = new PoserMainDroite(this.tapis);
		cmd.setArgument(p);
		
		cmd.execute();	
		
		this.selectionDrawer.clean();
		this.selectionDrawer.clean();
		this.tapisDrawer.draw();
		this.fenetre.repaint();
	}

	@Override
	public void update(Observable obs, Object arg) {
		if(arg instanceof State){
			State st = (State) arg;
			if(st == State.MainDroitePleine)
				this.mainVide = false;
			else if(st == State.MainDroiteVide)
				this.mainVide = true;
			else if(st == State.PuzzleFini)
				System.out.println("Fini!!!");
		}
		
	}

	
	@Override
	public void mouseLeftReleased(int x, int y) {
		this.mousePosition.setX(x);
		this.mousePosition.setY(y);
		
	}

	@Override
	public void mouseLeftPressed(int x, int y) {
		this.mousePosition.setX(x);
		this.mousePosition.setY(y);
		
		if(this.mainVide){
			this.attraper(x, y);
		}else{
			if(this.clips){
				ClipserParam param = new ClipserParam();
				CommandeArgument<ClipserParam> cmd = new ClipserMainDroite(this.tapis);
				cmd.setArgument(param);
				
				param.setCandidat(this.isClipsParam.getCandidats().get(0));
				cmd.execute();
				
				this.clips = false;

				
				this.selectionParam.clearCandidats();
				this.selectionDrawer.clean();
				this.tapisDrawer.draw();
				this.fenetre.repaint();
			}else this.poser(x, y);
		}// else
		
	}


	@Override
	public void mouseMove(int x, int y,boolean isShiftDown) {
		this.mousePosition.setX(x);
		this.mousePosition.setY(y);
		
		if(!this.mainVide && !this.shiftPressed){
			
			this.selectionParam.setPosition(new Point(x,y));
			
			this.selectionDrawer.clean();
			this.selectionDrawer.draw();
			this.fenetre.repaint();
		}
	}


	@Override
	public void mouseWheel(boolean up) {
		if(!this.mainVide){
			this.tourner(up);
		}
		
	}


	@Override
	public void keyShiftPressed() {
		this.shiftPressed = true;
		
		if(!this.tryClips && !this.mainVide){
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
		if(!this.mainVide){
			
			this.selectionParam.setPosition(new Point(this.mousePosition.getX(),this.mousePosition.getY()));
			this.selectionDrawer.clean();
			this.selectionDrawer.draw();
			this.fenetre.repaint();
		}
	}


	@Override
	public void mouseDrag(int x, int y) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseRightPressed(int x, int y) {
		this.rightClick = true;	
	}

	@Override
	public void mouseRightReleased(int x, int y) {
		this.rightClick = false;
	}


	
}
