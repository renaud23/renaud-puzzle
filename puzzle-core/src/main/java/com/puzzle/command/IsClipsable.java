package com.puzzle.command;


import java.util.ArrayList;
import java.util.List;import com.puzzle.command.param.IsClipsParam;
import com.puzzle.model.ComponentPiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.MyRect;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.Tapis;


/**
 * la piece doit etre posee sur le tapis.
 * hypothese :
 * dans le puzzle assemble, les piece sont num�rot� du haut vers le bas de gauche � droite.
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
			ComponentPiece cmp = MainDroite.getInstance().getContenu();
			// calcul du point selon l'ancre.
			double x = this.param.getCentre().getX();
			x += MainDroite.getInstance().getAncre().getX();
			double y = this.param.getCentre().getY();
			y += MainDroite.getInstance().getAncre().getY();
			Point pos = new Point(x,y);
			pos.tourner(cmp.getAngle(), this.param.getCentre().getX(),this.param.getCentre().getY());
			
			cmp.getCentre().setX(pos.getX());
			cmp.getCentre().setY(pos.getY());
			((MyRect)cmp.getRect()).update();
		
			List<Piece> cdt = this.tapis.chercherPiece(cmp.getRect());
			List<Piece> elus = new ArrayList<Piece>();
		
			for(Piece p : cdt){
				boolean state = false;
				if(cmp.getPuzzle().getId() == p.getPuzzle().getId()){
					state = cmp.verifierClips(p);
					if(state) elus.add(p);
				}
			}// for
		
			this.param.setCandidats(elus);
			if(!elus.isEmpty()){
				this.param.setComponent(cmp);
				
				this.tapis.change();
				this.tapis.notifyObservers(this.param);
			}
		}// if
		
	}
	
	
	
	

	@Override
	public void setArgument(IsClipsParam arg) {
		this.param = arg;
	}
}
