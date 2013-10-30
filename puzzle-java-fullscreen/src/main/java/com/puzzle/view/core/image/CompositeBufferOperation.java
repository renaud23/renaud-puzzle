package com.puzzle.view.core.image;

import com.puzzle.model.CompositePiece;
import com.puzzle.view.core.IDrawer;


public class CompositeBufferOperation{
	/**
	 * r�duction apport�e � l'image.
	 */
	private double scale;
	private CompositePiece composite;
	private IDrawer buffer;
	
	
	public CompositeBufferOperation(double scale, IDrawer buffer,CompositePiece composite) {
		this.scale = scale;
		this.buffer = buffer;
		this.composite = composite;
	}
	
	
	
	public CompositePiece getComposite() {
		return composite;
	}
	public double getScale() {
		return scale;
	}
	public IDrawer getBuffer() {
		return buffer;
	}
	
	
}