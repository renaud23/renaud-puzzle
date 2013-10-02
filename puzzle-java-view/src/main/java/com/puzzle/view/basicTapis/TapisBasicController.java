package com.puzzle.view.basicTapis;

import java.awt.Component;
import java.util.Observable;
import java.util.Observer;
import com.puzzle.command.AttrapperMainDroite;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.IsClipsParam;
import com.puzzle.command.IsClipsable;
import com.puzzle.command.PoserMainDroite;
import com.puzzle.command.tournerMainDroite;
import com.puzzle.controller.IController;
import com.puzzle.controller.TapisConverter;
import com.puzzle.model.ComponentPiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.drawer.DrawCandidats;
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
	private Tapis tapis;

	
	private boolean mainVide;
	
	
	public TapisBasicController(Fenetre fenetre,Tapis tapis){
		this.fenetre = fenetre;
		this.tapis = tapis;
		this.selectionParam = new DrawSelectionParam();
		
		// IOC
		this.converter = new TapisBasicConverter();
		((TapisBasicConverter)this.converter).setOffscreen(this.fenetre.getOffscreen());
		((TapisBasicConverter)this.converter).setTapis(tapis);
		((TapisBasicConverter)this.converter).update();
		this.tapisDrawer = new TapisBasicDrawer(tapis, fenetre.getBuffer(0),this.converter);
		
		this.tapis.addObserver(this);
		
		
		this.tapisDrawer.draw();
		this.fenetre.repaint();
		
		
		this.mainVide = true;
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
	public void mousePressed(int x, int y) {
		if(this.mainVide){
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
		}else{
			Point p = new Point(x, y);
			
			this.converter.convertScreenToModel(p);
			CommandeArgument<Point> cmd = new PoserMainDroite(this.tapis);
			cmd.setArgument(p);
			
			cmd.execute();	
			
			this.selectionDrawer.clean();
			this.tapisDrawer.draw();
			this.fenetre.repaint();
			
		}
		
	}


	@Override
	public void mouseMove(int x, int y,boolean isShiftDown) {
		if(!this.mainVide){
			this.fenetre.getBuffer(1).transparentClean();
			if(isShiftDown){
				this.selectionParam.clearCandidats();
				Point p = new Point(x,y);
				this.converter.convertScreenToModel(p);
				
				IsClipsParam param = new IsClipsParam();
				param.setCentre(p);
				CommandeArgument<IsClipsParam> cmd = new IsClipsable(this.tapis);
				cmd.setArgument(param);
				cmd.execute();
				
				if(!param.getCandidats().isEmpty()){
					this.selectionParam.addCandidats(param.getCandidats());
				} 
				
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
}
