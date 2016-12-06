package board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import letter.Alphabet;

public class Hand {
	private int num = 7;
	private List<Alphabet> hand = new ArrayList<>();
	private List<List<String>> words = new ArrayList<>();

	public Hand(List<Alphabet> l) {
		num = l.size();
		hand = new ArrayList<>(l);
		for (int i = 0; i < num; i++) {
			words.add(new ArrayList<>());
		}
	}

	public void genWords() {
		genWords(new HashSet<>(), "", 1);
	}

	private void genWords(HashSet<Integer> used, String s, int lim) {
		if (s.length() == lim) {
			return;
		}
		for (int i = 0; i < num; i++) {
			if (used.contains(i)) {
				continue;
			}
			s += hand.get(i).getChar();
			used.add(i);
			genWords(used, s, lim);
		}
	}

	public List<List<String>> getWords() {
		return words;
	}
}
