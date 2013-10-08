package com.puzzle.view.mainGauche;

import java.util.Observable;
import java.util.Observer;

import com.puzzle.command.Commande;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.ParcourirMainGauche;
import com.puzzle.command.PasserDansMainDroite;
import com.puzzle.command.param.ParcourirMainGaucheParam;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.controller.IController;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.drawer.IDrawerParametrable;

public class MainGaucheController implements IController,Observer{
	private Tapis tapis;
	private Fenetre fenetre;
	private IDrawerParametrable<Integer> drawer;
	
	
	
	
	public MainGaucheController(Tapis tapis, Fenetre fenetre){
		this.tapis = tapis;
		this.fenetre = fenetre;
		this.drawer = new MainGaucheDrawer(fenetre.getMainGauche().getBuffer(0));

		this.tapis.addObserver(this);
		this.drawer.draw();
		this.fenetre.repaint();
	}



	@Override
	public void mouseLeftPressed(int x, int y) {
		Commande cmd = new PasserDansMainDroite(this.tapis);
		cmd.execute();
		
		this.drawer.setParam(-1);
		this.drawer.clean();
		this.drawer.draw();
		this.fenetre.repaint();
	}



	@Override
	public void mouseLeftReleased(int x, int y) {
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



	@Override
	public void mouseMove(int x, int y, boolean isShiftDown) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseDrag(int x, int y) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseWheel(boolean up) {
		ParcourirMainGaucheParam param = new ParcourirMainGaucheParam();
		param.setUp(up);
		CommandeArgument<ParcourirMainGaucheParam> cmd = new ParcourirMainGauche();
		cmd.setArgument(param);
		
		cmd.execute();
		
		this.drawer.setParam(param.getFocused());
		
		this.drawer.clean();
		this.drawer.draw();
		this.fenetre.repaint();
		
	}



	@Override
	public void keyShiftPressed() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyShiftReleased() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyControlPressed() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyControlReleased() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void update(Observable o, Object param) {
		if(param instanceof State){
			State state = (State) param;
			if(state == State.droiteToGauche || state == State.gaucheToDroite){
				this.drawer.setParam(-1);
				this.drawer.clean();
				this.drawer.draw();
				this.fenetre.repaint();
			}
		}
		
	}
	
	



}
