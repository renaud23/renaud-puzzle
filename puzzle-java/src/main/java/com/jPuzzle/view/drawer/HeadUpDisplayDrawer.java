package com.jPuzzle.view.drawer;


import com.jPuzzle.view.basicTapis.TapisBasicConverter;
import com.jPuzzle.view.image.ImageBuffer;
import com.puzzle.model.ComponentPiece;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;

public class HeadUpDisplayDrawer implements IDrawerParametrable<Transformation>{
	
	private ImageBuffer offscreen;
	private ComponentPiece mainDroite;
	private Transformation point;

	public HeadUpDisplayDrawer(ImageBuffer offscreen) {
		super();
		this.offscreen = offscreen;
	}

	@Override
	public void draw() {
		this.offscreen.transparentClean();
		
		if(!MainDroite.getInstance().isEmpty() && this.point != null){
			
			ComponentPiece cmp = MainDroite.getInstance().getPiece();
			
			if(cmp instanceof Piece){
				PieceDrawer dr = new PieceDrawer((Piece)cmp, this.offscreen);
				
				
				
				dr.setParameter(this.point);
				dr.draw();
				
			}else if(cmp instanceof CompositePiece){
				
				// TODO
			}
		}
	}


	public ComponentPiece getMainDroite() {
		return mainDroite;
	}

	public void setMainDroite(ComponentPiece mainDroite) {
		this.mainDroite = mainDroite;
	}

	@Override
	public void setParameter(Transformation param) {
		this.point = param;
	}

	
	
	
}
