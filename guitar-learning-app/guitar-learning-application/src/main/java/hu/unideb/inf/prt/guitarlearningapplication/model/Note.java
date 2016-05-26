package hu.unideb.inf.prt.guitarlearningapplication.model;

/**
 * Model class for a Note object.
 * 
 * @author Dávid Kistamás
 */
public class Note {

	private int position;
	private String name;

	public Note(int position, String name) {
		super();
		this.position = position;
		this.name = name;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
