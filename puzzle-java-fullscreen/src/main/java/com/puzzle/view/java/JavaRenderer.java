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
import com.puzzle.model.CompositePiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view.context.PuzzleContext;
import com.puzzle.view.context.PuzzleContext.PuzzleParam;
import com.puzzle.view.core.IDrawer;
import com.puzzle.view.core.Lunette;
import com.puzzle.view.core.Renderer;
import com.puzzle.view.core.TapisConverteur;
import com.puzzle.view.core.image.CompositeBufferOperation;
import com.puzzle.view.core.image.CompositeImageProvider;
import com.puzzle.view.core.image.ImageMemoryManager;
import com.puzzle.view.core.image.PieceBufferOperation;
import com.puzzle.view.core.image.PieceLoader;
import com.puzzle.view.hud.HudRenderer;
import com.renaud.manager.IRect;
import com.renaud.manager.Rect;


public class JavaRenderer implements Renderer,Observer,MouseMotionListener{
	
	private IDrawer drawer;
	private Tapis tapis;
	private Image background;
	private TapisConverteur converter;


	
	private boolean isSelection;
	
	private Point mousePosition = new Point();
	private Point corner = new Point();
	private double scaleConverter;
	private double largeurConverter;
	private double hauteurConverter;
	private final List<Piece> candidats = Collections.synchronizedList(new ArrayList<Piece>());
	
	private HudRenderer hudRenderer;

	public JavaRenderer( Tapis tapis, TapisConverteur converter,
			IDrawer drawer, Image background) {
		this.drawer = drawer;
		this.tapis = tapis;
		this.converter = converter;

		this.background = background;
		this.hudRenderer = new HudRenderer(tapis, drawer, converter);
		this.isSelection = false;
		
	
		
		
		PieceLoader.getInstance().addObserver(this);
		this.tapis.addObserver(this);
	}

	

	@Override
	public void Render() {
		this.corner.setX(this.converter.getCorner().getX());
		this.corner.setY(this.converter.getCorner().getY());
		this.scaleConverter = this.converter.getScaleX();
		this.largeurConverter = this.converter.getLargeur();
		this.hauteurConverter = this.converter.getHauteur();
		
		this.clean();
		this.drawTapis();
		this.drawClips();
		if(this.isSelection) this.drawSelection();
		if(this.hudRenderer != null) this.hudRenderer.Render();
		
	}
	
	
	public void drawTapis(){
		
		// dessin tapis
		List<CompositePiece> alreadyDraw = new ArrayList<CompositePiece>();
		
		// filtrage des pi�ce dans la zone.
		IRect rect = new Rect(corner.getX(), corner.getY(), largeurConverter, hauteurConverter);
		List<Piece> pieces = this.tapis.chercherPiece(rect);
		Collections.sort(pieces);
	
		for(Piece piece : pieces){
			if(piece.getComposite() == null){
				if(piece.getRect().isIn(rect)){
					PieceBufferOperation pbo = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getElementDeferred(piece,this);
					if(pbo != null) this.drawPiece(pbo);	
				}// if in
			}else{
				if(!alreadyDraw.contains(piece.getComposite())){
					alreadyDraw.add(piece.getComposite());
					CompositePiece cmp = piece.getComposite();
					
					if(cmp.getRect().isIn(rect)){
						CompositeBufferOperation sb =  CompositeImageProvider.getInstance().getElementDeferred(cmp, this);
						if(sb != null) this.drawComposite(sb);
					}// if isIn	
				}// if already
			}// else
			
		}// for
	}
	
	
	private void drawPiece(PieceBufferOperation pbo){
		Point p = new Point(
				pbo.getPiece().getCentre().getX(),
				pbo.getPiece().getCentre().getY());

		// conversion en dur plutot qu'avec le converter à cause du threading
		double xi = p.getX()  - corner.getX();
		xi *= this.scaleConverter;
		double yi = corner.getY() - p.getY();
		yi *= this.scaleConverter;
		
		p.setX(xi);
		p.setY(yi);
		
		double x = p.getX();
		x -= pbo.getPiece().getLargeur() / 2.0 * this.scaleConverter;
		
		double y = p.getY();
		y -= pbo.getPiece().getHauteur() / 2.0 * this.scaleConverter;
	
		this.drawer.drawImage(pbo.getImage(),
				x,  y, 
				p.getX() , p.getY(), -pbo.getPiece().getAngle(), 
				this.scaleConverter, this.scaleConverter, 
				1.0f);
	}
	
