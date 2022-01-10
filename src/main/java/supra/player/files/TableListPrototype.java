package supra.player.files;

import java.io.File;

public class TableListPrototype {
	File file;
	String title;
	String minuteSeconde;

	public TableListPrototype(File file) {
		this.file = file;
		this.title = file.getName();
		this.minuteSeconde = "NaN";
	}
	
	public File getFile() {
		return file;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getMinuteSeconde() {
		return minuteSeconde;
	}

	public void setMinuteSeconde(String minuteSeconde) {
		this.minuteSeconde = minuteSeconde;
	}
	
}
