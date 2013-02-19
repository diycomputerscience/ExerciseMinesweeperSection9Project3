package com.diycomputerscience.minesweeper;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {

	private Board board;
	private HardcodedMineInitializationStrategy mineInitializationStrategy;
	
	@Before
	public void setUp() throws Exception {
		this.mineInitializationStrategy = new HardcodedMineInitializationStrategy();
		this.board = new Board(mineInitializationStrategy);
	}

	@After
	public void tearDown() throws Exception {
		this.board = null;
	}

	@Test
	public void testSquaresGridSize() throws Exception {
		Square squares[][] = this.board.getSquares();
		assertEquals(Board.MAX_ROWS, squares.length);
		for(Square squareRow[] : squares) {
			assertEquals(Board.MAX_COLS, squareRow.length);
		}
	}
	
	@Test
	public void testSquaresNotNull() {
		Square squares[][] = this.board.getSquares();		
		for(Square squareRow[] : squares) {			
			for(Square aSquare : squareRow) {
				assertNotNull(aSquare);
			}
		}
	}
	
	@Test
	public void testAtleastOneSquareShouldBeAMine() throws Exception {
		boolean mineFound = false;
		Square squares[][] = this.board.getSquares();
		for(int row=0; row<squares.length; row++) {
			Square squareRow[] = squares[row];
			for(int col=0; col<squareRow.length; col++) {
				Square square = squareRow[col];
				if(square.isMine()) {
					// this code can be refactored to avoid unnecassary looping if a mine is found once
					// however we have kept it as is for the sake of simplicity
					mineFound = true;
				}
			}
		}
		// test will fail if at least one mine has not been found
		assertTrue(mineFound);
	}
	
	@Test
	public void testUncoverSquaresWhichAreNotMines() throws Exception {
		Square squares[][] = this.board.getSquares();
		for(int row=0; row<squares.length; row++) {
			Square squareRow[] = squares[row];
			for(int col=0; col<squareRow.length; col++) {
				Square square = squareRow[col];
				if(!square.isMine()) {
					// this should uncover the Square object we are holding
					this.board.uncover(new Point(row, col));
					assertEquals(Square.SquareState.UNCOVERED, square.getState());
				}
			}
		}
	}
	
	@Test
	public void testUncoverSquaresWhichAreMines() throws Exception {
		Square squares[][] = this.board.getSquares();
		for(int row=0; row<squares.length; row++) {
			Square squareRow[] = squares[row];
			for(int col=0; col<squareRow.length; col++) {
				Square square = squareRow[col];
				if(square.isMine()) {
					try {
						// expect an exception
						this.board.uncover(new Point(row, col));
						// control should never come here, because invoking the line above should throw an Exception
						fail("Exception was not thrown when a mine was uncovered");
					} catch(UncoveredMineException eme) {
						// this is what we expect... so the code passes
					}
				}
			}
		}
	}
	
	@Test
	public void testUncoverSquaresWhichAreAlreadyUncovered() throws Exception {
		Square squares[][] = this.board.getSquares();
		for(int row=0; row<squares.length; row++) {
			Square squareRow[] = squares[row];
			for(int col=0; col<squareRow.length; col++) {
				Square square = squareRow[col];
				if(!square.isMine()) {
					this.board.uncover(new Point(row, col));
					assertEquals(Square.SquareState.UNCOVERED, square.getState());
					// uncover the same square and verify that it's state is still UNCOVERED
					this.board.uncover(new Point(row, col));
					assertEquals(Square.SquareState.UNCOVERED, square.getState());
				}
			}
		}
	}
	
	@Test
	public void testMarkSquare() throws Exception {
		Square squares[][] = this.board.getSquares();
		for(int row=0; row<squares.length; row++) {
			Square squareRow[] = squares[row];
			for(int col=0; col<squareRow.length; col++) {
				Square square = squareRow[col];
				this.board.mark(new Point(row, col));
				assertEquals(Square.SquareState.MARKED, square.getState());				
			}
		}
	}
	
	@Test
	public void testMarkSquaresWhichAreAlreadyMarked() throws Exception {
		Square squares[][] = this.board.getSquares();
		for(int row=0; row<squares.length; row++) {
			Square squareRow[] = squares[row];
			for(int col=0; col<squareRow.length; col++) {
				Square square = squareRow[col];
				this.board.mark(new Point(row, col));
				assertEquals(Square.SquareState.MARKED, square.getState());
				// mark the square again and verify that the state is changed to COVERED
				this.board.mark(new Point(row, col));
				assertEquals(Square.SquareState.COVERED, square.getState());
			}
		}
	}

	@Test
	public void testMarkSquaresWhichAreUncovered() throws Exception {
		Square squares[][] = this.board.getSquares();
		for(int row=0; row<squares.length; row++) {
			Square squareRow[] = squares[row];
			for(int col=0; col<squareRow.length; col++) {
				Square square = squareRow[col];
				if(!square.isMine()) {
					// this should uncover the Square object we are holding
					this.board.uncover(new Point(row, col));
					assertEquals(Square.SquareState.UNCOVERED, square.getState());
					// marking an uncovered Square should have no effect
					this.board.uncover(new Point(row, col));
					assertEquals(Square.SquareState.UNCOVERED, square.getState());
				}
			}
		}
	}
	
	@Test
	public void testUncoverASquareWhichIsAlreadyMarked() throws Exception {
		Square squares[][] = this.board.getSquares();
		for(int row=0; row<squares.length; row++) {
			Square squareRow[] = squares[row];
			for(int col=0; col<squareRow.length; col++) {
				Square square = squareRow[col];
				this.board.mark(new Point(row, col));
				assertEquals(Square.SquareState.MARKED, square.getState());
				// uncovering a marked square should have no effect
				this.board.uncover(new Point(row, col));
				assertEquals(Square.SquareState.MARKED, square.getState());
			}
		}
	}
	
	@Test
	public void testSquareCount() throws Exception {
		Square squares[][] = this.board.getSquares();
		for(int row=0; row<Board.MAX_ROWS; row++) {
			for(int col=0; col<Board.MAX_COLS; col++) {
				assertEquals(this.mineInitializationStrategy.getSquareCount(new Point(row, col)),
							 squares[row][col].getCount());
			}
		}
	}
}
