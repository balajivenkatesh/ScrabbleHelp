package board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

import letter.Alphabet;
import words.EngWords;
import words.Words;

public class BoardImpl implements Board {
	private static final int side = 11;
	private Alphabet grid[][] = new Alphabet[side][side];

	private String horiz[][] = new String[side][side];
	private String verti[][] = new String[side][side];

	private Words engWords = new EngWords();

	public BoardImpl() {
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				grid[i][j] = Alphabet.Empty;
			}
		}
	}

	public BoardImpl(Board b1) throws Exception {
		Alphabet x[][] = b1.getGrid();
		if (x.length != side || x[0].length != side) {
			throw new Exception();
		}
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				grid[i][j] = x[i][j];
			}
		}
	}

	@Override
	public int getSize() {
		return side;
	}

	@Override
	public Alphabet[][] getGrid() {
		return grid;
	}

	@Override
	public void printBoard() {
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				System.out.print(grid[i][j] + "  ");
			}
			System.out.println();
		}
	}

	@Override
	public void setTile(int i, int j, Alphabet a) throws Exception {
		if (i >= side || j >= side) {
			throw new Exception();
		}
		grid[i][j] = a;
	}

	@Override
	public FillWordResult fillWord(String s, int x, int y, boolean fillVert) {
		int n = s.length();

		FillWordResult check = checkSizeBeforeFill(n, x, y, fillVert);
		if (check != null) {
			return check;
		}

		boolean touch = false;

		// HashSet<String> resWords = new HashSet<>();
		int sIndex = 0, i = x, j = y;
		StringBuilder sb = new StringBuilder();
		sb.append(getLeftNeighbour(x, y, fillVert));
		while (sIndex < n && i < side && j < side) {
			if (grid[i][j] == Alphabet.Empty) {
				char c = s.charAt(sIndex);
				sb.append(c);
				sIndex++;
				String perpenWord = getLeftNeighbour(i, j, !fillVert) + c + getRightNeighbour(i, j, !fillVert);
				if (perpenWord.length() > 1) {
					if (!engWords.isWord(perpenWord)) {
						return FillWordResult.newFillWordError("No word - " + perpenWord);
					} else {
						touch = true;
					}
				}
				// resWords.add(perpenWord);
			} else {
				sb.append(grid[i][j].getChar());
			}
			if (fillVert) {
				i++;
			} else {
				j++;
			}
		}
		if (sIndex != n) {
			return FillWordResult.newFillWordError("Insufficient space");
		}
		sb.append(getWordAt(i, j, fillVert));

		String mainWord = sb.toString();
		if (mainWord.equals(s) && touch == false) {
			return FillWordResult.newFillWordError("No touch");
		}
		if (!engWords.isWord(mainWord)) {
			return FillWordResult.newFillWordError("No word - " + mainWord);
		}
		// resWords.add(sb.toString());
		// Collection<String> invalid = engWords.isValidWords(resWords);
		// if (invalid.isEmpty()) {
		// return FillWordResult.SUCCESS;
		// }

		return FillWordResult.newFillWordSuccess(10);
	}

	@Override
	public FillWordResult putWord(String s, int x, int y, boolean fillVert) {
		int n = s.length();

		FillWordResult check = checkSizeBeforeFill(n, x, y, fillVert);
		if (check != null) {
			return check;
		}

		ArrayList<Integer> iList = new ArrayList<>();
		ArrayList<Integer> jList = new ArrayList<>();
		int sIndex = 0, i = x, j = y;
		while (sIndex < n && i < side && j < side) {
			if (grid[i][j] == Alphabet.Empty) {
				try {
					grid[i][j] = Alphabet.getAlphabetEnum(s.charAt(sIndex));
					iList.add(i);
					jList.add(j);
				} catch (Exception e) {
					revertAtList(iList, jList);
					return FillWordResult.newFillWordError("Invalid char");
				}
				sIndex++;
			}
			if (fillVert) {
				i++;
			} else {
				j++;
			}
		}

		if (sIndex != n) {
			revertAtList(iList, jList);
			return FillWordResult.newFillWordError("Insufficient space");
		}

		return FillWordResult.newFillWordSuccess(0);
	}

	@Override
	public Collection<Move> bestScoreMove(String s) {
		Collection<Move> bestMoves = new PriorityQueue<>(10);
		bestScoreMove(s, true, bestMoves);
		bestScoreMove(s, false, bestMoves);
		return bestMoves;
	}
	
	private void bestScoreMove(String s, boolean fillVert, Collection<Move> bestMoves) {
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				FillWordResult res = fillWord(s, i, j, fillVert);
				if (res.getState() == FillWordResultState.SUCCESS) {
					bestMoves.add(new Move(s, i, j, fillVert, res.getScore()));
				}
			}
		}
	}

	private void revertAtList(ArrayList<Integer> iList, ArrayList<Integer> jList) {
		for (int i = 0; i < iList.size(); i++) {
			grid[iList.get(i)][jList.get(i)] = Alphabet.Empty;
		}
	}

	private FillWordResult checkSizeBeforeFill(int len, int x, int y, boolean fillVert) {
		if (grid[x][y] != Alphabet.Empty) {
			return FillWordResult.newFillWordError("Non-empty start");
		}

		// Insufficient space
		int init = x;
		if (fillVert) {
			init = y;
		}
		if (init + len > side) {
			return FillWordResult.newFillWordError("Insufficient space before start");
		}

		return null;
	}

	private String getWordAt(int x, int y, boolean fillVert) {
		if (fillVert) {
			if (x < side) {
				return verti[x][y];
			}
		} else {
			if (y < side) {
				return horiz[x][y];
			}
		}
		return "";
	}

	private String getLeftNeighbour(int x, int y, boolean fillVert) {
		if (fillVert) {
			if (x > 0) {
				return verti[x - 1][y];
			}
		} else {
			if (y > 0) {
				return horiz[x][y - 1];
			}
		}
		return "";
	}

	private String getRightNeighbour(int x, int y, boolean fillVert) {
		if (fillVert) {
			if (x < side - 1) {
				return verti[x + 1][y];
			}
		} else {
			if (y < side - 1) {
				return horiz[x][y + 1];
			}
		}
		return "";
	}
}