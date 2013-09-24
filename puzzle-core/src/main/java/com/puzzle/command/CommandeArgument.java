package com.puzzle.command;

public interface CommandeArgument<U> extends Commande{
	public void setArgument(U arg);
}
