package puzzle.generator.modele;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import puzzle.generator.descriptor.PieceDescripteur;
import puzzle.generator.memory.ImageProvider;
import puzzle.generator.memory.TemplateMemoryManager;
import puzzle.generator.modele.frame.FrameEx;
import puzzle.generator.utilitaire.FileUtils;






/**
 * Classe charg�e de construire les pi�ce � partir
 * de la frame et e l'image.
 * @author kqhlz2
 *
 */
public class GeneratorEx {

//	private static Map<Integer, MyImage> images;
	private ImageProvider templateProvider;
	
	private FrameEx frame;
	private PieceDescripteur decriptor;
	private BufferedImage imageOriginale;
	private BufferedImage imageScaled;
	private double finalWidth;
	private double finalHeight;
	private double scalePiece;
	
	private int tamponWidth;
	private int tamponHeight;
	private double tamponRatio;
	private BufferedImage tampon;
	

	
	/**
	 * 
	 */
	private void loadTemplate(){
//		GeneratorEx.images = new HashMap<Integer, MyImage>();
//		for(int i=0;i<256;i++){
//			String path = MainScreen.getInstance().getPuzzle().getDescriptor().getPath()+"/"+i+".png";
//			File file = new File(path);
//			if(file.exists()){
//				MyImage image = new MyImage(path);
//				GeneratorEx.images.put(new Integer(i), image);
//			}
//		}
		this.templateProvider = TemplateMemoryManager.getInstance();
		((TemplateMemoryManager)this.templateProvider).setDirectory(this.decriptor.getPath());
	}
	
	public GeneratorEx(FrameEx frame, PieceDescripteur descriptor,BufferedImage image,double scalePiece){
		this.frame = frame;
		this.decriptor = descriptor;
		this.imageOriginale = image;
		this.scalePiece = scalePiece;
		
		this.finalWidth = this.decriptor.getPieceCote() * this.frame.getLargeur() * this.scalePiece;
		this.finalHeight = this.decriptor.getPieceCote() * this.frame.getHauteur() * this.scalePiece;

		this.tamponWidth = 1000;//(int) this.finalWidth;
		this.tamponHeight =  (int) ((this.decriptor.getPieceCote() + 2 * this.decriptor.getPieceVarMax() ) * this.scalePiece);
		this.tamponRatio = (double)this.imageOriginale.getHeight() /(double)this.finalHeight ;	
		this.tampon = new BufferedImage(
				this.tamponWidth, 
				this.tamponHeight, BufferedImage.TYPE_INT_ARGB);
	}
	
	/**
	 * Cr�e les pi�c�s et les enregiste � l'adresse fournit.
	 * Fait le m�nage avant tout, attention !
	 * @param path
	 */
	public void genereAndSave(String buildPath){
		this.loadTemplate();
		this.cleanDirectory(buildPath);
		int tamponY = 0;
		for(int j=0;j<this.frame.getHauteur();j++){
			int tamponX = 0;
			int nextY = Integer.MAX_VALUE;
			this.fillBuffer(tamponY,tamponX);
			for(int i=0;i<this.frame.getLargeur();i++){
				Rectangle rect = this.getRectangle(i, j);

				if(j < (this.frame.getHauteur()-1)){
					Rectangle rectNext = this.getRectangle(i, j+1);
					int yi = rectNext.y;
					yi -= tamponY;
					if(yi<nextY)nextY=yi;
				}
				if((rect.x+rect.width) > (tamponX+this.tamponWidth)){
					tamponX = rect.x;
					this.fillBuffer(tamponY,tamponX);
				}
				
				if(this.frame.getCell(i, j).getType() != FrameEx.case_vide){
					BufferedImage img =  createImagePieceEx(tamponY,tamponX,i,j, rect);
					int id = 1+j*this.frame.getLargeur()+i;
					//
					FileUtils.savePng(img, buildPath, String.valueOf(id));
				}
			}// type != 0
			tamponY += nextY;
		}
		
	}
	
