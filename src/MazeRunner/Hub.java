package MazeRunner;

import java.io.FileNotFoundException;

import javax.swing.JFrame;

public class Hub extends JFrame {
	
	public Maze maze;
	
	public Hub(String file) throws FileNotFoundException {
		maze = new Maze(file);
	}

	public Maze getMaze() {
		return maze;
	}
}
