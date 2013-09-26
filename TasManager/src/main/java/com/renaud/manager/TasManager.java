package com.renaud.manager;




import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;





public class TasManager<U extends IRectable> {

	private Tas<U> root;

	public TasManager(int profondeurMax,double x,double y,double largeur, double hauteur) {
		this.root = new Tas<U>(1, profondeurMax, x, y, largeur, hauteur);
	}
	
	

	public void clear(){
		if(this.root != null) this.root.clear();
	}

	public void remove(U e){
		this.root.remove(e);
	}
	
	
	public void put(U e){
		this.root.put(e);
	}
	
	
	/**
	 * rempli la liste fournie.
	 * @param e
	 */
	public Set<U> getAll(){
		Set<U> liste = new HashSet<U>();
		this.root.getAll(liste);
		
		return liste;
	}
	
	
	public Set<U> get(Set<U> elmts,U e){
		return this.get(e.getRect());
	}
	
	
	public Set<U> get(IRect r){
		Set<U> set = new HashSet<U>();
		this.root.get(set, r);
		
		return set;
	}
	
	public Set<U> get(double x,double y){
		return this.get(new Rect(x,y,1,1));
	}

	
	public List<U> getRootList(){
		return this.root.getElements();
	}

}
