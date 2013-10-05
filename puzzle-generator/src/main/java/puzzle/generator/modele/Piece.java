package puzzle.generator.modele;

import java.awt.Image;


/**
 * Objet symbolique permettant de calculer la position de chaque pièce,
 * avant leur génération.
 * @author kqhlz2
 *
 */
public class Piece {
	int id;
	int x;
	int y;
	Image image;


	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
