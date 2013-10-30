package com.puzzle.view.hud;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.puzzle.view.context.PuzzleContext;
import com.puzzle.view.context.PuzzleContext.PuzzleParam;
import com.puzzle.view.core.IDrawer;
import com.puzzle.view.core.Renderer;

public class HudArea implements MouseMotionListener,MouseListener,Renderer{
	private HudShape shape;
	private HudTask task;
	
	private boolean in;
	
	public HudArea(HudShape shape, HudTask task) {
		this.shape = shape;
		this.task = task;
	}



	public boolean isIn(int x,int y){
		return this.shape.isIn(x, y);
	}
	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		this.task.execute();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		in = true;
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		in = false;
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		
	}



	@Override
	public void Render() {
		IDrawer dr = (IDrawer) PuzzleContext.getInstance().get(PuzzleParam.drawer);
		Box b = (Box)this.shape;
		dr.drawRect(Color.black, b.getX(), b.getY(), b.getLargeur(), b.getHauteur(), 1.0f);
		
		if(in)dr.fillRect(Color.blue, b.getX(), b.getY(), b.getLargeur(), b.getHauteur(), 0.2f);
	}
}
