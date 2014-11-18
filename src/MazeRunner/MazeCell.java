package MazeRunner;

import java.awt.Color;

public class MazeCell {
	private int row,col;
	private char initial;
	private boolean boo;
	private Color color;
	
	public MazeCell(int r, int c, boolean b, char mnum) {
		// TODO Auto-generated constructor stub
		row = r;
		col = c;
		boo = b;
		initial = mnum;
		if(boo) {
			int r1 = (int)(Math.random()*255);
			int g1 = (int)(Math.random()*255);
			int b1 = (int)(Math.random()*255);
			color = new Color(r1,g1,b1);
		}
		else if(mnum == 'X') {
			color = Color.BLACK;
		}
		else {
			color = Color.WHITE;
		}
	}
	
	public char getInitial() {
		return initial;
	}
	
	public Color getColor() {
		return color;
	}
	
	public boolean isMarker() {
		return boo;
	}

	public void draw() {
		
	}
}
