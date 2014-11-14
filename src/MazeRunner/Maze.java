package MazeRunner;

import java.awt.Component;
import java.awt.Graphics;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

public class Maze extends JPanel {

	private ArrayList<Character> markers;
	private MazeCell[][] mazeMap;
	private static int NUM_ROWS;
	private static int NUM_COLUMNS;
	
	public Maze(String layout){
		markers = new ArrayList<Character>();
		try {
			loadMaze(layout);
		}
		catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		setBorder(new EtchedBorder());
	}

	private void loadMaze(String layout) throws FileNotFoundException {
		// TODO Auto-generated method stub
		int i=0;
		Scanner sc = new Scanner(new FileReader(layout));
		mazeMap = new MazeCell[sc.nextInt()][sc.nextInt()];
		sc.nextLine();
		while(sc.hasNextLine())
		{
			int j=0;
			String st[] = sc.nextLine().split(",");
			for(String s:st)
			{
				boolean bool = false;
				char c = s.toCharArray()[0];
				if(c != 'X' && c != '0')
				{
					bool = true;
					markers.add(c);
				}
				mazeMap[i][j]=new MazeCell(i,j,bool,c);
				j++;
			}
			i++;
		}
	}

	public ArrayList<Character> getMarkers() {
		return markers;
	}

	public MazeCell[][] getMazeMap() {
		// TODO Auto-generated method stub
		return mazeMap;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillRect(50,50,50,50);
	}
	
	public boolean isAdjacent(int x1, int y1, int x2, int y2)
	{//I am not sure if this is actually going to be necessary
		return false;
	}
}
