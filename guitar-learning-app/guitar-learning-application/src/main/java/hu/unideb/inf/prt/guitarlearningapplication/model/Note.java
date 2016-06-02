package hu.unideb.inf.prt.guitarlearningapplication.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Note object.
 * 
 * @author Dávid Kistamás
 */
public class Note {

	private StringProperty name;
	private IntegerProperty position;
	
	/**
	 * Constructor without parameters.
	 */
	public Note() {
		name = new SimpleStringProperty();
		position = new SimpleIntegerProperty();
	}

	/**
	 * Constructor with parameters.
	 * 
	 * @param position the position
	 * @param name the name
	 */
	public Note(int position, String name) {
		super();
		this.name = new SimpleStringProperty(name);
		this.position = new SimpleIntegerProperty(position);
	}

	/**
	 * public getter for name
	 * 
	 * @return String
	 */
	public String getName() {
		return name.get();
	}

	/**
	 * public setter for name
	 * 
	 * @param name the name
	 */
	public void setName(String name) {
		this.name.set(name);
	}
	
	/**
	 * public property for the name of the Note
	 * 
	 * @return StringProperty
	 */
	public StringProperty nameProperty() {
		return name;
	}

	/**
	 * public getter for position
	 * 
	 * @return int
	 */
	public int getPosition() {
		return position.get();
	}
	
	/**
	 * public setter for position
	 * 
	 * @param position the position
	 */
	public void setPosition(int position) {
		this.position.set(position);
	}
	
	/**
	 * public property for the position of the Note
	 * 
	 * @return IntegerProperty
	 */
	public IntegerProperty positionProperty() {
		return position;
	}
}
