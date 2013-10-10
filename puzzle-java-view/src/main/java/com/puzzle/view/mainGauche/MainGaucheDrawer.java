package com.puzzle.view.mainGauche;

import java.awt.Color;
import java.awt.Image;

import com.puzzle.model.MainGauche;
import com.puzzle.model.MyRect;
import com.puzzle.model.Piece;
import com.puzzle.view.drawer.IDrawer;
import com.puzzle.view.drawer.IDrawerParametrable;
import com.puzzle.view.tool.ImageBuffer;
import com.puzzle.view.tool.ImageMemoryManager;

public class MainGaucheDrawer implements IDrawerParametrable<Integer>{
	
	private ImageBuffer buffer;
	private int margeHorizontale;
	private int margeVerticale;
	private int largeur;
	private int marge;
	private int focused;

	public MainGaucheDrawer(ImageBuffer buffer) {
		this.buffer = buffer;
		this.focused = -1;
		this.init();
	}

	@Override
	public void draw() {
		
		int i = 0;
		double scale = 0.0;
		Piece pFocused = null;
		Image imgFocused = null;
		double px =0, py =0;
		float alpha = 1.0f;
		if(this.focused != -1) alpha = 0.5f;
		for(Piece p : MainGauche.getInstance()){
			if(i == 0) scale =  this.largeur / p.getLargeur();
			
//			Image img = ImageMemoryManager.getInstance().getImage(p.getId());
			Image img = ImageMemoryManager.getInstance().get(p.getPuzzle().getId()).getImage(p.getId());
			
			double xi = this.margeVerticale;
			xi += this.largeur / 2.0;
			double x = xi - p.getLargeur() / 2.0 * scale;
			double yi = this.margeHorizontale;
			yi += i * this.marge;
			double y = yi;
			y -= p.getHauteur() / 2.0 * scale;
			
			if(this.focused != i){
			this.buffer.drawImage(
					img, 
					x,y, 
					xi, yi, -p.getAngle(), 
					scale, scale, alpha);
			}else{
				pFocused = p;
				imgFocused = img;
				px = xi;
				py = yi;
			}
			
			i++;
		}
		
		if(pFocused != null){
			scale = this.buffer.getLargeur() / pFocused.getRect().getLargeur();
			double x = px - pFocused.getLargeur() / 2.0 *scale;
			double y = py - pFocused.getHauteur() / 2.0 *scale;
			
//			this.buffer.drawImageMask(imgFocused, x, y, px, py, -pFocused.getAngle(), scale, scale, Color.red);
			
			this.buffer.drawImage(
					imgFocused, 
					x,y, 
					px, py, -pFocused.getAngle(), 
					scale, scale, 1.0f);
			
			
		}
	}

	@Override
	public void clean() {
		this.buffer.clean();
	}

	
	private void init(){
		this.margeHorizontale = (int)(0.08 * this.buffer.getHauteur());
		this.margeVerticale = (int)(0.25 * this.buffer.getLargeur());
		
		int s = MainGauche.getInstance().getSize();
		this.largeur = this.buffer.getLargeur() - 2 * this.margeVerticale;
		this.marge = (this.buffer.getHauteur() - largeur) / s;
	}

	@Override
	public void setParam(Integer param) {
		this.focused = param;
	}
}
