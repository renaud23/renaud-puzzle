package puzzle.generator.modele;

/**
 * Element de config du tapis de jeu par défaut.
 * @author kqhlz2
 *
 */
public class Tapis {
	
	public static Tapis DEFAULT_TAPIS;
	
	static{
		DEFAULT_TAPIS = new Tapis();
		
		DEFAULT_TAPIS.largeur = 2000;
		DEFAULT_TAPIS.hauteur = 2000;
		DEFAULT_TAPIS.r = 100;
		DEFAULT_TAPIS.g = 100;
		DEFAULT_TAPIS.b = 100;
	}
	
	
	private int largeur;
	private int hauteur;
	private int r;
	private int g;
	private int b;
	
	
	
	public int getLargeur() {
		return largeur;
	}
	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}
	public int getHauteur() {
		return hauteur;
	}
	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}
	public int getR() {
		return r;
	}
	public void setR(int r) {
		this.r = r;
	}
	public int getG() {
		return g;
	}
	public void setG(int g) {
		this.g = g;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
	
	
}
