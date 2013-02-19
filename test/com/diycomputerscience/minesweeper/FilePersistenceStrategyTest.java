package com.diycomputerscience.minesweeper;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FilePersistenceStrategyTest {
	
	private Board board;
	private String fileName = "c:/tmp/jminesweepertest.db";
	private FilePersistenceStrategy persistenceStrategy;
	private HardcodedMineInitializationStrategy mineInitializationStrategy;
	private String expectedLinesInSavedFile[] = {
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
																									
	
	@Before
	public void setUp() throws Exception {
		this.mineInitializationStrategy = new HardcodedMineInitializationStrategy();
		this.board = new Board(mineInitializationStrategy);
		this.persistenceStrategy = new FilePersistenceStrategy(fileName);
	}

	@After
	public void tearDown() throws Exception {
		File dbFile = new File(fileName);
		if(dbFile.exists()) {
			dbFile.delete();
		}
	}

	@Test
	public void testSave() throws Exception {
		this.board.uncover(new Point(1, 3));
		this.persistenceStrategy.save(this.board);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(this.fileName));
			String line = null;
			int count = 0;
			while((line = reader.readLine()) != null) {
				String msg = "Line in saved file did not match on line " + count;
				assertEquals(msg, this.expectedLinesInSavedFile[count], line);
				count++;
			}
		} finally {
			if(reader != null) {
				reader.close();
			}
		}
	}
	
	@Test
	public void testLoad() throws Exception {
		// create the database file
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileWriter(this.fileName));
			for(String line : this.expectedLinesInSavedFile) {
				writer.println(line);
			}
		} finally {
			if(writer != null) {
				writer.close();
			}			
		}
		
		Board board = this.persistenceStrategy.load();
		Square squares[][] = board.getSquares();
		int count = 0;
		for(int row=0; row<squares.length; row++) {
			for(int col=0; col<squares[row].length; col++) {
				Square square = squares[row][col];
				String actualData = FilePersistenceStrategy.dataForSquare(row, col, square);
				String expectedData = this.expectedLinesInSavedFile[count];
				String msg = "Incorrect square for row " + row + " col " + col;
				assertEquals(msg, expectedData, actualData);
				count++;
			}
		}
	}
}
