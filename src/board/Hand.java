package board;

import java.util.Collection;
import java.util.List;

import board.Board.Move;

public interface Hand {

	public void genWords();

	public List<List<String>> getWords();

	public int getWordCount();

	public Collection<Move> getBestMove(Board board);
}