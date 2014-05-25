package com.puzzle.view2.layer;


import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.puzzle.model.Piece;
import com.puzzle.model.Tapis;
import com.puzzle.view2.DrawOperationAware;
import com.puzzle.view2.controller.ControllerAdaptater;
import com.puzzle.view2.controller.Converter;
import com.puzzle.view2.image.IDrawOperation;
import com.puzzle.view2.image.IDrawable;
import com.puzzle.view2.image.ImageProvider;
import com.puzzle.view2.later.state.IState;
import com.puzzle.view2.later.state.MainVideState;
import com.renaud.manager.IRect;
import com.renaud.manager.Rect;

public class TapisLayer extends ControllerAdaptater implements IDrawable,DrawOperationAware,Observer{
	private int largeur;
	private int hauteur;
	
	private BackgroundLayer bckLayer;
	private int mouseX;
	private int mouseY;
	
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
		this.state.mousePressed(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		this.state.mouseReleased(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		this.state.mouseDragged(e);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		this.state.mouseWheelMoved(e);
	}


	
	
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Tapis){
			Tapis tapis = (Tapis) o;
			
			System.out.println("oooo");
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
	public void draw() {
		// filtrage des pieces dans la zone.
		IRect rect = new Rect(bckLayer.getVue().getX(),bckLayer.getVue().getY(),bckLayer.getVue().getLargeur(),bckLayer.getVue().getHauteur());
		List<Piece> pieces = this.tapis.chercherPiece(rect);
		Collections.sort(pieces);
		
		for(Piece piece : pieces){
			Image img = ImageProvider.getInstance().getImage(piece);
			if(img != null){
				this.drawPiece(piece,img);
			}else{
				System.out.println(piece.getId()+" loading...");
			}
		}
	}
	
	
	
	
	private void drawPiece(Piece p,Image img){
		double scale = bckLayer.getLargeurScreen() / bckLayer.getVue().getLargeur();
		
		double cx = p.getCentre().getX();
		cx -= bckLayer.getVue().getX();
		cx *= scale;
		
		double cy = p.getCentre().getY();
		cy -= bckLayer.getVue().getY() - bckLayer.getVue().getHauteur()  ;
		cy = bckLayer.getVue().getHauteur()  - cy;
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
