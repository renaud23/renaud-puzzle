package com.puzzle.view.mainGauche;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

import com.puzzle.model.MainGauche;
import com.puzzle.model.Piece;
import com.puzzle.model.Point;
import com.puzzle.model.RectPiece;
import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.hud.FreeBox;
import com.puzzle.view.hud.HudControler;
import com.puzzle.view.tool.ImageMemoryManager;
import com.puzzle.view.tool.JImageBuffer;
import com.puzzle.view.tool.provider.PieceBufferOperation;



public class Pocket implements IDrawer{
	private Map<Piece, Integer> pocket = new HashMap<Piece, Integer>();
	private PieceInPocket[] table;
	private JImageBuffer buffer;
	
	private HudControler controller;
	private Fenetre fenetre;
	private Tapis tapis;
	
	private int xRef = 150;
	private int yRef = 500;
	private int xVar = 50;
	private double scale;
	
	private int limite = MainGauche.getInstance().getSize();
	private boolean first = true;
	
	public Pocket(HudControler controller, Tapis tapis, Fenetre f){
		this.controller = controller;
		this.fenetre = f;
		this.tapis = tapis;
		this.table = new PieceInPocket[20];
		
		this.buffer = f.getBuffer(1);
		yRef = (int) (buffer.getHauteur() - 0.1 * buffer.getHauteur());
		xRef = (int)(0.1 * buffer.getLargeur());
		xVar = (buffer.getLargeur() - 2 * xRef) / (limite -1);
		
	}
	
	public void add(Piece p){
		if(first){
			first = false;
			this.scale = this.buffer.getLargeur() * 0.1;
			this.scale /= p.getLargeur();
		}
		
		int index = this.getNextIndex();
		
		int x = this.xRef + this.xVar * index;
		int y = this.yRef;
		
		RectPiece r = (RectPiece) p.getRect();
		Point[] in = r.getCoins();
		java.awt.Point[] out = new java.awt.Point[4];
		Point c = p.getCentre();
		
		for(int i=0;i<4;i++){
			out[i] = new java.awt.Point();
			out[i].x = (int) Math.round( (in[i].getX() - c.getX()) * scale);
			out[i].x += x;
			out[i].y = (int) Math.round( (c.getY() - in[i].getY()) * scale);
			out[i].y += y;
		}
		java.awt.Point centre = new java.awt.Point(x,y);
		
		FreeBox b = new FreeBox(out,centre);
		PieceInPocket pi = new PieceInPocket(p,this.controller,this, b, scale);
		
		this.controller.addArea(pi);
		this.putInTable(index,p, pi);
		
	}
	
	public void remove(Piece p){
		Integer index = this.pocket.get(p);
		if(index != null){
			this.table[index] = null;
			this.pocket.remove(p);
//			this.refactor();
		}
		
		
	}
	
	
	public PieceInPocket get(Piece p){
		PieceInPocket v = null;
		Integer index = this.pocket.get(p);
		if(index != null) v = this.table[index];
		return v;
	}
	
	private void refactor(){
		boolean fini = false;
		while(!fini){
			fini = true;
			for(int i=1;i<this.table.length;i++){
				if(this.table[i] != null && this.table[i-1] == null){System.out.println(i);
					fini = false;
					
					// TODO
					
					this.table[i-1] = this.table[i];
					this.table[i] = null;
				}
			}
		}
	}
	
	
	private int getNextIndex(){
		int index = -1;
		
		for(int i=0;i<this.table.length;i++){
			if(this.table[i] == null){
				index = i;
				break;
			}
		}
		
		return index;
	}
	
	private void putInTable(int index,Piece p,PieceInPocket pi){
		if(index != -1 && index < this.table.length){
			this.table[index] = pi;
			this.pocket.put(p, index);
		}
		
	}
	
	public List<PieceInPocket> getEntry(){
		List<PieceInPocket> l = new ArrayList<PieceInPocket>();
		for(int i=0;i<this.table.length;i++){
			if(this.table[i] != null) l.add(this.table[i]);
		}
		
		return l;
	}

	@Override
	public void draw() {
		
		for(int i=0;i<this.table.length;i++){
			if(this.table[i] != null){
				PieceInPocket pi = this.table[i];
				FreeBox b = (FreeBox) pi.getShape();
				Piece piece = pi.getPiece();
				PieceBufferOperation pbo = ImageMemoryManager.getInstance().get(piece.getPuzzle().getId()).getElement(piece);
			
			double x = (int) Math.round(b.getCentre().getX() - pbo.getImage().getWidth() * pi.getScaleEffectif() / 2.0);
			double y = (int) Math.round(b.getCentre().getY() - pbo.getImage().getHeight() * pi.getScaleEffectif() / 2.0);
			this.buffer.drawImage(pbo.getImage(), 
					x,y,
					b.getCentre().getX(),b.getCentre().getY(), -piece.getAngle(),
					pi.getScaleEffectif(), pi.getScaleEffectif(), 
					pi.getAlpha());
			}
		}
		
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}
	
}
