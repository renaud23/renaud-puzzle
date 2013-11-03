package com.puzzle.zoomTapis.state;


import com.puzzle.command.ClipserMainDroite;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.param.ClipserParam;
import com.puzzle.command.param.IsClipsParam;
import com.puzzle.model.CompositePiece;
import com.puzzle.view.tool.provider.CompositeImageProvider;


public class TryClips implements IState {
	
	private TapisZoomControllerEx controller;
	private IsClipsParam iscParam;
	private double mouseX;
	private double mouseY;
	
	
	

	public TryClips(TapisZoomControllerEx controller, IsClipsParam iscParam,double mouseX,double mouseY) {
		this.controller = controller;
		this.iscParam = iscParam;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		
		if(!this.iscParam.getCandidats().isEmpty()){
			this.controller.getDrawSelectionParam().setCandidats(iscParam.getCandidats());
			
			this.controller.getDrawerSelection().clean();
			this.controller.getDrawerSelection().draw();
			this.controller.repaint();
		}
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
		if(!this.iscParam.getCandidats().isEmpty()){
			ClipserParam param = new ClipserParam();
			CommandeArgument<ClipserParam> cmd = new ClipserMainDroite(this.controller.getTapis());
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
			
			IState state = new MainVide(this.controller);
			this.controller.setState(state);
			this.controller.getDrawSelectionParam().clearCandidats();
			this.controller.getDrawerSelection().setSelection(false);
			
			this.controller.getDrawerSelection().clean();
			this.controller.getDrawerSelection().draw();
			this.controller.getDrawerTapis().draw();
		}
	}

	@Override
	public void mouseLeftReleased(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseRightPressed(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseRightReleased(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMove(int x, int y, boolean isShiftDown) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDrag(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheel(boolean up) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyShiftPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyShiftReleased() {
		this.controller.getDrawSelectionParam().clearCandidats();
		
		IState state = new MainPleine(this.controller,this.mouseX,this.mouseY);
		this.controller.setState(state);
		
		this.controller.getDrawerSelection().clean();
		this.controller.getDrawerSelection().draw();
		this.controller.repaint();
		
	}

	@Override
	public void keyControlPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyControlReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlPlusS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlPlusL() {
		// TODO Auto-generated method stub
		
	}

}
