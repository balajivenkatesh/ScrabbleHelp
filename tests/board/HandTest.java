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
		genHandAssertCount(15);

		List<List<String>> words = h.getWords();
		System.out.println(words);
	}

	@Test
	public void testGenWordsSingleRepeatedLetter() {
		hand.add(Alphabet.A);
		hand.add(Alphabet.B);
		hand.add(Alphabet.C);
		hand.add(Alphabet.C);
		genHandAssertCount(34);
	}

	@Test
	public void testGenWordsMultipleRepeatedLetter() {
		hand.add(Alphabet.A);
		hand.add(Alphabet.B);
		hand.add(Alphabet.B);
		hand.add(Alphabet.A);
		genHandAssertCount(18);
	}

	private void genHandAssertCount(int count) {
		h = new Hand(hand);
		h.genWords();
		assertEquals(count, h.getWordCount());
	}
}
