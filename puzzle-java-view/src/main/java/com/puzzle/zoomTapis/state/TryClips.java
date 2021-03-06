package com.puzzle.zoomTapis.state;


import com.puzzle.command.ClipserMainDroite;
import com.puzzle.command.CommandeArgument;
import com.puzzle.command.param.ClipserParam;
import com.puzzle.command.param.IsClipsParam;
import com.puzzle.model.CompositePiece;
import com.puzzle.view.tool.provider.CompositeImageProvider;
import com.puzzle.view.zoomTapis.TapisZoomController;


public class TryClips extends StateAdapter {
	
	private TapisZoomController controller;
	private IsClipsParam iscParam;
	private double mouseX;
	private double mouseY;
	
	
	

	public TryClips(TapisZoomController controller, IsClipsParam iscParam,double mouseX,double mouseY) {
		this.controller = controller;
		this.iscParam = iscParam;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		
		if(!this.iscParam.getCandidats().isEmpty()){
			this.controller.getDrawSelectionParam().setCandidats(iscParam.getCandidats());
			
			this.controller.getDrawerSelection().draw();
		}
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
			
			IState state = new MainVide(this.controller,this.mouseX,this.mouseY);
			this.controller.setState(state);
			this.controller.getDrawSelectionParam().clearCandidats();
			this.controller.getDrawerSelection().setSelection(false);
			
			this.controller.getDrawerSelection().draw();
			this.controller.getDrawerTapis().draw();
		}
	}

	@Override
	public void keyShiftReleased() {
		this.controller.getDrawSelectionParam().clearCandidats();
		
		IState state = new MainPleine(this.controller,this.mouseX,this.mouseY);
		this.controller.setState(state);
		
		this.controller.getDrawerSelection().draw();
//		this.controller.repaint();
		
	}



}
