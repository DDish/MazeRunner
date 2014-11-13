package MazeRunner;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Maze extends JFrame {

	private ArrayList<Character> markers;
	private MazeCell[][] mazeMap;
	
	public Maze(String layout) {
		// TODO Auto-generated constructor stub
		mazeMap = new MazeCell[34][34];
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
