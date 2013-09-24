package com.puzzle.model;

import com.renaud.manager.IRectable;

public interface ComponentPiece extends IRectable{

	public int getZIndex();
	public void setZIndex(int index);
	public Point getCentre();
	public double getAngle();

}
