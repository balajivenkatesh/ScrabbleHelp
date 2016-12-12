package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

import letter.Alphabet;
import words.EngWords;
import words.Words;

public class BoardImpl implements Board {
	private static final int side = 11;
	private Alphabet grid[][] = new Alphabet[side][side];
	private boolean empty = true;

	private String horiz[][] = new String[side][side];
	private String verti[][] = new String[side][side];

	private Words engWords = new EngWords();

	public BoardImpl() {
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				grid[i][j] = Alphabet.Empty;
			}
		}
		initVertiHoriz();
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
		initVertiHoriz();
	}

	private void initVertiHoriz() {
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				horiz[i][j] = "";
				verti[i][j] = "";
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
		System.out.println("Board -- ");
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				System.out.print(grid[i][j]);
				int wm = getWordMultiplier(i, j);
				int lm = getLetterMultiplier(i, j);
				if (wm == 2) {
					System.out.print("dw  ");
				} else if (wm == 3) {
					System.out.print("tw  ");
				} else if (lm == 2) {
					System.out.print("dl  ");
				} else if (lm == 3) {
					System.out.print("tl  ");
				} else {
					System.out.print("    ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	@Override
	public void setTile(int i, int j, Alphabet a) throws Exception {
		if (i >= side || j >= side) {
			throw new Exception();
		}
		grid[i][j] = a;
		empty = false;
	}

	@Override
	public FillWordResult fillWord(String s, int x, int y, boolean fillVert, boolean ignoreTouch) {
		int n = s.length();

		FillWordResult check = checkSizeBeforeFill(n, x, y, fillVert);
		if (check != null) {
			return check;
		}

		boolean touch = false;
		int score = 0;
		int mainScore = 0, mainWordMultiplier = 1;

		int sIndex = 0, i = x, j = y;
		StringBuilder sb = new StringBuilder();
		String mainLeftWord = getLeftNeighbour(x, y, fillVert);
		sb.append(mainLeftWord);
		mainScore += Alphabet.getWordScore(mainLeftWord);
		while (sIndex < n && i < side && j < side) {
			if (grid[i][j] == Alphabet.Empty) {
				char c = s.charAt(sIndex);
				sb.append(c);
				sIndex++;

				int cScore = Alphabet.getWordScore(c + "");
				int wordMultiplier = getWordMultiplier(i, j);
				int letterMultiplier = getLetterMultiplier(i, j);

				String leftWord = getLeftNeighbour(i, j, !fillVert);
				String rightWord = getRightNeighbour(i, j, !fillVert);
				String perpenWord = leftWord + c + rightWord;

				if (perpenWord.length() > 1) {
					if (!engWords.isWord(perpenWord)) {
						return FillWordResult.newFillWordError("No perpend word - " + perpenWord);
					} else {
						touch = true;
						score += wordMultiplier * (Alphabet.getWordScore(leftWord) + Alphabet.getWordScore(rightWord)
								+ letterMultiplier * cScore);
					}
				}

				mainWordMultiplier *= wordMultiplier;
				mainScore += letterMultiplier * cScore;
			} else {
				char c = grid[i][j].getChar();
				mainScore += Alphabet.getWordScore(c + "");
				sb.append(c);
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
		String mainRightWord = getWordAt(i, j, fillVert);
		sb.append(mainRightWord);
		mainScore += Alphabet.getWordScore(mainRightWord);

		String mainWord = sb.toString();

		if (mainWord.equals(s) && !ignoreTouch && touch == false) {
			return FillWordResult.newFillWordError("No touch");
		}
		if (!engWords.isWord(mainWord)) {
			return FillWordResult.newFillWordError("No word - " + mainWord);
		}

		mainScore *= mainWordMultiplier;
		score += mainScore;

		return FillWordResult.newFillWordSuccess(score);
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

		updateVertiHoriz(iList.get(0), jList.get(0), fillVert);
		for (i = 0; i < iList.size(); i++) {
			updateVertiHoriz(iList.get(i), jList.get(i), !fillVert);
		}

		return FillWordResult.newFillWordSuccess(0);
	}

	private void updateVertiHoriz(int x, int y, boolean fillVert) {
		int xStart = x;
		int yStart = y;
		String mainWord = getLeftNeighbour(x, y, fillVert);
		if (!mainWord.equals("")) {
			if (fillVert) {
				xStart = xStart - mainWord.length();
			} else {
				yStart = yStart - mainWord.length();
			}
		}
		mainWord = grid[x][y] + getRightNeighbour(x, y, fillVert);
		if (mainWord.length() == 1) {
			return;
		}
		if (fillVert) {
			verti[xStart][yStart] = mainWord;
		} else {
			horiz[xStart][yStart] = mainWord;
		}
	}

	private PriorityQueue<Move> bestScoreFirstMove(String s) {
		PriorityQueue<Move> bestMoves = new PriorityQueue<>(Collections.reverseOrder());

		for (int j = Math.max(0, 5 - s.length() + 1); j <= 5; j++) {
			FillWordResult res = fillWord(s, 5, j, false, true);
			if (res.getState() == FillWordResultState.SUCCESS) {
				bestMoves.add(new Move(s, 5, j, false, res.getScore()));
			}
		}

		for (int j = Math.max(0, 5 - s.length() + 1); j <= 5; j++) {
			FillWordResult res = fillWord(s, j, 5, true, true);
			if (res.getState() == FillWordResultState.SUCCESS) {
				bestMoves.add(new Move(s, j, 5, true, res.getScore()));
			}
		}

		return bestMoves;
	}

	@Override
	public PriorityQueue<Move> bestScoreMove(String s) {
		if (empty) {
			return bestScoreFirstMove(s);
		}
		PriorityQueue<Move> bestMoves = new PriorityQueue<>(Collections.reverseOrder());
		bestScoreMove(s, true, bestMoves);
		bestScoreMove(s, false, bestMoves);
		return bestMoves;
	}

	private void bestScoreMove(String s, boolean fillVert, PriorityQueue<Move> bestMoves) {
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				FillWordResult res = fillWord(s, i, j, fillVert, false);
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
				x--;
				String s = "";
				while (x >= 0 && (s = verti[x][y]).equals("")) {
					char c = grid[x][y].getChar();
					if ('A' <= c && c <= 'Z') {
						x--;
					} else {
						break;
					}
				}
				return s;
			}
		} else {
			if (y > 0) {
				y--;
				String s = "";
				while (y >= 0 && (s = horiz[x][y]).equals("")) {
					char c = grid[x][y].getChar();
					if ('A' <= c && c <= 'Z') {
						y--;
					} else {
						break;
					}
				}
				return s;
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

	private int[][] dlIndex = { { 2, 2 }, { 2, 4 }, { 2, 6 }, { 2, 8 }, { 4, 2 }, { 6, 2 }, { 8, 2 }, { 8, 4 },
			{ 8, 6 }, { 8, 8 }, { 6, 8 }, { 4, 8 }, { 2, 8 } };
	private int[][] tlIndex = { { 0, 0 }, { 0, 10 }, { 10, 0 }, { 10, 10 }, { 3, 3 }, { 3, 7 }, { 7, 3 }, { 7, 7 } };
	private int[][] dwIndex = { { 1, 1 }, { 1, 5 }, { 1, 9 }, { 5, 1 }, { 5, 9 }, { 9, 1 }, { 9, 5 }, { 9, 5 },
			{ 9, 9 } };
	private int[][] twIndex = { { 0, 2 }, { 0, 8 }, { 2, 0 }, { 8, 0 }, { 10, 2 }, { 10, 8 }, { 2, 10 }, { 8, 10 } };

	private int getLetterMultiplier(int x, int y) {
		if (getMultiplier(dlIndex, x, y)) {
			return 2;
		}
		if (getMultiplier(tlIndex, x, y)) {
			return 3;
		}
		return 1;
	}

	private int getWordMultiplier(int x, int y) {
		if (getMultiplier(dwIndex, x, y)) {
			return 2;
		}
		if (getMultiplier(twIndex, x, y)) {
			return 3;
		}
		return 1;
	}

	private boolean getMultiplier(int[][] arr, int x, int y) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i][0] == x && arr[i][1] == y) {
				return true;
			}
		}
		return false;
	}
}