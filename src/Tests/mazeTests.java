package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import MazeRunner.*;

public class mazeTests {

	private static Hub hub;
	private static Maze maze;
	private static MazeCell[][] map;
	private static ArrayList<Character> markers;
	public static final int NUM_MARKS = 15;
	public static final int NUM_ROWS = 34;
	public static final int NUM_COLUMNS = 34;
	
	@Before
	public void setUp() throws Exception {
		hub = new Hub("Maze.csv");
		maze = hub.getMaze();
		map = maze.getMazeMap();
		markers = maze.getMarkers();
	}

	//Tests initial loading of the maze layout file
	@Test
	public void loadTest() {
		Assert.assertEquals(NUM_ROWS, map.length);
		Assert.assertEquals(NUM_COLUMNS, map[0].length);
		Assert.assertEquals(NUM_MARKS,markers.size());
		
		for(int i=0;i<map.length;i++)
			for(int j=0;j<map[0].length;j++)
			{
				Assert.assertNotNull(map[i][j]);
				if(map[i][j].isMarker())
					Assert.assertTrue(markers.contains(map[i][j].getInitial()));
			}
		
	}
	
	//Test adjacency procedure for cycling through MazeCells
	@Test
	public void adjacencyTest() {
		Assert.assertTrue(maze.isAdjacent(1,1,1,2));
		Assert.assertTrue(maze.isAdjacent(1,1,2,1));
		Assert.assertFalse(maze.isAdjacent(1,1,3,1));
		Assert.assertTrue(maze.isAdjacent(15,6,15,5));
	}

}
