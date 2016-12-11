package words;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class EngWords implements Words {
	private List<HashSet<String>> allWords;
	private int wordCount = 0;
	private int minSize = 2;
	private int maxSize = 7;

	public EngWords() {
		this(2, 7);
	}
	
	public EngWords(int min, int max) {
		minSize = min;
		maxSize = max;
		allWords = new ArrayList<>();
		for (int i = minSize; i <= maxSize; i++) {
			allWords.add(new HashSet<>());
		}
		indexWords();
	}

	@Override
	public boolean isWord(String s) {
		if (s.length() < minSize || s.length() > maxSize) {
			return false;
		}
		return allWords.get(s.length() - minSize).contains(s.toLowerCase());
	}

	@Override
	public void indexWords() {
		for (int i = minSize; i <= maxSize; i++) {
			HashSet<String> wordSet = allWords.get(i - minSize);
			String fileName = getFileName(i);
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(fileName));
				String line = br.readLine();
				while (line != null) {
					if (line.length() != i) {
						continue;
					}
					wordSet.add(line);
					wordCount++;
					line = br.readLine();
				}
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	private String getFileName(int i) {
		String path = "src/words/";
		switch (i) {
		case 2:
			return path + "two.txt";
		case 3:
			return path + "three.txt";
		case 4:
			return path + "four.txt";
		case 5:
			return path + "five.txt";
		case 6:
			return path + "six.txt";
		case 7:
			return path + "seven.txt";
		default:
			return path + "two.txt";
		}
	}

	@Override
	public List<String> listWords() {
		List<String> listWords = new ArrayList<>();
		for (int i = minSize; i <= maxSize; i++) {
			listWords.addAll(allWords.get(i - minSize));
		}
		return listWords;
	}

	@Override
	public int getWordsCount() {
		return wordCount;
	}
	
	@Override
	public Collection<String> isValidWords(Collection<String> words) {
		HashSet<String> invalid = new HashSet<>();
		for (String word : words) {
			if (!isWord(word)) {
				invalid.add(word);
			}
		}
		return invalid;
	}
}
