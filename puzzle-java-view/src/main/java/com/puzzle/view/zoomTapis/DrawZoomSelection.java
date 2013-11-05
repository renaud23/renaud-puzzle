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
	


	public DrawZoomSelection(){
		
	}
	
	public DrawZoomSelection(JImageBuffer buffer,TapisZoomConverteur converter) {
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
