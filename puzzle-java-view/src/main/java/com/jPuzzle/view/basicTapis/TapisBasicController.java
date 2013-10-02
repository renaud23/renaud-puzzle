package com.jPuzzle.view.basicTapis;

import java.util.Observable;
import java.util.Observer;

import com.puzzle.command.AttrapperMainDroite;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.PoserMainDroite;
import com.puzzle.controller.IController;
import com.puzzle.controller.TapisConverter;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.drawer.DrawSelection;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.drawer.IDrawerParametrable;

public class TapisBasicController implements IController,Observer{
	
	private Fenetre fenetre;
	private TapisConverter converter;
	private IDrawer tapisDrawer;
	private IDrawerParametrable<Point> selectionDrawer;
	private Tapis tapis;
	
	private boolean mainVide;
	
	
	public TapisBasicController(Fenetre fenetre,Tapis tapis){
		this.fenetre = fenetre;
		this.tapis = tapis;
		
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
				this.selectionDrawer.setParam(new Point(x,y));
				
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
	public void mouseMove(int x, int y) {
		if(!this.mainVide){
			this.selectionDrawer.draw();
			this.selectionDrawer.setParam(new Point(x,y));
			this.fenetre.repaint();
		}
		
	}
}
