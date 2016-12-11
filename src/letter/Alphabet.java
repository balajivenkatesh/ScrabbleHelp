package letter;

public enum Alphabet {
	A('A', 1), B('B', 4), C('C', 4), D('D', 2), E('E', 1), F('F', 4), G('G', 3), H('H', 3), I('I', 1), J('J', 10), K(
			'K', 5), L('L', 2), M('M', 4), N('N', 2), O('O', 1), P('P', 4), Q('Q', 10), R('R', 1), S('S', 1), T('T',
					1), U('U', 2), V('V', 5), W('W', 4), X('X', 8), Y('Y', 3), Z('Z', 10), Any('*', 0), Empty('_', 0);

	private Alphabet(char ch, int v) {
		c = ch;
		value = v;
	}

	private int value;
	private char c;

	public String toString() {
		return c + " (" + value + ")";
	}

	public char getChar() {
		return c;
	}

	public static Alphabet getAlphabetEnum(char c) throws Exception {
		if ('a' <= c && c <= 'z') {
			c -= 32;
		}
		if ('A' <= c && c <= 'Z') {
			return Alphabet.values()[c - 'A'];
		} else if (c == '*') {
			return Any;
		} else if (c == '_') {
			return Empty;
		}
		throw new Exception();
	}

	public static int getWordScore(String s) {
		s = s.toUpperCase();
		int score = 0;
		if (s.length() == 0) {
			return 0;
		}
		for (char c : s.toCharArray()) {
			score += Alphabet.values()[c - 'A'].value;
		}
		return score;
	}
}