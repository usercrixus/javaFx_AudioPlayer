package supra.player.utilities.math;

public class MathTime {
	public static MinuteSeconde minuteSeconde(long microSec) {
		
		int lengthSeconde = (int) microSec / 1000000;
		int min = lengthSeconde/60;
		int sec = lengthSeconde - 60*min;
				
		return new MinuteSeconde(min, sec);
	}
}
