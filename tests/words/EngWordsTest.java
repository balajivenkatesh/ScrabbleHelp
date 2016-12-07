package words;

import org.junit.Test;
import static org.junit.Assert.*;

public class EngWordsTest {

	@Test
	public void testTwoLetters() {
		Words w = new EngWords(2, 2);
		System.out.println(w.getWordsCount());
		System.out.println(w.listWords());
	}

	@Test
	public void testAllWords() {
		Words w;
		int minSize = 2;
		int maxSize = 7;
		for (int i = minSize; i <= maxSize; i++) {
			w = new EngWords(i, i);
			int actual = w.getWordsCount();
			int expected = 0;
			switch (i) {
			case 2:
				expected = 101;
				break;
			case 3:
				expected = 1015;
				break;
			case 4:
				expected = 4030;
				break;
			case 5:
				expected = 8938;
				break;
			case 6:
				expected = 15788;
				break;
			case 7:
				expected = 24029;
				break;
			}
			assertEquals(expected, actual);
		}
	}
}
