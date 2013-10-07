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
		// calcul de la position selon le point d'ancrage de la pièce
		double x = this.position.getX();
		x += MainDroite.getInstance().getAncre().getX();
		double y = this.position.getY();
		y += MainDroite.getInstance().getAncre().getY();
		Point pos = new Point(x,y);
		pos.tourner(cmp.getAngle(), this.position.getX(),this.position.getY());
		
		
		if(cmp != null){
			if(cmp instanceof Piece){
				Piece piece = (Piece) cmp;
				
				piece.getCentre().setX(pos.getX());
				piece.getCentre().setY(pos.getY());
				
				((MyRect)piece.getRect()).update();

				piece.poser(this.tapis);
			}else if(cmp instanceof ComponentPiece){
				CompositePiece composite = (CompositePiece) cmp;
				
				composite.getCentre().setX(pos.getX());
				composite.getCentre().setY(pos.getY());
				
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
