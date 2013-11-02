package com.puzzle.view.tool.provider;


import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.puzzle.model.CompositePiece;


public class CompositeImageProvider implements ProviderElement<CompositePiece, CompositeBufferOperation>{
	private static CompositeImageProvider instance;
	private double scale = 0.5;
	private static double limite = 2048.0;
	
	private Map<CompositePiece, CompositeBufferOperation> buffers;
	
	public static CompositeImageProvider getInstance(){
		if(instance == null) instance = new CompositeImageProvider();
		return instance;
	}
	
	
	private CompositeImageProvider(){
		this.buffers = new HashMap<CompositePiece, CompositeBufferOperation>(); 
	}
	
	
	public void removeBuffer(CompositePiece cmp){
		this.buffers.remove(cmp); 
	}
	
	public CompositeBufferOperation getElement(CompositePiece cmp){
		
		CompositeBufferOperation b = this.buffers.get(cmp);
		if(b == null){
			CompositeBufferTask task = new CompositeBufferTask(cmp, limite);
			task.run();
			this.buffers.put(cmp, task.getOperation());
		}

		return b;
	}
	
	public CompositeBufferOperation getElementDeferred(CompositePiece composite,Observer observer){
		
		CompositeBufferOperation b = this.buffers.get(composite);
		if(b == null){
			CompositeBufferTask task = new CompositeBufferTask(composite, limite);
			task.addObserver(observer);
			task.addObserver(this);
			task.start();
		}
		
		return b;
	}


	public double getScale() {
		return this.scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}


	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof CompositeBufferOperation){
			CompositeBufferOperation sb = (CompositeBufferOperation) arg;
			this.buffers.put(sb.getComposite(), sb);
			o.deleteObservers();
		}
	}	
}
