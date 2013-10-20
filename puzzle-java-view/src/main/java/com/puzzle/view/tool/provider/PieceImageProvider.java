package com.puzzle.view.tool.provider;


import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;


import com.puzzle.model.Piece;


public class PieceImageProvider implements ImageProvider,ProviderElement<Piece, PieceBufferOperation> {
	
	
	private String path;
	private Map<Integer, SoftReference<PieceBufferOperation>> images;
	

	public PieceImageProvider(String path){
		this.path = path;
		this.images = new HashMap<Integer, SoftReference<PieceBufferOperation>>();
	}


	public PieceBufferOperation getElement(Piece piece) {
		if(this.images.get(piece.getId())== null ||
			this.images.get(piece.getId()).get() == null){
			
			LoadImageTask task = new LoadImageTask(piece, this.path);
			task.run();
			
			PieceBufferOperation pb = task.getOperation();
			SoftReference<PieceBufferOperation> soft = new SoftReference<PieceBufferOperation>(pb);
			this.images.put(pb.getPiece().getId(), soft);
			
		}
		return this.images.get(piece.getId()).get();
	}

	public PieceBufferOperation getElementDeferred (Piece piece,Observer observer) {
		PieceBufferOperation pbo = null;
		
		if(this.images.get(piece.getId())== null ||
			this.images.get(piece.getId()).get() == null){
			LoadImageTask task = new LoadImageTask(piece, this.path);
			task.addObserver(observer);
			task.addObserver(this);
			task.start();
			
		}else pbo = this.getElement(piece);
		
		return pbo;
	}

	public void clear(){
		this.images.clear();
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


	@Override
	public void update(Observable o, Object arg) {
		if(arg != null){
			if(arg instanceof PieceBufferOperation){
				PieceBufferOperation pb = (PieceBufferOperation) arg;
				SoftReference<PieceBufferOperation> soft = new SoftReference<PieceBufferOperation>(pb);
				this.images.put(pb.getPiece().getId(), soft);
				
				o.deleteObservers();
			}
		}
		
	}


}
