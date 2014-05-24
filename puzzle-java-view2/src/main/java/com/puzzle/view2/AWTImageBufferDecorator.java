package com.puzzle.view2;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import com.puzzle.view2.draw.IDrawOperation;
import com.puzzle.view2.draw.JImageBuffer;




public class AWTImageBufferDecorator extends JPanel implements IDrawOperation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7927839937475130643L;
	
	private IDrawOperation backBuffer;
	
	

	
	
	
	
	public AWTImageBufferDecorator(int largeur,int hauteur) {
		this.backBuffer = new JImageBuffer(Color.red, largeur, hauteur);
		this.backBuffer.clean();
		this.setPreferredSize(new Dimension(largeur, hauteur));
	}

	public void sizing(int largeur,int hauteur) {
		this.backBuffer = new JImageBuffer(Color.red, largeur, hauteur);
	}

	protected void paintComponent(Graphics g){
		g.drawImage(backBuffer.getImage(), 0, 0, null);
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

	
	/*
	 *
	 */
	
	
	
	@Override
	public Image getImage() {
		return this.backBuffer.getImage();
	}

	@Override
	public void drawImage(Image image, double x, double y, double xRotation, double yRotation, double theta, double scale, float alpha) {
		this.backBuffer.drawImage(image, x, y, xRotation, yRotation, theta, scale, alpha);;
	}

	@Override
	public void clean() {
		this.backBuffer.clean();
	}

	@Override
	public void drawPart(Image image, int dx1, int dy1, int dx2, int dy2,int sx1, int sy1, int sx2, int sy2) {
		this.backBuffer.drawPart(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2);
	}

	@Override
	public void drawRect(Color color, int x, int y, int width, int height) {
		this.backBuffer.drawRect(color, x, y, width, height);
	}

	@Override
	public void fillRect(Color color, double x, double y, double width, double height, float alpha) {
		this.backBuffer.fillRect(color, x, y, width, height, alpha);
	}

	@Override
	public void drawImage(Image image, double x, double y, double xRotation,
			double yRotation, double theta, double scaleX, double scaleY,
			float alpha) {
		this.backBuffer.drawImage(image, x, y, xRotation, yRotation, theta, scaleX, scaleY, alpha);
		
	}
	
	
}
