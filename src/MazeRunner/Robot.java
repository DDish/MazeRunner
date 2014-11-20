package MazeRunner;

public class Robot {
	public enum Marker {
		CAVERN,BREADCRUMB;
	}
	private int row, col;
	char marker;
	Maze maze;
	
	public Robot(int row, int col, Maze maze){
		this.row = row;
		this.col = col;
		this.maze = maze;
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

	public void placeBreadcrumb(int row, int col){
		maze.getMazeMap()[row][col].dropBreadcrumb();
	}
	
	public void pickupBreadcrumb(int row, int col){
		maze.getMazeMap()[row][col].pickupBreadCrumb();
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
	
	public void moveToDestination(char marker)
	{
		//if marker in map, A*
		//else maze traversal algorithm w/ random so not all robots explore the same way
	}
	
}
