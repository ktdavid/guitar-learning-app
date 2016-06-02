package hu.unideb.inf.prt.guitarlearningapplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.prt.guitarlearningapplication.model.Chord;
import hu.unideb.inf.prt.guitarlearningapplication.model.Chords;
import hu.unideb.inf.prt.guitarlearningapplication.model.Note;
import hu.unideb.inf.prt.guitarlearningapplication.view.BottomNoteButtonsViewController;
import hu.unideb.inf.prt.guitarlearningapplication.view.GuitarNeckViewController;
import hu.unideb.inf.prt.guitarlearningapplication.view.MenusController;
import hu.unideb.inf.prt.guitarlearningapplication.view.SavedChordsViewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class for the main program.
 * 
 * @author Dávid Kistamás
 * @version 1.0
 */
public class Main extends Application {

	/**
	 * A logger object used for logging.
	 */
	private Logger logger = LoggerFactory.getLogger(Main.class);

	/**
	 * The main window of the application.
	 */
	private Stage primaryStage;

	/**
	 * The root view of the application.
	 */
	private BorderPane rootView;

	/**
	 * The {@code GridPane} for displaying the Notes on the guitar's neck.
	 */
	private GridPane gridPane;

	/**
	 * The list for all base notes.
	 */
	private List<Note> baseNoteList = new ArrayList<>();

	private Chords chordList = new Chords();

	/**
	 * Public getter for the {@code Chords} wrapper {@code class}.
	 * 
	 * @return {@code Chords} the wrapped {@code Chord} objects from the saved {@code xml} file.
	 */
	public Chords getWrapperChordList() {
		return chordList;
	}

	private ObservableList<Chord> chordsForTableView = FXCollections.observableArrayList();

	/**
	 * public getter for observablelist Chords.
	 * @return {@code ObservableList<Chord>}
	 */
	public ObservableList<Chord> getChordsForTableView() {
		return chordsForTableView;
	}

	/**
	 * The constructor of the class.
	 */
	public Main() {
		// add the base notes to a list
		baseNoteList.add(new Note(1, "C"));
		baseNoteList.add(new Note(2, "C#"));
		baseNoteList.add(new Note(3, "D"));
		baseNoteList.add(new Note(4, "D#"));
		baseNoteList.add(new Note(5, "E"));
		baseNoteList.add(new Note(6, "F"));
		baseNoteList.add(new Note(7, "F#"));
		baseNoteList.add(new Note(8, "G"));
		baseNoteList.add(new Note(9, "G#"));
		baseNoteList.add(new Note(10, "A"));
		baseNoteList.add(new Note(11, "A#"));
		baseNoteList.add(new Note(12, "B"));
		baseNoteList.add(new Note(13, "C"));
		baseNoteList.add(new Note(14, "C#"));
		baseNoteList.add(new Note(15, "D"));
		baseNoteList.add(new Note(16, "D#"));
		baseNoteList.add(new Note(17, "E"));
		baseNoteList.add(new Note(18, "F"));
		baseNoteList.add(new Note(19, "F#"));
		baseNoteList.add(new Note(20, "G"));
		baseNoteList.add(new Note(21, "G#"));
		baseNoteList.add(new Note(22, "A"));
		baseNoteList.add(new Note(23, "A#"));
		baseNoteList.add(new Note(24, "B"));
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 * 			The main stage.
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Returns the base notes list.
	 * 
	 * @return {@code List<Note>} the base notes list
	 */
	public List<Note> getBaseNoteList() {
		return baseNoteList;
	}

	/**
	 * Method for launching the application.
	 * 
	 * @param args
	 *            the command-line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		createRootView();
		createGuitarNeckView(null, 0, 0);
		createBottomNoteButtonsView(null, null);
	}

	/**
	 * Creates the root view of the application.
	 */
	public void createRootView() {

		FXMLLoader loader = new FXMLLoader();

		try {
			loader.setLocation(getClass().getResource("view/RootView.fxml"));
			this.primaryStage.setTitle("Guitar Learning Application");
			rootView = (BorderPane) loader.load();

			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/Menus.fxml"));

			MenuBar menuBar = (MenuBar) loader.load();
			MenusController controller = loader.getController();
			controller.setMainApp(this);
			rootView.setTop(menuBar);

			Image image = new Image(getClass().getResourceAsStream("/images/MainGuitarIcon.png"));
			this.primaryStage.getIcons().add(image);

			clearLeft();
			clearRight();

			this.primaryStage.setScene(new Scene(rootView));
			this.primaryStage.show();
			this.primaryStage.setResizable(false);

			logger.info("init - Root View has been created.");

		} catch (IOException e) {
			logger.error(e.getMessage() + " - " + e.getStackTrace());
		}
	}

	/**
	 * Creates the guitar neck's view in the middle of the root view.
	 * 
	 * @param readyChord the chord that is ready to showed
	 * @param lowerFretTreshold the lower fret's number
	 * @param upperFretTreshold the upper fret's number
	 */
	public void createGuitarNeckView(Chord readyChord, int lowerFretTreshold, int upperFretTreshold) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("view/GuitarNeckView.fxml"));

