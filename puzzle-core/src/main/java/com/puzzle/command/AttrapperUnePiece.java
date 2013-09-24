package com.puzzle.command;

import java.util.List;
import com.puzzle.model.ComponentPiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Piece;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;

public class AttrapperUnePiece implements ICommand{
	
	private Tapis tapis;
	private double x;
	private double y;

	public AttrapperUnePiece(Tapis tapis) {
		this.tapis = tapis;
		this.x = Double.MAX_VALUE;
		this.y = Double.MAX_VALUE;
	}

	@Override
	public void execute() {
		if(MainDroite.getInstance().isEmpty()){
			List<Piece> candidats = this.tapis.chercherPiece(this.x, this.y);
			int ref = -1;
			Piece candidat = null;
			
			for(Piece cmp : candidats){
				// TODO : invoqué test précis
				
				if(cmp.getRect().contains(x, y)){
					if(cmp.getZIndex() > ref){
						candidat = cmp;
						ref = cmp.getZIndex();
					}
				}
			}
			
			if(candidat != null){
				ComponentPiece candidatfinal = candidat;
				if(candidat.getComposite() != null) {
					candidatfinal = candidat.getComposite();
					// TODO retirer le groupe de pièces
				}else{
					this.tapis.retirerPiece(candidat);
				}
					
				MainDroite.getInstance().setX(this.x - candidatfinal.getCentre().getX());
				MainDroite.getInstance().setY(this.y - candidatfinal.getCentre().getY());
				MainDroite.getInstance().setPiece(candidatfinal);
				
				this.tapis.change();
				this.tapis.notifyObservers(State.MainDroitePleine);
			}
		}
		
	}

	



	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
