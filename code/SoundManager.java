import javax.sound.sampled.AudioInputStream;		// for playing sound clips
import javax.sound.sampled.*;
import java.io.*;
import java.util.HashMap;				// for storing sound clips

public class SoundManager {				// a Singleton class
	HashMap<String, Clip> clips;

	private static SoundManager instance = null;	// keeps track of Singleton instance

	private float volume;

	private SoundManager () {
		clips = new HashMap<String, Clip>();

		Clip clip = loadClip("code/sounds/level1.wav");
		clips.put("level1", clip);

		Clip clip1 = loadClip("code/sounds/level2.wav");
		clips.put("level2", clip1);

		Clip clip2 = loadClip("code/sounds/level3.wav");
		clips.put("level3", clip2);

		Clip clip3 = loadClip("code/sounds/coin.wav");
		clips.put("coin", clip3);

		Clip clip4 = loadClip("code/sounds/die.wav");
		clips.put("die", clip4);

		Clip clip5 = loadClip("code/sounds/doubleCoin.wav");
		clips.put("doubleCoin", clip5);

		Clip clip6 = loadClip("code/sounds/fly.wav");
		clips.put("fly", clip6);

		Clip clip7 = loadClip("code/sounds/gameOver.wav");
		clips.put("gameOver", clip7);

		Clip clip8 = loadClip("code/sounds/invisible.wav");
		clips.put("invisible", clip8);

		Clip clip9 = loadClip("code/sounds/laser.wav");
		clips.put("laser", clip9);

		Clip clip10 = loadClip("code/sounds/missile.wav");
		clips.put("missile", clip10);

		Clip clip11 = loadClip("code/sounds/speed.wav");
		clips.put("speed", clip11);
	}


	public static SoundManager getInstance() {	// class method to retrieve instance of Singleton
		if (instance == null)
			instance = new SoundManager();
		
		return instance;
	}		


    	public Clip loadClip (String fileName) {	// gets clip from the specified file
 		AudioInputStream audioIn;
		Clip clip = null;

		try {
    			File file = new File(fileName);
    			audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL()); 
    			clip = AudioSystem.getClip();
    			clip.open(audioIn);
		}
		catch (Exception e) {
 			System.out.println ("Error opening sound files: " + e);
		}
    		return clip;
    	}


	public Clip getClip (String title) {

		return clips.get(title);
	}


    	public void playClip(String title, boolean looping) {
		Clip clip = getClip(title);
		if (clip != null) {
			clip.setFramePosition(0);
			if (looping)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			else
				clip.start();
		}
    	}


    	public void stopClip(String title) {
		Clip clip = getClip(title);
		if (clip != null) {
			clip.stop();
		}
    	}

}