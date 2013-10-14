package com.puzzle.view.drawer;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;

import com.puzzle.model.Tapis;
import com.puzzle.view.tool.ImageBuffer;
import com.puzzle.view.zoomTapis.TapisZoomConverteur;

public class DrawZoomSelection extends DrawerDecorator{

	private final static Font zoomFont = new Font("Comic Sans MS", Font.PLAIN, 32);
	private ImageBuffer buffer;
	private TapisZoomConverteur converter;
	private double zoomScale;
	private Tapis tapis;
	
	private double x;
	private double y;
	private double largeurTapis;
	private double hauteurTapis;
	private double rectScale;
	
	public DrawZoomSelection(ImageBuffer buffer,TapisZoomConverteur converter, Tapis tapis) {
		this.decore = new DrawSelection(buffer,converter);
		this.buffer = buffer;
		this.converter = converter;
		this.zoomScale = converter.getScaleX();
		this.rectScale = 0.2;
		
		this.largeurTapis = buffer.getLargeur() * this.rectScale;
		this.hauteurTapis = this.largeurTapis * tapis.getHauteur() / tapis.getLargeur();
		this.x = this.buffer.getLargeur() - this.largeurTapis - 10.0;
		this.y = 10.0;
		this.tapis = tapis;
//		this.largeur = ((TapisZoomConverteur)converter).getLargeur();
	}

	@Override
	public void setParam(DrawSelectionParam param) {
		this.decore.setParam(param);
	}

	
	public void drawZoom(){
		DecimalFormat df = new DecimalFormat("0.00");
		String format = "x0";
		if(this.zoomScale != 0) format = "x"+df.format(this.zoomScale);
		this.buffer.drawString(format,10,30,zoomFont,Color.white);
		this.buffer.drawString(format,9,29,zoomFont,Color.gray);
	}

	public double getZoomScale() {
		return zoomScale;
	}

	public void setZoomScale(double zoomScale) {
		this.zoomScale = zoomScale;
	}
	
	public void drawRectZoom(){
		this.buffer.drawRect(Color.black, 
				(int)Math.round(this.x), (int)Math.round(this.y), 
				(int)Math.round(this.largeurTapis), (int)Math.round(this.hauteurTapis));
	
		double sx = this.largeurTapis / tapis.getLargeur();
		
		double xi = this.converter.getCorner().getX() * sx;
		xi += tapis.getLargeur() * sx / 2.0;
		xi += this.x;
		double yi = -this.converter.getCorner().getY() * sx;
		yi += tapis.getHauteur() * sx / 2.0;
		yi += this.y;
		double l = this.converter.getLargeur() * sx;
		double h = this.converter.getHauteur() * sx;
		
		this.buffer.drawRect(Color.black, 
				(int)Math.round(xi), (int)Math.round(yi), 
				(int)Math.round(l), (int)Math.round(h));
	
	}
	
	public void draw(){
		this.decore.draw();
		this.drawZoom();
		this.drawRectZoom();
	}

}
