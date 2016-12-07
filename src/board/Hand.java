package board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import letter.Alphabet;

public class Hand {
	private int num;
	private List<Alphabet> hand;
	private List<List<String>> words;
	private int wordCount;

	public Hand(List<Alphabet> l) {
		num = l.size();
		hand = new ArrayList<>(l);
		words = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			words.add(new ArrayList<>());
		}
		wordCount = 0;
	}

	public void genWords() {
		genWords(new HashSet<>(), "", num);
	}

	private void genWords(HashSet<Integer> used, String s, int lim) {
		if (s.length() > 0) {
			words.get(s.length() - 1).add(s);
			wordCount++;
		}
		if (s.length() == lim) {
			return;
		}
		HashSet<Character> usedCharAtIndex = new HashSet<>();
		for (int i = 0; i < num; i++) {
			if (used.contains(i)) {
				continue;
			}
			Alphabet curr = hand.get(i);
			char currChar = curr.getChar();
			if (usedCharAtIndex.contains(currChar)) {
				continue;
			}
			usedCharAtIndex.add(currChar);
			String s1 = s + currChar;
			used.add(i);
			genWords(used, s1, lim);
			used.remove(i);
		}
	}

	public List<List<String>> getWords() {
		return words;
	}
	
	public int getWordCount() {
		return wordCount;
	}
}
