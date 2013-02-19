package com.diycomputerscience.minesweeper.view;

import java.util.List;

import javax.swing.JOptionPane;

import org.fest.swing.core.MouseButton;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.diycomputerscience.minesweeper.Board;
import com.diycomputerscience.minesweeper.HardcodedMineInitializationStrategy;
import com.diycomputerscience.minesweeper.MockPersistenceStrategy;

public class UIPersistenceTest {

	private FrameFixture window;
	private UI ui;
	private Board board;
	private HardcodedMineInitializationStrategy mineInitializationStrategy;
	private MockOptionPane mockOptionPane;
	private MockPersistenceStrategy persistenceStrategy;
	
	@BeforeClass 
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}
	
	@Before
	public void setUp() throws Exception {
		this.mineInitializationStrategy = new HardcodedMineInitializationStrategy();
		this.mockOptionPane = new MockOptionPane(JOptionPane.YES_OPTION);
		this.persistenceStrategy = new MockPersistenceStrategy();
		this.board = new Board(mineInitializationStrategy);
		this.ui = GuiActionRunner.execute(new GuiQuery<UI>() {

			@Override
			protected UI executeInEDT() throws Throwable {
				return UI.build(board, mockOptionPane, persistenceStrategy);
			}
			
		});
		this.window = new FrameFixture(this.ui);
		this.window.show();
	}

	@After
	public void tearDown() throws Exception {
		this.window.cleanUp();
	}

	@Test
	public void testSave() throws Exception {
		// uncover a Square		
		this.window.button(1+","+3).click(MouseButton.LEFT_BUTTON);
		
		// save from the UI menu
		this.window.menuItem("file-save").click();		
		
		// verify data in MockPersistenceStrategy
		List<String> savedBoardLayout = this.persistenceStrategy.getSavedBoardLayout();
		Assert.assertEquals(MockPersistenceStrategy.expectedLinesInSavedBoard.length, savedBoardLayout.size());
		
		for(int i=0; i<MockPersistenceStrategy.expectedLinesInSavedBoard.length; i++) {
			Assert.assertEquals(MockPersistenceStrategy.expectedLinesInSavedBoard[i], savedBoardLayout.get(i));
		}
	}
	
	@Test
	public void testLoad() {
		// click the load file menu
		this.window.menuItem("file-load").click();
		Assert.assertEquals("3", this.window.button(1+","+3).text());
	}
}
