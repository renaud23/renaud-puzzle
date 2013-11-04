package com.puzzle.view.mainGauche;

import java.awt.Color;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingUtilities;

import com.puzzle.command.Commande;
import com.puzzle.command.PasserDansMainDroite;
import com.puzzle.model.MainDroite;
import com.puzzle.model.MainGauche;
import com.puzzle.model.Piece;
import com.puzzle.model.State;
import com.puzzle.view.Fenetre;
import com.puzzle.view.RepaintTask;
import com.puzzle.view.hud.FreeBox;
import com.puzzle.view.hud.HudArea;
import com.puzzle.view.hud.HudControler;
import com.puzzle.view.hud.HudShape;
import com.puzzle.view.hud.HudTask;
import com.puzzle.view.tool.ImageMemoryManager;
import com.puzzle.view.tool.JImageBuffer;
import com.puzzle.view.tool.provider.PieceBufferOperation;




public class PieceInPocket  extends HudArea implements Observer{
	
	private Piece piece;
	private boolean in;
	
	private double scale;
	private double scaleEffectif;
	
	private JImageBuffer buffer;
	private Fenetre fenetre;
	private HudControler controller;
	private int level;
	

	public PieceInPocket(Piece piece,final HudControler controller,HudShape shape,double scale){
		this.fenetre = controller.getFenetre();
		this.buffer = fenetre.getBuffer(1);
		this.shape = shape;
		this.piece = piece;
		this.scale = scale;
		this.scaleEffectif = scale;
		this.controller = controller;
		
		
		this.task = new HudTask() {
			
			@Override
			public void execute() {
				
				Commande cmd = new PasserDansMainDroite(controller.getTapis());
				cmd.execute();
			}
		};
		controller.getTapis().addObserver(this);
		
	}


	public void clean(){
		
	}
	
	@Override
	public void draw() {
		FreeBox b = (FreeBox) this.shape;
		
		PieceBufferOperation pbo = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getElement(piece);
		
		double x = (int) Math.round(b.getCentre().getX() - pbo.getImage().getWidth() * this.scaleEffectif / 2.0);
		double y = (int) Math.round(b.getCentre().getY() - pbo.getImage().getHeight() * this.scaleEffectif / 2.0);
		this.buffer.drawImage(pbo.getImage(), 
				x,y,
				b.getCentre().getX(),b.getCentre().getY(), -piece.getAngle(),
				this.scaleEffectif, this.scaleEffectif, 1.0f);
		
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
		this.scaleEffectif = this.scale * 2;
		
		MainGauche.getInstance().focused(this.piece);

		this.controller.getDrawer().draw();
		SwingUtilities.invokeLater(new RepaintTask(this.fenetre));
	}


	@Override
	public void mouseExited() {
		this.in = false;
		this.scaleEffectif = this.scale;

		this.controller.getDrawer().draw();
		SwingUtilities.invokeLater(new RepaintTask(this.fenetre));
	}


	@Override
	public void update(Observable o, Object arg) {
		if(arg == State.gaucheToDroite){
			if(this.piece == MainDroite.getInstance().getContenu()){
				this.controller.getTapis().deleteObserver(this);
				this.controller.removeArea(this.piece);
				
				this.controller.getDrawer().draw();
				SwingUtilities.invokeLater(new RepaintTask(this.fenetre));
			}
			
		}
		
	}





	
	
	

}
