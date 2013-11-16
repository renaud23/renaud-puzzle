package com.puzzle.view.hud;


import java.awt.TrayIcon.MessageType;
import java.util.List;

import com.puzzle.model.Piece;
import com.puzzle.view.controller.ControllerMessage;
import com.puzzle.view.controller.ControllerMessage.Message;
import com.puzzle.view.controller.ControllerMessage.TypeMessage;
import com.puzzle.view.drawer.IDrawer;

public class HighLightedPieces implements IDrawer{
	private List<Piece> candidats;

	@Override
	public void draw() {
		Message<List<Piece>> msg = (Message<List<Piece>>) ControllerMessage.getInstance().peek(TypeMessage.highLight);
		
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

}
