package com.diycomputerscience.minesweeper;

import java.util.ArrayList;
import java.util.List;

public class MockPersistenceStrategy implements PersistenceStrategy {

	private List<String> savedBoardLayout = new ArrayList<String>();
	
	public static final String expectedLinesInSavedBoard[] = {
			"0 0 COVERED 0 true",
			"0 1 COVERED 0 true",
			"0 2 COVERED 0 true",
			"0 3 COVERED 2 false",
			"0 4 COVERED 0 false",
			"0 5 COVERED 0 false",
			"1 0 COVERED 0 true",
			"1 1 COVERED 8 false",
			"1 2 COVERED 0 true",
			"1 3 UNCOVERED 3 false",
			"1 4 COVERED 0 false",
			"1 5 COVERED 0 false",
			"2 0 COVERED 0 true",
			"2 1 COVERED 0 true",
			"2 2 COVERED 0 true",
			"2 3 COVERED 3 false",
			"2 4 COVERED 1 false",
			"2 5 COVERED 1 false",
			"3 0 COVERED 4 false",
			"3 1 COVERED 5 false",
			"3 2 COVERED 3 false",
			"3 3 COVERED 2 false",
			"3 4 COVERED 0 true",
			"3 5 COVERED 1 false",
			"4 0 COVERED 0 true",
			"4 1 COVERED 0 true",
			"4 2 COVERED 1 false",
			"4 3 COVERED 1 false",
			"4 4 COVERED 1 false",
			"4 5 COVERED 1 false",
			"5 0 COVERED 2 false",
			"5 1 COVERED 2 false",
			"5 2 COVERED 1 false",
			"5 3 COVERED 0 false",
			"5 4 COVERED 0 false",
			"5 5 COVERED 0 false",
		};
	@Override
	public void save(Board board) throws PersistenceException {
		Square squares[][] = board.getSquares();
		for(int row=0; row<squares.length; row++) {
			Square squaresRow[] = squares[row];
			for(int col=0; col<squaresRow.length; col++) {
				Square square = squaresRow[col];
				savedBoardLayout.add(FilePersistenceStrategy.dataForSquare(row, col, square));
			}
		}
	}

	@Override
	public Board load() throws PersistenceException {
		Board board = new Board(new HardcodedMineInitializationStrategy());
		try {
			board.uncover(new Point(1,3));
		} catch(UncoveredMineException ume) {
			throw new AssertionError("Unexpected exception " + ume);
		}
		return board;
	}
	
	public List<String> getSavedBoardLayout() {
		return this.savedBoardLayout;
	}

}
