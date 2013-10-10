package com.puzzle.io;

import java.util.List;

import com.puzzle.model.Piece;
import com.puzzle.model.Puzzle;

public interface PuzzleLoader {
	public List<Piece> getPieces();
	public Puzzle getPuzzle();
}
