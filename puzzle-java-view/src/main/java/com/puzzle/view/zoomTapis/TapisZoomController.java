package com.puzzle.view.zoomTapis;

import java.awt.Component;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

import com.puzzle.model.State;
import com.puzzle.model.Tapis;
import com.puzzle.view.PuzzleCursor;
import com.puzzle.view.PuzzleCursor.CursorType;
import com.puzzle.view.controller.IController;
import com.puzzle.view.drawer.DrawSelection;
import com.puzzle.view.drawer.DrawSelectionParam;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.drawer.IDrawerSelection;
import com.puzzle.view.tool.JImageBuffer;
import com.puzzle.zoomTapis.state.IState;
import com.puzzle.zoomTapis.state.MainVide;


public class TapisZoomController implements IController,Observer{
	
	private Tapis tapis;
	private TapisZoomConverteur converter;
	private IState state;
	private IDrawer drawerTapis;
	private IDrawerSelection drawerSelection;
	private Component component;
	
	protected DrawSelectionParam drawSelectionParam;
	

	public TapisZoomController(Tapis tapis, TapisZoomConverteur converter,Image background, JImageBuffer buffertTapis,JImageBuffer bufferHud,Component component) {
		this.tapis = tapis;
		this.component = component;
		this.converter = converter;
		this.drawerTapis = new TapisZoomDrawer(background,tapis,buffertTapis,this.converter);
		
		this.drawerSelection = new DrawSelection(bufferHud, this.converter);
		this.drawSelectionParam = new DrawSelectionParam();
		this.drawerSelection.setParam(this.drawSelectionParam);
		
		this.state = new MainVide(this);
		
		this.drawerSelection.draw();
		this.drawerTapis.draw();
		
		tapis.addObserver(this);
	}
	
	public TapisZoomController(
			Tapis tapis, 
			TapisZoomConverteur converter,
			TapisZoomDrawer tapisDrawer,
			IDrawerSelection drawerSelection,
			Component component) {
		this.tapis = tapis;
		this.component = component;
		this.converter = converter;
		this.drawerTapis = tapisDrawer;
		
		this.drawerSelection = drawerSelection;
		this.drawSelectionParam = new DrawSelectionParam();
		this.drawerSelection.setParam(this.drawSelectionParam);
		
		this.state = new MainVide(this);
		
		this.drawerSelection.draw();
		this.drawerTapis.draw();
		
		tapis.addObserver(this);
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
		this.state.mouseLeftReleased(x, y);
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
		this.state.keyControlReleased();
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

	public void setDrawerSelection(IDrawerSelection drawerSelection) {
		this.drawerSelection = drawerSelection;
	}

	public Tapis getTapis() {
		return tapis;
	}

	public DrawSelectionParam getDrawSelectionParam() {
		return drawSelectionParam;
	}


	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof State){
			State state = (State) arg;
			if(state == State.nouveauPuzzle){
				this.drawerTapis.draw();
			}else if(state == State.MainDroitePleine){
				this.component.setCursor(PuzzleCursor.getInstance().get(CursorType.mainPleine));
			}else if(state == State.MainDroiteVide){
				this.component.setCursor(PuzzleCursor.getInstance().get(CursorType.mainVide));
			}else if(state == State.gaucheToDroite){
				this.component.setCursor(PuzzleCursor.getInstance().get(CursorType.mainPleine));
			}else if(state == State.droiteToGauche){
				this.component.setCursor(PuzzleCursor.getInstance().get(CursorType.mainVide));
			}else if(state == State.retirerPuzzle){
				this.drawerTapis.draw();
			}else if(state == State.PuzzleFini){
				System.out.println("Fini!!!");
			}
		}
	}

	@Override
	public void keyZPressed() {
		this.state.keyZPressed();
	}

	@Override
	public void keyZReleased() {
		this.state.keyZReleased();
		
	}


	
}
