package com.puzzle.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.puzzle.model.CompositePiece;
import com.puzzle.model.Piece;
import com.puzzle.model.Puzzle;
import com.puzzle.model.Puzzle.Position;
import com.puzzle.model.Tapis;

public class Assemblage implements Commande{

	private Tapis tapis;
	
	
	
	
	
	
	public Assemblage(Tapis tapis) {
		this.tapis = tapis;

	}





	@Override
	public void execute() {
		for(Puzzle puzz : this.tapis.getPuzzles())
			this.assPuzzle(puzz);
		
	}
	
	
	private void assPuzzle(Puzzle puzz){
		List<Piece> sac = new ArrayList<Piece>();
		for(int i=1;i<=puzz.getTaille();i++)sac.add(puzz.get(i));
		
		Random rnd = new Random();
		
		List<Piece> already = new ArrayList<Piece>();
		while(!sac.isEmpty()){
			int ref =rnd.nextInt(sac.size());
			Piece p = sac.get(ref);
			
			CompositePiece cmp = new CompositePiece(0, 0);
			int i=0;

			
			
			while(i<30 && !sac.isEmpty()){
				if(p != null && !already.contains(p)){
					sac.remove(p);
					already.add(p);
					
					this.tapis.retirerPiece(p);
					cmp.addComponent(p);
				}
				//
				
				int w = rnd.nextInt(4);
			
				Piece next = null;
				if(w == 0){
					next = puzz.get(Position.est, p.getId());
				}else if(w == 1){
					next = puzz.get(Position.nord, p.getId());
				}else if(w == 2){
					next = puzz.get(Position.sud, p.getId());
				}else{
					next = puzz.get(Position.ouest, p.getId());
				}
				
				if(next != null && !already.contains(next)) p = next;
				
				//
				
				i++;
			}
			

			
			cmp.poser(this.tapis);
			
		}
	}

}
