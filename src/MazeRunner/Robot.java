package MazeRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Robot {
	public enum Marker {
		CAVERN,BREADCRUMB;
	}
	private int row, col;
	char marker;
	Maze maze;
	JLabel groot;
	Player p;
	
	public Robot(int row, int col, Maze maze, JLabel groot){
		this.row = row;
		this.col = col;
		this.maze = maze;//new Maze(row,col,maze.getRows(),maze.getCols());//make a blank copy of the map
		this.groot = groot;
		p = new Player();
	}
	
	public void moveUp(){
		if(row != 0){
			if(maze.getMazeMap()[row-1][col].getInitial() != 'X'){
				row--;
			}
		}
	}
	
	public void moveDown(){
		if(row < maze.getRows()){
			if(maze.getMazeMap()[row+1][col].getInitial() != 'X'){
				row++;
			}
		}
	}
	
	public void moveLeft(){
		if(row != 0){
			if(maze.getMazeMap()[row][col-1].getInitial() != 'X'){
				col--;
			}
		}
	}
	
	public void moveRight(){
		if(row < maze.getCols()){
			if(maze.getMazeMap()[row][col+1].getInitial() != 'X'){
				col++;
			}
		}
	}
	
	public int getRow(){
		return row;
	}
	
	public int getColumn(){
		return col;
	}

	public void placeBreadcrumb(int row, int col,Maze trueMap){
		trueMap.getMazeMap()[row][col].dropBreadcrumb();
	}
	
	public void pickupBreadcrumb(int row, int col,Maze trueMap){
		trueMap.getMazeMap()[row][col].pickupBreadCrumb();
	}
	
	public char getMarker() {
		return marker;
	}
	
	public void setMarker(char command) {
		marker = command;
	}
	
	public void shareMap(Maze newMap)
	{
		//map = union(map, newMap)
	}
	
	private class searchNode implements Comparable<searchNode>{
		public int row;
		public int column;
		public int pathCost;
		public int hCost;
		public int Cost;
		public searchNode parent;
		
		public searchNode(int row, int col, int pathCost, int hCost, searchNode parent)
		{
			this.row=row;
			column=col;
			this.pathCost=pathCost;
			this.hCost=hCost;
			this.Cost=pathCost+hCost;
			this.parent=parent;
		}

		@Override
		public int compareTo(searchNode arg0) {
			return this.Cost-arg0.Cost;
		}
	}
	
	private int mDistance(int x, int y, int goalX, int goalY)
	{
		return Math.abs(x-goalX)+Math.abs(y-goalY);
	}
	
	public void moveToDestination(Maze trueMap, char marker)
	{
		this.marker=marker;
		if (maze.getMarkers().contains(marker))
		{
			//if marker in map, A*, assumes map is continuous, that there are no places in the map that are impossible to reach
			MazeCell goal = null;
			for (MazeCell i : maze.getMarkerList())
			{
				if (i.getInitial()==marker)
				{
					goal=i;
					break;
				}
			}
			
			PriorityQueue<searchNode> openList = new PriorityQueue<searchNode>();
			ArrayList<searchNode> closedList = new ArrayList<searchNode>();//probably not the best but I can't have two comparison operators to make a tree work, and I don't want to make a new hash
			openList.add(new searchNode(this.row, this.col, 0,mDistance(this.row, this.col, goal.getRow(), goal.getCol()),null));
			int drow[] = {0,1,0,-1};
			int dcol[] = {1,0,-1,0};//for convenient looping over adjacents
			while(openList.peek().row!=goal.getRow()||openList.peek().column!=goal.getCol())
			{
				searchNode current = openList.poll();
				closedList.add(current);
				for (int i=0;i<drow.length;i++)
				{
					if(maze.getMazeMap()[current.row+drow[i]][current.column+dcol[i]].getInitial()!='X')
					{
						searchNode next=new searchNode(current.row+drow[i],current.column+dcol[i],current.pathCost+1,mDistance(current.row+drow[i],current.column+dcol[i],goal.getRow(),goal.getCol()),current);
						boolean addToOpenList=true;
						
						for(searchNode s : openList)
						{
							if(s.row==next.row&&s.column==next.column)
							{
								if (s.pathCost>next.pathCost)
								{
									openList.remove(s);//duplicate with higher cost
									break;//there shouldn't be multiple duplicates if this happens for every insert
								}
								else
								{
									addToOpenList=false;//duplicate with lower/same cost, dont add the new one.
									break;
								}
							}
						}
						if(addToOpenList)
						{
							for(searchNode s : closedList)//closedList will usually be bigger, so search it second
							{
								if(s.row==next.row&&s.column==next.column)
								{
									addToOpenList=false;
									break;//we already looked at this one.
								}
							}
						}
						if(addToOpenList)
						{
							openList.add(next);
						}
					}
				}

				System.out.format("Open List: %d\nClosed List: %d\nCurrent: %d, %d\n",openList.size(),closedList.size(),current.row,current.column);
			}
			//back track out path
			searchNode current = openList.peek();
			searchNode parent = current.parent;
			System.out.format("Open List Peek, Should be goal: %d,%d\n",current.row, current.column);
			ArrayList<Integer> directions= new ArrayList<Integer>();//{right,0}{down,1}{left,2}{up,3}//matches indices for drow and dcol
			while(parent!=null)
			{
				int dx=current.column-parent.column;
				int dy=current.row-parent.row;
				if(dx==1&&dy==0)
				{
					directions.add(0);
				}
				else if(dx==0&&dy==1)
				{
					directions.add(1);
				}
				else if(dx==-1&&dy==0)
				{
					directions.add(2);
				}
				else if(dx==0&&dy==-1)
				{
					directions.add(3);
				}
				else{assert(false);}//Invalid parent/child relationship
				current=parent;
				parent=current.parent;
						
			}
			Collections.reverse(directions);//it was built from back to front
			for (int dir : directions)
			{
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				switch(dir)
				{
					case 0:
						System.out.println("move Right");
						moveRight();
						break;
					case 1:
						System.out.println("move Down");
						moveDown();
						break;
					case 2:
						System.out.println("move Left");
						moveLeft();
						break;
					case 3:
						System.out.println("move Up");
						moveUp();
						break;
				}	
				this.placeBreadcrumb(this.row, this.col, trueMap);
				trueMap.repaint();
			}
			groot.setIcon(new ImageIcon("images/Marker.gif"));
			p.playSound("Found");
			try {
				Thread.sleep(3400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			groot.setIcon(new ImageIcon("images/Static.gif"));
			System.out.format("Current Location %d, %d",this.row,this.col);
		}
		else
		{
			//else maze traversal algorithm w/ random so not all robots explore the same way
		}
	}
	
}
