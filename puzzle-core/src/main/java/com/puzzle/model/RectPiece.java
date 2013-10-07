package com.puzzle.model;



import com.renaud.manager.IRect;
import com.renaud.manager.Rect;

public class RectPiece implements MyRect{
	private Piece piece;
	private double x;
	private double y;
	private double largeur;
	private double hauteur;
	private Point[] coins = new Point[4];
	

	public RectPiece(){
		
	}
	
	public RectPiece(Piece piece) {
		this.piece = piece;
		
		this.coins[0] = new Point();
		this.coins[1] = new Point();
		this.coins[2] = new Point();
		this.coins[3] = new Point();
		
		this.update();
	}
	
	public boolean isIn(double x,double y,double largeur,double hauteur){
		return this.isIn(new Rect(x, y, largeur, hauteur));
	}
	
	public boolean isIn(IRect r){
		boolean state = false;
		
		double x = this.piece.getCentre().getX();
		x -= this.piece.getLargeur() / 2.0;
		
		double y = this.piece.getCentre().getY();
		y += this.piece.getHauteur() / 2.0;
		
		double minx = Math.min(x , r.getX());
		double maxx = Math.max(x + this.piece.getLargeur() , r.getX() + r.getLargeur());
		double maxy = Math.max(y , r.getY());
		double miny = Math.min(y - this.piece.getHauteur() , r.getY() - r.getHauteur());
		
		double ecartx = maxx;
		ecartx -= minx;
		
		double ecarty = maxy;
		ecarty -= miny;
		
		double refx = this.piece.getLargeur();
		refx += r.getLargeur();
		double refy = this.piece.getHauteur();
		refy += r.getHauteur();
		
		if(ecartx < refx && ecarty < refy) state = true;
		
		return state;
	}
	
	

	
	@Override
	public boolean contains(double x, double y) {
		boolean state = false;
		Point p = new Point(x,y);
		
		if(p.IsInsideTriangle(this.coins[0],this.coins[1],this.coins[3])){
			state = true;
		}else if(p.IsInsideTriangle(this.coins[1],this.coins[2],this.coins[3])){
			state = true;
		}
		
		return state;
	}
	
	public RectPiece clone(){ 
		RectPiece p = new RectPiece(this.piece);
		p.update();

		return p;
	}
	
	public String toString(){
		return "[RectPiece x="+this.getX()+" y="+this.getY()+" largeur="+this.getLargeur()+" hauteur="+this.getHauteur()+"]";
	}
	
	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}
	
	public double getHauteur() {
		return this.hauteur;
	}
	
	public double getLargeur() {
		return this.largeur;
	}

	@Override
	public void update() {
		double ml = this.piece.getLargeur() / 2.0;
		double mh = this.piece.getHauteur() / 2.0;
		
		this.coins[0].setX(this.piece.getCentre().getX() - ml);
		this.coins[0].setY(this.piece.getCentre().getY() + mh);
		this.coins[1].setX(this.piece.getCentre().getX() + ml);
		this.coins[1].setY(this.piece.getCentre().getY() + mh);
		this.coins[2].setX(this.piece.getCentre().getX() + ml);
		this.coins[2].setY(this.piece.getCentre().getY() - mh);
		this.coins[3].setX(this.piece.getCentre().getX() - ml);
		this.coins[3].setY(this.piece.getCentre().getY() - mh);
		
		this.coins[0].tourner(this.piece.getAngle(), this.piece.getCentre());
		this.coins[1].tourner(this.piece.getAngle(), this.piece.getCentre());
		this.coins[2].tourner(this.piece.getAngle(), this.piece.getCentre());
		this.coins[3].tourner(this.piece.getAngle(), this.piece.getCentre());
		
		double minx = Double.MAX_VALUE;
		double maxx = -Double.MAX_VALUE;
		double miny = Double.MAX_VALUE;
		double maxy = -Double.MAX_VALUE;
		
		for(Point p : this.coins){
			if(p.getX() < minx) minx = p.getX();
			if(p.getX() > maxx) maxx = p.getX();
			if(p.getY() > maxy) maxy = p.getY();
			if(p.getY() < miny) miny = p.getY();
		}
		
		this.x = minx;
		this.y = maxy;
		this.largeur = maxx - minx;
		this.hauteur = maxy - miny;
	}

	public Point[] getCoins() {
		return coins;
	}

	
	
	


}
