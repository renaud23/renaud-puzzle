package com.puzzle.command;

import com.puzzle.model.Point;

public interface CommandeLocalisee extends Commande{
	public void setPosition(Point position);
}
