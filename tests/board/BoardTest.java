package board;

import org.junit.Test;

import letter.Alphabet;

public class BoardTest {
	@Test
	public void testEmptyBoard() {
		Board b = new Board();
		b.printBoard();
	}

	@Test
	public void testNonEmptyBoard() throws Exception {
		Board b = new Board();
		b.setTile(0, 0, Alphabet.A);
		b.printBoard();
	}

	@Test
	public void testCopyBoard() throws Exception {
		Board b1 = new Board();
		b1.setTile(0, 0, Alphabet.A);
		Board b2 = new Board(b1);
		b2.printBoard();
	}
}
