package hu.unideb.inf.prt.guitarlearningapplication.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.prt.guitarlearningapplication.Main;
import hu.unideb.inf.prt.guitarlearningapplication.model.Chord;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

/**
 * Controller class for the SavedChordsView.fxml.
 * 
 * @author Dávid
 */
public class SavedChordsViewController {

	/**
	 * A logger object used for logging.
	 */
	private Logger logger = LoggerFactory.getLogger(SavedChordsViewController.class);

	@FXML
	private TableView<Chord> chordTable;

	@FXML
	private TableColumn<Chord, String> baseNoteColumn;
	@FXML
	private TableColumn<Chord, String> chordTypeColumn;

	private Main main;

	/**
	 * Constructor without parameters.
	 */
	public SavedChordsViewController() {
	}

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

					//main.createGuitarNeckView(new Chord(), 1, 3);
				}
			}
		});
	}

	/**
	 * public setter for main application.
	 * 
	 * @param main the main application
	 */
	public void setMain(Main main) {
		this.main = main;

		chordTable.setItems(main.getChordsForTableView());
	}
}
