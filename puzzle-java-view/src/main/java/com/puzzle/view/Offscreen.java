package com.puzzle.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;

public class Offscreen extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7927839937475130643L;
	
	private ImageBuffer frontBuffer;
	private List<ImageBuffer> backBuffer;
	
	
	
	
	public Offscreen(ImageBuffer buffer,List<ImageBuffer> backBuffer) {
		this.frontBuffer = buffer;
		this.backBuffer = backBuffer;
		this.setPreferredSize(new Dimension(this.frontBuffer.getLargeur(), this.frontBuffer.getHauteur()));
		
	}




	protected void paintComponent(Graphics g){
		Graphics g1 = this.frontBuffer.getImage().getGraphics();
		for(ImageBuffer buff : this.backBuffer)
			g1.drawImage(buff.getImage(), 0, 0, null);
		g1.dispose();
		
		g.drawImage(this.frontBuffer.getImage(),0,0,null);
		g.dispose();
	}
	
	
	public int getLargeur(){
		return this.frontBuffer.getLargeur();
	}
	
	public int getHauteur(){
		return this.frontBuffer.getHauteur();
	}
	
}
