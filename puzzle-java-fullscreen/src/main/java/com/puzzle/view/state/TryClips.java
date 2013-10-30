package com.puzzle.view.state;

import com.puzzle.command.ClipserMainDroite;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.param.ClipserParam;
import com.puzzle.command.param.IsClipsParam;
import com.puzzle.model.CompositePiece;
import com.puzzle.view.context.PuzzleContext;
import com.puzzle.view.context.PuzzleContext.PuzzleParam;
import com.puzzle.view.core.image.CompositeImageProvider;
import com.puzzle.view.java.Game;
import com.puzzle.view.java.JavaRenderer;


public class TryClips implements IState{
	
	private Game game;
	private IsClipsParam iscParam;
	private JavaRenderer renderer;
	
	
	

	public TryClips(Game game, IsClipsParam iscParam) {
		this.game = game;
		this.iscParam = iscParam;
		this.renderer =	(JavaRenderer) PuzzleContext.getInstance().get(PuzzleParam.renderer);
		
		if(!this.iscParam.getCandidats().isEmpty()){
			this.renderer.addCandidats(this.iscParam.getCandidats());
		}
	}

	@Override
	public void wheel(boolean up,boolean shift) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragRight(int vx, int vy, boolean shift) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pressLeft(int x, int y, boolean shift) {
		if(!iscParam.getCandidats().isEmpty()){
			ClipserParam param = new ClipserParam();
			CommandeArgument<ClipserParam> cmd = new ClipserMainDroite(this.game.getTapis());
			cmd.setArgument(param);
			
			param.setCandidat(this.iscParam.getCandidats().get(0));
			cmd.execute();
			
			// vidage du cache des composites.
			if(param.getComponent() instanceof CompositePiece){
				CompositeImageProvider.getInstance().removeBuffer((CompositePiece) param.getComponent());
			}
			if(param.getDetruit() instanceof CompositePiece){
				CompositeImageProvider.getInstance().removeBuffer((CompositePiece) param.getDetruit());
			}
			
			IState state = new MainVide(this.game);
			this.game.setState(state);
			this.renderer.clearCandidats();
		}
	}

	@Override
	public void shiftReleased(int x,int y) {
		IState state = new MainPleine(this.game);
		this.game.setState(state);
		this.renderer.clearCandidats();
		
	}

	@Override
	public void shiftPressed(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
