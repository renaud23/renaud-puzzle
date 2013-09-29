package com.puzzle.command;



import com.puzzle.model.ComponentPiece;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.MyRect;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
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
				
				((MyRect)piece.getRect()).update();

				piece.poser(this.tapis);
			}else if(cmp instanceof ComponentPiece){
				CompositePiece composite = (CompositePiece) cmp;
				
				composite.getCentre().setX(this.position.getX());
				composite.getCentre().setY(this.position.getY());
				
				((MyRect)composite.getRect()).update();
				
				for(Piece p : composite) {
					p.poser(this.tapis);
				}
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
