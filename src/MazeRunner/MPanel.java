package MazeRunner;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.*;
import javax.swing.border.EtchedBorder;


public class MPanel extends JPanel{
	
	private Hub hub;
	private JButton play, pause, mute;
	private JLabel song = new JLabel("Paused");
	private ArrayList<String> music;
	File songFile;
    AudioInputStream stream;
    AudioFormat format;
    DataLine.Info info;
    Clip clip;
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(play))
			{
					hub.groot.setIcon(new ImageIcon("images/Groot.gif"));
					song.setText("Songs Not Implemented");
					//clip.start();
			}
			if(e.getSource().equals(pause))
			{
					hub.groot.setIcon(new ImageIcon("images/Static.gif"));
					song.setText("Paused");
					//clip.stop();
			}
			if(e.getSource().equals(mute))
			{
					
			}
		}
	}
	
	public MPanel(Hub hub) {
		this.hub = hub;
		setLayout(new GridLayout(1,2));
		setBorder(new EtchedBorder());
		
		music = new ArrayList<String>();
		
		try {
			Scanner sc = new Scanner(new FileReader("music.txt"));
			while(sc.hasNextLine())
				music.add(sc.nextLine());
		}
		catch(Exception e) {
			System.out.println("Bad song file");
		}
		
		int i=(int)(Math.random()*music.size());
		newSong(i);
		
		JPanel psong = new JPanel();
		psong.add(song);
		add(psong);
		
		JPanel mbar = new JPanel();
		play = new JButton();
		play.setIcon(new ImageIcon("images/Play.png"));
		play.addActionListener(new ButtonListener());
		
		pause = new JButton();
		pause.setIcon(new ImageIcon("images/Pause.png"));
		pause.addActionListener(new ButtonListener());
		
		mute = new JButton();
		mute.setIcon(new ImageIcon("images/Mute.png"));
		mute.addActionListener(new ButtonListener());
		mbar.add(play);
		mbar.add(pause);
		mbar.add(mute);
		add(mbar);
	}
	
	public void newSong(int x) {
		try {
		    songFile = new File("songs/"+music.get(x)+".wav");
		    stream = AudioSystem.getAudioInputStream(songFile);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clip = (Clip) AudioSystem.getLine(info);
		    clip.open(stream);
		}
		catch (Exception ex) {
		    System.out.println("Bad song name");
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		repaint();
	}
}