	/**
	 * G�n�re un jeu de piece dans un tampon en m�moire.
	 * @param buildPath r�pertoire de travail.
	 * @return
	 */
	public Map<Integer, BufferedImage> genere(){
		this.loadTemplate();
		this.scaling();
		
		Map<Integer, BufferedImage> output = new HashMap<Integer, BufferedImage>();

		int tamponY = 0;
		for(int j=0;j<this.frame.getHauteur();j++){
			int tamponX = 0;
			int nextY = Integer.MAX_VALUE;
			this.fillBuffer(tamponY,tamponX);
			for(int i=0;i<this.frame.getLargeur();i++){
				Rectangle rect = this.getRectangle(i, j);

				if(j < (this.frame.getHauteur()-1)){
					Rectangle rectNext = this.getRectangle(i, j+1);
					int yi = rectNext.y;
					yi -= tamponY;
					if(yi<nextY)nextY=yi;
				}
				if((rect.x+rect.width) > (tamponX+this.tamponWidth)){
					
					tamponX = rect.x;
					this.fillBuffer(tamponY,tamponX);
				}
				
				
				BufferedImage img =  createImagePieceEx(tamponY,tamponX,i,j, rect);
				int id = 1+j*this.frame.getLargeur()+i;
				output.put(id, img);
//				FileUtils.savePng(img, "test/", String.valueOf(i+j*this.frame.getLargeur()));
			}
			tamponY += nextY;
		}
		
		return output;
	}
	
	/**
	 * Rempli le tampon avec la prochaine ligne de g�n�ration de pi�ces.
	 * @param y
	 */
	private void fillBuffer(int y,int x){
		Graphics g = this.tampon.getGraphics();
	
		
		g.drawImage(this.imageOriginale, 0, 0, this.tamponWidth, this.tamponHeight, 
				(int) (x*this.tamponRatio), (int) (y*this.tamponRatio), 
				(int) ((x+this.tamponWidth )* this.tamponRatio), (int) ((y+this.tamponHeight )* this.tamponRatio), null);
	
//		FileUtils.savePng(this.tampon, "test/","y_"+y+"_"+x);
	}
	
