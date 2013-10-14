package com.puzzle.view.drawer;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;

import com.puzzle.view.controller.TapisConverter;
import com.puzzle.view.tool.ImageBuffer;

public class DrawZoomSelection extends DrawerDecorator{

	private final static Font zoomFont = new Font("Comic Sans MS", Font.PLAIN, 32);
	private ImageBuffer buffer;
	private TapisConverter converter;
	private double zoomScale;
	
	public DrawZoomSelection(ImageBuffer buffer,TapisConverter converter) {
		this.decore = new DrawSelection(buffer,converter);
		this.buffer = buffer;
		this.converter = converter;
		this.zoomScale = converter.getScaleX();
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
	
	public void draw(){
		this.decore.draw();
		this.drawZoom();
	}

}
