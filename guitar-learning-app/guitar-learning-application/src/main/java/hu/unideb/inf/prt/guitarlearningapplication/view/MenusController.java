package hu.unideb.inf.prt.guitarlearningapplication.view;

import java.io.IOException;

import hu.unideb.inf.prt.guitarlearningapplication.Main;
import hu.unideb.inf.prt.guitarlearningapplication.helper.Helper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;

/**
 * Controller class for the Menus.fxml view.
 * 
 * @author Dávid Kistamás
 * @version 1.0
 */
public class MenusController {
	
	/**
	 * A reference to the main application.
	 */
	private Main main;
	
	/**
	 * It is called by the main application to give a reference back to itself.
	 * 
	 * @param main the main application
	 */
	public void setMainApp(Main main) {
		this.main = main;
	}
	
	/**
	 * Switches to the guitar chord builder screen.
	 */
	@FXML
	private void switchToBuilderScreen() {
		main.createRootView();
		main.createGuitarNeckView(null, 0, 0);
		main.createBottomNoteButtonsView(null, null);
	}
	
	/**
	 * Switches to the saved {@code Chords} page.
	 * @throws IOException
	 */
	@FXML
	private void switchToLoaderScreen() {
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
     * Opens the about dialog.
     */
    @FXML
    private void handleAbout() {
    	Helper.createAlert("GuitarLearningApp", "About\n\nAuthor: Dávid Kistamás", AlertType.INFORMATION);
    }
}
