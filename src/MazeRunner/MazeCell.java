package MazeRunner;

public class MazeCell {
	private int row,col;
	private char initial;
	private boolean boo;
	
	public MazeCell(int r, int c, boolean b, char mnum) {
		// TODO Auto-generated constructor stub
		row = r;
		col = c;
		boo = b;
		initial = mnum;
	}
	
	public char getInitial() {
		return initial;
	}
	
	public boolean isMarker() {
		return boo;
	}

	public void draw() {
		
	}
}
