package board;

import java.util.List;

public interface Hand {

	void genWords();

	List<List<String>> getWords();

	int getWordCount();

}