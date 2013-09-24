package com.puzzle.model;

import com.renaud.manager.IRect;

public class RectCompositePiece implements MyRect{
	
	private CompositePiece composite;

	@Override
	public boolean isIn(double x, double y, double largeur, double hauteur) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isIn(IRect r) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getLargeur() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getHauteur() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
