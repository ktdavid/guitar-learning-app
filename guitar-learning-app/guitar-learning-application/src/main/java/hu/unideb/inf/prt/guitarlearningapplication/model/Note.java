package hu.unideb.inf.prt.guitarlearningapplication.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Note object.
 * 
 * @author Dávid Kistamás
 * @version 1.0
 */
public class Note {

	/**
	 * The name of the {@code Note} object.
	 */
	private StringProperty name;
	
	/**
	 * The position of the {@code Note} object.
	 */
	private IntegerProperty position;
	
	/**
	 * Constructs an empty {@code Note} object.
	 */
	public Note() {
		name = new SimpleStringProperty();
		position = new SimpleIntegerProperty();
	}

	/**
	 * Constructs a {@code Note} object with the specified parameters.
	 * 
	 * @param position the position of the {@code Note} object
	 * @param name the name of the {@code Note} object
	 */
	public Note(int position, String name) {
		super();
		this.name = new SimpleStringProperty(name);
		this.position = new SimpleIntegerProperty(position);
	}

	/**
	 * Returns the name of the {@code Note} object as a String.
	 * 
	 * @return the name of the {@code Note} object as a String
	 */
	public String getName() {
		return name.get();
	}

	/**
	 * Sets the name of the {@code Note} object.
	 *
	 * @param name the name to be set to the {@code Note} object
	 */
	public void setName(String name) {
		this.name.set(name);
	}
	
	/**
	 * Returns the name property of the {@code Note} object.
	 * 
	 * @return the name property of the {@code Note} object
	 */
	public StringProperty nameProperty() {
		return name;
	}

	/**
	 * Returns the position of the {@code Note} object as an int.
	 * 
	 * @return the position of the {@code Note} object as an int
	 */
	public int getPosition() {
		return position.get();
	}
	
	/**
	 * Sets the position of the {@code Note} object.
	 *
	 * @param position the position to be set to the {@code Note} object
	 */
	public void setPosition(int position) {
		this.position.set(position);
	}
	
	/**
	 * Returns the position property of the {@code Note} object.
	 * 
	 * @return the position property of the {@code Note} object
	 */
	public IntegerProperty positionProperty() {
		return position;
	}
}
