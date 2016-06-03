package hu.unideb.inf.prt.guitarlearningapplication.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Public wrapper class for the Chord objects.
 * 
 * @author Dávid Kistamás
 * @version 1.0
 */
@XmlRootElement(name = "Chords")
public class Chords{
	
	/**
	 * A wrapper list for {@code Chord} objects.
	 */
	private List<Chord> chords;
	
	/**
	 * Constructs an empty {@code Chords} object.
	 */
	public Chords() {
	}
	
	/**
	 * Returns the chords of the {@code Chords} object.
	 * 
	 * @return the chords of the {@code Chords} object
	 */
	@XmlElement(name="Chord")
	public List<Chord> getChords() {
		return chords;
	}
	
	/**
	 * Sets the chords of the {@code Chords} object.
	 *
	 * @param chords the chords to be set to the {@code Chords} object
	 */
	public void setChords(List<Chord> chords) {
		this.chords = chords;
	}
	
	/**
	 * Adds a new {@code Chord} object to the list of {@code Chord} objects.
	 * If the list hasn't been initialized yet, this method initializes it first,
	 * then adds the {@code Chord} object to the list.
	 * 
	 * @param chord the chord to be added to the list of {@code Chord} objects
	 */
	public void add(Chord chord) {
		if(chords == null) {
			this.chords = new ArrayList<Chord>();
		}
		this.chords.add(chord);
	}
}
