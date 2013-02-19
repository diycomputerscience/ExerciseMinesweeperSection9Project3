package com.diycomputerscience.minesweeper;

import java.util.List;

import org.apache.log4j.Logger;

import com.diycomputerscience.minesweeper.utils.MinesweeperUtils;

public class Board {

	public static final int MAX_ROWS = 6;
	public static final int MAX_COLS = 6;
	
	private Square squares[][];
	
	private Logger logger = Logger.getLogger(Board.class);
	
	public Board() {
		
	}
	
	public Board(MineInitializationStrategy mineInitializationStrategy) {
		logger.info("Initializing Board");
		this.squares = new Square[MAX_ROWS][MAX_COLS];
		// intitialize squares
		for(int row=0; row<MAX_ROWS; row++) {
			for(int col=0; col<MAX_COLS; col++) { 				
				squares[row][col] = new Square();
			}
		}
		
		//TODO: Use the mineInitializationStrategy to set mines in required squares
		List<Point> mines = mineInitializationStrategy.mines(new Point(Board.MAX_ROWS, Board.MAX_COLS));
		for(Point mine : mines) {
			this.squares[mine.row][mine.col].setMine(true);
		}
		
		logger.debug("Completed setting mines");
		
		computeCounts();
	}
	
	/**
	 * This method uncovers the specified Square
	 * @param point The Point representing the location to uncover
	 * @throws UncoveredMineException if the specified Square is a mine
	 */
	public void uncover(Point point) throws UncoveredMineException {
		logger.debug("Uncovering " + point);
		this.squares[point.row][point.col].uncover();
	}
	
	/**
	 * This method marks the specified Square
	 * @param point The point of the specified square
	 */
	public void mark(Point point) {
		logger.debug("marking " + point);
		this.squares[point.row][point.col].mark();
	}
	
	public void setSquares(Square squares[][]) {
		this.squares = squares;
	}
	
	public Square[][] getSquares() {
		return this.squares;		
	}

	public void computeCounts() {
		// determine counts of all squares that are not mines
		for(int row=0; row<MAX_ROWS; row++) {
			for(int col=0; col<MAX_COLS; col++) {
				if(!squares[row][col].isMine()) {
					List<Point> neighbours = MinesweeperUtils.computeNeibhbours(new Point(row, col));
					int count=0;
					for(Point neighbour : neighbours) {
						if(squares[neighbour.row][neighbour.col].isMine()) {
							count++;
						}
					}
					squares[row][col].setCount(count);
				}				
			}
		}
		
		logger.debug("Completed square counts");
	}
}
