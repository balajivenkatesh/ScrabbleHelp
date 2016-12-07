package board;

import letter.Alphabet;

public interface Board {

	Alphabet[][] getGrid();

	void printBoard();

	void setTile(int i, int j, Alphabet a) throws Exception;

}