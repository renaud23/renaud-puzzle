package com.puzzle.view.controller;

import java.util.HashMap;
import java.util.Map;

public class ControllerMessage {
	
	
	private Map<TypeMessage, Message<?>> queue;
	
	private static ControllerMessage instance;
	
	private ControllerMessage(){
		this.queue = new HashMap<ControllerMessage.TypeMessage, Message<?>>();
	}
	
	
	public static ControllerMessage getInstance(){
		if(instance == null)instance = new ControllerMessage();
		return instance;
	}
	
	
	
	public enum TypeMessage{
		highLight,clearHighLight;
		
	
	}
	
	
	
	public void post(Message<?> msg){
		this.queue.put(msg.getMessage(), (Message<?>) msg.getParameter());
	}
	
	public Message<?> peek(TypeMessage type){
		Message<?> m = (Message<?>) this.queue.remove(type);
		return m;
	}
	
	public class Message<U>{
		private TypeMessage message;
		private U parameter;

		public Message(TypeMessage message, U parameter) {
			this.message = message;
			this.parameter = parameter;
		}
		public TypeMessage getMessage() {
			return message;
		}
		public U getParameter() {
			return parameter;
		}
	}
	
	
	
	
}
