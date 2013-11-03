package com.puzzle.command;

import com.puzzle.command.param.ClipserParam;
import com.puzzle.model.MainDroite;
import com.puzzle.model.State;
import com.puzzle.model.Tapis;

public class ClipserMainDroite implements CommandeArgument<ClipserParam>{
	private Tapis tapis;
	private ClipserParam param;
	private CommandeArgument<ClipserParam> clipser;
	

	public ClipserMainDroite(Tapis tapis) {
		this.tapis = tapis;
		this.clipser = new Clipser(tapis);
	}

	@Override
	public void execute() {
		if(!MainDroite.getInstance().isEmpty()){
			this.param.setComponent(MainDroite.getInstance().getContenu());
			this.clipser.setArgument(this.param);
			this.clipser.execute();
			
			
			MainDroite.getInstance().libere();
			this.tapis.change();
			this.tapis.notifyObservers(State.MainDroiteVide);
		}
	}

	@Override
	public void setArgument(ClipserParam arg) {
		this.param = arg;
	}

}
