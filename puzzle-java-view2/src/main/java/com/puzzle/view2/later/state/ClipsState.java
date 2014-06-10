package com.puzzle.view2.later.state;


import java.util.Observer;
import com.puzzle.command.ClipserMainDroite;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.param.ClipserParam;
import com.puzzle.command.param.IsClipsParam;
import com.puzzle.model.ComponentPiece;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.MainDroite;
import com.puzzle.model.Piece;
import com.puzzle.model.Tapis;
import com.puzzle.view2.DrawOperationAware;
import com.puzzle.view2.controller.ControllerAdaptater;
import com.puzzle.view2.controller.Converter;
import com.puzzle.view2.image.IDrawOperation;
import com.puzzle.view2.image.IDrawable;
import com.puzzle.view2.image.ImageProvider;
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
	private IAnimation animation;
	

	

	public ClipsState(Tapis tapis, BackgroundLayer bckLayer,IsClipsParam param,double x,double y,Observer observer) {
		this.tapis = tapis;
		this.bckLayer = bckLayer;
		this.param = param;
		this.x = x;
		this.y = y;
		this.observer = observer;
		this.converter = new Converter(this.bckLayer);
		
		this.animation = new clipsAnimation(param.getComponent(),x,y,bckLayer.getScale());
	}
	
	
	private void clips(){
		ComponentPiece cmp = MainDroite.getInstance().getContenu();
		ClipserParam param = new ClipserParam();
		CommandeArgument<ClipserParam> cmd = new ClipserMainDroite(this.tapis);
		cmd.setArgument(param);
		
		Piece candidat = this.param.getCandidats().get(0);
		param.setCandidat(candidat);
		cmd.execute();
		
		this.observer.update(null, TapisLayerEvent.endClips);
		
		if(cmp instanceof CompositePiece) ImageProvider.getInstance().removeCompositeImage((CompositePiece) cmp);
		if(candidat.getComposite() != null) ImageProvider.getInstance().removeCompositeImage(candidat.getComposite());
		
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
		this.animation.setDrawOperation(op);
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
		this.animation.setDrawOperation(op);
		if(!this.animation.isFinish()) this.animation.play();
		else this.clips();
	}

	
}