	private void drawComposite(CompositeBufferOperation sb){
		IDrawer b = sb.getBuffer();
		double scale = sb.getScale();
		CompositePiece cmp = sb.getComposite();
		Point p = new Point(cmp.getCentre().getX(),cmp.getCentre().getY());
		
		double xi = p.getX()  - corner.getX();
		xi *= this.scaleConverter;
		double yi = this.corner.getY() - p.getY();
		yi *= this.scaleConverter;
		
		p.setX(xi);
		p.setY(yi);
		
		double x = p.getX();
		x -= b.getLargeur() / 2.0 * this.scaleConverter / scale;
		
		double y = p.getY();
		y -= b.getHauteur() / 2.0 * this.scaleConverter / scale;
		
		this.drawer.drawImage(((JavaBuffer)b).getImage(),
				x,  y, 
				p.getX() , p.getY(), -cmp.getAngle(), 
				this.scaleConverter/scale, this.scaleConverter/scale, 
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
				this.scaleConverter, this.scaleConverter, 1.0f);
		}else if(compomnent instanceof CompositePiece){
			CompositeBufferOperation sb = CompositeImageProvider.getInstance().getElement((CompositePiece)compomnent);
			Image img = ((JavaBuffer)sb.getBuffer()).getImage();
			double scale = sb.getScale();
			double cx = (double)img.getWidth(null) / 2.0 * this.scaleConverter/scale;
			double cy = (double)img.getHeight(null) / 2.0 * this.scaleConverter/scale;
			
			double x = this.mousePosition.getX();
			double y = this.mousePosition.getY();
			x += ancre.getX() * this.scaleConverter;
			y -= ancre.getY() * this.scaleConverter;
			x -= cx;
			y -= cy;
			
			this.drawer.drawImage(
				img, 
				x,y, 
				this.mousePosition.getX(), this.mousePosition.getY(), -compomnent.getAngle(), 
				this.scaleConverter/scale, this.scaleConverter/scale, 1.0f);
		}
	}
	
	private void drawClips(){
		synchronized (this.candidats) {
			for(Piece piece : this.candidats){
				PieceBufferOperation pbo = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getElement(piece);
				Point p = new Point(piece.getCentre().getX(),piece.getCentre().getY());
				this.converter.convertModelToScreen(p);
				
				double xi = p.getX();
				xi -= pbo.getImage().getWidth(null) / 2.0 * this.converter.getScaleX();
				
				double yi = p.getY();
				yi -= pbo.getImage().getHeight(null) / 2.0 * this.converter.getScaleY();
	
				this.drawer.drawImageMask(pbo.getImage(),
						xi,  yi, 
						p.getX() , p.getY(), -piece.getAngle(), 
						this.converter.getScaleX(), this.converter.getScaleY(), 
						Color.yellow);
			}
		}
	}
	
	private void clean(){
		double scalex = tapis.getLargeur() / (double)background.getWidth(null);
		double scaley = tapis.getHauteur() / (double)background.getHeight(null);
		
		double x = -this.tapis.getLargeur() / 2.0;
		x -= corner.getX();
		x *= scaleConverter;
		double y = -this.tapis.getHauteur() / 2.0;
		y += corner.getY();
		y *= scaleConverter;

		this.drawer.drawImage(this.background, 
				x,y, 
				0, 0, 0, 
				scalex*scaleConverter, scaley*scaleConverter, 1.0f);
	}
	
	
	public void setBackground(Image background) {
		this.background = background;
	}

	@Override
	public void update(Observable obs, Object arg) {
		if(arg instanceof PieceBufferOperation){
//			this.drawPiece((PieceBufferOperation) arg);
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
		synchronized (this) {
			this.candidats.clear();
		}
	}
	
	public void addCandidats(Collection<Piece> pieces){
		synchronized (this) {
			this.candidats.addAll(pieces);
		}
	}
	
	public void addCandidats(Piece piece){
		this.candidats.add(piece);
	}
	public HudRenderer getHudRenderer() {
		return hudRenderer;
	}
	public void setHudRenderer(HudRenderer hudRenderer) {
		this.hudRenderer = hudRenderer;
	}
	
	
}
