package puzzle.generator.modele.frame;

public class Cell {
	private int position = 0;
	private boolean locked = false;
	private int type = 0;


	public boolean isCaseVide(){
		return this.type == FrameEx.case_vide;
	}

	
	public boolean isEst(){
		return (this.type & FrameEx.est) == FrameEx.est;
	}
	
	public boolean isCoteEst(){
		return (this.type & FrameEx.cote_est) == FrameEx.cote_est;
	}
	
	public boolean isNord(){
		return (this.type & FrameEx.nord) == FrameEx.nord;
	}
	
	public boolean isCoteNord(){
		return (this.type & FrameEx.cote_nord) == FrameEx.cote_nord;
	}
	
	public boolean isOuest(){
		return (this.type & FrameEx.ouest) == FrameEx.ouest;
	}
	
	public boolean isCoteOuest(){
		return (this.type & FrameEx.cote_ouest) == FrameEx.cote_ouest;
	}

	public boolean isSud(){
		return (this.type & FrameEx.sud) == FrameEx.sud;
	}
	
	public boolean isCoteSud(){
		return (this.type & FrameEx.cote_sud) == FrameEx.cote_sud;
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
