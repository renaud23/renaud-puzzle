package com.puzzle.zoomTapis.state;

import java.awt.Image;

import javax.swing.SwingUtilities;




import com.puzzle.model.Tapis;
import com.puzzle.view.Fenetre;
import com.puzzle.view.RepaintTask;
import com.puzzle.view.controller.IController;
import com.puzzle.view.drawer.DrawSelectionParam;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.drawer.IDrawerSelection;
import com.puzzle.view.zoomTapis.DrawZoomSelection;
import com.puzzle.view.zoomTapis.Lunette;
import com.puzzle.view.zoomTapis.TapisZoomConverteur;
import com.puzzle.view.zoomTapis.TapisZoomDrawer;


public class TapisZoomControllerEx implements IController{
	
	private Tapis tapis;
	private TapisZoomConverteur converter;
	private IState state;
	private Fenetre fenetre;
	private IDrawer drawerTapis;
	private IDrawerSelection drawerSelection;
	
	protected DrawSelectionParam drawSelectionParam;
	

	public TapisZoomControllerEx(Tapis tapis, Image background, Fenetre fenetre) {
		this.tapis = tapis;
		this.fenetre = fenetre;
		this.converter = new TapisZoomConverteur(fenetre.getOffscreen(), tapis);
		this.drawerTapis = new TapisZoomDrawer(fenetre,background,tapis,fenetre.getBuffer(0),this.converter);
		
		Lunette lunette = new Lunette();
		lunette.setTapis(tapis);
		lunette.setScale(0.2);
		lunette.setLargeur(this.fenetre.getBuffer(1).getLargeur() * lunette.getScale());
		lunette.setHauteur(lunette.getLargeur() * tapis.getHauteur() / tapis.getLargeur());
		lunette.setX(this.fenetre.getBuffer(1).getLargeur() - lunette.getLargeur() - 10.0);
		lunette.setY(10.0);
		this.drawerSelection = new DrawZoomSelection(this.fenetre.getBuffer(1), (TapisZoomConverteur) this.converter,lunette);
		this.drawSelectionParam = new DrawSelectionParam();
		this.drawerSelection.setParam(this.drawSelectionParam);
		
		this.state = new MainVide(this);
		
		this.drawerTapis.draw();
		this.drawerSelection.draw();
	}
	
	
	public void repaint(){
		SwingUtilities.invokeLater(new RepaintTask(this.fenetre));
	}

	@Override
	public void mouseEntered() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseLeftPressed(int x, int y) {
		this.state.mouseLeftPressed(x, y);
	}

	@Override
	public void mouseLeftReleased(int x, int y) {
//		this.state.mouseLeftReleased(x, y);
	}

	@Override
	public void mouseRightPressed(int x, int y) {
		this.state.mouseRightPressed(x, y);
	}

	@Override
	public void mouseRightReleased(int x, int y) {
		this.state.mouseRightReleased(x, y);
	}

	@Override
	public void mouseMove(int x, int y, boolean isShiftDown) {
		this.state.mouseMove(x, y, isShiftDown);
	}

	@Override
	public void mouseDrag(int x, int y) {
		this.state.mouseDrag(x, y);	
	}

	@Override
	public void mouseWheel(boolean up) {
		this.state.mouseWheel(up);
	}

	@Override
	public void keyShiftPressed() {
		this.state.keyShiftPressed();
	}

	@Override
	public void keyShiftReleased() {
		this.state.keyShiftReleased();
	}

	@Override
	public void keyControlPressed() {
		this.state.keyControlPressed();
	}

	@Override
	public void keyControlReleased() {
//		this.state.keyControlReleased();
	}

	@Override
	public void controlPlusS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlPlusL() {
		// TODO Auto-generated method stub
		
	}

	public TapisZoomConverteur getConverter() {
		return converter;
	}

	public DrawSelectionParam getSelectionParam() {
		return drawSelectionParam;
	}

	public void setState(IState state) {
		this.state = state;
	}

	public IDrawer getDrawerTapis() {
		return drawerTapis;
	}

	public IDrawerSelection getDrawerSelection() {
		return drawerSelection;
	}


	public Tapis getTapis() {
		return tapis;
	}


	public DrawSelectionParam getDrawSelectionParam() {
		return drawSelectionParam;
	}
	
	
	
	

}
