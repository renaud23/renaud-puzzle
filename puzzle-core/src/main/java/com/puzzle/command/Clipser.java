package com.puzzle.command;

import com.puzzle.command.param.ClipserParam;
import com.puzzle.model.ComponentPiece;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;

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
		
		
		cmp.poser(this.tapis);
		
		// renvoi du nouveau component
		this.param.setDetruit(this.param.getComponent());
		this.param.setComponent(cmp);
		
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
