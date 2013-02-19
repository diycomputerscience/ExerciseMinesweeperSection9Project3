package com.diycomputerscience.minesweeper.view;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.fest.swing.core.MouseButton;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.diycomputerscience.minesweeper.Board;
import com.diycomputerscience.minesweeper.HardcodedMineInitializationStrategy;
import com.diycomputerscience.minesweeper.MockPersistenceStrategy;

@RunWith(Parameterized.class)
public class I18NTest {
		
	private FrameFixture window;
	private Board board;
	private HardcodedMineInitializationStrategy mineInitializationStrategy;
	private MockPersistenceStrategy persistenceStrategy;
	private MockOptionPane mockOptionPane;
	private ResourceBundle resourceBundle;
	
	@Parameters
	public static Collection data() {
		List data = new ArrayList();
		data.add(new Object[]{Locale.US});
		data.add(new Object[]{Locale.FRANCE});
		return data;
	}
	
	public I18NTest(Locale locale) {
		locale.setDefault(locale);
	}
	
	@BeforeClass 
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}
	
	@Before
	public void setUp() throws Exception {
		this.resourceBundle = ResourceBundle.getBundle("MessageBundle");
		this.mineInitializationStrategy = new HardcodedMineInitializationStrategy();
		this.persistenceStrategy = new MockPersistenceStrategy();
		// Using the NO_OPTION here is important, because it prevents the application
		// from disposing the JFrame if a mine is clicked
		this.mockOptionPane = new MockOptionPane(JOptionPane.NO_OPTION);
		this.board = new Board(mineInitializationStrategy);
		
		UI ui = GuiActionRunner.execute(new GuiQuery<UI>() {

			@Override
			protected UI executeInEDT() throws Throwable {
				return UI.build(board, mockOptionPane, persistenceStrategy);
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
	public void testTitle() throws Exception {
		
		assertEquals(this.resourceBundle.getString("title"), this.window.target.getTitle());		
	}		
	
	@Test
	public void testMenu() throws Exception {		
		assertEquals(this.resourceBundle.getString("menuitem.save"),
				     this.window.menuItem("file-save").target.getLabel());		
		
		assertEquals(this.resourceBundle.getString("menuitem.load"),
					 this.window.menuItem("file-load").target.getLabel());
		
		assertEquals(this.resourceBundle.getString("menuitem.close"),
					 this.window.menuItem("file-close").target.getLabel());
		
		assertEquals(this.resourceBundle.getString("menuitem.about"),
					 this.window.menuItem("help-about").target.getLabel());
		
	}
	
	@Test
	public void testGameoverDialogue() throws Exception {
		this.window.button("0,0").click(MouseButton.LEFT_BUTTON);		
		// verify that the MockOptionpane was called
		assertEquals(this.resourceBundle.getString("gameover.dialogue.msg.title"), 
				     this.mockOptionPane.getTitle());
		assertEquals(this.resourceBundle.getString("gameover.dialogue.msg"), 
				     this.mockOptionPane.getMessage());		
	}
}
