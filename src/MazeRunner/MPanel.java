package MazeRunner;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class MPanel extends JPanel{
	
	private Hub hub;
	
	public MPanel(Hub hub) {
		this.hub = hub;
		setLayout(new BorderLayout());
		setBorder(new EtchedBorder());
		
		JPanel psong = new JPanel();
		add(psong,BorderLayout.WEST);
		
		JPanel mbar = new JPanel();
		add(mbar,BorderLayout.WEST);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		repaint();
	}
}