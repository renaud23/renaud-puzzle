package com.puzzle.command;



import com.puzzle.model.ComponentPiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.MyRect;
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
				double x = MainDroite.getInstance().getX() * -1;
				double y = MainDroite.getInstance().getY() * -1;
	
				double xi = x * Math.cos(piece.getAngle()) - y * Math.sin(piece.getAngle());
				double yi = x * Math.sin(piece.getAngle()) + y * Math.cos(piece.getAngle());
				xi += this.position.getX();
				yi += this.position.getY();
				
				piece.getCentre().setX(xi);
				piece.getCentre().setY(yi);
				
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
