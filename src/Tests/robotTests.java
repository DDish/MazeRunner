package Tests;

import static org.junit.Assert.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import MazeRunner.Maze;
import MazeRunner.Robot;

public class robotTests {
	JLabel groot = new JLabel();
	private Robot r, r1, r2, r4;
	private Maze m;
	private int row, col, row1, col1, row2, col2;
	@Before
	public void setUp(){
		m = new Maze("Maze.csv");
		r = new Robot(17,28, m,groot);
		r1 = new Robot(32, 1, m,groot);
		r2 = new Robot(1, 32, m,groot);
		r4 = new Robot(1,15,m,groot);
		
		col = r.getColumn();
		row = r.getRow();
		col1 = r1.getColumn();
		row1 = r1.getRow();
		col2 = r2.getColumn();
		row2 = r2.getRow();
		
	}
	
	@Test
	public void testUp() {
		r.moveUp();
		Assert.assertTrue(r.getColumn() == col && r.getRow()+1 == row);
		System.out.println(r.getRow()+1 + " " + row);
		r2.moveUp();
		Assert.assertTrue(r2.getColumn() == col2 && r2.getRow()+1 == row2);
	}
	@Test
	public void testDown(){
		System.out.println("before move: " + r.getRow() + " " + r.getColumn());
		r.moveDown();
		System.out.println("after move: " + r.getRow() + " " + r.getColumn());
		Assert.assertTrue(r.getColumn() == col);
		Assert.assertTrue(r.getRow()-1 == row);
		r1.moveDown();
		Assert.assertTrue(r1.getColumn() == col1 && r1.getRow()-1 == row1);
	}
	@Test
	public void testRight(){
		r.moveRight();
		Assert.assertTrue(r.getColumn()-1 == col && r.getRow() == row);
		r2.moveRight();
		Assert.assertTrue(r2.getColumn()-1 == col2 && r2.getRow() == row2);
	}
	@Test
	public void testLeft(){
		r.moveLeft();
		Assert.assertTrue(r.getColumn()+1 == col && r.getRow() == row);
		r1.moveLeft();
		Assert.assertTrue(r1.getColumn()+1 == col1 && r1.getRow() == row1);		
	}
	
	@Test
	public void testPathfinding(){
		r4.moveToDestination(m,'A');
		System.out.println(r4.getTestMarker());
		Assert.assertEquals('A',r4.getTestMarker());
		Assert.assertEquals(1, r4.getTestRow());
		Assert.assertEquals(1, r4.getTestColumn());
		r4.moveToDestination(m,'S');
		Assert.assertEquals('S',r4.getMarker());
		Assert.assertEquals(1, r4.getRow());
		Assert.assertEquals(15, r4.getColumn());
	}
	
	@Test
	public void testMemory() {
		r4.moveToDestination(m, 'A');
		Assert.assertFalse(r4.getVisited().isEmpty());
	}
	
	@Test 
	public void testCommunication() {
		r1.shareMap(m);
		Assert.assertFalse(r1.getMaze().isEmpty());
	}
	
}
