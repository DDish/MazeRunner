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
	
	public static void main(String[] args) {
		try {
			Hub h = new Hub("Maze.csv");
			MazeCell[][] m = h.getMaze().getMazeMap(); 
			for(int i=0;i<m.length;i++) {
				for(int j=0;j<m[0].length;j++)
					System.out.print(""+m[i][j].getInitial());
				System.out.println();
			}
				
		}
		catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
