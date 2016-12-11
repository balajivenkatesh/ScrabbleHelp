package board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import board.Board.Move;
import letter.Alphabet;

public class HandImpl implements Hand {
	private int num;
	private List<Alphabet> hand;
	private List<List<String>> words;
	private int wordCount;

	public HandImpl(List<Alphabet> l) {
		num = l.size();
		hand = new ArrayList<>(l);
		hand.sort(new AlphabetCompare());
		
		words = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			words.add(new ArrayList<>());
		}
		wordCount = 0;
	}

	@Override
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

	@Override
	public List<List<String>> getWords() {
		return words;
	}
	
	@Override
	public int getWordCount() {
		return wordCount;
	}
	
	@Override
	public Collection<Move> getBestMove(Board board) {
		Collection<Move> bestMoves = new PriorityQueue<>(10);
		
		for (int i = num - 1; i >= 0; i--) {
			for (String s: words.get(i)) {
				bestMoves.addAll(board.bestScoreMove(s));
			}
		}
		
		return bestMoves;
	}
	
	private class AlphabetCompare implements Comparator<Alphabet> {
		@Override
		public int compare(Alphabet a, Alphabet b) {
			char ac = a.getChar(), bc = b.getChar();
			if (ac > bc) {
				return 1;
			} else if (ac == bc) {
				return 0;
			} else {
				return -1;
			}
		}
	}
}