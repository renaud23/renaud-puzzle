package com.puzzle.command;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.puzzle.model.ComponentPiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Tapis;


/**
 * la piece doit etre posee sur le tapis.
 * hypothese :
 * dans le puzzle assemble, les piece sont numéroté du haut vers le bas de gauche à droite.
 * @author Renaud
 *
 */
public class IsClipsable implements CommandeArgument<ClipsParam>{
	
	private Tapis tapis;
	private ClipsParam param;
	
	

	public IsClipsable(Tapis tapis) {
		this.tapis = tapis;
	}

	@Override
	public void execute() {
		ComponentPiece cdt = param.getComponent();// la piece candidate
		Set<Piece> pieces = this.tapis.chercherPiece(cdt.getRect());// les pieces voisines sur le tableau
		List<Piece> candidats = new ArrayList<Piece>();
		
		for(Piece p : pieces){
			if(cdt.verifierClips(p)){
				candidats.add(p);
				this.param.setClipsable(true);
			}
		}// for
	
		this.param.setCandidats(candidats);
//		System.out.println(this.param.getCandidats());
		
	}

	@Override
	public void setArgument(ClipsParam arg) {
		this.param = arg;
	}
}
