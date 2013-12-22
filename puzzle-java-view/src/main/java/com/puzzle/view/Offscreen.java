package com.puzzle.view;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.List;

import javax.swing.JPanel;

import com.puzzle.view.tool.JImageBuffer;

public class Offscreen extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7927839937475130643L;
	
	
	private JImageBuffer frontBuffer;
	private List<JImageBuffer> backBuffer;
	
	

	
	
	
	
	public Offscreen(List<JImageBuffer> backBuffer) {
		this.backBuffer = backBuffer;
		this.frontBuffer = new JImageBuffer(Color.red, this.backBuffer.get(0).getLargeur(),  this.backBuffer.get(0).getHauteur());
	}




	protected void paintComponent(Graphics g){
		Graphics gr = this.frontBuffer.getImage().getGraphics();
		
		for(JImageBuffer buff : this.backBuffer){
			gr.drawImage(buff.getImage(), 0, 0, null);
		}
		
		gr.dispose();
		
		g.drawImage(this.frontBuffer.getImage(), 0, 0, null);
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
	
	
	public void setBackBuffer(List<JImageBuffer> backBuffer) {
		this.backBuffer = backBuffer;
	}

	public int getLargeur(){
		return this.backBuffer.get(0).getLargeur();
	}
	
	public int getHauteur(){
		return this.backBuffer.get(0).getHauteur();
	}
	
}
