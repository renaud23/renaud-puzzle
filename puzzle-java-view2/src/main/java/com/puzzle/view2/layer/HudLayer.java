package com.puzzle.view2.layer;

import java.util.ArrayList;
import java.util.List;

import com.puzzle.view2.DrawOperationAware;
import com.puzzle.view2.image.IDrawOperation;
import com.puzzle.view2.image.IDrawable;
import com.puzzle.view2.widget.Widget;

public class HudLayer implements IDrawable,DrawOperationAware{
	
	private List<Widget> widgets = new ArrayList<>();
	private IDrawOperation op;

	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

	@Override
	public boolean isChange() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setChange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		for(Widget w : this.widgets){
			if(w instanceof DrawOperationAware)((DrawOperationAware)w).setDrawOperation(op);
			if(w instanceof IDrawable)((IDrawable)w).draw();
		}
	}
	
	
	/*
	 * 
	 */
	
	public void addWidget(Widget w){
		this.widgets.add(w);
	}
	
	public void removeWidget(Widget w){
		this.widgets.remove(w);
	}

}
