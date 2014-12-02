package MazeRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Robot {
	public enum Marker {
		CAVERN,BREADCRUMB;
	}
	private int NUM_ROWS = 34;
	private int NUM_COLS = 34;
	private int row, col;
	private boolean going = false, mute = false;
	char marker;
	Maze maze;
	JLabel groot;
	Player p;
	private Set<MazeCell> visited = new HashSet<MazeCell>();


	private boolean foundCavern;
	private Stack<String> path;//contains a representation of the movements that it takes for a robot to reach an associated node.
	private Map<String, ArrayList<Stack<String>>> pathsList;//key is a string of the marker, values are paths.
	private long pause = 1;

	public Robot(int row, int col, Maze maze, JLabel groot){
		this.row = row;
		this.col = col;//These start reversed, not sure if they are reversed or the calls to here are.
		this.maze = new Maze(row,col,NUM_ROWS,NUM_COLS);//make a blank copy of the map
		this.groot = groot;
		p = new Player();

		foundCavern = false;
		path = new Stack <String>();
		pathsList = new HashMap<String, ArrayList<Stack<String>>>();
	}

	public void moveUp(){
		if(row != 0){
			if(maze.getMazeMap()[row-1][col].getInitial() != 'X'){
				row--;
				path.push("Up");
			}
		}
	}

	public void moveDown(){
		if(row < maze.getRows()){
			if(maze.getMazeMap()[row+1][col].getInitial() != 'X'){
				row++;
				path.push("Down");
			}
		}
	}

	public void moveLeft(){
		if(col != 0){
			if(maze.getMazeMap()[row][col-1].getInitial() != 'X'){
				col--;
				path.push("Left");
			}
		}
	}

	public void moveRight(){
		if(row < maze.getCols()){
			if(maze.getMazeMap()[row][col+1].getInitial() != 'X'){
				col++;
				path.push("Right");
			}
		}
	}

	public void stepBack(){
		if(!path.empty()){
			if(path.firstElement().equals("Up")){
				path.pop();
				pickupBreadcrumb(row, col, maze);
				this.moveDown();
			}
			else if(path.firstElement().equals("Down")){
				path.pop();
				pickupBreadcrumb(row, col, maze);
				this.moveUp();
			}
			else if(path.firstElement().equals("Left")){
				path.pop();
				pickupBreadcrumb(row, col, maze);
				this.moveRight();
			}
			else if(path.firstElement().equals("Right")){
				path.pop();
				pickupBreadcrumb(row, col, maze);
				this.moveLeft();
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
		for(MazeCell[] row : newMap.getMazeMap()){
			for(MazeCell i : row){
				if (maze.getCellAt(i.getRow(), i.getCol()).getInitial()=='?'){
					maze.getCellAt(i.getRow(), i.getCol()).setInitial(i.getInitial());
					if(maze.getCellAt(i.getRow(), i.getCol()).getInitial()!='?'&&maze.getCellAt(i.getRow(), i.getCol()).getInitial()!='X'&&maze.getCellAt(i.getRow(), i.getCol()).getInitial()!='0')
					{
						maze.addMarker(i);
					}
				}
			}
		}
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
			this.column=col;
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
		trueMap.editInProgress=true;
		trueMap.clearBreadCrumbs();
		if (maze.getMarkers().contains(marker))
		{
			//if marker in map, A*, assumes map is continuous, that there are no places in the map that are impossible to reach
			System.out.println("Starting A* Search");
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
					if(maze.getMazeMap()[current.row+drow[i]][current.column+dcol[i]].getInitial()!='X'&&maze.getMazeMap()[current.row+drow[i]][current.column+dcol[i]].getInitial()!='?')
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
			int prev = -1;
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
			prev = -1;
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
				this.placeBreadcrumb(this.row, this.col, trueMap);//what happened to this? This used to work right?
				trueMap.setRobRow(row);
				trueMap.setRobCol(col);
				trueMap.repaint();
				playGIF(prev, dir);
				prev = dir;
			}
	
			System.out.format("Current Location %d, %d",this.row,this.col);
			System.out.println("marker: " + this.getMarker());
			groot.setIcon(new ImageIcon("images/cavern.gif"));
			going = false;
		}
		else
		{
			groot.setIcon(new ImageIcon("images/Search.gif"));
			visited=new HashSet<MazeCell>();
			//else maze traversal algorithm w/ random so not all robots explore the same way
			findRecursively(trueMap,row,col);
			
		}
		if(marker!='S') moveToDestination(trueMap,'S');//Go home
		trueMap.editInProgress=false;
		going=false;	
		
		try {
			Thread.sleep(3400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		groot.setIcon(new ImageIcon("images/COMMAND.gif"));
	}
		
		

	public void setMute(boolean b) {
		// TODO Auto-generated method stub
		mute = b;
	}



	//The following are used for non a* navigation.
	public void findRoute(Maze maze){
		if(pathsList.containsKey(Character.toString(marker))){
			maze.clearBreadCrumbs();
			Stack<String> shortest = new Stack<String>();
			for(Stack<String> s: pathsList.get(Character.toString(marker))){
				if(shortest.size() == 0 || s.size() < shortest.size()){
					shortest = s;
				}
			}
			maze.editInProgress = true;
			followRoute(shortest);
			maze.editInProgress = false;
		}
		else{
			maze.editInProgress = true;
			maze.clearBreadCrumbs();
			path = new Stack <String>();
			foundCavern = false;//set found state to false, so we can check if we need to add the path when we search
			findRecursively(maze, row, col);
			if(foundCavern){
				String cavern = Character.toString(marker);//cast the cavern to string for use in map
				ArrayList<Stack<String>> list = new ArrayList<Stack<String>>();
				if(pathsList.containsKey(cavern)){
					list = pathsList.get(cavern);//get the list, if it is not null
				}
				if(!path.empty())list.add(path);//add the path
				pathsList.put(cavern, list);//replace old list
				maze.editInProgress = false;
				System.out.println("found");
			}
			maze.editInProgress = false;
		}
	}

	private void followRoute(Stack<String> shortestKnown){
		while(!shortestKnown.empty()){
			if(path.firstElement().equals("Up")){
				path.pop();
				pickupBreadcrumb(row, col, maze);
				this.moveUp();
			}
			else if(path.firstElement().equals("Down")){
				path.pop();
				pickupBreadcrumb(row, col, maze);
				this.moveDown();
			}
			else if(path.firstElement().equals("Left")){
				path.pop();
				pickupBreadcrumb(row, col, maze);
				this.moveLeft();
			}
			else if(path.firstElement().equals("Right")){
				path.pop();
				pickupBreadcrumb(row, col, maze);
				this.moveRight();
			}
		}
	}

	private void findRecursively(Maze maze, int row, int col){
		MazeCell currentLocation = maze.getCellAt(row, col);
		this.row=row;//actually move the robot
		this.col=col;
		maze.repaint();
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (visited.contains(maze.getCellAt(row, col))) return;//don't revisit cells
		visited.add(maze.getCellAt(row,col));
		try {
			Thread.sleep(pause);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		maze.setRobRow(row);
		maze.setRobCol(col);
		placeBreadcrumb(row, col, maze);
		if(currentLocation.getInitial() == marker || foundCavern == true){
			foundCavern = true;
			groot.setIcon(new ImageIcon("images/cavern.gif"));
			if(!mute)
				p.playSound("Found");
			try {
				Thread.sleep(3400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			groot.setIcon(new ImageIcon("images/Static.gif"));
			return;
		}
		recRight(maze, row, col);
		recDown(maze, row, col);
		recUp(maze, row, col);
		recLeft(maze, row, col);
		
		
		if(!foundCavern){
			maze.getCellAt(row, col).pickupBreadCrumb();
		}
	}

	private void recUp(Maze maze, int row, int col){//calling everything row, col, and maze is confusing
		if(row > 0 && !foundCavern){
			this.maze.getCellAt(row-1, col).setInitial(maze.getCellAt(row-1, col).getInitial());//update local map
			if (this.maze.getCellAt(row-1, col).getInitial()!='X'||this.maze.getCellAt(row-1, col).getInitial()!='0') this.maze.addMarker(this.maze.getCellAt(row-1, col));
			if(!(maze.getCellAt(row-1, col).getInitial() == 'X') && !maze.getCellAt(row-1, col).hasBreadCrumb() && !foundCavern){
				moveUp();
				findRecursively(maze, row-1, col);
				if(!foundCavern){
					stepBack();
					try {
						Thread.sleep(pause );
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}
	}
	private void recDown(Maze maze, int row, int col){
		if(row < maze.getRows() - 1 && !foundCavern){
			this.maze.getCellAt(row+1, col).setInitial(maze.getCellAt(row+1, col).getInitial());//update local map
			if (this.maze.getCellAt(row+1, col).getInitial()!='X'||this.maze.getCellAt(row+1, col).getInitial()!='0') this.maze.addMarker(this.maze.getCellAt(row+1, col));
			if(!(maze.getCellAt(row+1, col).getInitial() == 'X') && !maze.getCellAt(row+1, col).hasBreadCrumb() && !foundCavern){
				moveDown();
				findRecursively(maze, row+1, col);
				if(!foundCavern){
					stepBack();
					try {
						Thread.sleep(pause );
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}
	}
	private void recLeft(Maze maze, int row, int col){
		if(col > 0 && !foundCavern){
			this.maze.getCellAt(row, col-1).setInitial(maze.getCellAt(row, col-1).getInitial());//update local map
			if (this.maze.getCellAt(row, col-1).getInitial()!='X'||this.maze.getCellAt(row, col-1).getInitial()!='0') this.maze.addMarker(this.maze.getCellAt(row, col-1));
			if(!(maze.getCellAt(row, col-1).getInitial() == 'X') && !maze.getCellAt(row, col-1).hasBreadCrumb() && !foundCavern){
				moveLeft();
				findRecursively(maze, row, col-1);
				if(!foundCavern){
					stepBack();
					try {
						Thread.sleep(pause );
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}
	}
	private void recRight(Maze maze, int row, int col){
		if(col < maze.getCols() - 1 && !foundCavern){
			this.maze.getCellAt(row, col+1).setInitial(maze.getCellAt(row, col+1).getInitial());//update local map
			if (this.maze.getCellAt(row, col+1).getInitial()!='X'||this.maze.getCellAt(row, col+1).getInitial()!='0') this.maze.addMarker(this.maze.getCellAt(row, col+1));
			if(!(maze.getCellAt(row, col+1).getInitial() == 'X') && !maze.getCellAt(row, col+1).hasBreadCrumb() && !foundCavern){
				moveRight();
				findRecursively(maze, row, col+1);
				if(!foundCavern){
					stepBack();
					try {
						Thread.sleep(pause );
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}
	}
	
	private void playGIF(int prev, int dir){
		String str ="";
		if(prev+1 == dir || prev-3 == dir)
			str+="Right";
		else if(prev+2 == dir || prev-2 == dir || prev == dir)
			str+="Forward";
		else if(dir+1 == prev || dir-3 == prev)
			str+="Left";
		groot.setIcon(new ImageIcon("images/"+str+".gif"));
		try {
			Thread.sleep(900);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
