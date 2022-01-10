package supra.player.utilities;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import supra.player.App;

public class FXMLBuilder {

	public static void createMediaControlBar() {
		FXMLLoader loader = loadFXML("MediaControlBar");
		try {
			App.borderPane.setBottom(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createMusicList() {
		FXMLLoader loader = loadFXML("MusicList");
		try {
			App.borderPane.setCenter(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createMenu() {
		FXMLLoader loader = loadFXML("Menu");
		try {
			App.borderPane.setTop(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    private static FXMLLoader loadFXML(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }
}
