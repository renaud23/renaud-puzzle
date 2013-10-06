package com.puzzle.command;

import java.util.List;

import com.puzzle.command.param.AttrapperMainDroiteParam;
import com.puzzle.model.ComponentPiece;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.MyRect;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;

public class AttrapperMainDroite implements CommandeArgument<AttrapperMainDroiteParam>{
	
	private Tapis tapis;
	private AttrapperMainDroiteParam param;
	

	public AttrapperMainDroite(Tapis tapis) {
		this.tapis = tapis;
	}

	@Override
	public void execute() {
		if(MainDroite.getInstance().isEmpty()){
			List<Piece> candidats = this.tapis.chercherPiece(this.param.getPosition().getX(), this.param.getPosition().getY());
			int ref = -1;
			Piece candidat = null;
			
			for(Piece cmp : candidats){		
				if(cmp.getRect().contains(this.param.getPosition().getX(), this.param.getPosition().getY())){
					if(cmp.getZIndex() > ref){
						candidat = cmp;
						ref = cmp.getZIndex();
					}
				}
			}
			
			if(candidat != null){
				ComponentPiece candidatfinal = candidat;
				
				double xi = this.param.getPosition().getX();
				xi -= candidatfinal.getCentre().getX();
				double yi = this.param.getPosition().getY();
				yi -= candidatfinal.getCentre().getY();
				this.param.setAncre(new Point(xi,yi));
				
				if(candidat.getComposite() != null) {
					candidatfinal = candidat.getComposite();
					
					for(Piece p : (CompositePiece)candidatfinal){
						this.tapis.retirerPiece(p);
					}
					
					candidatfinal.getCentre().setX(0.0);
					candidatfinal.getCentre().setY(0.0);
					((MyRect)candidatfinal.getRect()).update();
				}else{
					this.tapis.retirerPiece(candidat);
				}
					
			
				MainDroite.getInstance().setPiece(candidatfinal);
				this.tapis.change();
				this.tapis.notifyObservers(State.MainDroitePleine);
			}
		}
		
	}

	@Override
	public void setArgument(AttrapperMainDroiteParam param) {
		this.param = param;
	}



	
}
