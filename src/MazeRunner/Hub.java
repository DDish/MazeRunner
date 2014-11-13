package MazeRunner;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import java.awt.*;

public class Hub extends JFrame {
	
	private Maze maze;
	private JPanel mazePanel;
	
	public Hub(String file){
		maze = new Maze(file);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(500,100);
		setSize(1000,750);
		setTitle("MazeRunner");
		setLayout(new BorderLayout());
		createGUI();
	}

	public Maze getMaze() {
		return maze;
	}
	
	private void createGUI() {
		add(maze, BorderLayout.WEST);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1,2));
		p.add(new LPanel());
		JTextArea t = new JTextArea();
		t.setVisible(false);
		p.add(t);
		add(p, BorderLayout.SOUTH);
		
	}
	
	public static void main(String[] args) {
		Hub h = new Hub("Maze.csv");
		MazeCell[][] m = h.getMaze().getMazeMap(); 
		for(int i=0;i<m.length;i++) {
			for(int j=0;j<m[0].length;j++)
				System.out.print(""+m[i][j].getInitial());
			System.out.println();
		}
		
		h.setVisible(true);
	}
}
