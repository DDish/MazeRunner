package MazeRunner;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Robot {
	public enum Marker {
		CAVERN,BREADCRUMB;
	}
	private int row, col, startRow, startCol;
	private boolean going = false, mute = false;
	char marker;
	Maze maze;
	JLabel groot;
	Player p;


	private boolean foundCavern;
	private Stack<String> path;//contains a representation of the movements that it takes for a robot to reach an associated node.
	private Map<String, ArrayList<Stack<String>>> pathsList;//key is a string of the marker, values are paths.
	private long pause = 10;

	//isn't called anywhere
	public void draw(Graphics g, Color c) {
		g.setColor(Color.BLACK);
		g.fillOval(42+col*16,42+row*16,10,10);
	}

	public Robot(int row, int col, Maze maze, JLabel groot){
		this.row = row;
		this.col = col;
		this.startRow = row;
		this.startCol = col;
		this.maze = maze;//new Maze(row,col,maze.getRows(),maze.getCols());//make a blank copy of the map
		this.groot = groot;
		p = new Player();

		foundCavern = false;
		path = new Stack <String>();
		pathsList = new HashMap<String, ArrayList<Stack<String>>>();
	}

	private void resetPosition(){
		this.row = this.startRow;
		this.col = this.startCol;
	}

	public boolean moveUp(){
		if(row != 0){
			if(maze.getMazeMap()[row-1][col].getInitial() != 'X'){
				row--;
				path.push("Up");
				return true;
			}
		}
		return false;
	}

	public boolean moveDown(){
		if(row < maze.getRows()){
			if(maze.getMazeMap()[row+1][col].getInitial() != 'X'){
				row++;
				path.push("Down");
				return true;
			}
		}
		return false;
	}

	public boolean moveLeft(){
		if(row != 0){
			if(maze.getMazeMap()[row][col-1].getInitial() != 'X'){
				col--;
				path.push("Left");
				return true;
			}
		}
		return false;
	}

	public boolean moveRight(){
		if(row < maze.getCols()){
			if(maze.getMazeMap()[row][col+1].getInitial() != 'X'){
				col++;
				path.push("Right");
				return true;
			}
		}
		return false;
	}

	public void stepBack(){
		if(!path.empty()){
			if(path.lastElement().equals("Up")){
				path.pop();//pops the move from the path, as we stepped back off of it
				if(this.moveDown()){
					path.pop();//if we can move in the direction we came from then a direction is placed on the stack, pop it off.
				}
			}
			else if(path.lastElement().equals("Down")){
				path.pop();
				if(this.moveUp()){
					path.pop();
				}
			}
			else if(path.lastElement().equals("Left")){
				path.pop();
				if(this.moveRight()){
					path.pop();
				}
			}
			else if(path.lastElement().equals("Right")){
				path.pop();
				if(this.moveLeft()){
					path.pop();
				}
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

	public boolean isGoing() {
		return going;
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
		going = true;
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
			if(!mute)
				p.playSound("Found");
			try {
				Thread.sleep(3400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			groot.setIcon(new ImageIcon("images/Static.gif"));
			System.out.format("Current Location %d, %d",this.row,this.col);
			System.out.println("marker: " + this.getMarker());
			going = false;
		}
		else
		{
			//else maze traversal algorithm w/ random so not all robots explore the same way
		}
	}	

	public void setMute(boolean b) {
		mute = b;
	}



	//The following are used for non a* navigation.
	public void findRoute(Maze maze){
		if(pathsList.containsKey(Character.toString(marker))){
			maze.clearBreadCrumbs();
			Stack<String> shortest = new Stack<String>();
			for(Stack<String> s: pathsList.get(Character.toString(marker))){
				if(shortest.size() == 0 || s.size() < shortest.size()){
					shortest = (Stack<String>) s.clone();
				}
			}
			maze.editInProgress = true;
			followRoute(shortest);
			maze.editInProgress = false;
		}
		else{
			maze.editInProgress = true;
			maze.clearBreadCrumbs();
			path.clear();
			foundCavern = false;//set found state to false, so we can check if we need to add the path when we search
			findRecursively(maze, row, col);
			if(foundCavern){
				String cavern = Character.toString(marker);//cast the cavern to string for use in map
				ArrayList<Stack<String>> list = new ArrayList<Stack<String>>();
				if(pathsList.containsKey(cavern)){
					list = (ArrayList<Stack<String>>) pathsList.get(cavern).clone();//get the list, if it is not null
				}
				if(!path.empty())list.add(path);//add the path
				pathsList.put(cavern, list);//replace old list
				maze.editInProgress = false;
				System.out.println("found");
				return;
			}
			maze.editInProgress = false;
			System.out.println("not found");
		}
	}

	private void followRoute(Stack<String> shortestKnown){
		Iterator<String> iter = shortestKnown.iterator();
		placeBreadcrumb(row, col, maze);
		while(iter.hasNext()){
			String s = iter.next();
			try {
				Thread.sleep(pause);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			if(s.equals("Up")){
				this.moveUp();
				placeBreadcrumb(row, col, maze);
			}
			else if(s.equals("Down")){
				this.moveDown();
				placeBreadcrumb(row, col, maze);
			}
			else if(s.equals("Left")){
				this.moveLeft();
				placeBreadcrumb(row, col, maze);
			}
			else if(s.equals("Right")){
				this.moveRight();
				placeBreadcrumb(row, col, maze);
			}
			maze.repaint();
		}
		
		foundCavern = true;
		resetPosition();
	}

	
	private void findRecursively(Maze maze, int row, int col){
		maze.repaint();
		try {
			Thread.sleep(pause);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		placeBreadcrumb(row, col, maze);
		if(maze.getCellAt(row, col).getInitial() == marker || foundCavern == true){
			foundCavern = true;
			resetPosition();
			return;
		}
		if(row > 0 && !foundCavern){
			if(!(maze.getCellAt(row-1, col).getInitial() == 'X') && !maze.getCellAt(row-1, col).hasBreadCrumb() && !foundCavern){
				moveUp();
				findRecursively(maze, row-1, col);
				if(!foundCavern){
				stepBack();
				maze.repaint();
				try {
					Thread.sleep(pause );
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				}
			}
		}
		if(col > 0 && !foundCavern){
			if(!(maze.getCellAt(row, col-1).getInitial() == 'X') && !maze.getCellAt(row, col-1).hasBreadCrumb() && !foundCavern){
				moveLeft();
				findRecursively(maze, row, col-1);
				if(!foundCavern){
				stepBack();
				maze.repaint();
				try {
					Thread.sleep(pause );
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				}
			}
		}
		if(row < maze.getRows() - 1 && !foundCavern){
			if(!(maze.getCellAt(row+1, col).getInitial() == 'X') && !maze.getCellAt(row+1, col).hasBreadCrumb() && !foundCavern){
				moveDown();
				findRecursively(maze, row+1, col);
				if(!foundCavern){
				stepBack();
				maze.repaint();
				try {
					Thread.sleep(pause );
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				}
			}
		}
		if(col < maze.getCols() - 1 && !foundCavern){
			if(!(maze.getCellAt(row, col+1).getInitial() == 'X') && !maze.getCellAt(row, col+1).hasBreadCrumb() && !foundCavern){
				moveRight();
				findRecursively(maze, row, col+1);
				if(!foundCavern){
				stepBack();
				maze.repaint();
				try {
					Thread.sleep(pause );
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				}
			}
		}

		if(!foundCavern){
			maze.getCellAt(row,col).pickupBreadCrumb();
		}
	}

}
