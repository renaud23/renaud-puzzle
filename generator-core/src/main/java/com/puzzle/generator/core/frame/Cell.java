package com.puzzle.generator.core.frame;

public class Cell {
	private int position = 0;
	private boolean locked = false;
	private int type = 0;


	public boolean isCaseVide(){
		return this.type == Frame.case_vide;
	}

	
	public boolean isEst(){
		return (this.type & Frame.est) == Frame.est;
	}
	
	public boolean isCoteEst(){
		return (this.type & Frame.cote_est) == Frame.cote_est;
	}
	
	public boolean isNord(){
		return (this.type & Frame.nord) == Frame.nord;
	}
	
	public boolean isCoteNord(){
		return (this.type & Frame.cote_nord) == Frame.cote_nord;
	}
	
	public boolean isOuest(){
		return (this.type & Frame.ouest) == Frame.ouest;
	}
	
	public boolean isCoteOuest(){
		return (this.type & Frame.cote_ouest) == Frame.cote_ouest;
	}

	public boolean isSud(){
		return (this.type & Frame.sud) == Frame.sud;
	}
	
	public boolean isCoteSud(){
		return (this.type & Frame.cote_sud) == Frame.cote_sud;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
