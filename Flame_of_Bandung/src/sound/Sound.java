package sound;

import javax.sound.sampled.Clip;
import static helpers.Constants.SoundIndex.*;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	
	Clip clip;
	URL sound[] = new URL[5];
	
	private float minusVolume;
	private boolean muted = false;
	private int musicIndex = -1;
	private boolean isPlaying = false;
	
	public Sound() {
		
		sound[MAIN] = getClass().getResource("/Music/Menu_Ost.wav");
		sound[CUTSCENES] = getClass().getResource("/Music/Cutscene_Ost.wav");
		sound[PLAY] = getClass().getResource("/Music/War_Ost.wav");
		sound[WIN] = getClass().getResource("/Music/WinScene_Ost.wav");
		sound[LOST] = getClass().getResource("/Music/LostScene_Ost.wav");
		
		this.minusVolume = 15.0f;
	}
	
	public void setMusic (int i) {
		musicIndex = i;
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(sound[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			isPlaying = true;
			refreshVolume();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		
		clip.stop();
	}
	
	private void refreshVolume() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		if (!muted) gainControl.setValue(-minusVolume);
		else gainControl.setValue(-80.0f);
	}
	
	public void setMinusVolume(float fl) {
		minusVolume = fl;
		refreshVolume();
	}
	
	public void changeMuted() {
		muted = !(muted);
		refreshVolume();
	}

	public int getMusicPlayingNow() {
		return musicIndex;
	}

	public boolean isPlay() {
		return isPlaying;
	}
}
