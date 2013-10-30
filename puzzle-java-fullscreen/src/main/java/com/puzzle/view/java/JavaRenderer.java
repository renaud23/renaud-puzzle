package com.puzzle.view.java;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.Tapis;
import com.puzzle.view.core.IDrawer;
import com.puzzle.view.core.Lunette;
import com.puzzle.view.core.Renderer;
import com.puzzle.view.core.TapisConverteur;
import com.puzzle.view.core.image.CompositeBufferOperation;
import com.puzzle.view.core.image.ImageMemoryManager;
import com.puzzle.view.core.image.PieceBufferOperation;
import com.puzzle.view.core.image.PieceLoader;
import com.renaud.manager.IRect;
import com.renaud.manager.Rect;

public class JavaRenderer implements Renderer,Observer{
	
	private IDrawer drawer;
	private Tapis tapis;
	private Image background;
	private TapisConverteur converter;
	private BufferStrategy strategy;
	private Lunette lunette;
	

	private Point corner = new Point();
	private double scale;
	private double largeur;
	private double hauteur;
	


	public JavaRenderer( Tapis tapis, TapisConverteur converter,
			IDrawer drawer, BufferStrategy strategy,Image background) {
		this.drawer = drawer;
		this.tapis = tapis;
		this.converter = converter;
		this.strategy = strategy;
		this.background = background;
		
		
		this.lunette = new Lunette();
		this.lunette.setTapis(tapis);
		this.lunette.setScale(0.2);
		this.lunette.setLargeur(this.drawer.getLargeur() * this.lunette.getScale());
		this.lunette.setHauteur(this.lunette.getLargeur() * tapis.getHauteur() / tapis.getLargeur());
		this.lunette.setX(10.0);
		this.lunette.setY(10.0);
		
		PieceLoader.getInstance().addObserver(this);
	}

	public IDrawer getDrawer() {
		return drawer;
	}

	public void setDrawer(IDrawer drawer) {
		this.drawer = drawer;
	}

	@Override
	public void Render() {
		
		
		this.corner.setX(this.converter.getCorner().getX());
		this.corner.setY(this.converter.getCorner().getY());
		this.scale = this.converter.getScaleX();
		this.largeur = this.converter.getLargeur();
		this.hauteur = this.converter.getHauteur();
		
		this.clean();
		this.drawTapis();
		this.drawLunette();
		
		
		
		this.strategy.show();
		
	}
	
	
	
	

	
	private void drawLunette(){
		this.drawer.drawRect(Color.black, 
				(int)Math.round(this.lunette.getX()), (int)Math.round(this.lunette.getY()), 
				(int)Math.round(this.lunette.getLargeur()), (int)Math.round(this.lunette.getHauteur()),1.0f);
	
		double sx = this.lunette.getLargeur() / this.lunette.getTapis().getLargeur();
		
		double xi = this.corner.getX() * sx;
		xi += this.lunette.getTapis().getLargeur() * sx / 2.0;
		xi += this.lunette.getX();
		double yi = -this.corner.getY() * sx;
		yi += this.lunette.getTapis().getHauteur() * sx / 2.0;
		yi += this.lunette.getY();
		double l = this.largeur * sx;
		double h = this.hauteur * sx;
		
		this.drawer.fillRect(Color.yellow, 
				(int)Math.round(xi), (int)Math.round(yi), 
				(int)Math.round(l), (int)Math.round(h),0.2f);
		
		this.drawer.drawRect(Color.black, 
				(int)Math.round(xi), (int)Math.round(yi), 
				(int)Math.round(l), (int)Math.round(h),1.0f);
	}
	
	
	public void drawTapis(){
		
		Rect r = new Rect(this.corner.getX(), this.corner.getY(), 
				this.largeur, this.hauteur);
		
		// filtrage des pi�ce dans la zone.
		IRect rect = new Rect(
				this.corner.getX(), this.corner.getY(),
				this.largeur, this.hauteur);
		List<Piece> pieces = this.tapis.chercherPiece(rect);
		Collections.sort(pieces);
		
		for(Piece piece : pieces){
			if(piece.getRect().isIn(r)){
				PieceBufferOperation pbo = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getElementDeferred(piece, this);
				if(pbo != null) this.drawPiece(pbo);	
			}// if in
		}
	}
	
	
	private void drawPiece(PieceBufferOperation pbo){
		Point p = new Point(
				pbo.getPiece().getCentre().getX(),
				pbo.getPiece().getCentre().getY());

		// conversion en dur plutot qu'avec le converter à cause du threading
		double xi = p.getX()  - corner.getX();
		xi *= this.scale;
		double yi = corner.getY() - p.getY();
		yi *= this.scale;
		
		p.setX(xi);
		p.setY(yi);
		
		
		
		double x = p.getX();
		x -= pbo.getPiece().getLargeur() / 2.0 * this.scale;
		
		double y = p.getY();
		y -= pbo.getPiece().getHauteur() / 2.0 * this.scale;
	
		this.drawer.drawImage(pbo.getImage(),
				x,  y, 
				p.getX() , p.getY(), -pbo.getPiece().getAngle(), 
				this.scale, this.scale, 
				1.0f);
	}
	
	private void clean(){
		double scalex = tapis.getLargeur() / (double)background.getWidth(null);
		double scaley = tapis.getHauteur() / (double)background.getHeight(null);
		
		double x = -this.tapis.getLargeur() / 2.0;
		x -= corner.getX();
		x *= scale;
		double y = -this.tapis.getHauteur() / 2.0;
		y += corner.getY();
		y *= scale;
		

		
		this.drawer.drawImage(this.background, 
				x,y, 
				0, 0, 0, 
				scalex*scale, scaley*scale, 1.0f);
	}
	
	
	public void setBackground(Image background) {
		this.background = background;
	}

	@Override
	public void update(Observable obs, Object arg) {
		if(arg instanceof PieceBufferOperation){
			this.drawPiece((PieceBufferOperation) arg);
		}
		
	}
	
	

}
