package com.puzzle.command;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.puzzle.command.param.IsClipsParam;
import com.puzzle.model.ComponentPiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.MyRect;
import com.puzzle.model.Piece;
import com.puzzle.model.Tapis;


/**
 * la piece doit etre posee sur le tapis.
 * hypothese :
 * dans le puzzle assemble, les piece sont numéroté du haut vers le bas de gauche à droite.
 * @author Renaud
 *
 */
public class IsClipsable implements CommandeArgument<IsClipsParam>{
	
	private Tapis tapis;
	private IsClipsParam param;
	

	public IsClipsable(Tapis tapis) {
		this.tapis = tapis;
	}

	@Override
	public void execute() {
		if(!MainDroite.getInstance().isEmpty()){
			ComponentPiece cmp = MainDroite.getInstance().getPiece();
			cmp.getCentre().setX(this.param.getCentre().getX());
			cmp.getCentre().setY(this.param.getCentre().getY());
			((MyRect)cmp.getRect()).update();
		
			Set<Piece> cdt = this.tapis.chercherPiece(cmp.getRect());
			List<Piece> elus = new ArrayList<Piece>();
		
			for(Piece p : cdt){
				boolean state = cmp.verifierClips(p);
				if(state) elus.add(p);
			}// for
		
			this.param.setCandidats(elus);
			if(!elus.isEmpty()){
				this.param.setComponent(cmp);
			}
		}// if
		
	}
	
	
	
	

	@Override
	public void setArgument(IsClipsParam arg) {
		this.param = arg;
	}
}
