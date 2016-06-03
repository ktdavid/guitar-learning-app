package hu.unideb.inf.prt.guitarlearningapplication.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Chord object.
 * 
 * @author Dávid Kistamás
 * @version 1.0
 */
@XmlRootElement(name = "Chord")
@XmlType(propOrder = {"name", "chordType"})
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Chord {
	
	/**
	 * The name of the {@code Chord} object.
	 */
	private StringProperty name;
	
	/**
	 * The type of the {@code Chord} object.
	 */
	private ObjectProperty<ChordType> chordType;
	
	/**
	 * The notes of the {@code Chord} object.
	 */
	private List<Note> notes;
	
	/**
	 * Constructs an empty {@code Chord} object.
	 */
	public Chord() {
		name = new SimpleStringProperty();
		chordType = new SimpleObjectProperty<>();
		//notes = new SimpleObjectProperty<>();
		notes = new ArrayList<>();
	}
	
	/**
	 * Constructs a {@code Chord} object with the specified parameters.
	 * 
	 * @param name the name of the {@code Chord} object
	 * @param chordType the type of the {@code Chord} object
	 * @param notes the notes of the {@code Chord} object
	 */
	public Chord(String name, ChordType chordType, List<Note> notes) {
		super();
		this.name = new SimpleStringProperty(name);
		this.chordType = new SimpleObjectProperty<>(chordType);
		this.notes = new ArrayList<>(notes);
	}
	
	/**
	 * Returns the name of the {@code Chord} object as a String.
	 * 
	 * @return the name of the {@code Chord} object as a String
	 */
	@XmlElement(name = "Name")
	public String getName() {
		return name.get();
	}
	
	/**
	 * Sets the name of the {@code Chord} object.
	 *
	 * @param name the name to be set to the {@code Chord} object
	 */
	public void setName(String name) {
		this.name.set(name);
	}
	
	/**
	 * Returns the name property of the {@code Chord} object.
	 * 
	 * @return the name property of the {@code Chord} object
	 */
	public StringProperty nameProperty() {
		return name;
	}
	
	/**
	 * Returns the type of the {@code Chord} object as a {@code ChordType} enum.
	 * 
	 * @return the type of the {@code Chord} object as a {@code ChordType} enum
	 */
	@XmlElement(name = "Type")
	public ChordType getChordType() {
		return chordType.get();
	}
	
	/**
	 * Sets the type of the {@code Chord} object.
	 *
	 * @param chordType the type to be set to the {@code Chord} object
	 */
	public void setChordType(ChordType chordType) {
		this.chordType.set(chordType);
	}
	
	/**
	 * Returns the type property of the {@code Chord} object.
	 * 
	 * @return the type property of the {@code Chord} object
	 */
	public ObjectProperty<ChordType> chordTypeProperty() {
		return chordType;
	}

	/**
	 * Returns the list of notes of the {@code Chord} object.
	 * 
	 * @return the list of notes of the {@code Chord} object
	 */
	@XmlTransient
	public List<Note> getNotes() {
		return notes;
	}

	/**
	 * Sets the list of notes of the {@code Chord} object.
	 *
	 * @param notes the list of notes to be set to the {@code Chord} object
	 */
	public void setNoteList(List<Note> notes) {
		this.notes = notes;
	}
}