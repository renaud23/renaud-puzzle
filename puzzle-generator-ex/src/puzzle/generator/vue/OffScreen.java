package puzzle.generator.vue;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.Scrollable;




/**
 * Surface offscreen ou est affiché le puzzle.
 * @author kqhlz2
 *
 */
public class OffScreen extends JPanel implements Scrollable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3399660292020760145L;
	private BufferedImage image;
	private int largeur;
	private int hauteur;
	private Color backgroundColor;



	/**
	 *
	 */
	public OffScreen (int largeur,int hauteur){
		this.hauteur = hauteur;
		this.largeur = largeur;

		this.image = new BufferedImage(largeur, hauteur,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D graphics = this.image.createGraphics();
		graphics.setPaint(new Color(0,0,0));
		graphics.fillRect(0, 0, largeur, hauteur);

		this.backgroundColor = Color.gray;
	}

	/**
	 *
	 */
	public OffScreen (Image image,int largeur,int hauteur){
		this.hauteur = hauteur;
		this.largeur = largeur;

		this.image = new BufferedImage(largeur, hauteur,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D graphics = this.image.createGraphics();
		graphics.setPaint(new Color(0,0,0));
		graphics.fillRect(0, 0, largeur, hauteur);

		this.drawImage(image, 0, 0, largeur, hauteur, 0.0, 1.0, 1.0f);
	}

	/**
	 *
	 * @param image
	 * @param x
	 * @param y
	 * @param xi
	 * @param yi
	 * @param theta
	 * @param scale
	 * @param alpha
	 */
	public void drawImage(Image image,int x,int y,double xi,double yi,double theta,double scale,float alpha){
		Graphics2D gr = this.image.createGraphics();

		/** Désactivation de l'anti-aliasing */
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		/** Demande de rendu rapide */
		gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);


		 gr.setComposite(AlphaComposite.getInstance(
	                AlphaComposite.SRC_OVER,alpha )) ;

		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();

		t.translate(x*scale, y*scale);
		t.scale(scale, scale);
		gr.rotate(theta, xi*scale, yi*scale);

		gr.drawImage(image,t,null);
	}
	
	public void drawImage(Image image,float alpha){
		Graphics2D gr = this.image.createGraphics();

		/** Désactivation de l'anti-aliasing */
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		/** Demande de rendu rapide */
		gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);

		gr.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER,alpha )) ;
		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();

		gr.drawImage(image,t,null);
	}


	/**
	 * Scale l'image, mais pas la position
	 * @param image
	 * @param x
	 * @param y
	 * @param xi
	 * @param yi
	 * @param theta
	 * @param scale
	 * @param alpha
	 */
	public void drawImageEx(Image image,int x,int y,double xi,double yi,double theta,double scale,float alpha){
		Graphics2D gr = this.image.createGraphics();

		/** Désactivation de l'anti-aliasing */
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		/** Demande de rendu rapide */
		gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
		gr.setComposite(AlphaComposite.getInstance(
	                AlphaComposite.SRC_OVER,alpha )) ;

		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();

		t.translate(x, y);
		t.scale(scale, scale);
		gr.rotate(theta, xi*scale, yi*scale);

		gr.drawImage(image,t,null);
	}
	
	
	public void drawImageMask(Image image,int x,int y,double xi,double yi,double theta,double scale,Color color){
		Graphics2D gr = this.image.createGraphics();

		/** Désactivation de l'anti-aliasing */
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		/** Demande de rendu rapide */
		gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
	 
		 gr.setComposite(new MonComposite(color)) ;
		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();

		t.translate(x, y);
		t.scale(scale, scale);
		gr.rotate(theta, xi*scale, yi*scale);

		gr.drawImage(image,t,null);
	}
	
	

	
	/**
	 * 
	 * @param image
	 * @param x
	 * @param y
	 * @param scale
	 * @param alpha
	 */
	public void drawImageScale(Image image,int x,int y,
			double scale,float alpha){
		Graphics2D gr = this.image.createGraphics();

		 gr.setComposite(AlphaComposite.getInstance(
	                AlphaComposite.SRC_OVER,alpha )) ;
		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();
		t.translate(x, y);
		t.scale(scale, scale);
//		gr.rotate(theta, xi*scale, yi*scale);
		gr.drawImage(image,t,null);
	}
	

	/**
	 *
	 *
	 */
	public void clean(){
		Graphics2D graphics = this.image.createGraphics();
		graphics.setColor(this.backgroundColor);//System.out.println(this.color);
		graphics.fillRect(0, 0, largeur, hauteur);
	}

	protected void paintComponent(Graphics g){
		g.setColor(Color.gray);
		g.fillRect(0, 0, this.image.getWidth(), this.image.getHeight());
		g.drawImage(this.image,0,0,null);
	}



	public void setTaille(Dimension dim){
		this.largeur = dim.width;
		this.hauteur = dim.height;
		this.image = new BufferedImage(this.largeur,this.hauteur,
				BufferedImage.TYPE_INT_ARGB);
		
	}



	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

    public int getLargeur(){return largeur;}
    public int getHauteur(){return hauteur;}



    /**
     *
     *
     */
    public Dimension getPreferredSize() {
    	try{
//    		int x = Screen.getPreference().getDimOffscreen().width;
//    		//x *= Screen.getPuzzle().getScale();
//    		int y = Screen.getPreference().getDimOffscreen().height;
//    		//y *= Screen.getPuzzle().getScale();
    		return new Dimension(this.largeur, this.hauteur);
    	}catch(NullPointerException e){
    		return new Dimension(800, 600);
    	}
    }

    public Dimension getPreferredScrollableViewportSize() {

        return getPreferredSize();
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect,
                                          int orientation,
                                          int direction) {

//        //Get the current position.
//        int currentPosition = 0;
//        if (orientation == SwingConstants.HORIZONTAL) {
//            currentPosition = visibleRect.x;
//        } else {
//            currentPosition = visibleRect.y;
//        }
//
//        //Return the number of pixels between currentPosition
//        //and the nearest tick mark in the indicated direction.
//        if (direction < 0) {
//            int newPosition = currentPosition -
//                             (currentPosition / maxUnitIncrement)
//                              * maxUnitIncrement;
//            return (newPosition == 0) ? maxUnitIncrement : newPosition;
//        } else {
//            return ((currentPosition / maxUnitIncrement) + 1)
//                   * maxUnitIncrement
//                   - currentPosition;
//        }
    	return 1;
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect,
                                           int orientation,
                                           int direction) {
//        if (orientation == SwingConstants.HORIZONTAL) {
//            return visibleRect.width - maxUnitIncrement;
//        } else {
//            return visibleRect.height - maxUnitIncrement;
//        }
    	return 1;
    }

    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    public boolean getScrollableTracksViewportHeight() {
        return false;
    }


    int maxUnitIncrement = 1;



	public Color getBackgroundColor() {
		return backgroundColor;
	}


	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}



}
