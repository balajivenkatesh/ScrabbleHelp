package words;

import java.util.Collection;
import java.util.List;

public interface Words {
	public boolean isWord(String s);

	public void indexWords();

	public List<String> listWords();

	public int getWordsCount();

	public Collection<String> isValidWords(Collection<String> words);
}
