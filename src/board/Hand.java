package board;

import java.util.List;
import java.util.PriorityQueue;

import board.Board.Move;

public interface Hand {

	public void genWords();

	public List<List<String>> getWords();

	public int getWordCount();

	public PriorityQueue<Move> getBestMove(Board board);
}