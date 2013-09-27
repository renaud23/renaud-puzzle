package com.jPuzzle.view.drawer;

import java.awt.Color;
import java.awt.Image;
import com.jPuzzle.view.basicTapis.TapisBasicConverter;
import com.jPuzzle.view.image.ImageBuffer;
import com.jPuzzle.view.image.ImageMemoryManager;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.renaud.manager.IRect;



public class PieceDrawer implements IDrawerParametrable<Transformation>{
	
	private Piece piece;
	private ImageBuffer offscreen;
	private Transformation transformation;
	
	

	public PieceDrawer(Piece piece, ImageBuffer offscreen) {
		this.piece = piece;
		this.offscreen = offscreen;
	}



	@Override
	public void draw() {
		Image img = ImageMemoryManager.getInstance().getImage(this.piece.getId());
	
		double x = transformation.getTx();
		x -= this.piece.getLargeur() / 2.0 * transformation.getSx();
		
		double y = transformation.getTy();
		y -= this.piece.getHauteur() / 2.0 * transformation.getSy();
		
		this.offscreen.drawImage(img, (int) x, (int) y, 
				transformation.getRx() , transformation.getRy(), -piece.getAngle(), 
				transformation.getSx(),
				transformation.getSy(), 1.0f);
		
		
		// dev
//		for(Point p : ((RectPiece) piece.getRect()).getCoins()){
//				Point o = new Point(p.getX(),p.getY());
//				TapisBasicConverter.getInstance().convertModelToScreen(o);
//				
//				this.offscreen.drawRect(Color.red,(int) o.getX(), (int) o.getY(), 2, 2);
//			
//		}
		
		IRect r = piece.getRect();
		Point a = new Point(r.getX(),r.getY());
		TapisBasicConverter.getInstance().convertModelToScreen(a);
		this.offscreen.drawRect(Color.red,(int) a.getX(), (int) a.getY(), 2, 2);
		
		Point o = new Point(piece.getCentre().getX(),piece.getCentre().getY());
		TapisBasicConverter.getInstance().convertModelToScreen(o);
		this.offscreen.drawRect(Color.yellow,(int) o.getX(), (int) o.getY(), 2, 2);
	}



	public void setTransformation(Transformation transformation) {
		this.transformation = transformation;
	}



	@Override
	public void setParameter(Transformation param) {
		this.transformation = param;
		
	}
}
