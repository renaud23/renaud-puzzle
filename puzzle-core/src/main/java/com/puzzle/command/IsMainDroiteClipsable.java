package com.puzzle.command;

import com.puzzle.model.ComponentPiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Piece;
import com.puzzle.model.Tapis;

public class IsMainDroiteClipsable implements CommandeArgument<ClipsParam>{
	
	private Tapis tapis;
	private ClipsParam param;
	
	

	public IsMainDroiteClipsable(Tapis tapis) {
		this.tapis = tapis;
	}

	@Override
	public void execute() {
		ComponentPiece cdt = MainDroite.getInstance().getPiece();
		
		for(Piece p : this.tapis){
			if(cdt.verifierClips(p)){
				this.param.getCandidats().add(p);
				this.param.setClipsable(true);
			}
		}
		
	}

	@Override
	public void setArgument(ClipsParam arg) {
		this.param = arg;
	}
}
