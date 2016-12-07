package board;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Test;

import letter.Alphabet;

public class HandTest {
	private List<Alphabet> hand = new ArrayList<>();
	private Hand h;

	@Test
	public void testGenWordsSimple() {
		hand.add(Alphabet.A);
		hand.add(Alphabet.B);
		hand.add(Alphabet.C);
		genHandAssertCount(15, false);
	}

	@Test
	public void testGenWordsSingleRepeatedLetter() {
		hand.add(Alphabet.A);
		hand.add(Alphabet.C);
		hand.add(Alphabet.C);
		hand.add(Alphabet.B);
		genHandAssertCount(34, true);
	}

	@Test
	public void testGenWordsMultipleRepeatedLetter() {
		hand.add(Alphabet.A);
		hand.add(Alphabet.B);
		hand.add(Alphabet.B);
		hand.add(Alphabet.A);
		genHandAssertCount(18, false);
	}

	private void genHandAssertCount(int count, boolean print) {
		h = new HandImpl(hand);
		h.genWords();
		assertEquals(count, h.getWordCount());
		
		if (print) {
			List<List<String>> words = h.getWords();
			System.out.println(words);
		}
	}
}
