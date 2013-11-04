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
import com.puzzle.view.tool.JImageBuffer;

public class PieceInPocket  extends HudArea implements Observer{
	
	private Piece piece;
	private boolean in;
	
	
	
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
		//dev
		FreeBox b = (FreeBox) this.shape;
		
		Color c = Color.yellow;
		if(in) c = Color.red;
		
		for(Point p : b.getPoints()){
			this.buffer.drawRect(c, p.x, p.y, 2, 2, 1.0f);
		}
			
			
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
