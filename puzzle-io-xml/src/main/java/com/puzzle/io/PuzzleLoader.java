package com.puzzle.io;



import java.util.List;

import com.puzzle.model.Piece;
import com.puzzle.model.Puzzle;
import com.puzzle.model.Tapis;



public interface PuzzleLoader {
	public void load() throws PuzzleIOException;
	public List<Piece> getPieces();
	public Puzzle getPuzzle();
	public void save(Tapis tapis) throws PuzzleIOException;
}
