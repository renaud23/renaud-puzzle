package com.puzzle.view.controller;

public class QueueMessage {
	
	
	
	
	private static QueueMessage instance;
	
	private QueueMessage(){
		
	}
	
	
	public static QueueMessage getInstance(){
		if(instance == null)instance = new QueueMessage();
		return instance;
	}
	
	
	
	public enum TypeMessage{
		highLight;
	}
	
	
	
	public void post(){
		
	}
	
	public void peek(){
		
	}
	
	public class Message<U>{
		private TypeMessage message;
		private U parameter;

		public Message(TypeMessage message, U parameter) {
			this.message = message;
			this.parameter = parameter;
		}

		public U getParameter() {
			return parameter;
		}
	}
}
