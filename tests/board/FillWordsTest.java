package board;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.Test;

import board.Board.Move;
import letter.Alphabet;

public class FillWordsTest {
	private List<Alphabet> hand = new ArrayList<>();
	private Hand h;

	@Test
	public void testGenWordsSimple1Debug() throws Exception {
		hand.add(Alphabet.E);
		hand.add(Alphabet.E);
		hand.add(Alphabet.I);
		hand.add(Alphabet.O);
		hand.add(Alphabet.O);
		hand.add(Alphabet.L);
		hand.add(Alphabet.E);

		h = new HandImpl(hand);
		h.genWords();

		Board b1 = new BoardImpl();
		
		char[][] g = {
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','O','_','_','_','_','_','_'},
				{'_','_','_','Q','I','_','_','_','_','_','_'},
				{'_','_','P','A','_','_','_','_','_','_','_'},
				{'_','I','O','T','A','S','_','_','_','_','_'}, // mid
				{'_','_','N','_','_','_','_','_','_','_','_'},
				{'_','_','G','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'}};
		
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				char c = g[i][j];
				if ('A' <= c && c <= 'Z') {
					b1.setTile(i, j, Alphabet.getAlphabetEnum(c));
				}
			}
		}
		
		b1.printBoard();

		PriorityQueue<Move> bm = h.getBestMove(b1);

		int cnt = 0;
		Move m = null;
		while ((m = bm.poll()) != null && cnt < 8) {
			System.out.println(m);
			Board b = new BoardImpl(b1);
			b.putWord(m.getWord(), m.getX(), m.getY(), m.isFillVert());
			b.printBoard();
			cnt++;
		}
	}
	
	@Test
	public void testGenWordsSimple1() throws Exception {
		hand.add(Alphabet.E);
		hand.add(Alphabet.E);
		hand.add(Alphabet.I);
		hand.add(Alphabet.O);
		hand.add(Alphabet.O);
		hand.add(Alphabet.L);
		hand.add(Alphabet.E);

		h = new HandImpl(hand);
		h.genWords();

		Board b1 = new BoardImpl();
		
		char[][] g = {
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','O','_','_','_','_','_','_'},
				{'_','_','_','Q','I','_','_','_','_','_','_'},
				{'_','_','P','A','_','_','_','_','_','_','_'},
				{'_','I','O','T','A','S','_','_','_','_','_'}, // mid
				{'_','_','N','_','_','_','_','_','_','_','_'},
				{'_','_','G','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'}};
		
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				char c = g[i][j];
				if ('A' <= c && c <= 'Z') {
					b1.setTile(i, j, Alphabet.getAlphabetEnum(c));
				}
			}
		}
		
		b1.printBoard();

		PriorityQueue<Move> bm = h.getBestMove(b1);

		int cnt = 0;
		Move m = null;
		while ((m = bm.poll()) != null && cnt < 8) {
			System.out.println(m);
			Board b = new BoardImpl(b1);
			b.putWord(m.getWord(), m.getX(), m.getY(), m.isFillVert());
			b.printBoard();
			cnt++;
		}
	}
	
	@Test
	public void testGenWordsSimple2() throws Exception {
		hand.add(Alphabet.S);
		hand.add(Alphabet.Z);
		hand.add(Alphabet.I);
		hand.add(Alphabet.S);
		hand.add(Alphabet.U);
		hand.add(Alphabet.P);
		hand.add(Alphabet.Y);

		h = new HandImpl(hand);
		h.genWords();

		Board b1 = new BoardImpl();
		
		char[][] g = {
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','A','S','_','_','_','_'}, // mid
				{'_','_','_','_','_','X','I','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'}};
		
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				char c = g[i][j];
				if ('A' <= c && c <= 'Z') {
					b1.setTile(i, j, Alphabet.getAlphabetEnum(c));
				}
			}
		}
		
		b1.printBoard();

		PriorityQueue<Move> bm = h.getBestMove(b1);

		int cnt = 0;
		Move m = null;
		while ((m = bm.poll()) != null && cnt < 8) {
			System.out.println(m);
			Board b = new BoardImpl(b1);
			b.putWord(m.getWord(), m.getX(), m.getY(), m.isFillVert());
			b.printBoard();
			cnt++;
		}
	}
	
	@Test
	public void testGenWordsSimple3() throws Exception {
		hand.add(Alphabet.L);
		hand.add(Alphabet.Q);
		hand.add(Alphabet.R);
		hand.add(Alphabet.L);
		hand.add(Alphabet.E);
		hand.add(Alphabet.E);
		hand.add(Alphabet.S);

		h = new HandImpl(hand);
		h.genWords();

		Board b1 = new BoardImpl();
		
		char[][] g = {
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','L','O','O','K','I','N','G','_'}, // mid
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'}};
		
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				char c = g[i][j];
				if ('A' <= c && c <= 'Z') {
					b1.setTile(i, j, Alphabet.getAlphabetEnum(c));
				}
			}
		}
		
		b1.printBoard();

		PriorityQueue<Move> bm = h.getBestMove(b1);

		int cnt = 0;
		Move m = null;
		while ((m = bm.poll()) != null && cnt < 8) {
			System.out.println(m);
			Board b = new BoardImpl(b1);
			b.putWord(m.getWord(), m.getX(), m.getY(), m.isFillVert());
			b.printBoard();
			cnt++;
		}
	}
	
	@Test
	public void testGenWordsSimple4() throws Exception {
		hand.add(Alphabet.E);
		hand.add(Alphabet.E);
		hand.add(Alphabet.I);
		hand.add(Alphabet.O);
		hand.add(Alphabet.O);
		hand.add(Alphabet.L);
		hand.add(Alphabet.E);

		h = new HandImpl(hand);
		h.genWords();

		Board b1 = new BoardImpl();
		
		char[][] g = {
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'}, // mid
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'},
				{'_','_','_','_','_','_','_','_','_','_','_'}};
		
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				char c = g[i][j];
				if ('A' <= c && c <= 'Z') {
					b1.setTile(i, j, Alphabet.getAlphabetEnum(c));
				}
			}
		}
		
		b1.printBoard();

		PriorityQueue<Move> bm = h.getBestMove(b1);

		int cnt = 0;
		Move m = null;
		while ((m = bm.poll()) != null && cnt < 8) {
			System.out.println(m);
			Board b = new BoardImpl(b1);
			b.putWord(m.getWord(), m.getX(), m.getY(), m.isFillVert());
			b.printBoard();
			cnt++;
		}
	}
}
