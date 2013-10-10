package com.puzzle.view;


import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.List;

import javax.swing.JPanel;

import com.puzzle.view.tool.ImageBuffer;

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
	
	public void cleanListener(){
		for(MouseListener l : this.getMouseListeners()){
			this.removeMouseListener(l);
		}
		for(MouseMotionListener l : this.getMouseMotionListeners()){
			this.removeMouseMotionListener(l);
		}
		for(MouseWheelListener l : this.getMouseWheelListeners()){
			this.removeMouseWheelListener(l);
		}
		for(KeyListener l : this.getKeyListeners()){
			this.removeKeyListener(l);
		}
	}
	
	
	public int getLargeur(){
		return this.backBuffer.get(0).getLargeur();
	}
	
	public int getHauteur(){
		return this.backBuffer.get(0).getHauteur();
	}
	
}
