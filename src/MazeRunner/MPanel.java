package MazeRunner;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class MPanel extends JPanel{
	
	private Hub hub;
	private JButton play, pause, next;
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(play))
			{
					hub.groot.setIcon(new ImageIcon("images/Groot.gif"));
			}
			if(e.getSource().equals(pause))
			{
					hub.groot.setIcon(new ImageIcon("images/Static.gif"));
			}
			if(e.getSource().equals(next))
			{
					
			}
		}
	}
	
	public MPanel(Hub hub) {
		this.hub = hub;
		//setLayout(new GridLayout(1,2));
		setBorder(new EtchedBorder());
		
		JPanel psong = new JPanel();
		psong.add(new JLabel("Song Title"));
		add(psong);
		
		JPanel mbar = new JPanel();
		play = new JButton();
		play.setIcon(new ImageIcon("images/Play.png"));
		play.addActionListener(new ButtonListener());
		
		pause = new JButton();
		pause.setIcon(new ImageIcon("images/Pause.png"));
		pause.addActionListener(new ButtonListener());
		
		next = new JButton();
		next.setIcon(new ImageIcon("images/Next.png"));
		next.addActionListener(new ButtonListener());
		mbar.add(play);
		mbar.add(pause);
		mbar.add(next);
		add(mbar);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		repaint();
	}
}