	/**
	 * assemble puzzle � sa taille dans une image.
	 * @return
	 */
	public BufferedImage makeVue(String pathImagesPieces,double scaleVue){
		BufferedImage img = new BufferedImage(
				(int)(this.finalWidth * scaleVue), 
				(int)(this.finalHeight * scaleVue), 
				BufferedImage.TYPE_INT_ARGB );
		
		Graphics2D g = img.createGraphics();
		
		double minVar = this.decriptor.getPieceVarMin() * this.scalePiece;
		double maxVar = this.decriptor.getPieceVarMax() * this.scalePiece;
		AffineTransform t = new AffineTransform();
		
		for(int j=0;j<this.frame.getHauteur();j++){
			for(int i=0;i<this.frame.getLargeur();i++){
				int value = this.frame.getFrame()[i][j];
				double x = i * this.decriptor.getPieceCote() * this.scalePiece;
				double y = j * this.decriptor.getPieceCote() * this.scalePiece;
				
				if( (value&FrameEx.nord) == FrameEx.nord){
					y -= maxVar;
				}else if( (value&FrameEx.cote_nord) != FrameEx.cote_nord){
					y -= minVar;
				}
				if( (value&FrameEx.ouest) == FrameEx.ouest){
					x -= maxVar;
				}else if( (value&FrameEx.cote_ouest) != FrameEx.cote_ouest){
					x -= minVar;
				}
				
				//
				int codeTemplate =1+i+j*this.frame.getLargeur();
				BufferedImage source = FileUtils.getPieceImage(codeTemplate, pathImagesPieces);
				t.setToIdentity();
				t.translate(x*scaleVue, y*scaleVue);
				t.scale(scaleVue, scaleVue);
				
				g.drawImage(source,t,null);
			}
		}
		return img;
	}
	
	
	/**
	 * assemble puzzle � sa taille dans une image.
	 * @return
	 */
	public BufferedImage makeApercu(Map<Integer,BufferedImage> images){
		BufferedImage img = new BufferedImage(
				(int)this.finalWidth, 
				(int)this.finalHeight, 
				BufferedImage.TYPE_INT_ARGB );
		
		Graphics2D g = img.createGraphics();
		
		double minVar = this.decriptor.getPieceVarMin() * this.scalePiece;
		double maxVar = this.decriptor.getPieceVarMax() * this.scalePiece;
		AffineTransform t = new AffineTransform();
		
		for(int j=0;j<this.frame.getHauteur();j++){
			for(int i=0;i<this.frame.getLargeur();i++){
				int value = this.frame.getFrame()[i][j];
				double x = i * this.decriptor.getPieceCote() * this.scalePiece;
				double y = j * this.decriptor.getPieceCote() * this.scalePiece;
				
				if( (value&FrameEx.nord) == FrameEx.nord){
					y -= maxVar;
				}else if( (value&FrameEx.cote_nord) != FrameEx.cote_nord){
					y -= minVar;
				}
				if( (value&FrameEx.ouest) == FrameEx.ouest){
					x -= maxVar;
				}else if( (value&FrameEx.cote_ouest) != FrameEx.cote_ouest){
					x -= minVar;
				}
				
				//
				int codeTemplate =1+i+j*this.frame.getLargeur();
				BufferedImage source = images.get(codeTemplate);
				t.setToIdentity();
				t.translate(x, y);
				
				g.drawImage(source,t,null);
			}
		}
		return img;
	}
	
	
	/**
	 * assemble puzzle � sa taille dans une image.
	 * @return
	 */
	public BufferedImage makeAper�u(Map<Integer,BufferedImage> images,double scaleTo){
		BufferedImage img = new BufferedImage(
				(int)(this.finalWidth * scaleTo), 
				(int)(this.finalHeight * scaleTo), 
				BufferedImage.TYPE_INT_ARGB );
		
		Graphics2D g = img.createGraphics();
		
		double minVar = this.decriptor.getPieceVarMin() * this.scalePiece;
		double maxVar = this.decriptor.getPieceVarMax() * this.scalePiece;
		AffineTransform t = new AffineTransform();
		
		for(int j=0;j<this.frame.getHauteur();j++){
			for(int i=0;i<this.frame.getLargeur();i++){
				int value = this.frame.getFrame()[i][j];
				double x = i * this.decriptor.getPieceCote() * this.scalePiece;
				double y = j * this.decriptor.getPieceCote() * this.scalePiece;
				
				if( (value&FrameEx.nord) == FrameEx.nord){
					y -= maxVar;
				}else if( (value&FrameEx.cote_nord) != FrameEx.cote_nord){
					y -= minVar;
				}
				if( (value&FrameEx.ouest) == FrameEx.ouest){
					x -= maxVar;
				}else if( (value&FrameEx.cote_ouest) != FrameEx.cote_ouest){
					x -= minVar;
				}
				
				//
				int codeTemplate =1+i+j*this.frame.getLargeur();
				BufferedImage source = images.get(codeTemplate);
				t.setToIdentity();
				t.translate(x*scaleTo, y*scaleTo);
				t.scale(scaleTo, scaleTo);
				
				g.drawImage(source,t,null);
			}
		}
		return img;
	}
	
	/**
	 * Calcul les coordonn�e du rectangle � saisir dans le puzzle final.
	 * @param i
	 * @param j
	 * @return
	 */
	private Rectangle getRectangle(int i,int j){
		Rectangle rect = new Rectangle();
		int value = this.frame.getFrame()[i][j];
		// initialisation.
		double w = this.decriptor.getPieceCote() * this.scalePiece;
		double h = w;
		double x = i * w;
		double y = j * w;
		
		double minVar = this.decriptor.getPieceVarMin() * this.scalePiece;
		double maxVar = this.decriptor.getPieceVarMax() * this.scalePiece;
		
		// calcul selon le type de la pi�ce.
		if( (value&FrameEx.nord) == FrameEx.nord){
			h += maxVar;
			y -= maxVar;
		}else if( (value&FrameEx.cote_nord) != FrameEx.cote_nord){
			h += minVar;
			y -= minVar;
		}
		if( (value&FrameEx.sud) == FrameEx.sud){
			h += maxVar;
		}else if( (value&FrameEx.cote_sud) != FrameEx.cote_sud){
			h += minVar;
		}
		if( (value&FrameEx.ouest) == FrameEx.ouest){
			w += maxVar;
			x -= maxVar;
		}else if( (value&FrameEx.cote_ouest) != FrameEx.cote_ouest){
			w += minVar;
			x -= minVar;
		}
		if( (value&FrameEx.est) == FrameEx.est){
			w += maxVar;
		}else if( (value&FrameEx.cote_est) != FrameEx.cote_est){
			w += minVar;
		}
		
		//
		rect.width = (int) w;
		rect.height = (int) h;
		rect.x = (int) x;
		rect.y = (int) y;
		
		return rect;
	}
	
