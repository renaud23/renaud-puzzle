package com.renaud.manager;




import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;





public class TasManager<U extends IRectable> {

	private Tas<U> root;
	private Set<U> listeComplete;
	


	public TasManager(int profondeurMax,double x,double y,double largeur, double hauteur) {
		this.root = new Tas<U>(1, profondeurMax, x, y, largeur, hauteur);
		this.listeComplete = new HashSet<U>();
	}
	
	public void clear(){
		this.listeComplete.clear();
		if(this.root != null) this.root.clear();
	}

	public void remove(U e){
		this.listeComplete.remove(e);
		this.root.remove(e);
	}
	
	
	public void put(U e){
		this.listeComplete.add(e);
		this.root.put(e);
	}
	
	
	
	public Set<U> getAll(){
		Set<U> liste = new HashSet<U>();
		liste.addAll(this.listeComplete);
		
		return liste;
	}
	
	
	public List<U> get(IRect r){
		Set<U> set = new HashSet<U>();
		this.root.get(set, r);
		List<U> liste = new ArrayList<U>(set);
		
		return liste;
	}
	
	public List<U> get(double x,double y){
		return this.get(new Rect(x,y,1,1));
	}
	
	
	public TasStatistique getStatistiques(){
		TasStatistique stat = new TasStatistique();
		int nbNode = 0;
		int nbLeaf = 0;
		double ec2 = 0;
		int nbMax = 0;
		int nbMin = Integer.MAX_VALUE;
	
		Stack<Tas<U>> pile = new Stack<>();
		pile.push(this.root);
		
		double total = 0;
		while(!pile.isEmpty()){
			boolean leaf = true;
			Tas<U> crt = pile.pop();
			
			nbNode++;
			
			if(crt.getGauche() != null) {pile.push(crt.getGauche());leaf=false;}
			if(crt.getDroite() != null) {pile.push(crt.getDroite());leaf=false;}
			
			if(leaf){
				nbLeaf++;
				stat.setLargeurLeaf(crt.getWidth());
				stat.setHauteurLeaf(crt.getHeight());
				int n = crt.getElements().size();
				nbMax = Math.max(nbMax, n);
				nbMin = Math.min(nbMin, n);
				total += n;
				ec2 += n*n;
			}
		}
		double mean = total / nbLeaf;
		double var = ec2/nbLeaf - Math.pow(mean, 2);
		stat.setNbLeaf(nbLeaf);
		stat.setNbNode(nbNode);
		stat.setMeanElement(mean);
		stat.setNbMax(nbMax);
		stat.setNbMin(nbMin);
		stat.setVariance(var);
		
		return stat;
	}

}
