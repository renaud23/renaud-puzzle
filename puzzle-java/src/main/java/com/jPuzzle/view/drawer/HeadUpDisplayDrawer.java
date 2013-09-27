package com.jPuzzle.view.drawer;

import com.jPuzzle.view.basicTapis.TapisBasicConverter;
import com.jPuzzle.view.image.ImageBuffer;
import com.puzzle.model.ComponentPiece;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.renaud.manager.IRect;

public class HeadUpDisplayDrawer implements IDrawerParametrable<Transformation>{
	
	private ImageBuffer offscreen;
//	private ComponentPiece mainDroite;
	private Transformation transformation;

	public HeadUpDisplayDrawer(ImageBuffer offscreen) {
		this.offscreen = offscreen;
	}

	@Override
	public void draw() {
		this.offscreen.transparentClean();
		
		if(!MainDroite.getInstance().isEmpty() && this.transformation != null){
			
			ComponentPiece cmp = MainDroite.getInstance().getPiece();
			
			if(cmp instanceof Piece){
				PieceDrawer dr = new PieceDrawer((Piece)cmp, this.offscreen);
				
				dr.setParameter(this.transformation);
				dr.draw();
				
			}else if(cmp instanceof CompositePiece){
				
				Transformation t= new Transformation();
				t.setSx(this.transformation.getSx());
				t.setSy(this.transformation.getSy());
				
				for(Piece p : (CompositePiece) cmp ){
					PieceDrawer dr = new PieceDrawer(p, this.offscreen);
					
					double x = p.getCentre().getX();
					x *= this.transformation.getSx();
					double y = p.getCentre().getY();
					y *= this.transformation.getSy();
					y *= -1.0;
					
					t.setTx(this.transformation.getTx() + x);
					t.setTy(this.transformation.getTy() + y);
					t.setRx(t.getTx());
					t.setRy(t.getTy());
					
					dr.setParameter(t);
					dr.draw();
				}// for p
				
			}
		}
	}


//	public ComponentPiece getMainDroite() {
//		return mainDroite;
//	}

//	public void setMainDroite(ComponentPiece mainDroite) {
//		this.mainDroite = mainDroite;
//	}

	@Override
	public void setParameter(Transformation param) {
		this.transformation = param;
	}

	
	
	
}
