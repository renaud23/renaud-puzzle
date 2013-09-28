package com.jPuzzle.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.JFrame;

import com.jPuzzle.view.basicTapis.TapisBasicControler;
import com.jPuzzle.view.basicTapis.TapisBasicConverter;
import com.jPuzzle.view.basicTapis.TapisBasicDrawer;
import com.jPuzzle.view.drawer.DrawSelection;
import com.jPuzzle.view.drawer.HeadUpDisplayDrawer;
import com.jPuzzle.view.drawer.HudParam;
import com.jPuzzle.view.drawer.IDrawable;
import com.jPuzzle.view.drawer.IDrawer;
import com.jPuzzle.view.drawer.IDrawerParametrable;
import com.jPuzzle.view.drawer.Transformation;
import com.jPuzzle.view.image.ImageBuffer;
import com.jPuzzle.view.image.ImageMemoryManager;
import com.jPuzzle.view.image.Offscreen;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Tapis;



public class MainScreen implements IDrawable<ScreenParam>{
	private JFrame fenetre;
	private Offscreen offscreen;
	private ImageBuffer tapisOffscreen;
	private ImageBuffer hudOffscreen;
	private ImageBuffer selectionBuffer;
	private ScreenParam param;
	private IDrawer tapisDrawer;
	private IDrawerParametrable<HudParam> hudDrawer;
	
	
	
	
	public MainScreen(int largeur,int hauteur){
		this.fenetre = new JFrame("JPuzzle");
		this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.tapisOffscreen = new ImageBuffer(new Color(255,150,150,255), largeur, hauteur);
		this.hudOffscreen = new ImageBuffer(new Color(0,0,0,0), largeur, hauteur);
		
		this.offscreen = new Offscreen(largeur, hauteur);
		this.offscreen.setPreferredSize(new Dimension(largeur,hauteur));
		
		this.fenetre.add(this.offscreen);
		
		this.fenetre.pack();
		this.fenetre.repaint();
		this.fenetre.setVisible(true);
	}

	

	
	
	public void drawTapis(){
		if(this.tapisDrawer != null) this.tapisDrawer.draw();
	}

	public void drawHud(){
		if(this.hudDrawer != null){
//			this.hudDrawer.setParameter(this.point);
			this.hudDrawer.draw();
		}
	}

	@Override
	public void drawSelection() {
		DrawSelection drw = new DrawSelection();
		drw.draw();
		this.selectionBuffer = drw.getBuffer();
	}
	
	
	public void setParam(ScreenParam param){
		this.param = param;
	}
	
	
	public void repaint(){
		// reaffiche
		this.offscreen.drawImage(this.tapisOffscreen.getImage(), 0, 0);
		
		if(!this.param.isMainDroiteVide()){
			this.painSelection();
		}
		this.offscreen.drawImage(this.hudOffscreen.getImage(), 0, 0);
		
		this.fenetre.repaint();
	}

	
	private void painSelection(){
		double x = this.param.getMouseX();
		double ml =  this.selectionBuffer.getLargeur() / 2.0;
	
		x -= ml;
		double y = this.param.getMouseY();
		double mh =  this.selectionBuffer.getHauteur() / 2.0;
	
		y -= mh;
		
		this.offscreen.drawImage(
				this.selectionBuffer.getImage(), 
				x, y, 
				this.param.getMouseX(), this.param.getMouseY(), -this.param.getAngleSelection(), 
				1.0, 1.0, 1.0f);
	}
	
	
	
	public static void main(String[] args){
		MainScreen m = new MainScreen(800,800);
		ImageMemoryManager.getInstance().setPath("E:/git/renaud-puzzle/puzzle-java/src/main/resources/mini_mimie/images/");
		
		int taille = 800;
		Tapis tapis = new Tapis(taille,taille);
		
		Random rnd = new Random();
		for(int i=0;i<2;i++){
			int te = taille - 200;
			Piece p1 = new Piece(1,rnd.nextInt(te)-te/2, rnd.nextInt(te)-te/2,50,43, 100, 86);
			Piece p2 = new Piece(2,rnd.nextInt(te)-(te)/2, rnd.nextInt(te)-te/2,117,43, 86, 86);
			Piece p3 = new Piece(3,rnd.nextInt(te)-(te)/2, rnd.nextInt(te)-te/2,43,110, 86, 100);
			Piece p4 = new Piece(4,rnd.nextInt(te)-(te)/2, rnd.nextInt(te)-te/2, 110,110,100, 100);
			
			tapis.poserPiece(p1);
			tapis.poserPiece(p2);
			tapis.poserPiece(p3);
			tapis.poserPiece(p4);
			
//			
//			p1.setAngle(Math.PI /8.0 *(1+rnd.nextInt(16)));
//			p2.setAngle(Math.PI /8.0 *(1+rnd.nextInt(16)));
//			p3.setAngle(Math.PI /8.0 *(1+rnd.nextInt(16)));
//			p4.setAngle(Math.PI /8.0 *(1+rnd.nextInt(16)));
			
			CompositePiece cmp = new CompositePiece(rnd.nextInt(te)-(te)/2, rnd.nextInt(te)-te/2); 
			tapis.ajouterAComposite(cmp, p1);
			tapis.ajouterAComposite(cmp, p2);
			tapis.ajouterAComposite(cmp, p3);
//			tapis.ajouterAComposite(cmp, p4);
			

		}
		
		
		
		
		TapisBasicControler tc = new TapisBasicControler(tapis);
		tc.setDrawable(m);
		m.getOffscreen().addMouseListener(tc);
		m.getOffscreen().addMouseMotionListener(tc);
		m.getOffscreen().addMouseWheelListener(tc);
		m.setTapisDrawer(new TapisBasicDrawer(tapis, m.getTapisOffscreen()));
		m.setHudDrawer(new HeadUpDisplayDrawer( m.getHudOffscreen() ));
		tapis.addObserver(tc);
		
		
		TapisBasicConverter.getInstance().setTapis(tapis);
		TapisBasicConverter.getInstance().setOffscreen(m.getOffscreen());
		TapisBasicConverter.getInstance().update();
		
		/* ** */
		m.drawTapis();
		m.drawHud();
		m.repaint();
		
		
//		System.out.println(tapis.chercherPiece(0, 0));
//		System.out.println(p3.getRect());
//		System.out.println(p4.getRect());
		
	}

	
	
	
	
	



	

	public Offscreen getOffscreen() {
		return offscreen;
	}

	public void setOffscreen(Offscreen offscreen) {
		this.offscreen = offscreen;
	}

	public ImageBuffer getHudOffscreen() {
		return hudOffscreen;
	}

	public void setHudOffscreen(ImageBuffer hudOffscreen) {
		this.hudOffscreen = hudOffscreen;
	}

	public ImageBuffer getTapisOffscreen() {
		return tapisOffscreen;
	}

	public void setTapisOffscreen(ImageBuffer tapisOffscreen) {
		this.tapisOffscreen = tapisOffscreen;
	}

	public IDrawer getTapisDrawer() {
		return tapisDrawer;
	}

	public void setTapisDrawer(IDrawer tapisDrawer) {
		this.tapisDrawer = tapisDrawer;
	}
	
	public IDrawerParametrable<HudParam> getHudDrawer() {
		return hudDrawer;
	}
	
	public void setHudDrawer(IDrawerParametrable<HudParam> hudDrawer) {
		this.hudDrawer = hudDrawer;
	}





	

	

	
}
