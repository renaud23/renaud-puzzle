package com.puzzle.view2.controller;

import com.puzzle.model.Point;
import com.puzzle.view2.layer.BackgroundLayer;
import com.puzzle.view2.layer.Vue;

public class Converter {
	private BackgroundLayer layer;
	
	private double ml;
	private double mh;
	
	public Converter(BackgroundLayer layer) {
		this.layer = layer;
		this.ml = layer.getLargeurTapis() / 2.0;
		this.mh = layer.getHauteurTapis() / 2.0;
	}


	public Point screenToModel(int x,int y){
		Point point = new Point();
		double scale = layer.getLargeurScreen();
		scale /= layer.getVue().getLargeur();
		
		double cx = x;
		cx /= scale;
		cx += this.layer.getVue().getX();
		double cy = layer.getVue().getY();
		cy -= y / scale;
		
		point.setX(cx);
		point.setY(cy);
		
		return point;
	}
	
	
	public Point modelToScreen(Vue vue,double x,double y){
		double scale = layer.getLargeurScreen();
		scale /= layer.getVue().getLargeur();
		
		double xi = x;
		xi -= vue.getX();
		xi *= scale;
		
		double yi = vue.getY();
		yi -= y;
		yi *= scale;
		
		Point p = new Point(xi,yi);
		
		return p;
	}
}
