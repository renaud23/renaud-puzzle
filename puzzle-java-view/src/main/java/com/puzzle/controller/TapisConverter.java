package com.puzzle.controller;

import com.puzzle.model.Point;

public interface TapisConverter {

	public void convertScreenToModel(Point p);
	public void convertModelToScreen(Point p);
	public double getScaleX();
	public double getScaleY();
	public void update();
}
