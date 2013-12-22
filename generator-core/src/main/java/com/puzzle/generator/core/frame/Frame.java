package com.puzzle.generator.core.frame;


import java.util.Collection;
import java.util.Random;





/**
 *	Décrit les piéces du puzzle. 
 * @author kqhlz2
 *
 */
public class Frame {
	

	public final static int nord = 8;
	public final static int sud = 4;
	public final static int est = 2;
	public final static int ouest = 1;

	public final static int cote_nord = 128;
	public final static int cote_sud = 64;
	public final static int cote_est = 32;
	public final static int cote_ouest = 16;
	
	public final static int case_vide = -1;
	
	private int largeur;
	private int hauteur;
	private int nbCells;
	private Cell[][] cells;

	

	/**
	 *
	 * @param largeur
	 * @param hauteur
	 */
	public Frame(int largeur,int hauteur){
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.nbCells = largeur*hauteur;
		this.cells = new Cell[largeur][hauteur];
		for(int i=0;i<this.nbCells;i++){
			Cell cell = new Cell();
			cell.setPosition(i+1);
			cells[i%largeur][i/largeur] = cell;
		}
	}

	/**
	 * génére une frame.
	 *
	 */
	public void genere(){
		for(int j=0;j<this.hauteur;j++){
			for(int i=0;i<this.largeur;i++){
				Cell cell = this.cells[i][j];
				this.voisinage(cell);
				this.ouverture(cell);

				cell.setLocked(true);
			}
		}
	}


	public Cell getCell(int i,int j){
		return this.cells[i][j];
	}

	/**
	 *
	 * @return
	 */
	public int[][] getFrame(){
		int[][] frame = new int[this.largeur][this.hauteur];
		for(int i=0;i<this.largeur;i++){
			for(int j=0;j<hauteur;j++){
				frame[i][j] = this.cells[i][j].getType();
			}
		}

		return frame;
	}

	/**
	 *
	 * @param cell
	 */
	private void voisinage(Cell cell){
		int i = (cell.getPosition()-1)%this.largeur;
		int j = (cell.getPosition()-1)/this.largeur;
		// nord
		try{
			if(this.cells[i][j-1].isLocked()){
				int statut = this.cells[i][j-1].getType()&sud;
				if(statut != sud){
					cell.setType(cell.getType()|nord);
				}
			}
		}catch(ArrayIndexOutOfBoundsException e){
			cell.setType(cell.getType()|cote_nord);
		}
		// sud
		try{
			if(this.cells[i][j+1].isLocked()){
				int statut = this.cells[i][j+1].getType()&nord;
				if(statut != nord){
					cell.setType(cell.getType()|sud);
				}
			}
		}catch(ArrayIndexOutOfBoundsException e){
			cell.setType(cell.getType()|cote_sud);
		}
		// est
		try{
			if(this.cells[i+1][j].isLocked()){
				int statut = this.cells[i+1][j].getType()&ouest;
				if(statut != ouest){
					cell.setType(cell.getType()|est);
				}
			}
		}catch(ArrayIndexOutOfBoundsException e){
			cell.setType(cell.getType()|cote_est);
		}
		//	ouest
		try{
			if(this.cells[i-1][j].isLocked()){
				int statut = this.cells[i-1][j].getType()&est;
				if(statut != est){
					cell.setType(cell.getType()|ouest);
				}
			}
		}catch(ArrayIndexOutOfBoundsException e){
			cell.setType(cell.getType()|cote_ouest);
		}
	}

	/**
	 *
	 * @param cell
	 */
	private void ouverture(Cell cell){
		int i = (cell.getPosition()-1)%this.largeur;
		int j = (cell.getPosition()-1)/this.largeur;
		// compte les trous.
		int nbTrous = 0;
		int type = cell.getType();
		if( (type&nord)!=nord) nbTrous++;
		if( (type&sud)!=sud) nbTrous++;
		if( (type&est)!=est) nbTrous++;
		if( (type&ouest)!=ouest) nbTrous++;

		// on essaie d'ouvir des portes
		Random rand = new Random();
		for(int ii=0;ii<(nbTrous);ii++){
			int where = rand.nextInt(4);
			type = cell.getType();
			if(where==0 && (type&cote_nord)!=cote_nord){
				if(!this.cells[i][j-1].isLocked()){
					cell.setType(type|nord);
				}
			}else if(where==1 && (type&cote_sud)!=cote_sud){
				if(!this.cells[i][j+1].isLocked()){
					cell.setType(type|sud);
				}
			}else if(where==2 && (type&cote_est)!=cote_est){
				if(!this.cells[i+1][j].isLocked()){
					cell.setType(type|est);
				}
			}else if(where==3 && (type&cote_ouest)!=cote_ouest){
				if(!this.cells[i-1][j].isLocked()){
					cell.setType(type|ouest);
				}
			}
		}

	}
	
	
	/**
	 * recalcul les bords en fonction des piéces contenus dans sélection.
	 * @param selection
	 */
	public void validateSelection(Collection<Integer> selection){
		for(int j=0;j<this.hauteur;j++){
			for(int i=0;i<this.largeur;i++){
				Cell cell = this.getCell(i, j);
				int type = cell.getType();
				int id = i+j*this.largeur+1;
				
				if(selection.contains(id)){
					// au dessus
					if((j-1)>=0){
						int idEx = i+(j-1)*this.largeur+1;
						if(!selection.contains(idEx)){//System.out.println("yo");
							type = type|Frame.cote_nord;
							type = (type|Frame.nord)^Frame.nord;
						}
					}// if
					
					// en dessous
					if((j+1)< (this.hauteur)){
						int idEx = i+(j+1)*this.largeur+1;
						if(!selection.contains(idEx)){
							type = type|Frame.cote_sud;
							type = (type|Frame.sud)^Frame.sud;
						}
					}// if
					
					// à l'ouest
					if((i-1) >= 0){
						int idEx = i+j*this.largeur;
						if(!selection.contains(idEx)){
							type = type|Frame.cote_ouest;
							type = (type|Frame.ouest)^Frame.ouest;
						}
					}// if
					
					// à l'est
					if((i+1) <(this.largeur)){
						int idEx = i+j*this.largeur+2;
						if(!selection.contains(idEx)){
							type = type|Frame.cote_est;
							type = (type|Frame.est)^Frame.est;
						}
					}// if
					
					cell.setType(type);
				}else{
					cell.setType(case_vide);
				}
			}// for
		}// for
	}
	
