package com.example.view;

import java.util.Random;
import java.util.TimerTask;

public class Test extends TimerTask{
	
	private float screenLargeur;
	private float screenHauteur;
	
	
	public Test(CustomRenderer renderer,float screenLargeur,float screenHauteur){
		
	this.screenLargeur = screenLargeur;
	this.screenHauteur = screenHauteur;
	
      for(int i=0;i<10;i++){
    	Random rnd = new Random();
    	float x = rnd.nextInt((int) screenLargeur-100)+50;
    	float y = rnd.nextInt((int) screenHauteur-100)+50;
    	
    	DrawElement e = new DrawElement(0,x, y, 50.0f, 50.0f);
    	renderer.addRenderable(e);
      }
	}
	

	@Override
	public void run() {
		System.out.println("rtoto");
		
	}

}
