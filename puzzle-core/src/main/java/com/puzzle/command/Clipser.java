package com.puzzle.command;

import com.puzzle.command.param.ClipserParam;
import com.puzzle.model.ComponentPiece;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.renaud.manager.IRect;

public class Clipser implements CommandeArgument<ClipserParam>{
	private Tapis tapis;
	private ClipserParam param;
	
	
	public Clipser(Tapis tapis) {
		this.tapis = tapis;
	}
	
	
	@Override
	public void execute() {
		ComponentPiece cmp = this.param.getComponent();
		Piece candidat = this.param.getCandidat();
			
		if(candidat.getComposite() != null){
			candidat.getComposite().addComponent(cmp);
			cmp = candidat.getComposite();
		}else{
			// nouveau composite
			CompositePiece composite = new CompositePiece(candidat.getCentre().getX(),candidat.getCentre().getY());
			composite.addComponent(candidat);
			composite.addComponent(cmp);
			
			cmp = composite;
		}
		
		// recalcul du centre.
		double maxx = -Double.MAX_VALUE;
		double minx = Double.MAX_VALUE;
		double maxy = -Double.MAX_VALUE;
		double miny = Double.MAX_VALUE;
		CompositePiece composite = (CompositePiece) cmp;
		for(Piece pi : composite){
			IRect r = pi.getRect();
			maxx = Math.max(maxx, r.getX() + r.getLargeur());
			minx = Math.min(minx, r.getX());
			maxy = Math.max(maxy,r.getY());
			miny = Math.min(miny, r.getY() - r.getHauteur());
		}
		composite.getCentre().setX(minx + (maxx - minx)/ 2.0);
		composite.getCentre().setY(miny + (maxy - miny)/ 2.0);
		
		cmp.poser(this.tapis);
		
		if(((CompositePiece)cmp).getTaille() == candidat.getPuzzle().getTaille()){
			candidat.getPuzzle().setFini(true);
			this.tapis.change();
			this.tapis.notifyObservers(State.PuzzleFini);
		}
		
	}
	
	

	@Override
	public void setArgument(ClipserParam arg) {
		this.param = arg;
	}

}
