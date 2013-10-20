package com.puzzle.view.tool;

import com.puzzle.model.CompositePiece;

public class ScaleBuffer{
	/**
	 * réduction apportée à l'image.
	 */
	private double scale;
	private CompositePiece composite;
	private JImageBuffer buffer;
	
	
	public ScaleBuffer(double scale, JImageBuffer buffer,CompositePiece composite) {
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
	public JImageBuffer getBuffer() {
		return buffer;
	}
	
	
}