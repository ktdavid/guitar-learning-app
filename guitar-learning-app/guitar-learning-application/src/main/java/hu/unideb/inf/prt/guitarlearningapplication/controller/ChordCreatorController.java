package hu.unideb.inf.prt.guitarlearningapplication.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.prt.guitarlearningapplication.Main;
import hu.unideb.inf.prt.guitarlearningapplication.helper.Helper;
import hu.unideb.inf.prt.guitarlearningapplication.model.Chord;
import hu.unideb.inf.prt.guitarlearningapplication.model.ChordType;
import hu.unideb.inf.prt.guitarlearningapplication.model.Note;
import javafx.scene.control.Alert.AlertType;

/**
 * Public wrapper class for the Chord objects.
 * 
 * @author Dávid Kistamás
 * @version 1.0
 */
public class ChordCreatorController {

	/**
	 * A logger object used for logging events at runtime.
	 */
	private Logger logger = LoggerFactory.getLogger(ChordCreatorController.class);

	/**
	 * A reference to the main application.
	 */
	private Main main;

	/**
	 * Constructs an empty {@code ChordCreatorController} object.
	 */
	public ChordCreatorController() {
	}

	/**
	 * It is called by the main application to give a reference back to itself.
	 * 
	 * @param main the main application
	 */
	public void setMainApp(Main main) {
		this.main = main;
	}

	/**
	 * Method for parsing and formatting the text input field's value into
	 * integer.
	 * 
	 * @param text the text to be parsed as an integer
	 * @return the formatted text as an {@code int}
	 */
	public int parseAndFormatNumber(String text) {
		int retVal = 0;

		if (text.isEmpty() || text.equals("0")) {
			retVal = 0;
			return retVal;
		}

		try {
			retVal = Integer.parseInt(text);
		} catch (NumberFormatException e) {
			logger.error("Not valid input!");
		}

		return retVal;
	}

	/**
	 * Creates the {@code Chord} objects based on the input parameters.
	 * 
	 * @param readyChord the display-ready {@code Chord} object as the return value
	 * @param selectedNoteName the selected note name
	 * @param selectedChordType the selected chord type
	 * @param lowerFretTreshold the lower fret number to display the {@code Note} objects from 
	 * @param upperFretTreshold the upper fret number to display the {@code Note} objects to
	 * @param bigInterval the bigger interval to step from a {@code Note} object to get the next {@code Note} object
	 * @param smallInterval the smaller interval to step from a {@code Note} object to get the next {@code Note} object
	 * @return a {@code Chord} object that is ready to be displayed on the guitar neck
	 */
	public Chord createChord(Chord readyChord, String selectedNoteName, String selectedChordType, int lowerFretTreshold,
			int upperFretTreshold, int bigInterval, int smallInterval) {
		List<Note> noteList = main.getBaseNoteList();

		switch (selectedChordType) {
		case "MAJOR":
			readyChord = new Chord(selectedNoteName, ChordType.valueOf(selectedChordType),
					createNoteList(noteList, selectedNoteName, bigInterval, smallInterval));
			readyChord.getNotes().stream().forEach(n -> logger.info(n.getName() + " " + n.getPosition()));
			break;
		case "MINOR":
			readyChord = new Chord(selectedNoteName, ChordType.valueOf(selectedChordType),
					createNoteList(noteList, selectedNoteName, smallInterval, bigInterval));
			readyChord.getNotes().stream().forEach(n -> logger.info(n.getName() + " " + n.getPosition()));
			break;
		case "AUGMENTED":
			readyChord = new Chord(selectedNoteName, ChordType.valueOf(selectedChordType),
					createNoteList(noteList, selectedNoteName, bigInterval, bigInterval));
			readyChord.getNotes().stream().forEach(n -> logger.info(n.getName() + " " + n.getPosition()));
			break;
		case "DIMINISHED":
			readyChord = new Chord(selectedNoteName, ChordType.valueOf(selectedChordType),
					createNoteList(noteList, selectedNoteName, smallInterval, smallInterval));
			readyChord.getNotes().stream().forEach(n -> logger.info(n.getName() + " " + n.getPosition()));
			break;
		default:
			Helper.createAlert("Not proper use!", "The chord type is unrecognized!", AlertType.ERROR);
			break;
		}

		if (readyChord != null) {
			main.createGuitarNeckView(readyChord, lowerFretTreshold, upperFretTreshold);
		}

		logger.info("Created chord: " + selectedNoteName + " " + selectedChordType);

		return readyChord;
	}

	/**
	 * Creates the list containing the {@code Note} objects for the {@code Chord} object.
	 * 
	 * @param noteList the list of {@code Note} objects
	 * @param selectedNoteName the selected note name
	 * @param firstInterval the first interval to step from the main {@code Note} object
	 * @param secondInterval the second interval to step from the second {@code Note} object
	 * @return list of {@code Note} objects
	 */
	private List<Note> createNoteList(List<Note> noteList, String selectedNoteName, int firstInterval, int secondInterval) {
		List<Note> chordNoteList = new ArrayList<>();

		Note mainNote = noteList.stream().filter(n -> n.getName().equals(selectedNoteName)).findFirst().get();
		chordNoteList.add(mainNote);

		Note secondNote = noteList.stream().filter(n -> n.getPosition() == mainNote.getPosition() + firstInterval)
				.findFirst().get();
		chordNoteList.add(secondNote);

		Note thirdNote = noteList.stream().filter(n -> n.getPosition() == secondNote.getPosition() + secondInterval)
				.findFirst().get();
		chordNoteList.add(thirdNote);

		return chordNoteList;
	}
}
