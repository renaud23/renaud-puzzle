package com.puzzle.view2.widget;

import com.puzzle.model.Piece;
import com.puzzle.view2.DrawOperationAware;
import com.puzzle.view2.controller.ControllerAdaptater;
import com.puzzle.view2.image.IDrawOperation;
import com.puzzle.view2.image.IDrawable;
import com.puzzle.view2.layer.Pocket;
import com.puzzle.view2.layer.Vue;

public class PieceInPocket extends ControllerAdaptater implements Widget,IDrawable,DrawOperationAware{
	private Piece piece;
	private Pocket pocket;
	private int x;
	private int y;
	private double scaleInit;
	
	private double scale;
	
	
	private IDrawOperation op;
	
	
	
	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}
	
	
	public boolean isChange() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setChange() {
		// TODO Auto-generated method stub
		
	}
	
	public void draw(Vue vue) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean contains(int x, int y) {
		return false;
	}
	
	
	
	
}
