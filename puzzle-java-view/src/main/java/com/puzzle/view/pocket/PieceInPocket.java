package com.puzzle.view.pocket;



import java.util.Observable;
import java.util.Observer;
import com.puzzle.command.Commande;
import com.puzzle.command.PasserDansMainDroite;
import com.puzzle.model.MainDroite;
import com.puzzle.model.MainGauche;
import com.puzzle.model.Piece;
import com.puzzle.model.State;
import com.puzzle.view.hud.HudArea;
import com.puzzle.view.hud.HudControler;
import com.puzzle.view.hud.HudShape;
import com.puzzle.view.hud.HudTask;




public class PieceInPocket  extends HudArea implements Observer{
	
	private Piece piece;

	private float alpha;
	private double scale;
	private double scaleEffectif;
	


	private HudControler controller;
	private Pocket pocket;
	private boolean focused;
	

	public PieceInPocket(Piece piece,final HudControler controller, Pocket pocket,HudShape shape,double scale){
		this.shape = shape;
		this.piece = piece;
		this.scale = scale;
		this.scaleEffectif = scale;
		this.controller = controller;
		this.pocket = pocket;
		this.alpha = 1.0f;
		
		this.task = new HudTask() {
			
			@Override
			public void execute() {
				Commande cmd = new PasserDansMainDroite(controller.getTapis());
				cmd.execute();
			}
		};
		controller.getTapis().addObserver(this);
		
	}


	public void clean(){
		
	}

	@Override
	public void mouseEntered() {
		this.scaleEffectif = this.scale * 2;
		this.focused = true;
		MainGauche.getInstance().focused(this.piece);

 		for(PieceInPocket pi : this.pocket.getEntry()){
			if(pi != this) pi.alpha = 0.4f;
			
		}

		this.controller.getDrawer().draw();
	}


	@Override
	public void mouseExited() {
		this.scaleEffectif = this.scale;
		this.focused = false;
		for(PieceInPocket pi : this.pocket.getEntry()){
			if(pi != this) pi.alpha = 1.0f;
		}

		this.controller.getDrawer().draw();
	}


	@Override
	public void update(Observable o, Object arg) {
		if(arg == State.gaucheToDroite){
			if(this.piece == MainDroite.getInstance().getContenu()){
				this.controller.getTapis().deleteObserver(this);

				this.controller.removeArea(this.pocket.get(this.piece));
				this.pocket.remove(this.piece);
				
				this.controller.getDrawer().draw();
			}
			
		}
		
	}


	public float getAlpha() {
		return alpha;
	}


	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}


	public Piece getPiece() {
		return piece;
	}


	public double getScaleEffectif() {
		return scaleEffectif;
	}


	public boolean isFocused() {
		return focused;
	}
	
	

}
