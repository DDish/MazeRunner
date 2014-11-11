package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import MazeRunner.Maze;



public class mazeTests {

	private static Maze maze;
	public static final int NUM_MARKS = 10;
	public static final int NUM_ROWS = 100;
	public static final int NUM_COLUMNS = 100;
	
	@Before
	public void setUp() throws Exception {
		maze = new maze("MazeLayout.csv");
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
