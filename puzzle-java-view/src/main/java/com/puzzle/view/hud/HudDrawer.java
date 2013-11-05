package com.puzzle.view.hud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.puzzle.view.drawer.DrawSelectionParam;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.zoomTapis.DrawZoomSelection;
import com.puzzle.view.zoomTapis.Lunette;


public class HudDrawer  extends DrawZoomSelection{
	
	private DrawZoomSelection drawerSelection;
	private List<IDrawer> drawable = new ArrayList<IDrawer>();
	
	
	
	
	public HudDrawer(DrawZoomSelection drawerSelection) {
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
		
		this.drawerSelection.clean();
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

	@Override
	public void drawZoom() {
		this.drawerSelection.drawZoom();
	}

	@Override
	public double getZoomScale() {
		return this.drawerSelection.getZoomScale();
	}

	@Override
	public void setZoomScale(double zoomScale) {
		this.drawerSelection.setZoomScale(zoomScale);
	}

	
	
	

}
