package com.puzzle.view2.image;

import java.awt.Image;
import java.io.File;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import com.puzzle.model.ComponentPiece;
import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.view2.image.tool.ImageLoadException;
import com.puzzle.view2.image.tool.ImageLoader;
import com.puzzle.view2.image.tool.SimpleImageLoader;

public class ImageProvider {
	private static ImageProvider instance;
	
	private final Map<CompositePiece, Image> imagesComposite = new HashMap<>();
	private final Map<Piece, SoftReference<Image>> imagesPiece = new HashMap<>();
	
	private String path;
	private Image imageWaiting;
	
	
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
			
		}
	}
	
	public Image getImage(ComponentPiece cmp){
		
		if(cmp instanceof Piece){
			return this.getPiece((Piece)cmp);
		}else if(cmp instanceof CompositePiece){
			
		}
		return null;
	}
	
	
	private Image getPiece(Piece p){
		SoftReference<Image> weak = this.imagesPiece.get(p);
		Image img = null;
		
		if(weak != null && weak.get() != null){
			img = weak.get();
		}else{
			ImageLoader.getInstance().loadImage(p);
			img = imageWaiting;
		}

		return img;
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
