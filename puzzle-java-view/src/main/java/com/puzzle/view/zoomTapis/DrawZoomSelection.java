package com.puzzle.view.zoomTapis;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import com.puzzle.view.drawer.DrawSelection;
import com.puzzle.view.drawer.DrawSelectionParam;
import com.puzzle.view.drawer.DrawerDecorator;
import com.puzzle.view.tool.JImageBuffer;

public class DrawZoomSelection extends DrawerDecorator{

	private final static Font zoomFont = new Font("Comic Sans MS", Font.PLAIN, 32);
	private JImageBuffer buffer;
	private TapisZoomConverteur converter;
	private double zoomScale;
	
	private Lunette lunette;

	
	public DrawZoomSelection(JImageBuffer buffer,TapisZoomConverteur converter,Lunette lunette) {
		this.decore = new DrawSelection(buffer,converter);
		this.buffer = buffer;
		
		this.converter = converter;
		this.zoomScale = converter.getScaleX();
		this.lunette = lunette;
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
				(int)Math.round(this.lunette.getX()), (int)Math.round(this.lunette.getY()), 
				(int)Math.round(this.lunette.getLargeur()), (int)Math.round(this.lunette.getHauteur()));
	
		double sx = this.lunette.getLargeur() / this.lunette.getTapis().getLargeur();
		
		double xi = this.converter.getCorner().getX() * sx;
		xi += this.lunette.getTapis().getLargeur() * sx / 2.0;
		xi += this.lunette.getX();
		double yi = -this.converter.getCorner().getY() * sx;
		yi += this.lunette.getTapis().getHauteur() * sx / 2.0;
		yi += this.lunette.getY();
		double l = this.converter.getLargeur() * sx;
		double h = this.converter.getHauteur() * sx;
		
		this.buffer.fillRect(Color.white, 
				(int)Math.round(xi), (int)Math.round(yi), 
				(int)Math.round(l), (int)Math.round(h),0.2f);
		this.buffer.drawRect(Color.black, 
				(int)Math.round(xi), (int)Math.round(yi), 
				(int)Math.round(l), (int)Math.round(h));
	
	}
	
	
	public Lunette getLunette() {
		return lunette;
	}

	public void setLunette(Lunette lunette) {
		this.lunette = lunette;
	}

	public void draw(){
		this.decore.draw();
		this.drawZoom();
		this.drawRectZoom();
	}

}
