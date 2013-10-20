package com.puzzle.view.tool;

public class ScaleBuffer{
	/**
	 * r�duction apport�e � l'image.
	 */
	private double scale;
	private JImageBuffer buffer;
	
	
	public ScaleBuffer(double scale, JImageBuffer buffer) {
		this.scale = scale;
		this.buffer = buffer;
	}
	public double getScale() {
		return scale;
	}
	public JImageBuffer getBuffer() {
		return buffer;
	}
	
	
}