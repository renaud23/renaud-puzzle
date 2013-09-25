package com.puzzle.command;



import com.puzzle.model.ComponentPiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.RectPiece;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;

public class PoserMainDroite implements CommandeArgument<Point>{
	private Tapis tapis;
	private Point position;

	public PoserMainDroite(Tapis tapis) {
		this.tapis = tapis;
	}

	@Override
	public void execute() {
		ComponentPiece cmp = MainDroite.getInstance().getPiece();
		
		if(cmp != null){
			if(cmp instanceof Piece){
				Piece piece = (Piece) cmp;
				
				piece.getCentre().setX(this.position.getX());
				piece.getCentre().setY(this.position.getY());
				
				((RectPiece)piece.getRect()).update();
				((RectPiece)piece.getRect()).checkAngle();
				
				this.tapis.poserPiece(piece);
			}else if(cmp instanceof ComponentPiece){
				// TODO
				
			
			}
			
			MainDroite.getInstance().libere();
			this.tapis.change();
			this.tapis.notifyObservers(State.MainDroiteVide);
		}// if !null
		
	}

	

	@Override
	public void setArgument(Point arg) {
		this.position = arg;
		
	}

	

}
