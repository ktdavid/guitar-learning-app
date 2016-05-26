package hu.unideb.inf.prt.guitarlearningapplication.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RootViewController {
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
