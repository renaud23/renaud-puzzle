package com.puzzle.view.java;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.puzzle.model.ComponentPiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view.core.IDrawer;
import com.puzzle.view.core.Lunette;
import com.puzzle.view.core.Renderer;
import com.puzzle.view.core.TapisConverteur;
import com.puzzle.view.core.image.ImageMemoryManager;
import com.puzzle.view.core.image.PieceBufferOperation;
import com.puzzle.view.core.image.PieceLoader;
import com.renaud.manager.IRect;
import com.renaud.manager.Rect;

public class JavaRenderer implements Renderer,Observer,MouseMotionListener{
	
	private IDrawer drawer;
	private Tapis tapis;
	private Image background;
	private TapisConverteur converter;
	private BufferStrategy strategy;
	private Lunette lunette;
	
	private boolean isSelection;
	
	private Point mousePosition = new Point();
	private Point corner = new Point();
	private double scale;
	private double largeur;
	private double hauteur;
	private List<Piece> candidats = new ArrayList<Piece>();
	


	public JavaRenderer( Tapis tapis, TapisConverteur converter,
			IDrawer drawer, BufferStrategy strategy,Image background) {
		this.drawer = drawer;
		this.tapis = tapis;
		this.converter = converter;
		this.strategy = strategy;
		this.background = background;
		
		this.isSelection = false;
		
		this.lunette = new Lunette();
		this.lunette.setTapis(tapis);
		this.lunette.setScale(0.2);
		this.lunette.setLargeur(this.drawer.getLargeur() * this.lunette.getScale());
		this.lunette.setHauteur(this.lunette.getLargeur() * tapis.getHauteur() / tapis.getLargeur());
		this.lunette.setX(10.0);
		this.lunette.setY(10.0);
		
		PieceLoader.getInstance().addObserver(this);
		this.tapis.addObserver(this);
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
		if(this.isSelection) this.drawSelection();
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
	
	
	private void drawSelection(){
		ComponentPiece compomnent = MainDroite.getInstance().getPiece();
		Point ancre = MainDroite.getInstance().getAncre();
		
		if(compomnent instanceof Piece){
			Piece piece = (Piece) compomnent;
			PieceBufferOperation pbo = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getElement(piece);
			
			double cx = (double)pbo.getImage().getWidth(null) / 2.0 * this.converter.getScaleX();
			double cy = (double)pbo.getImage().getHeight(null) / 2.0 * this.converter.getScaleY();
			
			double x = this.mousePosition.getX();
			double y = this.mousePosition.getY();
			x += ancre.getX() * this.converter.getScaleX();
			y -= ancre.getY() * this.converter.getScaleY();
			x -= cx;
			y -= cy;
			
			this.drawer.drawImage(
					pbo.getImage(), 
				x,y, 
				this.mousePosition.getX(), this.mousePosition.getY(), -piece.getAngle(), 
				this.converter.getScaleX(), this.converter.getScaleY(), 1.0f);
		}
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
		}else if(arg instanceof State){
			State st = (State) arg;
			if(st == State.MainDroitePleine){
				this.isSelection = true;
			}else if(st == State.MainDroiteVide){
				this.isSelection = false;
			}
		}
		
	}
	
	public IDrawer getDrawer() {
		return drawer;
	}

	
	
	/*
	 * MouseMotionListener
	 */
	public void setDrawer(IDrawer drawer) {
		this.drawer = drawer;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		this.mousePosition.setX(e.getX());
		this.mousePosition.setY(e.getY());
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		this.mousePosition.setX(e.getX());
		this.mousePosition.setY(e.getY());
	}



	public void clearCandidats(){
		this.candidats.clear();
	}
	
	public void addCandidats(Collection<Piece> pieces){
		this.candidats.addAll(pieces);
	}
	
	public void addCandidats(Piece piece){
		this.candidats.add(piece);
	}
	

}
