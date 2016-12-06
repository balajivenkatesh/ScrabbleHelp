package board;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import letter.Alphabet;

public class HandTest {

	@Test
	public void testGenWords() {
		List<Alphabet> hand = new ArrayList<>();
		hand.add(Alphabet.A);
		hand.add(Alphabet.A);
		hand.add(Alphabet.C);
		hand.add(Alphabet.C);
		Hand h = new Hand(hand);
		h.genWords();
		List<List<String>> words = h.getWords();
		System.out.println(words);
	}
}
