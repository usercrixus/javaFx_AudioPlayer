package supra.player.audio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
	
	// singleton
	static AudioPlayer audioPlayer;

	//observer
	ArrayList<ObserverAudioPlayer> observersAudioPlayer;
	
	// open an audio file
	String filePath;
	AudioInputStream audioInputStream;
	
	// to store current position
	Long currentFrame;
	Clip clip;

	// current status of clip
	int status;
	boolean isTrackDefined;

	public AudioPlayer() {
		try {
			observersAudioPlayer = new ArrayList<>();
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void defineVolume(double volume) {
		FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		float vol = (float)volume/100*80;
		volumeControl.setValue(-vol);
	}
	
	public void defineTrack(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if(isTrackDefined) {
			close();
		}else {
			isTrackDefined = true;
		}
		
		this.filePath = filePath;
		audioInputStream =  AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

		// open audioInputStream to the clip
		clip.open(audioInputStream);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	// Work as the user enters his choice

	// Method to play the audio
	public void play() {
		//start the clip
		clip.start();

		status = 0;
		notifyStatus(0);
		
	}

	// Method to pause the audio
	public void pause(){
		if (status == 1) 
		{
			System.out.println("audio is already paused");
			return;
		}
		this.currentFrame = this.clip.getMicrosecondPosition();
		clip.stop();
		status = 1;
		notifyStatus(1);
	}

	// Method to stop the audio
	public void stop() throws IOException{
		currentFrame = 0L;
		clip.stop();
		clip.setMicrosecondPosition(0);
		notifyStatus(1);
	}
	
	// Method to stop the audio
	public void close() throws IOException{
		clip.stop();
		clip.close();
		notifyStatus(1);
	}

	// Method to jump over a specific part
	public void jump(long c) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		if (c > 0 && c < clip.getMicrosecondLength()) {
			clip.stop();
			clip.close();
			resetAudioStream();
			currentFrame = c;
			clip.setMicrosecondPosition(c);
			this.play();
		}
	}

	// Method to reset audio stream
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
		clip.open(audioInputStream);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	//----------------Observable--------------
	
	public void subscribeStatus(ObserverAudioPlayer observer) {
		observersAudioPlayer.add(observer);
	}
	
	public void notifyStatus(int status) {
		for (ObserverAudioPlayer observerAudioPlayer : observersAudioPlayer) {
			observerAudioPlayer.updateAudioPlayerStatus(status);
		}
	}
	
	
	//------------Getter-Setter-------------
	
	public static AudioPlayer getAudioPlayer(){
		
		if(audioPlayer == null) {
			audioPlayer = new AudioPlayer();
		}
		
		return audioPlayer;
	}
	
	public Clip getClip() {
		return clip;
	}

}