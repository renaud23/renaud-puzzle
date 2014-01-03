package com.puzzle.view.tool;


import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class CircleComposite implements Composite,CompositeContext{
	
	private int rayon;
	private boolean nord;
	private boolean ouest;
	
	

	
	public CircleComposite(int rayon) {
		this.rayon = rayon;
	}
	
	
	
	
	

	public CircleComposite(int rayon, boolean nord, boolean ouest) {
		this.rayon = rayon;
		this.nord = nord;
		this.ouest = ouest;
	}






	@Override
	public CompositeContext createContext(ColorModel arg0, ColorModel arg1,
			RenderingHints arg2) {
		return this;
	}

	@Override
	public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {

		
		
		
		int[] srcPixel = new int[4];
		int[] dstPixel = new int[4];
		
		int cx = rayon;//dstIn.getMinX() + dstIn.getWidth()/2;
		int cy = rayon;//dstIn.getMinY() + dstIn.getHeight()/2;
		

		for (int y = dstIn.getMinY(); y < (dstIn.getMinY() +
			dstIn.getHeight()); y++) {
			for (int x = dstIn.getMinX(); x < dstIn.getMinX()
				+ dstIn.getWidth(); x++) {
				
				int vx = 2*rayon - dstIn.getWidth();
				int vy = 2*rayon - dstIn.getHeight();
	
				if(!nord)vy =0;
				if(!ouest)vx =0;
				
				double dist = Math.pow(x+vx-cx, 2)+Math.pow(y+vy-cy, 2);
				dist = Math.sqrt(dist);
				
				if(dist <= rayon){
					srcPixel = src.getPixel(x, y, srcPixel);
					
	
	
					dstPixel[0] = srcPixel[0];
					dstPixel[1] = srcPixel[1];
					dstPixel[2] = srcPixel[2];
					dstPixel[3] = srcPixel[3];
					
					dstOut.setPixel(x, y, dstPixel);
				}else{
//					dstPixel[0] = ((srcPixel[0] * color.getRed()) >> 8) & 0xFF;
//					dstPixel[1] = ((srcPixel[1] * color.getGreen()) >> 8) & 0xFF;
//					dstPixel[2] = ((srcPixel[2] * color.getBlue()) >> 8) & 0xFF;
//					dstPixel[3] = srcPixel[3];
//					
//					dstOut.setPixel(x, y, dstPixel);
				}
				
				
		
			}
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
