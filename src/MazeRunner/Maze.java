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
		markers = new ArrayList<Character>();
		loadMaze(layout);
		
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
}
