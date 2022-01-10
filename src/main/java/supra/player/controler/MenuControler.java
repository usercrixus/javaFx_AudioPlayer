package supra.player.controler;

import java.io.File;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import supra.player.files.FileHandler;
import supra.player.files.TableListPrototype;

public class MenuControler {
    @FXML
    private MenuItem menuItemOpenFile;

    @FXML
    void openFile(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	List<File> list = fileChooser.showOpenMultipleDialog(null);
    	list.stream().forEach((file) -> {
            FileHandler.addFile(new TableListPrototype(file));
        });
    }
}
