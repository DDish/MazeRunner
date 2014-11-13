package Tests;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import MazeRunner.Maze;
import MazeRunner.Robot;

public class robotTests {
	private Robot r;
	private Maze m;
	private int row, col;
	@Before
	public void setUp(){
		r = new Robot(17,28);
		m = new Maze("Maze.csv");
		col = r.getColumn();
		row = r.getRow();
	}
	
	@Test
	public void testUp() {
		r.moveUp();
		Assert.assertTrue(r.getColumn() == col && r.getRow()-1 == row);
	}
	@Test
	public void testDown(){
		r.moveDown();
		Assert.assertTrue(r.getColumn() == col && r.getRow()+1 == row);
	}
	@Test
	public void testRight(){
		r.moveRight();
		Assert.assertTrue(r.getColumn()-1 == col && r.getRow() == row);
	}
	@Test
	public void testLeft(){
		r.moveLeft();
		Assert.assertTrue(r.getColumn()+1 == col && r.getRow() == row);
	}
	
	
	
}
