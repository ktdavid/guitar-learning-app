package hu.unideb.inf.prt.guitarlearningapplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.prt.guitarlearningapplication.model.Chord;
import hu.unideb.inf.prt.guitarlearningapplication.model.Note;
import hu.unideb.inf.prt.guitarlearningapplication.view.BottomNoteButtonsViewController;
import hu.unideb.inf.prt.guitarlearningapplication.view.GuitarNeckViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Class for the main program.
 * 
 * @author Dávid Kistamás
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
	
	private GridPane gridPane;
	
	/**
	 * The list for all base notes.
	 */
	private List<Note> baseNoteList = new ArrayList<Note>();
	
	private List<Chord> chordList = new ArrayList<Chord>();
	
	/**
	 * The constructor.
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
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	/**
	 * Returns the base notes list.
	 * 
	 * @return 
	 */
	public List<Note> getBaseNoteList() {
		return baseNoteList;
	}
	
	private List<Chord> getChords() {
		return chordList;
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
		createBottomNoteButtonsView();
	}

	/**
	 * Creates the root view of the application.
	 */
	private void createRootView() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/RootView.fxml"));
		
		try {
			this.primaryStage.setTitle("Guitar Learning Application");
			rootView = (BorderPane) loader.load();
			
			Image image = new Image(Main.class.getResourceAsStream("/images/MainGuitarIcon.png"));
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

	/**
	 * Creates the guitar neck's view in the middle of the root view.
	 */
	public void createGuitarNeckView(Chord readyChord, int lowerFretTreshold, int upperFretTreshold) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/GuitarNeckView.fxml"));
		
		try {
			AnchorPane guitarNeckView = (AnchorPane) loader.load();
			
			rootView.setCenter(guitarNeckView);
			
			GuitarNeckViewController controller = loader.getController();
			controller.setMainApp(this);
			controller.setChord(readyChord);
			controller.setGridPane(gridPane);
			controller.showGuitarNeckView(controller.getChord(), lowerFretTreshold, upperFretTreshold);
			if(controller.getGridPane() != null) {
				rootView.setCenter(controller.getGridPane());
			}
			chordList.add(controller.getChord());

			logger.info("init - GuitarNeck View has been created.");
		
		} catch (IOException e) {
			logger.error(e.getMessage() + " - " + e.getStackTrace());
		}
	}
	
	/**
	 * Creates the guitar neck's view at the bottom of the root view.
	 */
	private void createBottomNoteButtonsView() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/BottomNoteButtonsView.fxml"));
		
		try {	
			SplitPane bottomNoteButtonsView = (SplitPane) loader.load();
			
			rootView.setBottom(bottomNoteButtonsView);
			
			BottomNoteButtonsViewController controller = loader.getController();
			controller.setMainApp(this);

			logger.info("init - BottomNoteButtons View has been created.");
		
		} catch (IOException e) {
			logger.error(e.getMessage() + " - " + e.getStackTrace());
		}
	}
	
	/**
	 * Returns the person file preference, i.e. the file that was last opened.
	 * The preference is read from the OS specific registry. If no such
	 * preference can be found, null is returned.
	 * 
	 * @return
	 */
	public File getPersonFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(Main.class);
	    String filePath = prefs.get("filePath", null);
	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	        return null;
	    }
	}

	/**
	 * Sets the file path of the currently loaded file. The path is persisted in
	 * the OS specific registry.
	 * 
	 * @param file the file or null to remove the path
	 */
	public void setPersonFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(Main.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());

	        // Update the stage title.
	        this.primaryStage.setTitle("AddressApp - " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Update the stage title.
	        this.primaryStage.setTitle("AddressApp");
	    }
	}
	
	/**
	 * Loads person data from the specified file. The current person data will
	 * be replaced.
	 * 
	 * @param file
	 */
//	public void loadPersonDataFromFile(File file) {
//	    try {
//	        JAXBContext context = JAXBContext
//	                .newInstance(ChordListWrapper.class);
//	        Unmarshaller um = context.createUnmarshaller();
//
//	        // Reading XML from the file and unmarshalling.
//	        //ChordListWrapper wrapper = (ChordListWrapper) um.unmarshal(file);
//
//	        chordList.clear();
//	        //chordList.addAll(wrapper.getChords());
//
//	        // Save the file path to the registry.
//	        setPersonFilePath(file);
//
//	    } catch (Exception e) { // catches ANY exception
//	        Alert alert = new Alert(AlertType.ERROR);
//	        alert.setTitle("Error");
//	        alert.setHeaderText("Could not load data");
//	        alert.setContentText("Could not load data from file:\n" + file.getPath());
//
//	        alert.showAndWait();
//	    }
//	}

	/**
	 * Saves the current person data to the specified file.
	 * 
	 * @param file
	 */
	public void savePersonDataToFile(File file, Chord chord) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(Chord.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Marshalling and saving XML to the file.
	        m.marshal(chord, file);

	        // Save the file path to the registry.
	        //setPersonFilePath(file);
	    } catch (Exception e) { // catches ANY exception
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not save data");
	        alert.setContentText("Could not save data to file:\n" + file.getPath());

	        alert.showAndWait();
	    }
	}
}
