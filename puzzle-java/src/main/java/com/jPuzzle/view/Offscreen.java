package com.jPuzzle.view;



import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.VolatileImage;
import javax.swing.JPanel;
import javax.swing.Scrollable;





/**
 * Surface offscreen ou est affiché le puzzle.
 * @author Genevois
 *
 */
public class Offscreen extends JPanel implements Scrollable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3399660292020760145L;
	private VolatileImage image;
	private int largeur;
	private int hauteur;
	private Color backgroundColor;

	private static GraphicsConfiguration gc = 
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();


	public Offscreen (Color color,int largeur,int hauteur){
		this.hauteur = hauteur;
		this.largeur = largeur;

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
		this.image = gc.createCompatibleVolatileImage(this.largeur, this.hauteur);

		Graphics2D graphics = this.image.createGraphics();
		graphics.setPaint(new Color(0,0,0));
		graphics.fillRect(0, 0, largeur, hauteur);

		this.backgroundColor = color;
		this.clean();
		
	}
	
	/**
	 *
	 */
	public Offscreen (int largeur,int hauteur){
		this.hauteur = hauteur;
		this.largeur = largeur;

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
		this.image = gc.createCompatibleVolatileImage(this.largeur, this.hauteur);

		Graphics2D graphics = this.image.createGraphics();
		graphics.setPaint(new Color(0,0,0));
		graphics.fillRect(0, 0, largeur, hauteur);

		this.backgroundColor = new Color(56,203,163);
		this.clean();
		
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
	public void drawImage(Image image,double x,double y,double xRotation,double yRotation,double theta,double scale,float alpha){
		
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
		 
		 if(this.image.contentsLost())
			 this.image.validate(gc);

		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();

		t.translate(x, y);
		t.scale(scale, scale);
		gr.rotate(theta, xRotation*scale, yRotation*scale);

		gr.drawImage(image,t,null);
		gr.dispose();

	}


public void drawImage(Image image,double x,double y,double xRotation,double yRotation,double theta,double scaleX,double scaleY,float alpha){
		
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
		 
		 if(this.image.contentsLost())
			 this.image.validate(gc);

		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();

		t.translate(x, y);
		t.scale(scaleX, scaleY);
		gr.rotate(theta, xRotation*1.0, yRotation*1.0);

		gr.drawImage(image,t,null);
		gr.dispose();

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
	 * @param color
	 */
	public void drawImageMask(Image image,int x,int y,double xi,double yi,double theta,double scale,Color color){
		Graphics2D gr = this.image.createGraphics();

		/** Désactivation de l'anti-aliasing */
//		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
//		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
//		/** Demande de rendu rapide */
//		gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
//		gr.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
//		gr.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
//		gr.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
	 
//		gr.setComposite(AlphaComposite.getInstance(
//                AlphaComposite.SRC_OVER,1.0f )) ;
		gr.setComposite(new MonComposite(color)) ;
		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();

		t.translate(x, y);
		t.scale(scale, scale);
		gr.rotate(theta, xi*scale, yi*scale);

		gr.drawImage(image,t,null);
		gr.dispose();

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
	
	/**
	 * 
	 * @param r
	 */
	public void clean(Rectangle r){
		Graphics2D graphics = this.image.createGraphics();
		graphics.setColor(this.backgroundColor);
		graphics.fillRect(r.x, r.y, r.width, r.height);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void fill(Color color,int x,int y,int width,int height){
		Graphics2D graphics = this.image.createGraphics();
		graphics.setColor(color);
		graphics.fillRect(x, y, width, height);
	}
	
	public void fill(Color color,Rectangle r){
		Graphics2D graphics = this.image.createGraphics();
		graphics.setColor(color);
		graphics.fillRect(r.x, r.y, r.width, r.height);
	}
	
	/**
	 * 
	 * @param color
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void drawRect(Color color,int x,int y,int width,int height){
		Graphics2D g = this.image.createGraphics();
		g.setColor(color);
		g.drawRect(x, y, width, height);
	}
	
	/**
	 * 
	 * @param color
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void drawFilledRect(Color color,int x,int y,int width,int height){
		Graphics2D g = this.image.createGraphics();
		 g.setComposite(AlphaComposite.getInstance(
	                AlphaComposite.SRC_OVER,0.2f )) ;
		g.setColor(color);
		g.drawRect(x, y, width, height);
		g.setColor(Color.gray);
		g.fillRect(x+1, y+1, width-1, height-1);
	}

	/**
	 * 
	 */
	protected void paintComponent(Graphics g){
		g.setColor(Color.gray);
		g.fillRect(0, 0, this.image.getWidth(), this.image.getHeight());
		g.drawImage(this.image,0,0,null);
	}



	public void setTaille(Dimension dim){
		this.largeur = dim.width;
		this.hauteur = dim.height;
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
		VolatileImage buff = gc.createCompatibleVolatileImage(this.largeur, this.hauteur);

		Graphics g = buff.getGraphics();
		g.drawImage(this.image, 0, 0, this.largeur,this.hauteur,
				0, 0, this.image.getWidth(), this.image.getHeight(), null);
		
		this.image = buff;
	}



	public Image getImage() {
		return image;
	}

//	public void setImage(BufferedImage image) {
//		this.image = image;
//	}

    public int getLargeur(){return largeur;}
    public int getHauteur(){return hauteur;}



    /**
     *
     *
     */
    public Dimension getPreferredSize() {
    	try{
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
