package com.puzzle.view.hud;

import java.awt.Color;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.tool.CircleComposite;
import com.puzzle.view.tool.JImageBuffer;
import com.puzzle.view.zoomTapis.TapisZoomConverteur;
import com.puzzle.view.zoomTapis.TapisZoomDrawer;

public class Loupe extends HudArea implements IDrawer,Observer,HudShape{
	
	private JImageBuffer tapisBuffer;
	private HudControler controller;
	private int mouseX;
	private int mouseY;
	private boolean isMainVide;
	private boolean isActive;
	private boolean circulaire = true;
	
	private double scale = 3.0;
	private double rayon;
	
	
	private JImageBuffer tmpBuffer;
	private TapisZoomDrawer tapisDrawer;
	private TapisZoomConverteur converter;
	private TapisZoomConverteur converterScreen;
	
	private Tapis tapis;
	private Image background;
	
	
	public Loupe(HudControler hudController,TapisZoomConverteur converter,Image background,Tapis tapis,JImageBuffer hudBuffer,JImageBuffer tapisBuffer){
		this.tapis = tapis;
		this.background = background;
		this.controller = hudController;
		this.converterScreen = converter;
		this.isMainVide = true;
		this.buffer = hudBuffer;
		this.tapisBuffer = tapisBuffer;
		this.shape = new ShapeTrue();
		tapis.addObserver(this);
		this.task = new HudTask() {
			
			@Override
			public void execute() {
				// Nothing to do
			}
		};
		
		this.initDrawer();
	}
	
	
	private void initDrawer(){
		this.rayon = tapisBuffer.getHauteur() * 0.2;
		this.tmpBuffer = new JImageBuffer(Color.black, (int)(this.rayon * 2.0 ), (int) (this.rayon * 2.0 ));
		this.converter = new TapisZoomConverteur(tapis.getLargeur(), tapis.getHauteur(), this.tmpBuffer.getLargeur(), this.tmpBuffer.getHauteur());
		this.tapisDrawer = new TapisZoomDrawer(background, tapis, this.tmpBuffer, this.converter);
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Fenetre){
			Fenetre f = (Fenetre)o;
			this.buffer = f.getBuffer(1);
			this.tapisBuffer = f.getBuffer(0);
			
			this.initDrawer();
		}else if(o instanceof Tapis){
			if(arg == State.droiteToGauche || arg == State.MainDroiteVide){
				this.isMainVide = true;
			}else if(arg == State.gaucheToDroite || arg == State.MainDroitePleine){
				this.isMainVide = false;
			}
		}
	}

//	@Override
//	public void draw() {
//		if(this.isActive){
//			int sx = (int) (this.mouseX - this.rayon);
//			int sy = (int) (this.mouseY - this.rayon);
//			int dx = (int) (this.mouseX - this.rayon * this.scale);
//			int dy = (int) (this.mouseY - this.rayon * this.scale);
//			int dl = (int) (this.rayon * 2.0 * this.scale);
//			int sl = (int) (this.rayon * 2.0);
//			
//			this.buffer.drawPart(this.tapisBuffer.getImage(), 
//					dx, dy, dx + dl, dy + dl, 
//					sx, sy, sx + sl, sy +sl);
//		}	
//	}
	
	@Override
	public void draw() {
		if(this.isActive){
			double sx = this.mouseX - this.rayon / scale;
			double sy = this.mouseY - this.rayon / scale;
			Point p = new Point(sx,sy);
			this.converterScreen.convertScreenToModel(p);
			
			
			this.converter.getCorner().setX(p.getX());
			this.converter.getCorner().setY(p.getY());
			this.converter.setScale(this.converterScreen.getScaleX() * this.scale);
			
			this.tapisDrawer.draw();
			
			boolean nord = this.mouseY < (this.buffer.getHauteur()/2);
			boolean ouest = this.mouseX < (this.buffer.getLargeur()/2);
			
			
			
			this.buffer.fillRect(Color.black, 0, 0, this.buffer.getLargeur(), this.buffer.getHauteur(),0.5f);
			if(circulaire){
				this.buffer.drawImage(this.tmpBuffer.getImage(), 
						this.mouseX - this.rayon , 
						this.mouseY - this.rayon , 0.0, 0.0, 0.0, 1.0, 1.0f, new CircleComposite((int) this.rayon,nord,ouest));
				this.buffer.drawCircle(this.mouseX, this.mouseY, this.rayon*2, Color.white);
			}else{
				this.buffer.drawImage(this.tmpBuffer.getImage(), 
						this.mouseX - this.rayon , 
						this.mouseY - this.rayon , 0.0, 0.0, 0.0, 1.0, 1.0f);
				this.buffer.drawRect(Color.white, this.mouseX-rayon, this.mouseY-rayon, this.rayon*2, this.rayon*2);
			}
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
			this.mouseX = this.controller.getMouseX();
			this.mouseY = this.controller.getMouseY();
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
