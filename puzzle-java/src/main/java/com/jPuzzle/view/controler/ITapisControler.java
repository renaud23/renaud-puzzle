package com.jPuzzle.view.controler;

import com.puzzle.model.Point;

public interface ITapisControler extends IControler {
	public void attraperMainDroite(Point position);
	public void poserMainDroite(Point position);
	public void tournerMainDroite(double angle);
}
