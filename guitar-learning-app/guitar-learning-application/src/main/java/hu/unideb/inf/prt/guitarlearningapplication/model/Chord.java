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
 */
@XmlRootElement(name = "Chord")
@XmlType(propOrder = {"name", "chordType"})
//@XmlSeeAlso({Notes.class})
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Chord {
	
	private StringProperty name;
	private ObjectProperty<ChordType> chordType;
	private List<Note> notes;
	
	/**
	 * Constructor without parameters.
	 */
	public Chord() {
		name = new SimpleStringProperty();
		chordType = new SimpleObjectProperty<>();
		//notes = new SimpleObjectProperty<>();
		notes = new ArrayList<>();
	}
	
	/**
	 * Constructor with parameters.
	 * 
	 * @param name the name
	 * @param chordType the chord type
	 * @param notes the notes of the chord
	 */
	public Chord(String name, ChordType chordType, List<Note> notes) {
		super();
		this.name = new SimpleStringProperty(name);
		this.chordType = new SimpleObjectProperty<>(chordType);
		//this.notes = new SimpleObjectProperty<>(notes);
		this.notes = new ArrayList<>(notes);
	}
	
	/**
	 * public getter for name field.
	 * @return String
	 */
	@XmlElement(name = "Name")
	public String getName() {
		return name.get();
	}
	
	/**
	 * public setter for name.
	 * @param name the name
	 */
	public void setName(String name) {
		this.name.set(name);
	}
	
	/**
	 * public property for name.
	 * @return StringProperty
	 */
	public StringProperty nameProperty() {
		return name;
	}
	
	/**
	 * public getter for chord type.
	 * @return ChordType
	 */
	@XmlElement(name = "Type")
	public ChordType getChordType() {
		return chordType.get();
	}
	
	/**
	 * public setter for chord type.
	 * @param chordType the chord type
	 */
	public void setChordType(ChordType chordType) {
		this.chordType.set(chordType);
	}
	
	/**
	 * public property for chord type.
	 * @return {@code ObjectProperty<ChordType>}
	 */
	public ObjectProperty<ChordType> chordTypeProperty() {
		return chordType;
	}

	/**
	 * public getter for Notes.
	 * @return {@code List<Note>}
	 */
	@XmlTransient
	public List<Note> getNotes() {
		return notes;
	}

	/**
	 * public setter for Notes.
	 * @param notes the notes
	 */
	public void setNoteList(List<Note> notes) {
		this.notes = notes;
	}
}