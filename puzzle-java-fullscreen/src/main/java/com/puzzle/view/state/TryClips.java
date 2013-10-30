package com.puzzle.view.state;

import com.puzzle.command.param.IsClipsParam;
import com.puzzle.view.context.PuzzleContext;
import com.puzzle.view.context.PuzzleContext.PuzzleParam;
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
		if(!this.iscParam.getCandidats().isEmpty()){
			this.renderer.addCandidats(this.iscParam.getCandidats());
		}
	}

	@Override
	public void shiftReleased() {
		IState state = new MainPleine(this.game);
		this.game.setState(state);
		this.renderer.clearCandidats();
		
	}

}
