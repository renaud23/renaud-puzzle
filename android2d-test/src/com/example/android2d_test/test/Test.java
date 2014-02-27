package com.example.android2d_test.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

import com.example.view.MyRenderer;
import com.example.view.RenderableElement;

public class Test extends TimerTask{
	
	private float screenLargeur;
	private float screenHauteur;
	private List<Bot> bots = new ArrayList<Bot>();
	
	public Test(MyRenderer renderer,float screenLargeur,float screenHauteur){
		
	this.screenLargeur = screenLargeur;
	this.screenHauteur = screenHauteur;
	
      for(int i=0;i<30;i++){
    	Random rnd = new Random();
    	
    	float x = rnd.nextInt((int) screenLargeur-100)+50;
    	float y = rnd.nextInt((int) screenHauteur-100)+50;
    	float angle = rnd.nextInt(628) / 100.0f;
    	
    	Bot e = new Bot(x,y);
    	e.turn(angle);
    	this.bots.add(e);
    	renderer.addRenderable(e.getDrawble());
      }
	}
	

	@Override
	public void run() {
		for(Bot b : this.bots){
			float nx = b.getNextX();
			float ny = b.getNextY();
			boolean change = false;
			
			
//			b.moveTo(nx, ny);
		}
		
	}

}
