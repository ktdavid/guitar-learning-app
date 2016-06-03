package hu.unideb.inf.prt.guitarlearningapplication.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.prt.guitarlearningapplication.Main;
import hu.unideb.inf.prt.guitarlearningapplication.controller.ChordCreatorController;
import hu.unideb.inf.prt.guitarlearningapplication.controller.IOController;
import hu.unideb.inf.prt.guitarlearningapplication.helper.Helper;
import hu.unideb.inf.prt.guitarlearningapplication.model.Chord;
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
	 * An instance of the {@code ChordCreatorController} class.
	 */
	private ChordCreatorController chordCreatorController = new ChordCreatorController();

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
	 * The selected {@code Note} name. It is the base note's name of the
	 * {@code Chord} that will be created.
	 */
	private String selectedNoteName;

	/**
	 * Returns the name of the selected {@code Note} object from the
	 * {@code BottomNoteButtonsViewController} class.
	 * 
	 * @return the name of the selected {@code Note} object
	 */
	public String getSelectedNoteName() {
		return selectedNoteName;
	}

	/**
	 * Sets the name of the selected {@code Note} object in the
	 * {@code BottomNoteButtonsViewController} class.
	 *
	 * @param selectedNoteName
	 *            the name to be set to the selected {@code Note} object
	 */
	public void setSelectedNoteName(String selectedNoteName) {
		this.selectedNoteName = selectedNoteName;
	}

	/**
	 * The selected {@code Chord} object's type.
	 */
	private String selectedChordType;

	/**
	 * Returns the type of the selected {@code Chord} object from the
	 * {@code BottomNoteButtonsViewController} class.
	 * 
	 * @return the type of the selected {@code Chord} object
	 */
	public String getSelectedChordType() {
		return selectedChordType;
	}

	/**
	 * Sets the type of the selected {@code Chord} object in the
	 * {@code BottomNoteButtonsViewController} class.
	 *
	 * @param selectedChordType
	 *            the type to be set to the selected {@code Chord} object
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
	 * The chord that is set to the {@code Chord} object that is ready to be
	 * displayed.
	 */
	private Chord readyChord;

	/**
	 * Returns the display-ready {@code Chord} object from the
	 * {@code BottomNoteButtonsViewController} class.
	 * 
	 * @return the display-ready {@code Chord} object
	 */
	public Chord getReadyChord() {
		return readyChord;
	}

	/**
	 * Constructs an empty {@code BottomNoteButtonsViewController} object, and
	 * initializes the IOController for reading and writing {@code Chord}
	 * objects to file.
	 */
	public BottomNoteButtonsViewController() {
	}

	/**
	 * Initializes the {@code BottomNoteButtonsViewController} view when
	 * {@code Chord} object is loaded from {@code TableView}.
	 */
	public void postInitialize() {
		Notes.getToggles().forEach(rb -> {
			if (rb.getUserData().equals(selectedNoteName)) {
				rb.setSelected(true);
			}
		});

		ChordTypes.getToggles().forEach(rb -> {
			if (rb.getUserData().equals(selectedChordType)) {
				rb.setSelected(true);
			}
		});
	}

	/**
	 * The createMyChordButton action for calling the
	 * {@code ChordCreatorController} in order to create the expected
	 * {@code Chord} object.
	 * 
	 * @param event
	 *            the actionevent
	 */
	@FXML
	public void createMyChordButtonAction(ActionEvent event) {
		chordCreatorController.setMainApp(main);

		if (selectedNoteName != null) {
			if (selectedChordType != null) {
				if (tbLowerFretTreshold != null && tbLowerFretTreshold.getText() != null
						&& !tbLowerFretTreshold.getText().isEmpty()) {
					lowerFretTreshold = chordCreatorController.parseAndFormatNumber(tbLowerFretTreshold.getText());
				}
				if (tbUpperFretTreshold != null && tbUpperFretTreshold.getText() != null
						&& !tbUpperFretTreshold.getText().isEmpty()) {
					upperFretTreshold = chordCreatorController.parseAndFormatNumber(tbUpperFretTreshold.getText());
				}
				readyChord = chordCreatorController.createChord(readyChord, selectedNoteName, selectedChordType, lowerFretTreshold,
						upperFretTreshold, bigInterval, smallInterval);
			} else {
				Helper.createAlert("Not proper use!", "Please select a chord type!", AlertType.ERROR);
				logger.error("No chord type was selected!");
			}
		} else {
			Helper.createAlert("Not proper use!", "Please select a note!", AlertType.ERROR);
			logger.error("No note was selected!");
		}
	}

	/**
	 * The saveChordButton action for saving a {@code Chord} object to the XML
	 * file.
	 * 
	 * @param event
	 *            the actionevent
	 */
	@FXML
	public void saveChordButtonAction(ActionEvent event) {
		IOController.setMainApp(main);

		if (selectedNoteName != null) {
			if (selectedChordType != null) {
				IOController.saveChordAction(readyChord);
			} else {
				Helper.createAlert("Not proper use!", "Nothing to save, please select a chord type!", AlertType.ERROR);
				logger.error("No chord type was selected!");
			}
		} else {
			Helper.createAlert("Not proper use!", "Nothing to save, please select a note!", AlertType.ERROR);
			logger.error("No note was selected!");
		}
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
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param main
	 *            the main application
	 */
	public void setMainApp(Main main) {
		this.main = main;
	}
}
