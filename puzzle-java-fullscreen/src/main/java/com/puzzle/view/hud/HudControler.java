package com.puzzle.view.hud;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;





import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.puzzle.model.Tapis;
import com.puzzle.view.context.PuzzleContext;
import com.puzzle.view.context.PuzzleContext.PuzzleParam;
import com.puzzle.view.java.Game;

public class HudControler implements MouseMotionListener,MouseListener,Observer{
	private Game game;
	private Tapis tapis;
	private List<HudArea> areas;
	
	private HudArea focused;
	

	public HudControler(Game game, Tapis tapis) {
		this.game = game;
		this.tapis = tapis;
		this.areas = new ArrayList<HudArea>();
		
		this.tapis.addObserver(this);
		
		
		
		// test 
		HudArea h = new HudArea(new Box(10, 300, 50, 200), new HudTask() {
			
			@Override
			public void execute() {
				System.out.println("on it !");
				
			}
		});
		HudRenderer rnd =
				(HudRenderer) PuzzleContext.getInstance().get(PuzzleParam.hudRenderer);
		rnd.addRenderer(h);
		this.addArea(h);
	}

	private HudArea getCandidat(int x,int y){
		HudArea cdt = null;
		int i=0;
		while(i<this.areas.size() && cdt == null){
			HudArea h = this.areas.get(i);
			if(h.isIn(x, y)) cdt = h;
			
			i++;
		}
		return cdt;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		HudArea h = this.getCandidat(e.getX(), e.getY());
		if(h != null){
			h.mouseClicked(e);
		}else this.game.mouseClicked(e);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.game.mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.game.mouseExited(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		HudArea h = this.getCandidat(e.getX(), e.getY());
		if(h != null){
			h.mousePressed(e);
		}else this.game.mousePressed(e);
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		HudArea h = this.getCandidat(e.getX(), e.getY());
		if(h != null){
			h.mouseReleased(e);
		}else this.game.mouseReleased(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		HudArea h = this.getCandidat(e.getX(), e.getY());
		if(h != null){
			h.mouseDragged(e);
		}else this.game.mouseDragged(e);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		HudArea h = this.getCandidat(e.getX(), e.getY());
		if(h != null){
			h.mouseMoved(e);
			if(focused == null){
				focused = h;
				focused.mouseEntered(e);
			}
		}else{
			this.game.mouseMoved(e);
			if(focused != null){
				focused.mouseExited(e);
				focused = null;
			}
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void addArea(HudArea a){
		this.areas.add(a);
	}
	
	public void removeArea(HudArea a){
		this.areas.remove(a);
	}
	
}
