package com.puzzle.android;


import java.util.TimerTask;
import com.gl2d.core.renderer.MyRenderer;




public class GameLoop  extends TimerTask{
	
	private float screenLargeur;
	private float screenHauteur;
	private MyRenderer renderer;
	
	


	
	
	
	

	public GameLoop(MyRenderer renderer,float screenLargeur, float screenHauteur) {
		this.screenLargeur = screenLargeur;
		this.screenHauteur = screenHauteur;
		this.renderer = renderer;
		
//		Background bck = new Background(renderer, screenLargeur, screenHauteur);
//		bck.setRect(new RectF(0.2f, 0.4f, 0.3f, 0.2f));
	}








	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
