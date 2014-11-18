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
		setLayout(new GridLayout(3,5));
		setBorder(new EtchedBorder());
		for(int i=0;i<15;i++)
		{
			JLabel l = new JLabel();
			l.setFont(new Font("Times New Roman", Font.PLAIN, 25));
			l.setText("      "+(char)(65+i));
			add(l);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int c=0;
		ArrayList<MazeCell> list = hub.getMaze().getMarkerList();
		for(int i=0; i<5;i++)
			for(int j=0;j<3;j++)
			{
				g.setColor(list.get(c).getColor());
				g.fillRect(10+i*92, 5+j*30, 20, 20);
				c++;
			}
		repaint();
	}
}
