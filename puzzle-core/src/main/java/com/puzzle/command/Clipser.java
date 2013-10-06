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
		Piece p = this.param.getCandidat();
			
		if(p.getComposite() != null){
			p.getComposite().addComponent(cmp);
			cmp = p.getComposite();
		}else{
			// nouveau composite
			CompositePiece composite = new CompositePiece(p.getCentre().getX(),p.getCentre().getY());
			composite.addComponent(p);
			composite.addComponent(cmp);
			
			cmp = composite;
		}
		
		cmp.poser(this.tapis);
		
		if(((CompositePiece)cmp).getTaille() == p.getPuzzle().getTaille()){
			p.getPuzzle().setFini(true);
			this.tapis.change();
			this.tapis.notifyObservers(State.PuzzleFini);
		}
		
	}
	
	

	@Override
	public void setArgument(ClipserParam arg) {
		this.param = arg;
	}

}
