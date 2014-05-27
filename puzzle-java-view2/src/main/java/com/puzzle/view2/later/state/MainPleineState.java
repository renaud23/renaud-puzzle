package com.puzzle.view2.later.state;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import com.puzzle.command.CommandeArgument;
import com.puzzle.command.PoserMainDroite;
import com.puzzle.command.tournerMainDroite;
import com.puzzle.model.ComponentPiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.Tapis;
import com.puzzle.view2.DrawOperationAware;
import com.puzzle.view2.controller.ControllerAdaptater;
import com.puzzle.view2.controller.Converter;
import com.puzzle.view2.image.IDrawOperation;
import com.puzzle.view2.image.IDrawable;
import com.puzzle.view2.image.ImageProvider;
import com.puzzle.view2.layer.BackgroundLayer;

public class MainPleineState extends ControllerAdaptater implements IState,IDrawable,DrawOperationAware{
	
	private Tapis tapis;
	private BackgroundLayer bckLayer;
	private ComponentPiece selection;
	private Point ancre;

	private IDrawOperation op;
	
	private int mouseX;
	private int mouseY;
	
	private Converter converter;
	
	private boolean rightButtonDown;
	
	
	public MainPleineState(Tapis tapis,BackgroundLayer bckLayer, ComponentPiece selection, Point ancre,int x,int y) {
		this.tapis = tapis;
		this.bckLayer = bckLayer;
		this.selection = selection;
		this.ancre = ancre;
		this.mouseX = x;
		this.mouseY = y;
		
		this.converter = new Converter(bckLayer);
	}
	
	
	
	@Override
	public void mouseMoved(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
	}


	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		boolean up = e.getWheelRotation() < 0;
		
		CommandeArgument<Boolean> cmd = new tournerMainDroite();
		cmd.setArgument(!up);
		cmd.execute();

	}


	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			Point p = this.converter.screenToModel(e.getX(), e.getY());
			
			CommandeArgument<Point> cmd = new PoserMainDroite(tapis);
			cmd.setArgument(p);
			cmd.execute();
		}else if(e.getButton() == MouseEvent.BUTTON3){
			this.rightButtonDown = true;
		}
	}
	
	
	

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON3){
			this.rightButtonDown = false;
		}
	}



	@Override
	public void mouseDragged(MouseEvent e) {
		if(this.rightButtonDown){
			int vx = this.mouseX - e.getX();
			int vy = this.mouseY - e.getY();
			double vex = vx / bckLayer.getScale();
			double vey = vy / bckLayer.getScale();
			double ex = bckLayer.getVue().getX() + Math.round(vex);
			double ey = bckLayer.getVue().getY() - Math.round(vey);
			
			bckLayer.moveTo(Math.round(ex), Math.round(ey));
		
			this.mouseY = e.getY();
			this.mouseX = e.getX();
		}
	}



	public boolean isChange() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setChange() {
		// TODO Auto-generated method stub
		
	}

	public void draw() {
		if(this.selection instanceof Piece){
			this.drawPiece();
		}else{
			
		}
	}

	
	
	

	/*
	 * draw
	 */
	

	private void drawPiece(){
		Piece p = (Piece)this.selection;
		Image img = ImageProvider.getInstance().getImage(p);
		
		if(img != null){
			double scale = bckLayer.getLargeurScreen() / bckLayer.getVue().getLargeur();
			
			double cx = (double)img.getWidth(null) / 2.0 * scale;
			double cy = (double)img.getHeight(null) / 2.0 * scale;
			
			double x = this.mouseX;
			double y = this.mouseY;
			x += this.ancre.getX() * scale;
			y -= this.ancre.getY() * scale;
			x -= cx;
			y -= cy;
			
			this.op.drawImage(img, x, y, this.mouseX, this.mouseY, -p.getAngle(), scale, 1.0f);
		}
	}



	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}
	
	
}
