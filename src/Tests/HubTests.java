package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import MazeRunner.Hub;
import MazeRunner.Maze;
import MazeRunner.Robot;

public class HubTests {
	private Robot r, r1, r2;
	private Maze m;
	private int row, col;
	Hub gui;
	@Before
	public void setUp(){
		gui = new Hub("Maze.csv",0);
		r = new Robot(17,28, m, null);
		r1 = new Robot(32, 1, m, null);
		r2 = new Robot(1, 32, m, null);
		gui.getBots().add(r);
		gui.getBots().add(r1);
		gui.getBots().add(r2);
		col = r.getColumn();
		row = r.getRow();
	}
	
	// tests number of robots.
	@Test
	public void testNumBots() {
		assertEquals(3, gui.getBots().size());
		Hub gui2=new Hub("Maze.csv");
		assertEquals(10, gui2.getBots().size());
	}
	
	// tests order of robots.
	@Test
	public void testOrderBots() {
	assertEquals(r, gui.getBots().peek());
	gui.getBots().add(gui.getBots().peek());
	gui.getBots().remove();
	assertEquals(r1, gui.getBots().peek());
	gui.getBots().add(gui.getBots().peek());
	gui.getBots().remove();
	assertEquals(r2, gui.getBots().peek());
	gui.getBots().add(gui.getBots().peek());
	gui.getBots().remove();
	assertEquals(r, gui.getBots().peek());
	}
	
	// tests that robot receives order correctly. 
	@Test
	public void testSetDestination() {
	gui.issueCommand(gui.getBots().peek(), 'F');
	assertEquals('F', gui.getBots().peek().getMarker());
	gui.getBots().remove();
	gui.issueCommand(gui.getBots().peek(), 'H');
	assertEquals('H', gui.getBots().peek().getMarker());
	gui.getBots().remove();
	gui.issueCommand(gui.getBots().peek(), 'O');
	assertEquals('O', gui.getBots().peek().getMarker());
	}
}
