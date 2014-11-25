package MazeRunner;

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hub extends JFrame {
	
	private Maze maze;
	private JPanel mazePanel;
	private Queue<Robot> robots;
	private JComboBox<Character> box;
	private JTextArea rlist;
	private JButton find,muteb;
	public JLabel groot;
	public Player p;
	public boolean mute = true;
	
	private int ct=1;
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(find))
			{
				p.playSound("Find");
				rlist.setText(newText());
				Robot r = robots.remove();
				robots.add(r);
				issueCommand(r, (char)box.getSelectedItem());
				
			}
			if(e.getSource().equals(muteb))
				mute = !mute;
		}
	}
	
	private class MyThread implements Runnable {

		Robot r;
		char marker;
		
	    public MyThread(Robot r, char marker) {
			this.r = r;
			this.marker = marker;
		}

		public void run(){
			r.moveToDestination(maze,marker);
	    }
	  }
	
	public Hub(String file){
		this(file, 10);
	}

	public Hub(String file, int numBots){
		maze = new Maze(file);
		groot = new JLabel(new ImageIcon("images/Static.gif"));
		robots = new LinkedList<Robot>();
		p = new Player();
		MazeCell start=null;
		for (MazeCell i : maze.getMarkerList())
		{
			if(i.getInitial()=='S') start=i;
		}
		for(int i=0;i<numBots;i++)
			robots.add(new Robot(start.getRow(),start.getCol(),maze, groot));
		
		box = new JComboBox<Character>();
		for (char c: maze.getMarkers())
			if(c != 'S')
				box.addItem(c);
		
		rlist = new JTextArea();
		rlist.setText(newText());
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(500,100);
		setSize(835,750);
		setTitle("MazeRunner");
		setLayout(new BorderLayout());
		createGUI();
	}

	private String newText() {
		// TODO Auto-generated method stub
		String s = "";
		for(int i=1;i<=robots.size();i++)
		{
			s+="Robot "+i;
			if(i==ct)
				s+=" (Current)";
			s+="\n";
		}
		if(ct == robots.size())
			ct=1;
		else ct++;
		return s;
	}

	public Maze getMaze() {
		return maze;
	}
	
	private void createGUI() {
		add(maze, BorderLayout.CENTER);			//MAZE
		
		JPanel p = new JPanel();				//LEGEND
		p.setLayout(new GridLayout(1,2));
		p.add(new LPanel(this));
		muteb = new JButton("Mute");
		muteb.addActionListener(new ButtonListener());
		p.add(muteb);
		add(p, BorderLayout.SOUTH);
		
		JPanel r = new JPanel();
		r.setBorder(new EtchedBorder());
		r.setLayout(new BoxLayout(r, BoxLayout.Y_AXIS));
		
		JPanel rg = new JPanel();
		rg.add(groot);
		r.add(rg,BorderLayout.WEST);
		
		JPanel r1 = new JPanel();
		r1.setLayout(new BorderLayout());
		r1.add(new JLabel("Robot List:"),BorderLayout.NORTH);
		r1.add(rlist,BorderLayout.CENTER);
		r.add(r1);
		
		JPanel r2 = new JPanel();
		r2.setLayout(new GridLayout(3,1));
		r2.add(new JLabel("Robot Destination:"));
		r2.add(box);
		find = new JButton("Find");
		find.addActionListener(new ButtonListener());
		r2.add(find);
		r.add(r2);
		
		add(r, BorderLayout.EAST);
	}
	
	public static void main(String[] args) {
		Hub h = new Hub("Maze.csv");
		h.setVisible(true);
		int x=0,y=0;
		while(true)
		{
			System.out.println(""+h.mute);
			if(h.mute)
			{
				while(y==x)
					x = 1+(int)(Math.random()*5);
			y = x;
			h.p.playSound("GLaD"+x);
			}
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}

	
	public void issueCommand (Robot r, char marker) {
		Runnable t = new MyThread(r, marker);
		Thread thread = new Thread(t);
		thread.start();
	}
	
	public Queue<Robot> getBots() {
		return robots;
	}
}
