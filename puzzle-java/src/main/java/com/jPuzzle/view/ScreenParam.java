package com.jPuzzle.view;

import com.puzzle.model.ComponentPiece;

public class ScreenParam {
	
	private boolean mainDroiteVide = true;
	private ComponentPiece selection;
	private double angleSelection;
	private int mouseX;
	private int mouseY;
	
	
	
	
	
	public double getAngleSelection() {
		return angleSelection;
	}
	public void setAngleSelection(double angleSelection) {
		this.angleSelection = angleSelection;
	}
	public boolean isMainDroiteVide() {
		return mainDroiteVide;
	}
	public void setMainDroiteVide(boolean mainDroiteVide) {
		this.mainDroiteVide = mainDroiteVide;
	}
	public ComponentPiece getSelection() {
		return selection;
	}
	public void setSelection(ComponentPiece selection) {
		this.selection = selection;
	}
	public int getMouseX() {
		return mouseX;
	}
	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}
	public int getMouseY() {
		return mouseY;
	}
	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}
}
