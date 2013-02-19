package com.diycomputerscience.minesweeper.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.ColorUIResource;

import org.fest.swing.core.MouseButton;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.diycomputerscience.minesweeper.Board;
import com.diycomputerscience.minesweeper.HardcodedMineInitializationStrategy;
import com.diycomputerscience.minesweeper.MockPersistenceStrategy;
import com.diycomputerscience.minesweeper.PersistenceStrategy;
import com.diycomputerscience.minesweeper.Point;
import com.diycomputerscience.minesweeper.Square;

public class UITest {
	
	private FrameFixture window;
	private Board board;
	private MockOptionPane optionPane;
	private HardcodedMineInitializationStrategy mineInitializationStrategy;
	private PersistenceStrategy persistenceStrategy;
	
	@BeforeClass 
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}
	
	@Before
	public void setUp() throws Exception {
		UI ui = GuiActionRunner.execute(new GuiQuery<UI>() {

			@Override
			protected UI executeInEDT() throws Throwable {
				optionPane = new MockOptionPane(JOptionPane.NO_OPTION);
				mineInitializationStrategy = new HardcodedMineInitializationStrategy();
				persistenceStrategy = new MockPersistenceStrategy();
				board = new Board(mineInitializationStrategy);
				return UI.build(board, optionPane, persistenceStrategy);
			}
			
		});
		
		this.window = new FrameFixture(ui);
		this.window.show();
	}

	@After
	public void tearDown() throws Exception {
		this.window.cleanUp();
	}

	@Test
	public void testUIVisibility() {
		this.window.requireVisible();		
	}

	@Test
	public void testUIDefaultCloseOperation() {
		assertEquals(JFrame.DISPOSE_ON_CLOSE, ((JFrame)this.window.target).getDefaultCloseOperation());
	}
	
	@Test
	public void testUITitle() {
		assertEquals("Minesweeper", this.window.target.getTitle());
	}
	
	@Test
	public void testMainPanel() {
		JPanel mainPanel = this.window.panel("MainPanel").target;
		
		// verify that the contentPane contains a JPanel called "MainPanel"
		assertNotNull(mainPanel);
		
		// verify that the layoutManaget of the mainPanel is GridLayout
		assertEquals(GridLayout.class, mainPanel.getLayout().getClass());
		
		// verify the dimensions of the GridLayout
		assertEquals(Board.MAX_ROWS, ((GridLayout)mainPanel.getLayout()).getRows());
		assertEquals(Board.MAX_COLS, ((GridLayout)mainPanel.getLayout()).getColumns());	
	}
	
	@Test
	public void testSquares() {
		JPanel mainPanel = this.window.panel("MainPanel").target;
		
		Component components[] = mainPanel.getComponents();
		
		// verify that the mainPanel has Board.MAX_ROWS x Board.MAX_COLS components
		assertEquals(Board.MAX_ROWS*Board.MAX_COLS, components.length);
		
		// verify that each component in the mainPanel is a JButton
		for(Component component : components) {
			assertEquals(JButton.class, component.getClass());
		}
	}
	
	@Test
	public void testLeftClickCoveredSquareWhichIsNotAMine() throws Exception {
		Square squares[][] = this.board.getSquares();
		Point point = getFirstCoveredSquareWhichIsNotAMine(squares);
		this.window.button(point.row+","+point.col).click(MouseButton.LEFT_BUTTON).equals(squares[point.row][point.col].getCount());
	}
	
	@Test
	public void testLeftClickUncoveredSquareWhichIsNotAMine() throws Exception {
		Square squares[][] = this.board.getSquares();
		Point point = getFirstCoveredSquareWhichIsNotAMine(squares);
		this.window.button(point.row+","+point.col).click(MouseButton.LEFT_BUTTON);
		this.window.button(point.row+","+point.col).click(MouseButton.LEFT_BUTTON).equals(squares[point.row][point.col].getCount());
	}
	
	@Test
	public void testLeftClickCoveredSquareWhichIsAMine() throws Exception {
		Square squares[][] = this.board.getSquares();
		Point point = getFirstCoveredSquareWhichIsAMine(squares);
		this.window.button(point.row+","+point.col).click(MouseButton.LEFT_BUTTON);
		assertEquals("Confirm quit", this.optionPane.getTitle());
	}
	
	@Test
	public void testRightClickCoveredSquareWhichIsNotAMine() throws Exception {
		Square squares[][] = this.board.getSquares();
		Point point = getFirstCoveredSquareWhichIsNotAMine(squares);
		this.window.button(point.row+","+point.col).click(MouseButton.RIGHT_BUTTON).background().requireEqualTo(ColorUIResource.MAGENTA);
	}
	
	@Test
	public void testRightClickCoveredSquareWhichIsAMine() throws Exception {
		Square squares[][] = this.board.getSquares();
		Point point = getFirstCoveredSquareWhichIsAMine(squares);
		this.window.button(point.row+","+point.col).click(MouseButton.RIGHT_BUTTON).background().requireEqualTo(ColorUIResource.MAGENTA);
	}
	
	@Test
	public void testRightClickMarkedSquareWhichIsNotAMine() throws Exception {
		Square squares[][] = this.board.getSquares();
		Point point = getFirstCoveredSquareWhichIsNotAMine(squares);
		this.window.button(point.row+","+point.col).click(MouseButton.RIGHT_BUTTON);
		this.window.button(point.row+","+point.col).click(MouseButton.RIGHT_BUTTON).background().requireEqualTo(new ColorUIResource(238, 238, 238));
	}
	
	@Test
	public void testRightClickMarkedSquareWhichIsAMine() throws Exception {
		Square squares[][] = this.board.getSquares();
		Point point = getFirstCoveredSquareWhichIsAMine(squares);
		this.window.button(point.row+","+point.col).click(MouseButton.RIGHT_BUTTON);
		this.window.button(point.row+","+point.col).click(MouseButton.RIGHT_BUTTON).background().requireEqualTo(new ColorUIResource(238, 238, 238));
	}
	
	@Test
	public void testLeftClickMarkedSquareWhichIsNotAMine() throws Exception {
		Square squares[][] = this.board.getSquares();
		Point point = getFirstCoveredSquareWhichIsNotAMine(squares);
		this.window.button(point.row+","+point.col).click(MouseButton.RIGHT_BUTTON);
		this.window.button(point.row+","+point.col).click(MouseButton.LEFT_BUTTON).background().requireEqualTo(ColorUIResource.MAGENTA);
	}
	
	@Test
	public void testLeftClickMarkedSquareWhichIsAMine() throws Exception {
		Square squares[][] = this.board.getSquares();
		Point point = getFirstCoveredSquareWhichIsAMine(squares);
		this.window.button(point.row+","+point.col).click(MouseButton.RIGHT_BUTTON);
		this.window.button(point.row+","+point.col).click(MouseButton.LEFT_BUTTON).background().requireEqualTo(ColorUIResource.MAGENTA);
	}
	
	@Test
	public void testRightClickUncoveredSquareWhichIsNotAMine() throws Exception {
		Square squares[][] = this.board.getSquares();
		Point point = getFirstCoveredSquareWhichIsNotAMine(squares);
		this.window.button(point.row+","+point.col).click(MouseButton.LEFT_BUTTON);
		this.window.button(point.row+","+point.col).click(MouseButton.RIGHT_BUTTON).text().equals(squares[point.row][point.col].getCount());
		this.window.button(point.row+","+point.col).background().requireEqualTo(new ColorUIResource(238, 238, 238));
	}		
	
	private Point getFirstCoveredSquareWhichIsAMine(Square[][] squares) {
		for(int row=0; row<squares.length; row++) {
			for(int col=0; col<squares[row].length; col++) {
				if(squares[row][col].getState().equals(Square.SquareState.COVERED)  && squares[row][col].isMine()) {
					return new Point(row, col);
				}
			}
		}
		return null;
	}
	
	private Point getFirstCoveredSquareWhichIsNotAMine(Square[][] squares) {
		for(int row=0; row<squares.length; row++) {
			for(int col=0; col<squares[row].length; col++) {
				if(squares[row][col].getState().equals(Square.SquareState.COVERED)  && !squares[row][col].isMine()) {
					return new Point(row, col);
				}
			}
		}
		return null;
	}
	
}
