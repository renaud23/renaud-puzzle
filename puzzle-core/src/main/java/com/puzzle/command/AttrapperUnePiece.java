package com.puzzle.command;

import java.util.Set;

import com.puzzle.model.ComponentPiece;
import com.puzzle.model.MainDroite;
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
			Set<ComponentPiece> candidats = this.tapis.chercherPiece(this.x, this.y);
			int ref = -1;
			ComponentPiece candidat = null;
			for(ComponentPiece cmp : candidats){
				if(cmp.getZIndex() > ref)
					candidat = cmp;
			}
			
			if(candidat != null) MainDroite.getInstance().setPiece(candidat);
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
