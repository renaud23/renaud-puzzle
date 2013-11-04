package com.puzzle.view.mainGauche;

import java.awt.Color;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingUtilities;

import com.puzzle.model.Piece;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.RepaintTask;
import com.puzzle.view.hud.FreeBox;
import com.puzzle.view.hud.HudArea;
import com.puzzle.view.hud.HudShape;
import com.puzzle.view.hud.HudTask;
import com.puzzle.view.tool.ImageMemoryManager;
import com.puzzle.view.tool.JImageBuffer;
import com.puzzle.view.tool.provider.PieceBufferOperation;

public class PieceInPocket  extends HudArea implements Observer{
	
	private Piece piece;
	private boolean in;
	
	private double scale = 0.5;
	
	private JImageBuffer buffer;
	private Fenetre fenetre;
	

	public PieceInPocket(Piece piece,Tapis tapis,Fenetre fenetre,HudShape shape){
		this.fenetre = fenetre;
		this.buffer = fenetre.getBuffer(1);
		this.shape = shape;
		this.piece = piece;
		
		
		
		this.task = new HudTask() {
			
			@Override
			public void execute() {
				
				
			}
		};
		
		tapis.addObserver(this);
	}


	public void clean(){
	
	}
	
	@Override
	public void draw() {
		FreeBox b = (FreeBox) this.shape;
		
		PieceBufferOperation pbo = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getElement(piece);
		this.scale =  0.3;//b.getLargeur() / (double)pbo.getImage().getWidth();
		
		double x = (int) Math.round(b.getCentre().getX() - pbo.getImage().getWidth() * this.scale / 2.0);
		double y = (int) Math.round(b.getCentre().getY() - pbo.getImage().getHeight() * this.scale / 2.0);
		this.buffer.drawImage(pbo.getImage(), 
				x,y,
				b.getCentre().getX(),b.getCentre().getY(), -piece.getAngle(),
				this.scale, this.scale, 1.0f);
		
		//dev
		Color c = Color.yellow;
		if(in) c = Color.red;
		
		for(Point p : b.getPoints()){
			this.buffer.drawRect(c, p.x, p.y, 2, 2, 1.0f);
		}
			
		this.buffer.drawRect(c, b.getCentre().x, b.getCentre().y, 2, 2, 1.0f);
	}


	@Override
	public void mouseEntered() {
		this.in = true;
		this.clean();
		this.draw();
		SwingUtilities.invokeLater(new RepaintTask(this.fenetre));
	}


	@Override
	public void mouseExited() {
		this.in = false;
		this.clean();
		this.draw();
		SwingUtilities.invokeLater(new RepaintTask(this.fenetre));
	}


	@Override
	public void update(Observable o, Object arg) {
//		System.out.println(arg);
		
	}


	
	
	

}
