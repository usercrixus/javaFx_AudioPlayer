package supra.player.controler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import supra.player.audio.AudioPlayer;
import supra.player.files.FileHandler;
import supra.player.files.TableListPrototype;

public class MusicListControler {

	@FXML
	private TableView<TableListPrototype> TableViewFiles;

	@FXML
	private TableColumn<TableListPrototype, String> tableColumnTitle;

	@FXML
	private TableColumn<TableListPrototype, String> tableColumnDuration;

    @FXML
    void initialize() {
    	tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
    	tableColumnDuration.setCellValueFactory(new PropertyValueFactory<>("minuteSeconde"));
		TableViewFiles.setItems(FileHandler.getFiles());
    }
    
    
    @FXML
    void launchMusic(MouseEvent event) {
    	if(event.getClickCount() >= 2) {
    		File file = TableViewFiles.getSelectionModel().getSelectedItem().getFile();
    		try {
				AudioPlayer player = AudioPlayer.getAudioPlayer();
				player.defineTrack(file.getAbsolutePath());
				player.play();
				
				//TableViewFiles.getSelectionModel().getSelectedItem().setMinuteSeconde("ok");
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
			}
    	}
    }
    

    @FXML
    void OnDragAcceptFile(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
        	event.acceptTransferModes(TransferMode.LINK);
        }
    }

    @FXML
    void OnDragDroppedAddFile(DragEvent event) {
        List<File> list = event.getDragboard().getFiles();
        for (File file : list) {
			FileHandler.addFile(new TableListPrototype(file));
		}
    }
    
}
