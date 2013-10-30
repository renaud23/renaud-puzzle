package com.puzzle.view.context;

import java.awt.image.BufferStrategy;
import java.util.HashMap;
import java.util.Map;

import com.puzzle.view.core.TapisConverteur;

public class PuzzleContext {
	private static PuzzleContext instance;
	
	private Map<PuzzleParam, Object> parameters;
	
	public synchronized static PuzzleContext getInstance(){
		if(instance == null)instance = new PuzzleContext();
		return instance;
	}
	
	private PuzzleContext(){
		this.parameters = new HashMap<PuzzleParam, Object>();
	}
	
	
	public void put(PuzzleParam key,Object value){
		if(value.getClass().equals(key.getClazz())) 
			this.parameters.put(key, value);
	}
	
	public Object get(PuzzleParam key){
		return this.parameters.get(key);
	}
	
	public enum PuzzleParam{
		strategy(BufferStrategy.class),
		converter(TapisConverteur.class),
		renderer(com.puzzle.view.core.Renderer.class),
		screenLargeur(Integer.class),
		screenHauteur(Integer.class);
		
		private Class<?> clazz;
		
		private PuzzleParam(Class<?> clazz){
			this.clazz = clazz;
		}
		
		public Class<?> getClazz(){
			return this.clazz;
		}
	}
}
