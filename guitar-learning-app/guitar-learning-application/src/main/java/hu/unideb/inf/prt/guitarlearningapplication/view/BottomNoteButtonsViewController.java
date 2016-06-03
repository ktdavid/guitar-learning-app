package hu.unideb.inf.prt.guitarlearningapplication.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.prt.guitarlearningapplication.Main;
import hu.unideb.inf.prt.guitarlearningapplication.controller.IOController;
import hu.unideb.inf.prt.guitarlearningapplication.helper.Helper;
import hu.unideb.inf.prt.guitarlearningapplication.model.Chord;
import hu.unideb.inf.prt.guitarlearningapplication.model.ChordType;
import hu.unideb.inf.prt.guitarlearningapplication.model.Note;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

/**
 * Controller class for the businesslogic of the application.
 * 
 * @author Dávid Kistamás
 * @version 1.0
 */
public class BottomNoteButtonsViewController {

	/**
	 * Reference to the main application.
	 */
	private Main main;
	
	/**
	 * An instance of the {@code IOController} class.
	 */
	private IOController IOController = new IOController();

	/**
	 * A logger object used for logging events at runtime.
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
	
	@FXML
	private Button btnLoadChord;

	@FXML
	private TextField tbLowerFretTreshold;

	@FXML
	private TextField tbUpperFretTreshold;

	/**
	 * The selected {@code Note} name. 
	 * It is the base note's name of the {@code Chord} that will be created.
	 */
	private String selectedNoteName;

	/**
	 * Returns the name of the selected {@code Note} object from the {@code BottomNoteButtonsViewController} class.
	 * 
	 * @return the name of the selected {@code Note} object
	 */
	public String getSelectedNoteName() {
		return selectedNoteName;
	}

	/**
	 * Sets the name of the selected {@code Note} object in the {@code BottomNoteButtonsViewController} class.
	 *
	 * @param selectedNoteName the name to be set to the selected {@code Note} object
	 */
	public void setSelectedNoteName(String selectedNoteName) {
		this.selectedNoteName = selectedNoteName;
	}

	/**
	 * The selected {@code Chord} object's type.
	 */
	private String selectedChordType;

	/**
	 * Returns the type of the selected {@code Chord} object from the {@code BottomNoteButtonsViewController} class.
	 * 
	 * @return the type of the selected {@code Chord} object
	 */
	public String getSelectedChordType() {
		return selectedChordType;
	}

	/**
	 * Sets the type of the selected {@code Chord} object in the {@code BottomNoteButtonsViewController} class.
	 *
	 * @param selectedChordType the type to be set to the selected {@code Chord} object
	 */
	public void setSelectedChordType(String selectedChordType) {
		this.selectedChordType = selectedChordType;
	}

	/**
	 * The small interval for creating a {@code Chord} object.
	 */
	private int smallInterval = 3;
	
	/**
	 * The big interval for creating a {@code Chord} object.
	 */
	private int bigInterval = 4;

	/**
	 * The lower fret number to show the {@code Chord} objects from.
	 */
	private int lowerFretTreshold = 1;
	
	/**
	 * The upper fret number to show the {@code Chord} objects to.
	 */
	private int upperFretTreshold = 3;

	/**
	 * The chord that is set to the {@code Chord} object that is ready to be displayed.
	 */
	private Chord readyChord;
	
	/**
	 * Returns the display-ready {@code Chord} object from the {@code BottomNoteButtonsViewController} class.
	 * 
	 * @return the display-ready {@code Chord} object
	 */
	public Chord getReadyChord() {
		return readyChord;
	}
	
	/**
	 * Constructs an empty {@code BottomNoteButtonsViewController} object,
	 * and initializes the IOController for reading and writing {@code Chord} objects to file.
	 */
	public BottomNoteButtonsViewController() {
	}

	/**
	 * Initializes the {@code BottomNoteButtonsViewController} view when 
	 * {@code Chord} object is loaded from {@code TableView}.
	 */
	public void postInitialize() {
		Notes.getToggles().forEach(rb -> {
			if(rb.getUserData().equals(selectedNoteName)) {
				rb.setSelected(true);
			}
		});

		ChordTypes.getToggles().forEach(rb -> {
			if(rb.getUserData().equals(selectedChordType)) {
				rb.setSelected(true);
			}
		});
	}

	/**
	 * The create my chord button's action.
	 * 
	 * @param event the actionevent
	 */
	@FXML
	public void createMyChordButtonAction(ActionEvent event) {
		if (selectedNoteName != null) {
			if (selectedChordType != null) {
				if(tbLowerFretTreshold != null && tbLowerFretTreshold.getText() != null && !tbLowerFretTreshold.getText().isEmpty()) {
					lowerFretTreshold = parseAndFormatNumber(tbLowerFretTreshold.getText());
				}
				if(tbUpperFretTreshold != null && tbUpperFretTreshold.getText() != null && !tbUpperFretTreshold.getText().isEmpty()) {
					upperFretTreshold = parseAndFormatNumber(tbUpperFretTreshold.getText());
				}
				createChord(selectedNoteName, selectedChordType, lowerFretTreshold, upperFretTreshold);
			} else {
				Helper.createAlert("Not proper use!", "Please select a chord type!", AlertType.ERROR);
			}
		} else {
			Helper.createAlert("Not proper use!", "Please select a note!", AlertType.ERROR);
		}
	}

	/**
	 * The save Chord button's action.
	 * 
	 * @param event the actionevent
	 */
	@FXML
	public void saveChordButtonAction(ActionEvent event) {
		IOController.setMainApp(main);
		
		if (selectedNoteName != null) {
			if (selectedChordType != null) {
				if (main.getWrapperChordList() != null && main.getWrapperChordList().getChords() != null
						&& !main.getWrapperChordList().getChords().stream().anyMatch(c -> c.getName().equals(readyChord.getName())
								&& c.getChordType().equals(readyChord.getChordType()))) {
					main.getWrapperChordList().add(readyChord);
					IOController.saveChordToFile(new File("chords.xml"), main.getWrapperChordList());
					logger.info("File has been saved successfully!");
				} else if (main.getWrapperChordList() == null || main.getWrapperChordList().getChords() == null) {
					main.getWrapperChordList().add(readyChord);
					IOController.saveChordToFile(new File("chords.xml"), main.getWrapperChordList());
					logger.info("File has been saved successfully!");
				} else {
					Helper.createAlert("Chord is saved before.",
							readyChord.getName() + " " + readyChord.getChordType() + " chord has been already saved before.", AlertType.ERROR);
				}
			} else {
				Helper.createAlert("Not proper use!", "Nothing to save, please select a chord type!", AlertType.ERROR);
			}
		} else {
			Helper.createAlert("Not proper use!", "Nothing to save, please select a note!", AlertType.ERROR);
		}
	}

	private Chord createChord(String selectedNoteName, String selectedChordType, int lowerFretTreshold,
			int upperFretTreshold) {
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

	private List<Note> createNoteList(List<Note> noteList, String selectedNoteName, int firstStep, int secondStep) {
		List<Note> chordNoteList = new ArrayList<>();

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

		tbLowerFretTreshold.setText(Integer.toString(lowerFretTreshold));
		tbUpperFretTreshold.setText(Integer.toString(upperFretTreshold));
	}

	/**
	 * Method for parsing and formatting a text input field into integer.
	 * 
	 * @param text the text
	 * @return int
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
			logger.error("Not valid input into fret field!");
		}

		return retVal;
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param main the main application
	 */
	public void setMainApp(Main main) {
		this.main = main;
	}
	
	
}
