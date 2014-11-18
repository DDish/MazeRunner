package MazeRunner;

import java.awt.Color;
import java.util.ArrayList;

public class MazeCell {
	private int row,col,r1=0,g1=0,b1=0;
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
			while(r1 <15 || r1 >230)
				r1 = (int)(Math.random()*255);
			while(g1 <5 || g1 >240)
				g1 = (int)(Math.random()*255);
			b1 = (int)(Math.random()*255);
			
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
