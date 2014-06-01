package com.puzzle.view2.layer;


import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import sun.awt.im.CompositionArea;

import com.puzzle.model.MainDroite;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view2.DrawOperationAware;
import com.puzzle.view2.controller.ControllerAdaptater;
import com.puzzle.view2.controller.Converter;
import com.puzzle.view2.image.IDrawOperation;
import com.puzzle.view2.image.IDrawable;
import com.puzzle.view2.image.ImageProvider;
import com.puzzle.view2.later.state.IState;
import com.puzzle.view2.later.state.MainPleineState;
import com.puzzle.view2.later.state.MainVideState;
import com.renaud.manager.IRect;
import com.renaud.manager.Rect;

public class TapisLayer extends ControllerAdaptater implements IDrawable,DrawOperationAware,Observer{
	private int largeur;
	private int hauteur;
	
	
	private int mouseX;
	private int mouseY;
	
	private BackgroundLayer bckLayer;

	
	private IState state;
	
	private boolean rightButtonDown;
	private Tapis tapis;
	
	private IDrawOperation op;
	
	
	public TapisLayer(Tapis tapis,BackgroundLayer bckLayeur, int largeur, int hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.bckLayer = bckLayeur;
		this.tapis = tapis;
		
		this.rectangle = new Rectangle(largeur, hauteur);
		this.state = new MainVideState(tapis, bckLayeur);
		
		this.tapis.addObserver(this);
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
	public void update(Observable o, Object arg) {
		if(o instanceof Tapis){
			Tapis tapis = (Tapis) o;
			
			if(arg ==  State.MainDroitePleine){
				MainVideState mvs = (MainVideState) this.state;
				this.state = new MainPleineState(this.tapis,this.bckLayer, mvs.getAttrParam().getContenu(),mvs.getAttrParam().getAncre(),this.mouseX,this.mouseY);
			}else if(arg ==  State.MainDroiteVide){
				this.state = new MainVideState(this.tapis,this.bckLayer);
			}if(arg == State.gaucheToDroite){
				this.state = new MainPleineState(this.tapis,this.bckLayer, MainDroite.getInstance().getContenu(),new Point(),this.mouseX,this.mouseY);
			}else if(arg == State.droiteToGauche){
				this.state = new MainVideState(tapis, bckLayer);
			}
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
		
		for(Piece piece : pieces){
			Image img = ImageProvider.getInstance().getImage(piece);
			if(img != null){
				this.drawPiece(vue,piece,img);
			}else{
				
			}
		}
		
		// sélection
		if(this.state instanceof DrawOperationAware) ((DrawOperationAware)this.state).setDrawOperation(op);
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
		
		
//		this.op.drawRect(Color.yellow, (int)x, (int)y, (int)2, (int)2);
//		this.op.drawRect(Color.white, (int)cx, (int)cy, (int)2, (int)2);
		
	}



	
}
