package supra.player.controler;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.LineUnavailableException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import supra.player.audio.AudioPlayer;
import supra.player.audio.ObserverAudioPlayer;
import supra.player.utilities.math.MathTime;
import supra.player.utilities.math.MinuteSeconde;

public class MediaControlBarControler implements ObserverAudioPlayer{

	public static Timer timer;

	@FXML
	private Button ButtonPlayPause;

	@FXML
	private Button buttonStop;

	@FXML
	private Label labelCurrentTime;

	@FXML
	private Label labelTotalTime;

	@FXML
	private Slider sliderTimeBar;

	@FXML
	private Slider sliderVolumeBar;



	public MediaControlBarControler() {
		AudioPlayer.getAudioPlayer().subscribeStatus(this);
	}

	@FXML
	void initialize() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				long currentTime = AudioPlayer.getAudioPlayer().getClip().getMicrosecondPosition();
				long totalTime = AudioPlayer.getAudioPlayer().getClip().getMicrosecondLength();
				
				MinuteSeconde minuteSecondeCurrent = MathTime.minuteSeconde(currentTime);
				MinuteSeconde minuteSecondeTotal = MathTime.minuteSeconde(totalTime);
				
				Platform.runLater(new Runnable() {
					@Override public void run() {
						if(totalTime != 0) {
							sliderTimeBar.setValue( (Double.longBitsToDouble(currentTime)/Double.longBitsToDouble(totalTime))*100 );
							labelCurrentTime.setText(minuteSecondeCurrent.getMin()+":"+minuteSecondeCurrent.getSec());
							labelTotalTime.setText(minuteSecondeTotal.getMin()+":"+minuteSecondeTotal.getSec());
						}
					}
				});
			}
		}, 0, 300);

	}

	@FXML
	void defineTrackTime(MouseEvent event) throws LineUnavailableException, InterruptedException {
		double time = sliderTimeBar.getValue()/100;
		double microSecPosition = Double.longBitsToDouble(AudioPlayer.getAudioPlayer().getClip().getMicrosecondLength()) * time;
		AudioPlayer.getAudioPlayer().getClip().setMicrosecondPosition(Double.doubleToLongBits(microSecPosition));
	}

	@FXML
	void defineVolume(MouseEvent event) throws LineUnavailableException {
		AudioPlayer.getAudioPlayer().defineVolume(sliderVolumeBar.getValue());
	}


	@FXML
	void playPause(ActionEvent event) throws LineUnavailableException {
		if(ButtonPlayPause.getText() == "||") {
			AudioPlayer.getAudioPlayer().pause();
		}else {
			AudioPlayer.getAudioPlayer().play();
		}
	}


	@FXML
	void stop(ActionEvent event) {
		try {
			AudioPlayer.getAudioPlayer().stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int updateAudioPlayerStatus(int status) {

		if(status == 0) {
			ButtonPlayPause.setText("||");
		}else if(status == 1){
			ButtonPlayPause.setText(">");
		}

		return 0;
	}

}
