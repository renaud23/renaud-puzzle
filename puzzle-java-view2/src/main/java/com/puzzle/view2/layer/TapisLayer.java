package com.puzzle.view2.layer;


import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import sun.awt.im.CompositionArea;

import com.puzzle.model.CompositePiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view2.DrawOperationAware;
import com.puzzle.view2.controller.ControllerAdaptater;
import com.puzzle.view2.controller.Converter;
import com.puzzle.view2.controller.IMousePositionAware;
import com.puzzle.view2.image.IDrawOperation;
import com.puzzle.view2.image.IDrawable;
import com.puzzle.view2.image.ImageProvider;
import com.puzzle.view2.later.state.ClipsState;
import com.puzzle.view2.later.state.IState;
import com.puzzle.view2.later.state.MainPleineState;
import com.puzzle.view2.later.state.MainVideState;
import com.renaud.manager.IRect;
import com.renaud.manager.Rect;

public class TapisLayer extends ControllerAdaptater implements IDrawable,DrawOperationAware,Observer,IMousePositionAware{
	private int largeur;
	private int hauteur;
	
	
	private int mouseX;
	private int mouseY;
	
	private BackgroundLayer bckLayer;

	
	private IState state;
	
	private boolean rightButtonDown;
	private Tapis tapis;
	
	private IDrawOperation op;
	private Converter converter;
	
	
	public TapisLayer(Tapis tapis,BackgroundLayer bckLayeur, int largeur, int hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.bckLayer = bckLayeur;
		this.tapis = tapis;
		
		this.rectangle = new Rectangle(largeur, hauteur);
		this.state = new MainVideState(tapis, bckLayeur);
		
		this.tapis.addObserver(this);
		
		this.converter = new Converter(this.bckLayer);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
		this.state.mousePressed(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		this.state.mouseReleased(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
		this.state.mouseDragged(e);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		this.state.mouseWheelMoved(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
		this.state.mouseMoved(e);
	}
	
	@Override
	public void controlPressed() {
		this.state.controlPressed();
	}

	@Override
	public void controlReleased() {
		this.state.controlReleased();
	}

	@Override
	public void shiftPressed() {
		this.state.shiftPressed();
	}

	@Override
	public void shiftReleased() {
		this.state.shiftReleased();
	}

	
	
	
	

	
	
	@Override
	public void update(Observable o, Object arg) {

		if(o instanceof Tapis){
			if(arg ==  State.MainDroitePleine){
				MainVideState mvs = (MainVideState) this.state;
				this.state = new MainPleineState(this.tapis,this.bckLayer, mvs.getAttrParam().getContenu(),mvs.getAttrParam().getAncre(),this.mouseX,this.mouseY,this);
			}else if(arg ==  State.MainDroiteVide){
				this.state = new MainVideState(this.tapis,this.bckLayer);
			}if(arg == State.gaucheToDroite){
				this.state = new MainPleineState(this.tapis,this.bckLayer, MainDroite.getInstance().getContenu(),new Point(),this.mouseX,this.mouseY,this);
			}else if(arg == State.droiteToGauche){
				this.state = new MainVideState(this.tapis, this.bckLayer);
			}else if(arg == State.clips){
				this.state = new MainVideState(this.tapis, this.bckLayer);
			}
		}else if(arg == TapisLayerEvent.startClips){
			if(this.state instanceof MainPleineState)
				this.state = new ClipsState(this.tapis, this.bckLayer,((MainPleineState)this.state).getIsClipsParam(),this.mouseX,this.mouseY,this);
		}else if(arg == TapisLayerEvent.endClips){
			this.state = new MainVideState(this.tapis,this.bckLayer);
		}

	}




	/*
	 * draw
	 */

	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

	@Override
	public boolean isChange() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setChange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Vue vue) {
		// filtrage des pieces dans la zone.
		IRect rect = new Rect(vue.getX(),vue.getY(),vue.getLargeur(),vue.getHauteur());
		List<Piece> pieces = this.tapis.chercherPiece(rect);
		Collections.sort(pieces);
		
		
		List<CompositePiece> alreadyDraw = new ArrayList<CompositePiece>();
		for(Piece piece : pieces){
			if(piece.getComposite() == null){
				Image img = ImageProvider.getInstance().getImage(piece);
				if(img != null){
					this.drawPiece(vue,piece,img);
				}else{
					this.drawPiece(vue,piece,ImageProvider.getInstance().getImageWaiting());
				}
			}else{
				if(!alreadyDraw.contains(piece.getComposite())){
					this.drawComposite(vue, piece.getComposite());
					alreadyDraw.add(piece.getComposite());
				}
			}
		}
		
		// sélection
		if(this.state instanceof DrawOperationAware) ((DrawOperationAware)this.state).setDrawOperation(this.op);
		if(this.state instanceof IDrawable) ((IDrawable)this.state).draw(vue);
	}
	
	
	
	
	private void drawPiece(Vue vue,Piece p,Image img){
		double scale = bckLayer.getLargeurScreen() / vue.getLargeur();
		
		double cx = p.getCentre().getX();
		cx -= vue.getX();
		cx *= scale;
		
		double cy = p.getCentre().getY();
		cy -= vue.getY() - vue.getHauteur()  ;
		cy = vue.getHauteur()  - cy;
		cy *= scale;
		
		double x = cx;
		x -= p.getLargeur()  / 2.0 * scale;
		double y = cy;
		y -= p.getHauteur()  / 2.0 * scale;
		
		this.op.drawImage(img, x, y, 
				cx, cy, -p.getAngle(), scale, 1.0f);//
		
	}
	
	private void drawComposite(Vue vue,CompositePiece cmp){
		Image img = ImageProvider.getInstance().getImage(cmp);
		Point centre = this.converter.modelToScreen(vue, cmp.getCentre().getX(), cmp.getCentre().getY());
		
		if(img == null)img = ImageProvider.getInstance().getImageWaiting();
		
		double scale = bckLayer.getLargeurScreen() / vue.getLargeur();
		double scaleImage = scale * cmp.getLargeur() / (double)img.getWidth(null);

		double cx = (double)cmp.getLargeur() / 2.0 * scale;
		double cy = (double)cmp.getHauteur() / 2.0 * scale;
		
		double xi = centre.getX();
		double yi = centre.getY();
		xi -= cx;
		yi -= cy;
		
		this.op.drawImage(img, xi, yi, centre.getX(), centre.getY(), -cmp.getAngle(), scaleImage, 1.0f);
	}

	@Override
	public void setMouseX(int x) {
		this.mouseX = x;
		this.state.setMouseX(x);
	}

	@Override
	public void setMouseY(int y) {
		this.mouseY = y;
		this.state.setMouseY(y);
	}



	public enum TapisLayerEvent{
		startClips,endClips;
	}
}
