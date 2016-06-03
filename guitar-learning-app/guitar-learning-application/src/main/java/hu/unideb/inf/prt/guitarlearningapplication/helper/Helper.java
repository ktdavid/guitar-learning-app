package hu.unideb.inf.prt.guitarlearningapplication.helper;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Helper class for common methods.
 * 
 * @author Dávid Kistamás
 * @version 1.0
 */
public final class Helper {
	
	/**
	 * This is a private constructor of the {@code Helper} class, in order to make it static.
	 */
	private Helper() {
	}
	
	/**
	 * Creates an alert of the specified type with the given message and title.
	 * 
	 * @param title the title of the alert
	 * @param headerText the text to be displayed in the alert window
	 * @param level the level of the alert eg. {@code AlertType.ERROR}
	 */
	public static void createAlert(String title, String headerText, AlertType level) {
		Alert alert = new Alert(level);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.showAndWait();
	}
}
