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

	public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
		int[] srcPixel = new int[4];
		int[] dstPixel = new int[4];

		for (int y = dstIn.getMinY(); y < dstIn.getMinY() +
			dstIn.getHeight(); y++) {
			for (int x = dstIn.getMinX(); x < dstIn.getMinX()
				+ dstIn.getWidth(); x++) {
				
				
				srcPixel = src.getPixel(x, y, srcPixel);
				
				dstPixel[0] = ((srcPixel[0] * color.getRed()) >> 8) & 0xFF;
				dstPixel[1] = ((srcPixel[1] * color.getGreen()) >> 8) & 0xFF;
				dstPixel[2] = ((srcPixel[2] * color.getBlue()) >> 8) & 0xFF;

				dstPixel[3] = srcPixel[3];
				
				
				if(dstPixel[3] == 255)
					dstOut.setPixel(x, y, dstPixel);
				
//				if(srcPixel[3] == 0){
//					dstPixel[0] = 255;
//					dstPixel[1] = 0;
//					dstPixel[2] = 0;
//					dstPixel[3] = 255;
//					dstOut.setPixel(x, y, dstPixel);
//				}
				
				
				
				int posY = 
			}
		}
		
	}


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
	
	
	
	
	public void dispose() {
		
		
	}

}
