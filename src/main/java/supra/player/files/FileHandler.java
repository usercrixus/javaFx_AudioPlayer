package supra.player.files;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileHandler {
	
	static ObservableList<TableListPrototype> files = FXCollections.observableArrayList();



	public static void addFile(TableListPrototype file) {
		files.add(file);
	}
	
	public static ObservableList<TableListPrototype> getFiles() {
		return files;
	}
	
}
