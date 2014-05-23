package com.puzzle.view2.tools;

public class ImageLoadException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3195398643872455341L;
	
	
	public ImageLoadException(String msg){
		super(msg);
	}
	
	public ImageLoadException(String msg,Throwable e){
		super(msg,e);
	}

}
