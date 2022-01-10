package supra.player.utilities.math;

public class MinuteSeconde {
	
	int min;
	int sec;
	
	public MinuteSeconde(int min, int sec) {
		this.min = min;
		this.sec = sec;
	}
	
	public String getFormatedTime() {
		return min+":"+sec;
	}
	
	public int getMin() {
		return min;
	}

	public int getSec() {
		return sec;
	}

}
