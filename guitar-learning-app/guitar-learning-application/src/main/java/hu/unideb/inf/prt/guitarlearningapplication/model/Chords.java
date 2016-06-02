package hu.unideb.inf.prt.guitarlearningapplication.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Public wrapper class for the Chord objects.
 * 
 * @author Dávid
 */
@XmlRootElement(name = "Chords")
public class Chords{
	
	private List<Chord> chords;
	
	/**
	 * Constructor without parameters.
	 */
	public Chords() {
	}
	
	/**
	 * public getter for chords.
	 * @return {@code List<Chord>}
	 */
	@XmlElement(name="Chord")
	public List<Chord> getChords() {
		return chords;
	}
	
	/**
	 * public setter for chords.
	 * @param chords the chords
	 */
	public void setChords(List<Chord> chords) {
		this.chords = chords;
	}
	
	/**
	 * public method for adding a Chord to the list of Chords.
	 * @param chord the chord
	 */
	public void add(Chord chord) {
		if(chords == null) {
			this.chords = new ArrayList<Chord>();
		}
		this.chords.add(chord);
	}
}
