package com.puzzle.view2.later.state;

import java.awt.Color;
import java.awt.Image;

import com.puzzle.model.ComponentPiece;
import com.puzzle.view2.image.IDrawOperation;
import com.puzzle.view2.image.ImageProvider;
import com.puzzle.view2.layer.Vue;
import com.sun.xml.internal.ws.spi.ProviderImpl;

public class clipsAnimation implements IAnimation{
	
	private IDrawOperation op;
	private ComponentPiece component;
	private double x;
	private double y;
	
	private long duree;
	private long ellapsed;
	private long current;
	
	private double angle;
	private double scale;

	public clipsAnimation(ComponentPiece component, double x, double y,double scale) {
		this.component = component;
		this.x = x;
		this.y = y;
		this.scale = scale;
		
		this.duree = 100l;
		this.current = System.currentTimeMillis();
		this.angle = -component.getAngle();
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
	public void draw(Vue vue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

	@Override
	public void play() {
		Image img = ImageProvider.getInstance().getImage(this.component);
		
		if(img != null){
			double xi = x - scale * img.getWidth(null) / 2.0;
			double yi = y - scale * img.getHeight(null) / 2.0;
		
			this.op.drawImage(img, xi, yi, x, y, this.angle, this.scale, 1.0f);
			
//			this.op.fillRect(Color.black, xi, yi, 2, 2, 1.0f);
//			this.op.fillRect(Color.red, x, y, 2, 2, 1.0f);
		}
	}

	@Override
	public boolean isFinish() {
		boolean finished = false;
		long c = System.currentTimeMillis();
		long interval = c;
		interval -= this.current;
		this.current = c;
		this.ellapsed += interval;
		
		this.angle += 0.5;
		this.scale *= 1.01;
		
		
		if(this.ellapsed >= this.duree) finished = true;
		
		return finished;
	}

}
