package com.puzzle.view2.widget;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import com.puzzle.model.CompositePiece;
import com.puzzle.model.Point;
import com.puzzle.view2.DrawOperationAware;
import com.puzzle.view2.controller.ControllerAdaptater;
import com.puzzle.view2.controller.Converter;
import com.puzzle.view2.image.IDrawOperation;
import com.puzzle.view2.image.IDrawable;
import com.puzzle.view2.image.ImageProvider;
import com.puzzle.view2.layer.BackgroundLayer;
import com.puzzle.view2.layer.Vue;




public class MiniMap extends ControllerAdaptater implements IDrawable,DrawOperationAware,Widget{
	
	private int x;
	private int y;
	private int largeur;
	private int hauteur;
	
	private double scale;
	private BackgroundLayer background;
	private Image backgroundImage;
	
	private IDrawOperation op;
	
	private boolean over;
	private float alphaZoom = 0.0f;
	private Color zoomColor = Color.black;
	private Converter converter;
	

	public MiniMap(BackgroundLayer background, Image backgroundImage,int x, int y,double scale) {
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.background = background;
		this.backgroundImage = backgroundImage;
		
		this.largeur = (int) Math.round(background.getLargeurTapis() * scale);
		this.hauteur = (int) Math.round(background.getHauteurTapis() * scale);
		
		this.rectangle = new Rectangle(x,y,largeur,hauteur);
		this.converter = new Converter(background);
	}

	

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
		
		
		int xi = x;
		xi += (int)Math.round((background.getLargeurTapis() / 2.0 + vue.getX()) * scale); 
		int yi = y;
		yi += (int)Math.round((background.getHauteurTapis() / 2.0 - vue.getY()) * scale);
		int l = (int) Math.round(vue.getLargeur() * scale);
		int h = (int) Math.round(vue.getHauteur() * scale);
		
		
		//
		if(over){
			this.op.drawImage(this.backgroundImage, x, y, 0, 0, 0, 
					(double)largeur/this.backgroundImage.getWidth(null),
					(double)hauteur/this.backgroundImage.getHeight(null), 
					1.0f);
			
			for(CompositePiece cmp : ImageProvider.getInstance().getDrawableComposite()){
				drawComposite(vue, cmp);
			}
			
			
			
			
			this.op.fillRect(zoomColor, x, y, xi - x, hauteur, alphaZoom);
			this.op.fillRect(zoomColor, xi + l, y, largeur - xi - l + x, hauteur, alphaZoom);
			this.op.fillRect(zoomColor, xi, y, l, yi - y, alphaZoom);
			this.op.fillRect(zoomColor, xi, yi+h, l, hauteur - yi - h + y, alphaZoom);
		}
		//
		
		this.op.drawRect(Color.black, x, y, largeur, hauteur);
		this.op.drawRect(Color.black, xi, yi, l, h);
		
		//
		
		
	}

	private void drawComposite(Vue vue,CompositePiece cmp){
		Image img = ImageProvider.getInstance().getImage(cmp);
		
		
		if(img == null)img = ImageProvider.getInstance().getImageWaiting();
		
		double scale = this.largeur / background.getLargeurTapis();
		double scaleImage = scale * cmp.getLargeur() / (double)img.getWidth(null);

		double mx = (double)cmp.getLargeur() / 2.0 * scale;
		double my = (double)cmp.getHauteur() / 2.0 * scale;
		
		double xi = cmp.getCentre().getX() + background.getLargeurTapis() / 2.0;
		xi *= scale;
		xi += x;
		double yi = background.getHauteurTapis() / 2.0 - cmp.getCentre().getY();
		yi *= scale;
		yi += y;
		double cx = xi;
		double cy = yi;
		xi -= mx;
		yi -= my;
		
		this.op.drawImage(img, xi, yi, cx, cy, -cmp.getAngle(), scaleImage, 1.0f);
	}

	

	
	
	/*
	 * 
	 */
	
	


	
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation()>0){
			background.zoomOut();
		}else{
			background.zoomIn();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		over = true;
		alphaZoom = 0.4f;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		over = false;
		alphaZoom = 0.0f;
	}



	@Override
	public void mouseDragged(MouseEvent e) {
		double x = e.getX() - this.x;
		x /= scale;
		x -= background.getLargeurTapis() /2.0;
		x -= background.getVue().getLargeur() / 2.0;
		double y = e.getY() - this.y;
		y /= scale;
		y = background.getHauteurTapis() / 2.0 - y;
		y += background.getVue().getHauteur() / 2.0;
	
		
		this.background.moveTo(x, y);
	}
	
	
	/*
	 * 
	 */
	
	public int getX() {
		return x;
	}

	



	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	
	
	
	
	
	
	
	
	
}
