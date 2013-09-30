package com.puzzle.model;

import com.renaud.manager.IRectable;

public interface ComponentPiece extends IRectable{

	public int getZIndex();
	public void setZIndex(int index);
	public Point getCentre();
	public double getAngle();
	public void updateRect();
	public void tournerGauche();
	public void tournerDroite();
	public boolean verifierClips(Piece piece);
	public double getLargeur();
	public double getHauteur();
	public void poser(Tapis tapis);

}
