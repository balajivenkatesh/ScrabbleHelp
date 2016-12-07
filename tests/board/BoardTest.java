package board;

import org.junit.Test;

import letter.Alphabet;

public class BoardTest {
	@Test
	public void testEmptyBoard() {
		Board b = newBoard();
		b.printBoard();
	}

	@Test
	public void testNonEmptyBoard() throws Exception {
		Board b = newBoard();
		b.setTile(0, 0, Alphabet.A);
		b.printBoard();
	}

	@Test
	public void testCopyBoard() throws Exception {
		Board b1 = newBoard();
		b1.setTile(0, 0, Alphabet.A);
		Board b2 = new BoardImpl(b1);
		b2.printBoard();
	}
	
	private Board newBoard() {
		return new BoardImpl();
	}
}
