package com.puzzle.view.basicTapis;



import java.awt.Image;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.Tapis;
import com.puzzle.view.controller.TapisConverter;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.tool.JImageBuffer;
import com.puzzle.view.tool.ImageMemoryManager;


public class TapisBasicDrawer implements IDrawer {
	
	private Tapis tapis;
	private JImageBuffer tapisBuffer;
	private TapisConverter converteur;


	public TapisBasicDrawer(Tapis tapis, JImageBuffer tapisBuffer,
			TapisConverter converteur) {
		this.tapis = tapis;
		this.tapisBuffer = tapisBuffer;
		this.converteur = converteur;
	}


	@Override
	public void draw() {
		this.tapisBuffer.clean();
		
		//	 dessin tapis
		for(Piece piece : this.tapis){
			Image img = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getImage(piece.getId());
			
			Point p = new Point(piece.getCentre().getX(),piece.getCentre().getY());
			this.converteur.convertModelToScreen(p);
			
			double x = p.getX();
			x -= img.getWidth(null) / 2.0 * this.converteur.getScaleX();
			
			double y = p.getY();
			y -= img.getHeight(null) / 2.0 * this.converteur.getScaleY();
		
			this.tapisBuffer.drawImage(img,
					x,  y, 
					p.getX() , p.getY(), -piece.getAngle(), 
					this.converteur.getScaleX(), this.converteur.getScaleY(), 
					1.0f);
			
			// pour dev
//			Point c = new Point(piece.getCentre().getX(), piece.getCentre().getY());
//			this.converteur.convertModelToScreen(c);
//			this.tapisBuffer.drawRect(Color.white, (int)Math.round(c.getX()), (int)Math.round(c.getY()), 2, 2);
			
			
		}
		
		
		
		
		
	}


	@Override
	public void clean() {
		this.tapisBuffer.clean();
	}
	
}