		try {
			if(rootView != null) {
				AnchorPane guitarNeckView = (AnchorPane) loader.load();

				rootView.setCenter(guitarNeckView);

				GuitarNeckViewController controller = loader.getController();
				controller.setMainApp(this);
				controller.setChord(readyChord);
				controller.setGridPane(gridPane);
				controller.showGuitarNeckView(controller.getChord(), lowerFretTreshold, upperFretTreshold);
				if (controller.getGridPane() != null) {
					rootView.setCenter(controller.getGridPane());
				}

				logger.info("init - GuitarNeck View has been created.");
			}

		} catch (IOException e) {
			logger.error(e.getMessage() + " - " + e.getStackTrace());
		}
	}

	/**
	 * Creates the guitar neck's view at the bottom of the root view.
	 * 
	 * @param selectedNoteName the selected Chord's base Note's name
	 * @param selectedChordType the selected Chord's type
	 */
	public void createBottomNoteButtonsView(String selectedNoteName, String selectedChordType) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("view/BottomNoteButtonsView.fxml"));

		try {
			SplitPane bottomNoteButtonsView = (SplitPane) loader.load();

			rootView.setBottom(bottomNoteButtonsView);

			BottomNoteButtonsViewController controller = loader.getController();
			controller.setMainApp(this);
			
			if(selectedNoteName != null) {
				controller.setSelectedNoteName(selectedNoteName);
			}
			if(selectedChordType != null) {
				controller.setSelectedChordType(selectedChordType);
			}
			if(controller.getSelectedNoteName() != null && controller.getSelectedChordType() != null) {
				controller.createMyChordButtonAction(new ActionEvent());
				controller.postInitialize();
			}

			logger.info("init - BottomNoteButtons View has been created.");

		} catch (IOException e) {
			logger.error(e.getMessage() + " - " + e.getStackTrace());
		}
	}

	/**
	 * creates the saved chords view as a tableview.
	 * 
	 * @throws IOException ecxeption when cant find files
	 */
	public void createSavedChordsView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("view/SavedChordsView.fxml"));

		AnchorPane savedChordsView = (AnchorPane) loader.load();

		rootView.setCenter(savedChordsView);

		clearBottom();

		SavedChordsViewController controller = loader.getController();
		controller.setMain(this);

		logger.info("init - SavedChords View has been created.");
	}

	/**
	 * Loads chords from the specified file.
	 * 
	 * @param file the file
	 */
	public void loadChordsFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(Chords.class);
			Unmarshaller um = context.createUnmarshaller();
			Chords wrapper = (Chords) um.unmarshal(file);
			chordsForTableView.clear();
			chordsForTableView = FXCollections.observableArrayList(wrapper.getChords());
			
			Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        marshaller.marshal(wrapper, System.out);

		} catch (JAXBException | IllegalArgumentException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data!");
			alert.setContentText("Could not load data from file:\n" + file.getPath());
			alert.showAndWait();
		}
	}

	/**
	 * Saves the chords to the specified file.
	 * 
	 * @param file the file
	 * @param chords the chords
	 */
	public void saveChordToFile(File file, Chords chords) {
		try {
			JAXBContext context = JAXBContext.newInstance(Chords.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(chords, System.out);
			m.marshal(chords, file);
			
		} catch (JAXBException | IllegalArgumentException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data!");
			alert.setContentText("Could not save data to file:\n" + file.getPath());
			alert.showAndWait();
		}
	}

	private void clearRight() {
		HBox hbox = new HBox();

		VBox vbox = new VBox(0);
		vbox.setPadding(new Insets(0, 0, 0, 0));
		vbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(new Separator(Orientation.VERTICAL), vbox);

		rootView.setRight(hbox);
	}

	private void clearLeft() {
		HBox hbox = new HBox();

		VBox vbox = new VBox(0);
		vbox.setPadding(new Insets(0, 0, 0, 0));
		vbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(new Separator(Orientation.VERTICAL), vbox);

		rootView.setLeft(hbox);
	}

	private void clearBottom() {
		VBox vbox = new VBox();

		HBox hbox = new HBox(0);
		hbox.setPadding(new Insets(0, 0, 0, 0));
		hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(new Separator(Orientation.HORIZONTAL), hbox);

		rootView.setRight(vbox);
	}
}
