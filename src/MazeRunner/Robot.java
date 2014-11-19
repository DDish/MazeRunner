package MazeRunner;

public class Robot {
	public enum Marker {
		CAVERN,BREADCRUMB;
	}
	private int row, col;
	char marker;
	
	public Robot(int row, int col){
		this.row = row;
		this.col = col;
	}
	
	public void moveUp(){
			
	}
	public void moveDown(){
		
	}
	public void moveLeft(){
		
	}
	public void moveRight(){
		
	}
	
	public int getRow(){
		return -1;
	}
	public int getColumn(){
		return -1;
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
