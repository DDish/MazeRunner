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
	private JButton find;
	public JLabel groot;
	
	private int ct=1;
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(find))
			{
				rlist.setText(newText());
				Robot r = robots.remove();
				robots.add(r);
				issueCommand(r, (char)box.getSelectedItem());
				
			}
		}
	}
	
	public Hub(String file){
		this(file, 10);
	}

	public Hub(String file, int numBots){
		maze = new Maze(file);
		robots = new LinkedList<Robot>();
		for(int i=0;i<numBots;i++)
			robots.add(new Robot(15,1,maze));
		
		box = new JComboBox<Character>();
		for (char c: maze.getMarkers())
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
		p.add(new MPanel(this));
		add(p, BorderLayout.SOUTH);
		
		JPanel r = new JPanel();
		r.setBorder(new EtchedBorder());
		r.setLayout(new BoxLayout(r, BoxLayout.Y_AXIS));
		
		JPanel rg = new JPanel();
		groot = new JLabel(new ImageIcon("images/Forward.gif"));
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
	}
	
	public void issueCommand (Robot r, char marker) {
		r.setMarker(marker);
	}
	
	public Queue<Robot> getBots() {
		return robots;
	}
}
