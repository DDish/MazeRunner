package Tests;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import MazeRunner.Maze;
import MazeRunner.Robot;

public class robotTests {
	private Robot r, r1, r2;
	private Maze m;
	private int row, col;
	@Before
	public void setUp(){
		r = new Robot(17,28);
		r1 = new Robot(32, 1);
		r2 = new Robot(1, 32);
		m = new Maze("Maze.csv");
		col = r.getColumn();
		row = r.getRow();
	}
	
	@Test
	public void testUp() {
		r.moveUp();
		Assert.assertTrue(r.getColumn() == col && r.getRow()-1 == row);
		r2.moveUp();
		Assert.assertTrue(r.getColumn() == col && r.getRow() == row);
	}
	@Test
	public void testDown(){
		r.moveDown();
		Assert.assertTrue(r.getColumn() == col && r.getRow()+1 == row);
		r1.moveDown();
		Assert.assertTrue(r.getColumn() == col && r.getRow() == row);
	}
	@Test
	public void testRight(){
		r.moveRight();
		Assert.assertTrue(r.getColumn()-1 == col && r.getRow() == row);
		r2.moveRight();
		Assert.assertTrue(r.getColumn() == col && r.getRow() == row);
	}
	@Test
	public void testLeft(){
		r.moveLeft();
		Assert.assertTrue(r.getColumn()+1 == col && r.getRow() == row);
		r1.moveLeft();
		Assert.assertTrue(r.getColumn() == col && r.getRow() == row);		
	}
	
	
	
}
