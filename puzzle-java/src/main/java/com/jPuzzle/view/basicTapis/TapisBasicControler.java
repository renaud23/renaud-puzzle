package com.jPuzzle.view.basicTapis;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Observable;
import java.util.Observer;

import com.jPuzzle.view.ScreenParam;
import com.jPuzzle.view.controler.ITapisControler;
import com.jPuzzle.view.drawer.IDrawable;
import com.jPuzzle.view.drawer.Transformation;
import com.puzzle.command.AttrapperMainDroite;
import com.puzzle.command.ClipsParam;
import com.puzzle.command.Clipser;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.IsClipsable;
import com.puzzle.command.PoserMainDroite;
import com.puzzle.command.tournerMainDroite;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Point;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;





public class TapisBasicControler implements ITapisControler,MouseListener,MouseMotionListener,MouseWheelListener,KeyListener,Observer{
	
	
	private boolean shift = false;
	private boolean mainDroiteVide;
	private ScreenParam screenParam;
	private Tapis tapis;
	
	
	private CommandeArgument<Point> attraper;
	private CommandeArgument<Point> poser;
	private CommandeArgument<Double> tourner;
	private CommandeArgument<ClipsParam> iSclips;
	private CommandeArgument<ClipsParam> clips;
	
	
	private IDrawable<ScreenParam> drawable;



	public TapisBasicControler(Tapis tapis){
		this.tapis = tapis;
		this.mainDroiteVide = true;
		this.screenParam = new ScreenParam();
		
		
		// TODO IOC
		this.attraper = new AttrapperMainDroite(tapis);
		this.poser = new PoserMainDroite(tapis);
		this.tourner = new tournerMainDroite();
		this.iSclips = new IsClipsable(tapis);
		this.clips = new Clipser(tapis);
	}
	
	
	@Override
	public void attraperMainDroite(Point position) {
		this.attraper.setArgument(position);
		this.attraper.execute();
		
		if(!this.mainDroiteVide){
			this.screenParam.setMainDroiteVide(this.mainDroiteVide);
			this.screenParam.setAngleSelection(MainDroite.getInstance().getPiece().getAngle());
			
			this.drawable.drawSelection();
			this.drawable.drawTapis();
			this.drawable.drawHud();
			this.drawable.repaint();
		}
	}
	
	
	@Override
	public void poserMainDroite(Point position) {

		//
		this.poser.setArgument(position);
		this.poser.execute();
		
		this.drawable.drawTapis();
		this.drawable.drawHud();
		this.drawable.repaint();
	}

	@Override
	public void tournerMainDroite(double sens) {
		double angle = Math.PI /8.0;
		angle *= sens;
		this.tourner.setArgument(angle);
		this.tourner.execute();
		
		this.screenParam.setAngleSelection(MainDroite.getInstance().getPiece().getAngle());
		
		this.drawable.drawHud();
		this.drawable.repaint();
	}
	
	
	
	
	
	/* **** */
	
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
//		this.screenParam.setMouseX(e.getX());
//		this.screenParam.setMouseY(e.getY());
//		
//		if(!this.mainDroiteVide){
//			
//			if(e.isShiftDown()){
//				this.clips.execute();
//			}
//			
//			this.drawable.drawHud();
//			this.drawable.repaint();
//		}
	}


	
	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		this.screenParam.setMouseX(e.getX());
		this.screenParam.setMouseY(e.getY());
		
		Point p = new Point(e.getX(), e.getY());
		TapisBasicConverter.getInstance().convertScreenToModel(p);
	
		if(e.getButton() == MouseEvent.BUTTON1){
			
			if(this.mainDroiteVide){
				this.attraperMainDroite(p);	
			}else{
				this.poserMainDroite(p);
			}
		
				
			
			
		}// if button gauche
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		
		
	}

	
	@Override
	public void mouseMoved(MouseEvent e) {
		double prevx = this.screenParam.getMouseX();
		double prevy = this.screenParam.getMouseY();
		this.screenParam.setMouseX(e.getX());
		this.screenParam.setMouseY(e.getY());
		
		if(!this.mainDroiteVide){
			
			if(e.isShiftDown()){
				double dirx = e.getX() - prevx;
				double diry = e.getY() - prevy;
				
				
			}
			
			this.drawable.drawHud();
			this.drawable.repaint();
		}
		
		
	}
	
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		this.screenParam.setMouseX(e.getX());
		this.screenParam.setMouseY(e.getY());
		
		double rotation = e.getPreciseWheelRotation();
		
		if(!this.mainDroiteVide){
			this.tournerMainDroite(rotation);
		}
		
		
	}

	@Override
	public void update(Observable o, Object a) {
		if(a instanceof State){
			State s = (State) a;
			if(s.equals(State.MainDroitePleine)) this.mainDroiteVide = false;
			else if(s.equals(State.MainDroiteVide)) this.mainDroiteVide = true;
		}
		
		
		this.screenParam.setMainDroiteVide(this.mainDroiteVide);
		
	}





	public void setDrawable(IDrawable<ScreenParam> drawable) {
		this.drawable = drawable;
		this.drawable.setParam(this.screenParam);
	}


	
	// key
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SHIFT){
			shift = true;
			Point p = new Point(this.screenParam.getMouseX(),this.screenParam.getMouseY());
			TapisBasicConverter.getInstance().convertScreenToModel(p);
			
			ClipsParam param = new ClipsParam();
			param.setCentre(p);
			
			this.iSclips.setArgument(param);
			
			this.iSclips.execute();
		}
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SHIFT){
			shift = false;
		}
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
	
		
	}


	


	


	

	

	

}
