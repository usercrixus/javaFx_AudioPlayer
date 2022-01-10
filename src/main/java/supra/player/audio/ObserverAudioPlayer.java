package supra.player.audio;

public interface ObserverAudioPlayer {
	/**
	 * 0 play
	 * 1 pause
	 */
	public int updateAudioPlayerStatus(int status);
}
