package com.puzzle.io;

public class PuzzleIOException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PuzzleIOException(String msg){
		super(msg);
	}
	
	public PuzzleIOException(String msg,Throwable e){
		super(msg,e);
	}

}
