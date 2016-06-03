package hu.unideb.inf.prt.guitarlearningapplication.view;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.prt.guitarlearningapplication.Main;
import hu.unideb.inf.prt.guitarlearningapplication.controller.IOController;
import hu.unideb.inf.prt.guitarlearningapplication.model.Chord;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

/**
 * Controller class for the SavedChordsView.fxml.
 * 
 * @author Dávid Kistamás
 * @version 1.0
 */
public class SavedChordsViewController {

	/**
	 * A logger object used for logging events at runtime.
	 */
	private Logger logger = LoggerFactory.getLogger(SavedChordsViewController.class);

	/**
	 * An instance of the {@code IOController} class.
	 */
	private IOController IOController = new IOController();
	
	/**
	 * The {@code TableView} for the {@code Chords} to display.
	 */
	@FXML
	private TableView<Chord> chordTable;
	
	/**
	 * The {@code TableColumn} for the {@code Chord} name to display.
	 */
	@FXML
	private TableColumn<Chord, String> baseNoteColumn;
	
	/**
	 * The {@code TableColumn} for the {@code Chord} type to display.
	 */
	@FXML
	private TableColumn<Chord, String> chordTypeColumn;

	/**
	 * A reference to the main application.
	 */
	private Main main;

	/**
	 * Constructs an empty {@code SavedChordsViewController} object.
	 */
	public SavedChordsViewController() {
	}

	/**
	 * Initializes the {@code SavedChordsViewController} class. This method is automatically called
	 * after the {@code FXML} file has been loaded.
	 */
	@FXML
	private void initialize() {

		baseNoteColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		chordTypeColumn.setCellValueFactory(cellData -> cellData.getValue().chordTypeProperty().asString());

		chordTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
					logger.info(chordTable.getSelectionModel().getSelectedItem().getName() + " "
							+ chordTable.getSelectionModel().getSelectedItem().getChordType() + " | CLICKED");
					
					main.createRootView();
					
					main.createGuitarNeckView(null, 1, 3);
					
					main.createBottomNoteButtonsView(chordTable.getSelectionModel().getSelectedItem().getName(), 
							chordTable.getSelectionModel().getSelectedItem().getChordType().toString());
				}
			}
		});
	}

	/**
	 * It is called by the main application to give a reference back to itself.
	 * Also sets the chords of the {@code TableView} from the {@code ObservableList}
	 * from the main application.
	 * 
	 * @param main the main application
	 */
	public void setMainApp(Main main) {
		
		this.main = main;

		chordTable.setItems(main.getChordsForTableView());
		
		initializeController();
	}

	/**
	 * Initializes the {@code IOController} class for the {@code SavedChordsViewController} FXML controller.
	 */
	private void initializeController() {
		
		IOController.setMainApp(main);
		IOController.loadChordsFromFile(new File("chords.xml"));
	}
}
