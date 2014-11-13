package Tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import MazeRunner.*;

public class mazeTests {

	private static Hub hub;
	private static Maze maze;
	private static MazeCell[][] map;
	public static final int NUM_MARKS = 14;
	public static final int NUM_ROWS = 34;
	public static final int NUM_COLUMNS = 34;
	
	@Before
	public void setUp() throws Exception {
		hub = new Hub("MazeLayout.csv");
		maze = hub.getMaze();
		map = maze.getMazeMap();
	}

	@Test
	public void test() {
		Assert.assertEquals(NUM_MARKS,maze.getMarkers().size());
		Assert.assertEquals(NUM_ROWS, map.length);
		Assert.assertEquals(NUM_COLUMNS, map[0].length);
		
		for(int i=0;i<map.length;i++)
			for(int j=0;j<map[0].length;j++)
				Assert.assertNotNull(map[i][j]);
	}

}
