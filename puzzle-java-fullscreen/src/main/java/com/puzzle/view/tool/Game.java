package com.puzzle.view.tool;

import java.awt.Color;
import java.awt.Graphics2D;

import com.puzzle.view.core.IDrawer;
import com.puzzle.view.core.IJouable;

public class Game implements IJouable {
	

	private IDrawer drawer;

	@Override
	public void activate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		this.drawer.drawRect(Color.red, 10, 10, 100, 100);
		
		System.out.println(this.drawer.getLargeur());
		
	}

	

	public IDrawer getDrawer() {
		return drawer;
	}

	public void setDrawer(IDrawer drawer) {
		this.drawer = drawer;
	}
	
	
	
}
