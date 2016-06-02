package hu.unideb.inf.prt.guitarlearningapplication.view;

import java.io.IOException;

import hu.unideb.inf.prt.guitarlearningapplication.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Controller class for the Menus.fxml view.
 * 
 * @author Dávid
 */
public class MenusController {
	
	private Main main;
	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param main the main application
	 */
	public void setMainApp(Main main) {
		this.main = main;
	}
	
	@FXML
	private void switchToBuilderScreen() {
		main.createRootView();
		main.createGuitarNeckView(null, 0, 0);
		main.createBottomNoteButtonsView(null, null);
	}
	
	@FXML
	private void switchToLoaderScreen() throws IOException {
		main.createRootView();
		main.createSavedChordsView();
	}
	
	/**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
    
    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("GuitarLearningApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Dávid Kistamás");
        alert.showAndWait();
    }
}
