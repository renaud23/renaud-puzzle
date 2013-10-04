package com.puzzle.view.basicTapis;


import java.util.Observable;
import java.util.Observer;

import com.puzzle.command.AttrapperMainDroite;
import com.puzzle.command.ClipserMainDroite;
import com.puzzle.command.ClipserParam;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.IsClipsParam;
import com.puzzle.command.IsClipsable;
import com.puzzle.command.PoserMainDroite;
import com.puzzle.command.tournerMainDroite;
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
	private Tapis tapis;
	private int mouseX;
	private int mouseY;


	
	private boolean mainVide;
	private boolean clips;

	
	
	public TapisBasicController(Fenetre fenetre,Tapis tapis){
		this.fenetre = fenetre;
		this.tapis = tapis;
		this.selectionParam = new DrawSelectionParam();
		this.isClipsParam = new IsClipsParam();
		
		// IOC
		this.converter = new TapisBasicConverter(this.fenetre.getOffscreen(),tapis);
		this.converter.update();
		this.tapisDrawer = new TapisBasicDrawer(tapis, fenetre.getBuffer(0),this.converter);
		
		this.tapis.addObserver(this);
		
		
		this.tapisDrawer.draw();
		this.fenetre.repaint();
		
		
		this.mainVide = true;
		this.clips = false;
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
		} else{
			this.clips = false;
		}
	}
	
	
	private void attraper(int x,int y){
		Point p = new Point(x, y);
		this.converter.convertScreenToModel(p);
		CommandeArgument<Point> cmd = new AttrapperMainDroite(this.tapis);
		cmd.setArgument(p);
		
		cmd.execute();
		
		if(!this.mainVide){
			this.selectionDrawer = new DrawSelection(
					this.fenetre.getBuffer(1), 
					MainDroite.getInstance().getPiece(), 
					this.converter);
			this.selectionParam.setPosition(new Point(x,y));
			this.selectionDrawer.setParam(this.selectionParam);
			
			this.tapisDrawer.draw();
			this.selectionDrawer.draw();
			this.fenetre.repaint();
		}
	}
	
	private void poser(int x,int y){
		Point p = new Point(x, y);
		
		this.converter.convertScreenToModel(p);
		CommandeArgument<Point> cmd = new PoserMainDroite(this.tapis);
		cmd.setArgument(p);
		
		cmd.execute();	
		
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
		}
		
	}

	
	@Override
	public void mouseLeftReleased(int x, int y) {
		
		
	}

	@Override
	public void mouseLeftPressed(int x, int y) {
		this.mouseX = x;
		this.mouseY = y;
		
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
		this.mouseX = x;
		this.mouseY = y;
		if(!this.mainVide){
			this.selectionDrawer.clean();
			if(isShiftDown){
				this.isClipsable(x, y);
			}
			
			this.selectionDrawer.draw();
			this.selectionParam.setPosition(new Point(x,y));
			this.fenetre.repaint();
		}
		
	}


	@Override
	public void mouseWheel(boolean up) {
		if(!this.mainVide){
			CommandeArgument<Boolean> cmd = new tournerMainDroite();
			cmd.setArgument(!up);
			cmd.execute();
			
			this.selectionDrawer.draw();
			this.fenetre.repaint();
		}
		
	}


	@Override
	public void keyShiftPressed() {
		if(!this.mainVide) {
			this.isClipsable(this.mouseX, this.mouseY);
			this.selectionDrawer.draw();
			this.selectionParam.setPosition(new Point(this.mouseX, this.mouseY));
			this.fenetre.repaint();
		}
		
	}


	@Override
	public void keyShiftReleased() {
		if(!this.mainVide){
			this.selectionParam.clearCandidats();
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
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseRightReleased(int x, int y) {
		// TODO Auto-generated method stub
		
	}


	
}
