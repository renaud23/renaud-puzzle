package com.puzzle.view.hud;

import java.util.ArrayList;
import java.util.List;
import com.puzzle.view.controller.TapisConverter;
import com.puzzle.view.drawer.DrawSelection;
import com.puzzle.view.drawer.DrawSelectionParam;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.tool.JImageBuffer;


public class HudDrawer  extends DrawSelection {
	
	

	private DrawSelection drawerSelection;
	private List<IDrawer> drawable = new ArrayList<IDrawer>();
	
	public HudDrawer(DrawSelection drawerSelection,JImageBuffer buffer, TapisConverter converter) {
		super(buffer, converter);
		this.drawerSelection = drawerSelection;
	}


	public void addDrawer(IDrawer d){
		this.drawable.add(d);
	}
	
	public void removeDrawer(IDrawer d){
		this.drawable.remove(d);
	}

	@Override
	public void draw() {
		this.drawerSelection.draw();
		for(IDrawer d : this.drawable) d.draw();
	}

	@Override
	public void clean() {
		this.drawerSelection.clean();
		for(IDrawer d : this.drawable) d.clean();
	}

	@Override
	public void setParam(DrawSelectionParam param) {
		this.drawerSelection.setParam(param);
	}

	@Override
	public void setSelection(boolean selection) {
		this.drawerSelection.setSelection(selection);
	}




	
}
