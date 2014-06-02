package com.puzzle.view2.layer;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.puzzle.command.Commande;
import com.puzzle.command.PasserDansMainDroite;
import com.puzzle.model.MainDroite;
import com.puzzle.model.MainGauche;
import com.puzzle.model.Piece;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view2.DrawOperationAware;
import com.puzzle.view2.Fenetre;
import com.puzzle.view2.controller.RootController;
import com.puzzle.view2.image.IDrawOperation;
import com.puzzle.view2.image.IDrawable;
import com.puzzle.view2.image.ImageProvider;
import com.puzzle.view2.widget.PieceInPocket;

public class Pocket implements Observer,IDrawable,DrawOperationAware{
	private List<PieceInPocket> widgets;
	
	private Tapis tapis;
	private HudLayer hud;
	private RootController controller;
	
	private int x;
	private int y;
	private int largeur;
	private int hauteur;
	private int eccart;
	
	private int maxSize;
	private int size;
	
	private IDrawOperation op;
	private double scale;
	private double scaleFocused;
	private double scaleMax;
	private boolean first = true;
	
	private PieceInPocket focused;
	
	private Timer thScale;
	
	
	public Pocket(Tapis tapis,HudLayer hud, RootController controller,int x, int y, int largeur, int hauteur) {
		this.tapis = tapis;
		this.hud = hud;
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.controller = controller;
		
		this.tapis.addObserver(this);
		
		this.maxSize = MainGauche.getInstance().getSize();
		this.size = 0;
		
		this.widgets = new ArrayList<PieceInPocket>();
		this.eccart  = (this.largeur ) / maxSize;
		
	}


	public void update(Observable o, Object arg) {
		if(arg instanceof State){
			State state = (State) arg;
			if(state == State.gaucheToDroite){
				this.size--;
			}else if(state == State.droiteToGauche){
				Piece p = MainGauche.getInstance().getLastIn();
				if(first){
					first = false;
					this.scale = this.hauteur / (Math.min(p.getHauteur(),p.getLargeur()) );
					this.scaleFocused = this.scale;
					this.scaleMax = this.scale * 2.0;
				}
				
				int x = this.x;
				x += this.size * this.eccart;
				int y = this.y;
				
				PieceInPocket pip = new PieceInPocket(this,p,x,y,scale);
				synchronized (this.widgets) {
					this.widgets.add(pip);
				}
				this.hud.addWidget(pip);
				this.controller.addController(pip);
				
				this.size++;
			}
		}
	}
	
	
	
	private void validate(){
		int i=0;
		
		for(PieceInPocket pip : this.widgets){
			int x = this.x;
			x += i * this.eccart;
			pip.setX(x);
			
			pip.init();
			i++;
		}
	}


	/*
	 * draw
	 */
	
	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}


	@Override
	public boolean isChange() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void setChange() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void draw(Vue vue) {
		synchronized (this.widgets) {
			Collections.sort(this.widgets,new Comparator<PieceInPocket>() {
	
				@Override
				public int compare(PieceInPocket a, PieceInPocket b) {
					int val = 0;
					if(a.getZIndex() > b.getZIndex()) val = 1;
					else if(a.getZIndex() < b.getZIndex()) val = -1;
					return val;
				}
			});
		
			
//			this.op.drawRect(Color.yellow, x, y, largeur, hauteur);
			
			for(PieceInPocket pip : this.widgets){
				Image img = ImageProvider.getInstance().getImage(pip.getPiece());
				if(img != null){
					if(pip != this.focused){
						double x = pip.getX();
						double y = pip.getY();
						double cx = pip.getX() + img.getWidth(null) * this.scale / 2.0;
						double cy = pip.getY() + img.getHeight(null)  * this.scale / 2.0;
				
	//					this.op.fillRect(Color.yellow, this.coins[0].getX(), this.coins[0].getY(), 2, 2, 1.0f);
	//					this.op.fillRect(Color.yellow, this.coins[1].getX(), this.coins[1].getY(), 2, 2, 1.0f);
	//					this.op.fillRect(Color.yellow, this.coins[2].getX(), this.coins[2].getY(), 2, 2, 1.0f);
	//					this.op.fillRect(Color.yellow, this.coins[3].getX(), this.coins[3].getY(), 2, 2, 1.0f);
						this.op.drawImage(img, 
								x, y, 
								cx, cy, -pip.getPiece().getAngle(), 
								this.scale, 1.0f);
					}
				
				}
			}
			
			if(this.focused != null){
				Image img = ImageProvider.getInstance().getImage(this.focused.getPiece());
				if(img != null){
					
					double cx = this.focused.getX() + img.getWidth(null) * this.scale / 2.0;
					double cy = this.focused.getY() + img.getHeight(null)  * this.scale / 2.0;
					double x = cx - this.focused.getPiece().getLargeur() * this.scaleFocused / 2.0;
					double y = cy - this.focused.getPiece().getHauteur() * this.scaleFocused / 2.0;;
				
					this.op.drawImage(img, 
							x, y, 
							cx, cy, -this.focused.getPiece().getAngle(), 
							this.scaleFocused, 1.0f);
				}
			}
		}//synchronised
		
	}

	public void pick(PieceInPocket pip){
		if(MainDroite.getInstance().isEmpty()){
			MainGauche.getInstance().focused(pip.getPiece());
			Commande cmd = new PasserDansMainDroite(this.tapis);
			cmd.execute();
			
			
			this.widgets.remove(pip);
			this.hud.removeWidget(pip);
			this.controller.removeController(pip);
			this.focused = null;
			this.validate();
		}
	}
	
	
	
	
	
	
	
	
	
	
	public void startScaling(){
		this.scaleFocused = this.scale;
		this.thScale = new Timer();
		
		final Pocket p = this;
		final Timer t = this.thScale;
		this.thScale.schedule(new TimerTask() {
			
			@Override
			public void run() {
				double ns = p.getScaleFocused() * 1.05;
				if(ns > p.getScaleMax()){ 
					ns = p.getScaleMax();
					this.cancel();
				}
				p.setScaleFocused(ns);
			}
		}, 0, 10);
	}
	
	
	
	

	public PieceInPocket getFocused() {
		return focused;
	}


	public void setFocused(PieceInPocket focused) {
		this.focused = focused;
		if(focused != null)this.startScaling();
		
	}


	public double getScaleFocused() {
		return scaleFocused;
	}


	public void setScaleFocused(double scaleFocused) {
		this.scaleFocused = scaleFocused;
	}


	public double getScaleMax() {
		return scaleMax;
	}
	
	
	
	
	
	
}
