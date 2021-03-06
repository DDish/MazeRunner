package MazeRunner;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

public class Maze extends JPanel {

	private ArrayList<Character> markers;
	private ArrayList<MazeCell> markerList;
	private MazeCell[][] mazeMap;
	private int rows;
	private int cols;
	private static int NUM_ROWS;
	private static int NUM_COLUMNS;
	private int robotRow;
	private int robotCol;
	public boolean editInProgress = false;
	private BufferedImage robot;
	
	public Maze(String layout){
		markers = new ArrayList<Character>();
		try {
			loadMaze(layout);
		}
		catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		try {
			robot = ImageIO.read(new File("images/r2d2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setBorder(new EtchedBorder());
		
		robotRow = 1;
		robotCol = 15;
	}

	public Maze(int startx, int starty, int r,int c) {
		//create a blank maze of the right size for the robots
		rows=r;
		cols=c;
		mazeMap = new MazeCell[r][c];
		markerList = new ArrayList<MazeCell>();
		markers = new ArrayList<Character>();
		for(int i=0;i<r;i++)
		{
			for(int j=0;j<c;j++)
			{
				mazeMap[i][j]=new MazeCell(i,j,false,'?');
			}
		}
		markers.add('S');
		mazeMap[startx][starty].setMarker(true);
		mazeMap[startx][starty].setInitial('S');
		markerList.add(mazeMap[startx][starty]);
	}

	private void loadMaze(String layout) throws FileNotFoundException {
		// TODO Auto-generated method stub
		int i=0;
		Scanner sc = new Scanner(new FileReader(layout));
		mazeMap = new MazeCell[sc.nextInt()][sc.nextInt()];
		markerList = new ArrayList<MazeCell>();
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
				if(bool)
					markerList.add(mazeMap[i][j]);
				j++;
				if(cols < j) cols = j;
			}
			i++;
			rows = i;
		}
	}

	public void clearBreadCrumbs(){
		for(MazeCell[] m1 : mazeMap){
			for(MazeCell m : m1){
				m.pickupBreadCrumb();
			}
		}
		this.repaint();
	}
	
	public ArrayList<Character> getMarkers() {
		return markers;
	}

	public MazeCell[][] getMazeMap() {
		return mazeMap;
	}
	
	
	public MazeCell getCellAt(int x, int y) {
		return mazeMap[x][y];
	}
	
	public int getRows(){
		return rows;
	}
	
	public int getCols(){
		return cols;
	}
	
	public boolean isAdjacent(int x1, int y1, int x2, int y2)
	{//I am not sure if this is actually going to be necessary
		if(Math.abs(x1-x2)==1 && Math.abs(y1-y2)==0) return true;
		if(Math.abs(x1-x2)==0 && Math.abs(y1-y2)==1) return true;
		return false;
	}

	public ArrayList<MazeCell> getMarkerList() {
		// TODO Auto-generated method stub
		return markerList;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i=0;i<mazeMap.length;i++)
			for(int j=0;j<mazeMap[0].length;j++)
				mazeMap[i][j].draw(g,j,i,mazeMap[i][j].getColor());
		repaint();
		
		g.drawImage(robot, 40+robotCol*16, 40+robotRow*16, null);
	}
	
	public void addMarker(MazeCell cell){
		markerList.add(cell);
		markers.add(cell.getInitial());
	}
	
	public void setRobRow(int row) {
		robotRow = row;
	}
	
	public void setRobCol(int col) {
		robotCol = col;
	}
	
	public Boolean isEmpty() {
		if(mazeMap.length == 0)
			return true;
		return false;
	}
	
}
