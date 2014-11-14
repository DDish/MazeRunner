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
		gui = new Hub("Maze.csv");
		r = new Robot(17,28);
		r1 = new Robot(32, 1);
		r2 = new Robot(1, 32);
		gui.getBots().add(r);
		gui.getBots().add(r1);
		gui.getBots().add(r2);
		col = r.getColumn();
		row = r.getRow();
	}
	
	// tests number of robots.
	@Test
	public void testNumBots() {
	assertEquals(4, gui.getBots());
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
	gui.issueCommand(r, 'D');
	assertEquals('D', gui.getBots().peek().getMarker());
	gui.issueCommand(r1, 'H');
	gui.getBots().remove();
	assertEquals('H', gui.getBots().peek().getMarker());
	gui.issueCommand(r2, 'E');
	gui.getBots().remove();
	assertEquals('E', gui.getBots().peek().getMarker());
	
	}
}
