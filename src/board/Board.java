package board;

import letter.Alphabet;

public class Board {
	private static final int side = 11;
	private Alphabet grid[][] = new Alphabet[side][side];

	public Board() {
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				grid[i][j] = Alphabet.Empty;
			}
		}
	}

	public Board(Board b1) throws Exception {
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

	public Alphabet[][] getGrid() {
		return grid;
	}

	public void printBoard() {
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				System.out.print(grid[i][j] + "  ");
			}
			System.out.println();
		}
	}
	
	public void setTile(int i, int j, Alphabet a) throws Exception {
		if (i >= side || j >= side) {
			throw new Exception();
		}
		grid[i][j] = a;
	}
}