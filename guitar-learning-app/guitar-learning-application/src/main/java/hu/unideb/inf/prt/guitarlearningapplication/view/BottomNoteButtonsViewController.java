package hu.unideb.inf.prt.guitarlearningapplication.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.prt.guitarlearningapplication.Main;
import hu.unideb.inf.prt.guitarlearningapplication.model.Chord;
import hu.unideb.inf.prt.guitarlearningapplication.model.ChordType;
import hu.unideb.inf.prt.guitarlearningapplication.model.Note;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class BottomNoteButtonsViewController {

	/**
	 * Reference to the main application.
	 */
	private Main main;

	private Alert alert;

	/**
	 * A logger object used for logging.
	 */
	private Logger logger = LoggerFactory.getLogger(BottomNoteButtonsViewController.class);

	@FXML
	private ToggleGroup Notes;

	@FXML
	private ToggleGroup ChordTypes;

	@FXML
	private Button btnMakeChord;

	@FXML
	private Button btnSaveChord;

	private String selectedNoteName;

	private String selectedChordType;

	private int smallSecund = 3;
	private int bigSecund = 4;
	
	private int lowerFretTreshold = 0;
	private int upperFretTreshold = 2;

	private Chord readyChord;

	@FXML
	private void createMyChordButtonAction(ActionEvent event) {
		if (selectedNoteName != null) {
			if (selectedChordType != null) {
				createChord(selectedNoteName, selectedChordType, lowerFretTreshold, upperFretTreshold);
			} else {
				createErrorAlert("Not proper use!", "Please select a chord type!");
			}
		} else {
			createErrorAlert("Not proper use!", "Please select a note!");
		}
	}

	@FXML
	private void saveChordButtonAction(ActionEvent event) {
		if (selectedNoteName != null) {
			if (selectedChordType != null) {
				main.savePersonDataToFile(new File("chords.xml"), readyChord);
			} else {
				createErrorAlert("Not proper use!", "Nothing to save, please select a chord type!");
			}
		} else {
			createErrorAlert("Not proper use!", "Nothing to save, please select a note!");
		}
	}

	private void createErrorAlert(String title, String headerText) {
		alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.showAndWait();
	}

	private Chord createChord(String selectedNoteName, String selectedChordType, int lowerFretTreshold, int upperFretTreshold) {
		List<Note> noteList = main.getBaseNoteList();

		switch (selectedChordType) {
		case "MAJOR":
			readyChord = new Chord(selectedNoteName, ChordType.valueOf(selectedChordType),
					createNoteList(noteList, selectedNoteName, bigSecund, smallSecund));
			readyChord.getNoteList().stream().forEach(n -> logger.info(n.getName() + " " + n.getPosition()));
			break;
		case "MINOR":
			readyChord = new Chord(selectedNoteName, ChordType.valueOf(selectedChordType),
					createNoteList(noteList, selectedNoteName, smallSecund, bigSecund));
			readyChord.getNoteList().stream().forEach(n -> logger.info(n.getName() + " " + n.getPosition()));
			break;
		case "AUGMENTED":
			readyChord = new Chord(selectedNoteName, ChordType.valueOf(selectedChordType),
					createNoteList(noteList, selectedNoteName, bigSecund, bigSecund));
			readyChord.getNoteList().stream().forEach(n -> logger.info(n.getName() + " " + n.getPosition()));
			break;
		case "DIMINISHED":
			readyChord = new Chord(selectedNoteName, ChordType.valueOf(selectedChordType),
					createNoteList(noteList, selectedNoteName, smallSecund, smallSecund));
			readyChord.getNoteList().stream().forEach(n -> logger.info(n.getName() + " " + n.getPosition()));
			break;
		default:
			createErrorAlert("Not proper use!", "The chord type is unrecognized!");
			break;
		}

		if (readyChord != null) {
			main.createGuitarNeckView(readyChord, lowerFretTreshold, upperFretTreshold);
		}

		logger.info("Created chord: " + selectedNoteName + " " + selectedChordType);

		return readyChord;
	}

	private List<Note> createNoteList(List<Note> noteList, String selectedNoteName, int firstStep, int secondStep) {
		List<Note> chordNoteList = new ArrayList<Note>();

		Note mainNote = noteList.stream().filter(n -> n.getName().equals(selectedNoteName)).findFirst().get();
		chordNoteList.add(mainNote);

		Note secondNote = noteList.stream().filter(n -> n.getPosition() == mainNote.getPosition() + firstStep)
				.findFirst().get();
		chordNoteList.add(secondNote);

		Note thirdNote = noteList.stream().filter(n -> n.getPosition() == secondNote.getPosition() + secondStep)
				.findFirst().get();
		chordNoteList.add(thirdNote);

		return chordNoteList;
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		Notes.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (Notes.getSelectedToggle() != null) {
					selectedNoteName = Notes.getSelectedToggle().getUserData().toString();
				}
			}
		});

		ChordTypes.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (ChordTypes.getSelectedToggle() != null) {
					selectedChordType = ChordTypes.getSelectedToggle().getUserData().toString();
				}
			}
		});
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param main
	 */
	public void setMainApp(Main main) {
		this.main = main;
	}
}
