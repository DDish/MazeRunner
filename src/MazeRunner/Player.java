package MazeRunner;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Player {
	
	public void playSound(String sound) {
		try {
		    File songFile = new File("sounds/"+sound+".wav");
		    AudioInputStream stream = AudioSystem.getAudioInputStream(songFile);
		    AudioFormat format = stream.getFormat();
		    DataLine.Info info = new DataLine.Info(Clip.class, format);
		    Clip clip = (Clip) AudioSystem.getLine(info);
		    clip.open(stream);
		    clip.start();
		}
		catch (Exception ex) {
		    System.out.println("Bad sound name");
		}
	}
}
