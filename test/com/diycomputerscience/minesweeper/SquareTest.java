package com.diycomputerscience.minesweeper;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SquareTest {
	
	private Square square;

	@Before	
	public void setUp() throws Exception {
		this.square = new Square();
	}

	@After
	public void tearDown() throws Exception {
		this.square = null;
	}

	@Test
	public void testInitialSquareState() throws Exception {
		assertEquals(Square.SquareState.COVERED, this.square.getState());
	}
	
	@Test
	public void testUncoverCoveredSquare() throws Exception {
		this.square.uncover();
		assertEquals(Square.SquareState.UNCOVERED, this.square.getState());
	}

	@Test
	public void testUncoverUncoveredSquare() throws Exception {
		this.square.uncover();
		this.square.uncover();
		assertEquals(Square.SquareState.UNCOVERED, this.square.getState());
	}
	
	@Test
	public void testMarkCoveredSquare() throws Exception {
		this.square.mark();
		assertEquals(Square.SquareState.MARKED, this.square.getState());
	}
	
	@Test
	public void testMarkUncoveredSquare() throws Exception {
		this.square.uncover();
		this.square.mark();
		assertEquals(Square.SquareState.UNCOVERED, this.square.getState());
	}
	
	@Test
	public void testUncoverMarkedSquareWhichIsNotAMine() throws Exception {
		this.square.mark();
		this.square.uncover();		
		assertEquals(Square.SquareState.MARKED, this.square.getState());
	}
	
	@Test
	public void testUncoverMarkedSquareWhichIsAAMine() throws Exception {
		this.square.setMine(true);
		this.square.mark();
		this.square.uncover();		
		assertEquals(Square.SquareState.MARKED, this.square.getState());
	}
	
	@Test
	public void testMarkMarkedSquare() throws Exception {
		this.square.mark();
		this.square.mark();
		assertEquals(Square.SquareState.COVERED, this.square.getState());
	}
	
	@Test(expected=UncoveredMineException.class)
	public void testUncoverMine() throws Exception {
		this.square.setMine(true);
		this.square.uncover();
	}
	
}
