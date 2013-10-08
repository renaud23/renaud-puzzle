package com.puzzle.view;


import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;

public class Offscreen extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7927839937475130643L;
	
	private List<ImageBuffer> backBuffer;
	
	
	
	
	public Offscreen(List<ImageBuffer> backBuffer) {
		this.backBuffer = backBuffer;
	}




	protected void paintComponent(Graphics g){
		for(ImageBuffer buff : this.backBuffer){
			g.drawImage(buff.getImage(), 0, 0, null);
		}
		
		g.dispose();
	}
	
	
	public int getLargeur(){
		return this.backBuffer.get(0).getLargeur();
	}
	
	public int getHauteur(){
		return this.backBuffer.get(0).getHauteur();
	}
	
}
