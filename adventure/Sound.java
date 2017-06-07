package adventure;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public enum Sound {
	InHouse("soundfiles/movingOut.mid");
	
	private Clip clip;
	
    Sound(String soundfile) {
		try {
			URL url = this.getClass().getClassLoader().getResource(soundfile);
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
	        // Get a clip resource.
	        clip = AudioSystem.getClip();
	        // Open audio clip and load samples from the audio input stream.
	        clip.open(audioInputStream);
	     } catch (UnsupportedAudioFileException e) {
	        e.printStackTrace();
	     } catch (IOException e) {
	        e.printStackTrace();
	     } catch (LineUnavailableException e) {
	        e.printStackTrace();
	     }
	}
    
	static void init() {
		values();
	}
	
	public void play() {
     if (clip.isRunning()) {
        clip.stop();
     }
     clip.setFramePosition(0);
     clip.start();
     clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
}
