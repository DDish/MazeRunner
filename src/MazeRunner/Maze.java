package MazeRunner;

import java.awt.Component;
import java.io.*;
import java.util.*;

import javax.swing.JFrame;

public class Maze extends JFrame {

	private ArrayList<Character> markers;
	private MazeCell[][] mazeMap;
	private static int NUM_ROWS;
	private static int NUM_COLUMNS;
	
	public Maze(String layout) throws FileNotFoundException{
		// TODO Auto-generated constructor stub
		Scanner sc = new Scanner(new FileReader(layout));
		mazeMap = new MazeCell[sc.nextInt()][sc.nextInt()];
		sc.nextLine();
		
		markers = new ArrayList<Character>();
	}

	public ArrayList<Character> getMarkers() {
		return markers;
	}

	public MazeCell[][] getMazeMap() {
		// TODO Auto-generated method stub
		return mazeMap;
	}
}