	/**
	 * 
	 * @param i
	 * @param j
	 * @param rect
	 * @return
	 */
//	private BufferedImage createImagePiece(int i,int j,Rectangle rect){
//		int value = this.frame.getFrame()[i][j];
//		BufferedImage bfi = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D g = bfi.createGraphics();
//		
//		//
//		g.drawImage(this.imageScaled, 0, 0, rect.width,rect.height, 
//				rect.x, rect.y, rect.x+rect.width, rect.y+rect.height, null);
//		//
//		Image ref = GeneratorEx.images.get(value).getImage();
//		g.drawImage(ref, 0, 0, rect.width,rect.height, 0, 0,
//				ref.getWidth(null), ref.getHeight(null), null);
//		
//		// alpha
//		Color color = new Color(0,0,0,0);
//		Color red = new Color(255,0,0,255);
//		for(int ii=0;ii<bfi.getWidth();ii++){
//			for(int ji=0;ji<bfi.getHeight();ji++){
//				int val = bfi.getRGB(ii, ji);
//				if(val == red.getRGB())
//					bfi.setRGB(ii, ji, color.getRGB());
//			}
//		}
//		
//		return bfi;
//	}
	
	/**
	 * 
	 * @param i
	 * @param j
	 * @param rect
	 * @return
	 */
	private BufferedImage createImagePieceEx(int tamponY,int tamponX, int i,int j,Rectangle rect){
		int value = this.frame.getFrame()[i][j];
		BufferedImage bfi = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bfi.createGraphics();
	
		
		//
		g.drawImage(this.tampon, 0, 0, rect.width,rect.height, 
				rect.x-tamponX, rect.y-tamponY, 
				rect.x+rect.width-tamponX, rect.y+rect.height-tamponY, null);
		//
		Image ref = this.templateProvider.getImage(value);//GeneratorEx.images.get(value).getImage();
		g.drawImage(ref, 0, 0, rect.width,rect.height, 0, 0,
				ref.getWidth(null), ref.getHeight(null), null);
		
		// alpha
		Color color = new Color(0,0,0,0);
		Color red = new Color(255,0,0,255);
		for(int ii=0;ii<bfi.getWidth();ii++){
			for(int ji=0;ji<bfi.getHeight();ji++){
				int val = bfi.getRGB(ii, ji);
				if(val == red.getRGB())
					bfi.setRGB(ii, ji, color.getRGB());
			}
		}
		
		return bfi;
	}

	/**
	 * 
	 */
	private void scaling(){	
		this.imageScaled = new BufferedImage(
				(int)(this.finalWidth), 
				(int)(this.finalHeight), 
				BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = this.imageScaled.createGraphics();
		AffineTransform t = new AffineTransform();
		t.setToIdentity();
		t.scale(this.finalWidth/this.imageOriginale.getWidth(), this.finalHeight/this.imageOriginale.getHeight());
	
		g.drawImage(this.imageOriginale,t,null);
	}
	
	/**
	 * Nettoie le r�pertoire de destination.
	 */
	private void cleanDirectory(String path){
		FileUtils.cleanDirectory(path);
	}
	
	/*
	 * 
	 */
	public PieceDescripteur getDecriptor() {
		return decriptor;
	}

	public void setDecriptor(PieceDescripteur decriptor) {
		this.decriptor = decriptor;
	}
	
	
	
	
}
