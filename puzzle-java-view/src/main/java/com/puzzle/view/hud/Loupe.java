package com.puzzle.view.hud;

import java.util.Observable;
import java.util.Observer;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.tool.JImageBuffer;

public class Loupe extends HudArea implements IDrawer,Observer,HudShape{
	
	private JImageBuffer tapisBuffer;
	private HudControler controller;
	private int mouseX;
	private int mouseY;
	private boolean isMainVide;
	private boolean isActive;
	
	private double scale = 3.0;
	private double rayon;
	
	
	public Loupe(HudControler hudController,Tapis tapis,JImageBuffer hudBuffer,JImageBuffer tapisbBuffer){
		this.controller = hudController;
		this.isMainVide = true;
		this.buffer = hudBuffer;
		this.tapisBuffer = tapisbBuffer;
		this.rayon = tapisbBuffer.getHauteur() * 0.05;
		this.shape = new ShapeTrue();
		tapis.addObserver(this);
		this.task = new HudTask() {
			
			@Override
			public void execute() {
				// Nothing to do
			}
		};
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Fenetre){
			Fenetre f = (Fenetre)o;
			this.buffer = f.getBuffer(1);
			this.tapisBuffer = f.getBuffer(0);
			
			this.rayon = this.tapisBuffer.getHauteur() * 0.2;
		}else if(o instanceof Tapis){
			if(arg == State.droiteToGauche || arg == State.MainDroiteVide){
				this.isMainVide = true;
			}else if(arg == State.gaucheToDroite || arg == State.MainDroitePleine){
				this.isMainVide = false;
			}
		}
	}

	@Override
	public void draw() {
		if(this.isActive){
			int sx = (int) (this.mouseX - this.rayon);
			int sy = (int) (this.mouseY - this.rayon);
			int dx = (int) (this.mouseX - this.rayon * this.scale);
			int dy = (int) (this.mouseY - this.rayon * this.scale);
			int dl = (int) (this.rayon * 2.0 * this.scale);
			int sl = (int) (this.rayon * 2.0);
			
			this.buffer.drawPart(this.tapisBuffer.getImage(), 
					dx, dy, dx + dl, dy + dl, 
					sx, sy, sx + sl, sy +sl);
		}	
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scale(double scale) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

	@Override
	public boolean isIn(int x, int y) {
		return this.isActive;
	}

	@Override
	public void keyZPressed() {
		if(this.isMainVide && !this.isActive){
			this.isActive = true;
			this.controller.getDrawer().draw();
		}
	}

	@Override
	public void keyZReleased() {
		this.isActive = false;
		this.controller.getDrawer().draw();
	}

	@Override
	public void mouseMove(int x, int y, boolean isShiftDown) {
		this.mouseX = x;
		this.mouseY = y;
		
		this.controller.getDrawer().draw();
	}



}