	/**
	 * recal la frame vers les bords, pour ignorer les lignes et colonnes vides.
	 */
	public void refacte(){
		boolean changeH = true;
		boolean changeL = true;
		
		while(changeH || changeL){
			changeH = true;
			for(int j=0;j<this.hauteur;j++){
				Cell cell = this.cells[0][j];
				if(!cell.isCaseVide()){
					changeH = false;
				}
			}// for
			if(changeH){
				this.moveLeft();
				
			}
			
			changeL = true;
			for(int i=0;i<this.largeur;i++){
				Cell cell = this.cells[i][0];
				if(!cell.isCaseVide()){
					changeL = false;
				}
			}// for
			if(changeL){
				this.moveUp();
			}
		}// while
		
//		this.sizing();
	}
	
	
	private void moveLeft(){
		for(int i=0;i<(this.largeur-1);i++){
			for(int j=0;j<this.hauteur;j++){
				Cell cell = this.cells[i][j];
				Cell next = this.cells[i+1][j];
				
				cell.setType(next.getType());
				next.setType(Frame.case_vide);
			}
		}
	}
	
	private void moveUp(){
		for(int j=0;j<(this.hauteur-1);j++){
			for(int i=0;i<this.largeur;i++){
				Cell cell = this.cells[i][j];
				Cell next = this.cells[i][j+1];
				
				cell.setType(next.getType());
				next.setType(Frame.case_vide);
			}
		}
	}
	
	
	/**
	 * Compte de nombre de ligne vide depuis le haut.
	 * @return
	 */
	public int compteFirstEmptyLigne(){
		
		boolean continu = true;
		int j = 0;
		while(continu && j<this.hauteur){
			for(int i =0;i<this.largeur;i++){
				if(this.getCell(i, j).getType() != case_vide) continu = false;
			}
			if(continu) j++;
		}
	
		return j;
	}
	
	/**
	 * 
	 * @return
	 */
	public int compteFirstEmptyColonne(){
		
		boolean continu = true;
		int i = 0;
		while(continu && i<this.largeur){
			for(int j =0;j<this.hauteur;j++){
				if(this.getCell(i, j).getType() != case_vide) continu = false;
			}
			if(continu) i++;
		}
	
		return i;
	}
	
	
	/**
	 * 
	 */
	public void sizing(){
		boolean change = true;
		int i = this.largeur-1;
		while(change && i>0){
			change = true;
		
			for(int j=0;j<this.hauteur;j++){
				Cell cell = this.cells[i][j];
				if(!cell.isCaseVide()) change = false;
			}
			if(change)this.largeur--;
			i--;
		}
		
		change = true;
		int j = this.hauteur-1;
		while(change && j>0){
			change = true;
		
			for(int x=0;x<this.largeur;x++){
				Cell cell = this.cells[x][j];
				if(!cell.isCaseVide()) change = false;
			}
			if(change)this.hauteur--;
			j--;
		}
	}
	
	/*
	 *
	 */
	
	
	public String toString(){
		StringBuilder bld = new StringBuilder();
		for(int j=0;j<this.hauteur;j++){
			for(int i=0;i<this.largeur;i++){
				int type = this.getCell(i, j).getType();
				bld.append(type).append("-");
			}
			bld.append("\r\n");
		}
		
		return bld.toString();
	}
	
	
	/*
	 * 
	 */
	public int getLargeur() {
		return largeur;
	}

	public int getHauteur() {
		return hauteur;
	}
	
	public int getNbPieces(){
		return this.nbCells;
	}
}
