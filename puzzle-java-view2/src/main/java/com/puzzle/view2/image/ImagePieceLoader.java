package com.puzzle.view2.image;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import com.puzzle.model.Piece;
import com.puzzle.view2.image.tool.ImageLoadException;
import com.puzzle.view2.image.tool.SimpleImageLoader;




public class ImagePieceLoader implements Runnable{

	private static ImagePieceLoader instance;
	
	private Thread th;
	
	private Stack<TacheChargement> taches = new Stack<>();
	private List<Piece> piecesenCour = new ArrayList<>();
	private int sizeMax = 50;
	
	
	public static ImagePieceLoader getInstance(){
		if(instance == null) instance = new ImagePieceLoader();
		return instance;
	}
	
	private ImagePieceLoader(){
		th = new Thread(this);
		th.start();
	}

	@Override
	public void run() {
		while(true){
			if(!this.taches.isEmpty()){
				TacheChargement t = this.taches.pop();
				try {
					SimpleImageLoader ld = new SimpleImageLoader();
					String path = ImageProvider.getInstance().getPath()+File.separator+"images"+File.separator+t.getPiece().getId()+".png";
					Image img = ld.getImage(path);
					ImageProvider.getInstance().PutImage(t.getPiece(), img);
					
					this.piecesenCour.remove(t.getPiece());
				} catch (ImageLoadException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
	}
	
	
	public void loadImage(Piece piece){
		if(!piecesenCour.contains(piece) && piecesenCour.size() < sizeMax){
			TacheChargement t = new TacheChargement(piece);
			this.taches.push(t);
			this.piecesenCour.add(piece);
		}
		
	}
	
	
	private class TacheChargement{
		private Piece piece;

		public TacheChargement(Piece piece) {
			super();
			this.piece = piece;
		}

		public Piece getPiece() {
			return piece;
		}
	}
	
}
