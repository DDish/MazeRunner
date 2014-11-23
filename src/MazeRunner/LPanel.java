package MazeRunner;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class LPanel extends JPanel{
	
	private Hub hub;
	
	public LPanel(Hub hub) {
		this.hub = hub;
		setLayout(new GridLayout(3,(int) Math.ceil(hub.getMaze().getMarkers().size()/3.0)));
		setBorder(new EtchedBorder());
		for(char i : hub.getMaze().getMarkers())
		{
			JLabel l = new JLabel();
			l.setFont(new Font("Times New Roman", Font.PLAIN, 25));
			l.setText(Character.toString(i));
			add(l);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int c=0;
		ArrayList<MazeCell> list = hub.getMaze().getMarkerList();
		for(int j=0;j<3;j++)
			for(int i=0; i<(int)Math.ceil(list.size()/3.0);i++)
			{
				g.setColor(list.get(c).getColor());
				g.fillRect(25+i*(int)(this.getWidth()/(Math.ceil(list.size()/3)+1)), 5+j*this.getHeight()/3, 20, 20);
				c++;
				if(c>=list.size()) break;
			}
		repaint();
	}
}
