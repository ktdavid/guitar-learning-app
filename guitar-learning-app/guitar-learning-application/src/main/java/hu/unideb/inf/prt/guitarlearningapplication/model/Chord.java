package hu.unideb.inf.prt.guitarlearningapplication.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class for a Chord object.
 * 
 * @author Dávid Kistamás
 */
@XmlRootElement(name = "chord")
public class Chord {
	
	private String name;
	private ChordType chordType;
	private List<Note> noteList;
	
	public Chord() {
	}
	
	public Chord(String name, ChordType chordType, List<Note> noteList) {
		super();
		this.name = name;
		this.chordType = chordType;
		this.noteList = new ArrayList<Note>(noteList);
	}
	
	public String getName() {
		return name;
	}
	
	@XmlElement(name = "name")
	public void setName(String name) {
		this.name = name;
	}
	
	public ChordType getChordType() {
		return chordType;
	}
	
	@XmlElement(name = "type")
	public void setChordType(ChordType chordType) {
		this.chordType = chordType;
	}

	public List<Note> getNoteList() {
		return noteList;
	}

	public void setNoteList(List<Note> noteList) {
		this.noteList = new ArrayList<Note>(noteList);
	}
}