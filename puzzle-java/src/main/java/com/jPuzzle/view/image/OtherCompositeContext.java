package com.jPuzzle.view.image;

import java.awt.Color;
import java.awt.CompositeContext;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class OtherCompositeContext implements CompositeContext{
	
	Color color = Color.blue;
 
 
	@Override
	public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
		int[] srcPixel = new int[4];
		int[] dstPixel = new int[4];

		for (int y = dstIn.getMinY(); y < dstIn.getMinY() +
			dstIn.getHeight(); y++) {
			for (int x = dstIn.getMinX(); x < dstIn.getMinX()
				+ dstIn.getWidth(); x++) {
				srcPixel = src.getPixel(x, y, srcPixel);
//				
//				dstPixel[0] = ((srcPixel[0] * color.getRed()) >> 8) & 0xFF;
//				dstPixel[1] = ((srcPixel[1] * color.getGreen()) >> 8) & 0xFF;
//				dstPixel[2] = ((srcPixel[2] * color.getBlue()) >> 8) & 0xFF;
//
//				dstPixel[3] = srcPixel[3];
				
				
				dstPixel[0] = dstPixel[0];
				dstPixel[1] = dstPixel[1];
				dstPixel[2] = dstPixel[2];

				dstPixel[3] = dstPixel[3];
//				
//				if(dstPixel[3] == 255)
//					dstOut.setPixel(x, y, dstPixel);
			}
		}
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
