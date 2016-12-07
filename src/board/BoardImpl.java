package board;

import letter.Alphabet;

public class BoardImpl implements Board {
	private static final int side = 11;
	private Alphabet grid[][] = new Alphabet[side][side];

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
}