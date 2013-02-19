package com.diycomputerscience.minesweeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HardcodedMineInitializationStrategy implements MineInitializationStrategy {
	
	private Map<Point, Integer> counts;
	
	public HardcodedMineInitializationStrategy() {
		this.counts = new HashMap<Point, Integer>();
		// row 0
		// X X X 2 0 0
		this.counts.put(new Point(0,0), 0);
		this.counts.put(new Point(0,1), 0);
		this.counts.put(new Point(0,2), 0);
		this.counts.put(new Point(0,3), 2);
		this.counts.put(new Point(0,4), 0);
		this.counts.put(new Point(0,5), 0);
		
		// row 1
		// X 8 X 3 0 0
		this.counts.put(new Point(1,0), 0);
		this.counts.put(new Point(1,1), 8);
		this.counts.put(new Point(1,2), 0);
		this.counts.put(new Point(1,3), 3);
		this.counts.put(new Point(1,4), 0);
		this.counts.put(new Point(1,5), 0);
		
		// row 2
		// X X X 3 1 1
		this.counts.put(new Point(2,0), 0);
		this.counts.put(new Point(2,1), 0);
		this.counts.put(new Point(2,2), 0);
		this.counts.put(new Point(2,3), 3);
		this.counts.put(new Point(2,4), 1);
		this.counts.put(new Point(2,5), 1);
		
		// row 3
		// 4 5 3 2 X 1
		this.counts.put(new Point(3,0), 4);
		this.counts.put(new Point(3,1), 5);
		this.counts.put(new Point(3,2), 3);
		this.counts.put(new Point(3,3), 2);
		this.counts.put(new Point(3,4), 0);
		this.counts.put(new Point(3,5), 1);
		
		// row 4
		// X X 1 1 1 1
		this.counts.put(new Point(4,0), 0);
		this.counts.put(new Point(4,1), 0);
		this.counts.put(new Point(4,2), 1);
		this.counts.put(new Point(4,3), 1);
		this.counts.put(new Point(4,4), 1);
		this.counts.put(new Point(4,5), 1);
		
		// row 5
		// 2 2 1 0 0 0
		this.counts.put(new Point(5,0), 2);
		this.counts.put(new Point(5,1), 2);
		this.counts.put(new Point(5,2), 1);
		this.counts.put(new Point(5,3), 0);
		this.counts.put(new Point(5,4), 0);
		this.counts.put(new Point(5,5), 0);
	}
	
	//	X X X 2 0 0
	//	X 8 X 3 0 0
	//	X X X 3 1 1
	//	4 5 3 2 X 1
	//	X X 1 1 1 1
	//	2 2 1 0 0 0
	//  There are 11 mines on the above board
	@Override
	public List<Point> mines(Point boardSize) {
		List<Point> mines = new ArrayList<Point>();
		mines.add(new Point(0,0));
		mines.add(new Point(0,1));
		mines.add(new Point(0,2));
		mines.add(new Point(1,0));
		mines.add(new Point(1,2));
		mines.add(new Point(2,0));
		mines.add(new Point(2,1));
		mines.add(new Point(2,2));
		mines.add(new Point(3,4));
		mines.add(new Point(4,0));
		mines.add(new Point(4,1));		
		return mines;
	}
	
	public int getSquareCount(Point point) {
		return this.counts.get(point);		
	}
	
	public String getSquareCountAsString(Point point) {
		int count = this.counts.get(point); 
		return String.valueOf(count);		
	}

}
