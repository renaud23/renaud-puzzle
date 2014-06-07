package com.puzzle.view2.later.state;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Observer;

import com.puzzle.command.ClipserMainDroite;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.param.ClipserParam;
import com.puzzle.command.param.IsClipsParam;
import com.puzzle.model.ComponentPiece;
import com.puzzle.model.Point;
import com.puzzle.model.Tapis;
import com.puzzle.view2.DrawOperationAware;
import com.puzzle.view2.controller.ControllerAdaptater;
import com.puzzle.view2.controller.Converter;
import com.puzzle.view2.controller.IController;
import com.puzzle.view2.image.IDrawOperation;
import com.puzzle.view2.image.IDrawable;
import com.puzzle.view2.layer.BackgroundLayer;
import com.puzzle.view2.layer.TapisLayer.TapisLayerEvent;
import com.puzzle.view2.layer.Vue;

public class ClipsState extends ControllerAdaptater implements IState,IDrawable,DrawOperationAware{
	
	
	
	private Tapis tapis;
	private BackgroundLayer bckLayer;
	private IsClipsParam param;
	private double x;
	private double y;
	private Observer observer;
	
	private IDrawOperation op;
	private Converter converter;
	
	

	

	public ClipsState(Tapis tapis, BackgroundLayer bckLayer,IsClipsParam param,double x,double y,Observer observer) {
		this.tapis = tapis;
		this.bckLayer = bckLayer;
		this.param = param;
		this.x = x;
		this.y = y;
		this.observer = observer;
		this.converter = new Converter(this.bckLayer);
	
		this.clips();
	}
	
	
	private void clips(){
		ClipserParam param = new ClipserParam();
		CommandeArgument<ClipserParam> cmd = new ClipserMainDroite(this.tapis);
		cmd.setArgument(param);
		
		param.setCandidat(this.param.getCandidats().get(0));
		cmd.execute();
		
		this.observer.update(null, TapisLayerEvent.endClips);
	}
	
	
	

	@Override
	public void setMouseX(int x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMouseY(int y) {
		// TODO Auto-generated method stub
		
	}

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
		ComponentPiece cmp = this.param.getComponent();
		
		
		
		this.op.fillRect(Color.black, x, y, 20, 20,1.0f);
		this.clips();
	}

	
}
