package MazeRunner;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import sun.awt.RepaintArea;

public class MazeCell {
	private int row,col,r1=0,g1=0,b1=0;
	private char initial;
	private boolean isMarker;
	private Color color;
	private boolean hasBreadcrumb;
	
	public MazeCell(int r, int c, boolean b, char mnum) {
		// TODO Auto-generated constructor stub
		row = r;
		col = c;
		isMarker = b;
		initial = mnum;
		
		if(isMarker) {
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
		else if(mnum == 'S') {
			color = Color.YELLOW;
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
		return isMarker;
	}
	
	public void dropBreadcrumb(){
		hasBreadcrumb = true;
	}
	public void pickupBreadCrumb(){
		hasBreadcrumb = false;
	}
	

	public void draw(Graphics g, int x, int y, Color c) {
		g.setColor(c);
		g.fillRect(40+x*16,40+y*16,15,15);
		if(hasBreadcrumb){
			g.setColor(Color.LIGHT_GRAY);
			g.fillOval(40+x*16,40+y*16,15,15);
		}
	}
}
