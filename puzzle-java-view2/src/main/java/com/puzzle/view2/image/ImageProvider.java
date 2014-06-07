package com.puzzle.view2.image;

import java.awt.Image;
import java.io.File;
import java.lang.Thread.State;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.puzzle.model.ComponentPiece;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;

public class ImageProvider {
	private static ImageProvider instance;
	
	private final Map<CompositePiece, Image> imagesComposite = new HashMap<CompositePiece, Image>();
	private final Map<Piece, SoftReference<Image>> imagesPiece = new HashMap<Piece, SoftReference<Image>>();
	
	private String path;
	private Image imageWaiting;
	
	private Thread compositeGenerator;
	
	
	private ImageProvider(){
		
	}

	public static ImageProvider getInstance(){
		if(instance == null) instance = new ImageProvider();
		return instance;
	}
	
	
	
	
	public void PutImage(ComponentPiece cmp, Image image){
		if(cmp instanceof Piece){
			Piece p = (Piece) cmp;
			SoftReference<Image> weak = new SoftReference<Image>(image);
			this.imagesPiece.put(p,weak);
			
		}else if(cmp instanceof CompositePiece){
			this.imagesComposite.put((CompositePiece) cmp, image);
		}
	}
	
	public Image getImage(ComponentPiece cmp){
		Image image = null;
		if(cmp instanceof Piece){
			image =  this.getPiece((Piece)cmp);
		}else if(cmp instanceof CompositePiece){
			
			image = this.imagesComposite.get(cmp);
			if(image == null && (this.compositeGenerator == null || this.compositeGenerator.getState().equals(Thread.State.TERMINATED))){
				this.compositeGenerator = new Thread(new CompositeBufferTask((CompositePiece) cmp, 2048));
				this.compositeGenerator.start();
				
			}
		
		}
		return image;
	}
	
	
	private Image getPiece(Piece p){
		SoftReference<Image> weak = this.imagesPiece.get(p);
		Image img = null;
		
		if(weak != null && weak.get() != null){
			img = weak.get();
		}else{
			ImagePieceLoader.getInstance().loadImage(p);
//			img = imageWaiting;
		}

		return img;
	}

	
	public Set<CompositePiece> getDrawableComposite(){
		return this.imagesComposite.keySet();
	}
	
	public void removeCompositeImage(CompositePiece cmp){
		if(this.imagesComposite.containsKey(cmp)) this.imagesComposite.remove(cmp);
	}
	
	public Image getImageWaiting() {
		return imageWaiting;
	}

	public void setImageWaiting(Image imageWaiting) {
		this.imageWaiting = imageWaiting;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
