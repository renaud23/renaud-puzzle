package com.puzzle.view.drawer;

import java.awt.Color;
import java.awt.CompositeContext;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class MonCompositeContext implements CompositeContext{
	
	private Color color;
	
	public MonCompositeContext(Color color){
		this.color = color;
	}

	

//
//	public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
//		int[] srcPixel = new int[4];
//		int[] dstPixel = new int[4];
//
//		for (int y = dstIn.getMinY(); y < dstIn.getMinY() +
//			dstIn.getHeight(); y++) {
//			for (int x = dstIn.getMinX(); x < dstIn.getMinX()
//				+ dstIn.getWidth(); x++) {
//				srcPixel = src.getPixel(x, y, srcPixel);
//				
//				dstPixel[0] = ((srcPixel[0] * color.getRed()) >> 8) & 0xFF;
//				dstPixel[1] = ((srcPixel[1] * color.getGreen()) >> 8) & 0xFF;
//				dstPixel[2] = ((srcPixel[2] * color.getBlue()) >> 8) & 0xFF;
//
//				dstPixel[3] = srcPixel[3];
//				
//				
//				if(dstPixel[3] == 255)
//					dstOut.setPixel(x, y, dstPixel);
//			}
//		}
//	}
//	
	private Raster src;
	private Raster dstIn;
	private WritableRaster dstOut;
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	
	public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
		this.src = src;
		this.dstIn = dstIn;
		this.dstOut = dstOut;
		
		int[] srcPixel = new int[4];
		int[] dstPixel = new int[4];

		for (int y = dstIn.getMinY(); y < dstIn.getMinY() +
			dstIn.getHeight(); y++) {
			for (int x = dstIn.getMinX(); x < dstIn.getMinX()
				+ dstIn.getWidth(); x++) {
				
				
//				srcPixel = src.getPixel(x, y, srcPixel);
//				
//				dstPixel[0] = ((srcPixel[0] * color.getRed()) >> 8) & 0xFF;
//				dstPixel[1] = ((srcPixel[1] * color.getGreen()) >> 8) & 0xFF;
//				dstPixel[2] = ((srcPixel[2] * color.getBlue()) >> 8) & 0xFF;
//
//				dstPixel[3] = srcPixel[3];
//				
//				
//				if(dstPixel[3] == 255)
//					dstOut.setPixel(x, y, dstPixel);
				
				dstPixel[0] = 255;
				dstPixel[1] = 0;
				dstPixel[2] = 255;
				dstPixel[3] = 150;
				
				if(this.isBord(x, y)){
					if(this.left){
						dstOut.setPixel(x-1, y, dstPixel);
					}
					if(this.right){
						dstOut.setPixel(x+1, y, dstPixel);
					}
					if(this.up){
						dstOut.setPixel(x, y-1, dstPixel);
					}
					if(this.down){
						dstOut.setPixel(x, y+1, dstPixel);
					}
				}// if
			}
		}
	}
	
	private boolean isBord(int x,int y){
		boolean state = false;
		int[] pixel = new int[4];
		
		this.left = false;
		this.right = false;
		this.up = false;
		this.down = false;
		
		this.src.getPixel(x, y, pixel);
		if(pixel[3] != 0){
			if(x>0){
				this.left = true;
				this.src.getPixel(x-1, y, pixel);
				if(pixel[3] == 0){
					state = true;
					
				}
			}
			
			if(x < (this.src.getWidth()-1)){
				this.right = true;
				this.src.getPixel(x+1, y, pixel);
				if(pixel[3] == 0){
					state = true;
				}
			}
			
			if(y>0){
				this.up = true;
				this.src.getPixel(x, y-1, pixel);
				if(pixel[3] == 0){
					state = true;
					this.up = true;
				}
			}
			
			if(y < (this.src.getHeight()-1)){
				this.right = true;
				this.src.getPixel(x, y+1, pixel);
				if(pixel[3] == 0){
					state = true;
					this.down = true;
				}
			}
		}
		
		return state;
	}
	
	
	public void dispose() {
		
		
	}

}
