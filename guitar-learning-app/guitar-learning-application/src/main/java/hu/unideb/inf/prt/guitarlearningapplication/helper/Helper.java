package hu.unideb.inf.prt.guitarlearningapplication.helper;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Helper class for using common methods.
 * 
 * @author Dávid
 */
public final class Helper {
	
	private Helper() {
	}
	
	/**
	 * Creates the error alert.
	 * 
	 * @param title the title
	 * @param headerText the text to be displayed as the error message
	 */
	public static void createErrorAlert(String title, String headerText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.showAndWait();
	}
}
