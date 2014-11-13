package MazeRunner;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class LPanel extends JPanel{
	
	public LPanel() {
		setLayout(new GridLayout(3,5));
		setBorder(new EtchedBorder());
		for(int i=0;i<15;i++)
		{
			JLabel l = new JLabel();
			l.setFont(new Font("Times New Roman", Font.PLAIN, 25));
			l.setText("            "+(char)(65+i));
			add(l);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i=0; i<5;i++)
			for(int j=0;j<3;j++)
				g.fillRect(i*50, j*50, 25, 25);
		repaint();
	}
}
