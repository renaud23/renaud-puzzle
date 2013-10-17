package com.puzzle.command.param;



import com.puzzle.model.ComponentPiece;
import com.puzzle.model.Piece;

public class ClipserParam {
	/**
	 * pi�ce candidate au clips.
	 */
	private Piece candidat;
	/**
	 * component pr�t � recevoir le candidat.
	 */
	private ComponentPiece component;
	/**
	 * composant inutile : l'ancine component, ins�r� dans le candidat.
	 */
	private ComponentPiece detruit;
	
	
			
	
	public ComponentPiece getDetruit() {
		return detruit;
	}
	public void setDetruit(ComponentPiece detruit) {
		this.detruit = detruit;
	}
	public Piece getCandidat() {
		return candidat;
	}
	public void setCandidat(Piece candidat) {
		this.candidat = candidat;
	}
	public ComponentPiece getComponent() {
		return component;
	}
	public void setComponent(ComponentPiece component) {
		this.component = component;
	}	
}
