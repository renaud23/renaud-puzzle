package com.renaud.manager;



import java.util.ArrayList;
import java.util.List;
import java.util.Set;



public class Tas<U extends IRectable> {
	
	private Rect r;
	private double x;
	private double y;
	private double height;
	private double width;
	private List<U> elements;
	
	private Tas<U> gauche = null;
	private Tas<U> droite = null;
	
	
	
	
	
	public Tas(int profondeur,int profondeurMax,double x, double y,  double width,double height) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.r = new Rect(this.x,this.y,this.width,this.height);

		if(profondeur < profondeurMax){
			if( (profondeur % 2) == 0){
				double hau = this.height/2.0;
				
				this.gauche = new Tas<U>(profondeur+1,profondeurMax, this.x, this.y, this.width, hau);
				this.droite = new Tas<U>(profondeur+1,profondeurMax, this.x, this.y-hau, this.width,hau);
			}else{
				double lar = this.width / 2.0;
				
				this.gauche = new Tas<U>(profondeur+1,profondeurMax, this.x, this.y, lar, this.height);
				this.droite = new Tas<U>(profondeur+1,profondeurMax, this.x+lar, this.y, lar, this.height);
			}
		}else{
			this.elements = new ArrayList<U>();
		}
	}
	
	

	
	public void put(U e){
		if(this.elements != null){
			this.elements.add(e);
		}else{
			IRect rect = e.getRect();
			if(rect.isIn(this.gauche.r)){
				this.gauche.put(e);
			}
			if(rect.isIn(this.droite.r)){
				this.droite.put(e);
			}
		}
	}
	
	
	public void remove(U e){
		if(this.elements != null) this.elements.remove(e);
		else{
			this.gauche.remove(e);
			this.droite.remove(e);
		}
	}
	
	
	public List<U> getElements() {
		return elements;
	}


	/**
	 * met tout les element 
	 * @param elmts
	 * @param rect
	 */
	public void get(Set<U> elmts,IRect rect){
		if(this.elements != null){
			elmts.addAll(this.elements);
		}else{
			if(rect.isIn(this.gauche.r))
					this.gauche.get(elmts,rect);

			if(rect.isIn(this.droite.r))
					this.droite.get(elmts,rect);
		}	
	}
	
	


	public void getAll(Set<U> e){
		if(this.elements != null){
			e.addAll(this.elements);
		}else{
			this.droite.getAll(e);
			this.gauche.getAll(e);
		}
	}
	
	
	public void clear(){
		if(this.elements != null) this.elements.clear();
		else{
			if(this.droite != null) this.droite.clear();
			if(this.gauche != null) this.gauche.clear();
		}
	}

}
