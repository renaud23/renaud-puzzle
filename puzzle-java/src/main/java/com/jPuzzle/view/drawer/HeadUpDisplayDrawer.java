package com.jPuzzle.view.drawer;



import com.jPuzzle.view.image.ImageBuffer;





public class HeadUpDisplayDrawer implements IDrawerParametrable<HudParam>{
	
	private ImageBuffer offscreen;
	private HudParam param;

	public HeadUpDisplayDrawer(ImageBuffer offscreen) {
		this.offscreen = offscreen;
	}

	@Override
	public void draw() {
		this.offscreen.transparentClean();
		
	}




	@Override
	public void setParameter(HudParam param) {
		this.param = param;
	}

	
}
