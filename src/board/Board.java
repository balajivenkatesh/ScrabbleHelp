package board;

import java.util.Collection;

import letter.Alphabet;

public interface Board {

	public int getSize();

	public Alphabet[][] getGrid();

	public void printBoard();

	public void setTile(int i, int j, Alphabet a) throws Exception;

	public FillWordResult fillWord(String s, int x, int y, boolean fillVert);

	public FillWordResult putWord(String s, int x, int y, boolean fillVert);
	
	public Collection<Move> bestScoreMove(String s);

	public enum FillWordResultState {
		SUCCESS, ERROR;
	}

	public class FillWordResult {
		private FillWordResultState state;
		private int score;
		private String msg;

		public FillWordResult(FillWordResultState state, int score, String msg) {
			this.state = state;
			this.score = score;
			this.msg = msg;
		}

		public static FillWordResult newFillWordError(String msg) {
			return new FillWordResult(FillWordResultState.ERROR, 0, msg);
		}

		public static FillWordResult newFillWordSuccess(int score) {
			return new FillWordResult(FillWordResultState.SUCCESS, score, "");
		}

		public FillWordResultState getState() {
			return state;
		}

		public int getScore() {
			return score;
		}

		public String getMsg() {
			return msg;
		}
	}

	public class Move implements Comparable<Move> {
		private int x, y;
		private boolean fillVert;
		private int score;
		private String word;
		
		public Move(String s, int i, int j, boolean fill, int p) {
			x = i;
			y = i;
			fillVert = fill;
			word = s;
			score = p;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public boolean isFillVert() {
			return fillVert;
		}

		public int getScore() {
			return score;
		}

		public String getWord() {
			return word;
		}

		@Override
		public int compareTo(Move o) {
			int aScore = score;
			int bScore = o.getScore();
			if (aScore > bScore) {
				return 1;
			} else if (aScore == bScore) {
				return 0;
			} else {
				return -1;
			}
		}
	}

}