package MazeRunner;

import javax.swing.JFrame;

public class Hub extends JFrame {
	
	public Maze maze;
	
	public Hub(String file) {
		maze = new Maze(file);
	}

	public Maze getMaze() {
		return maze;
	}
}
