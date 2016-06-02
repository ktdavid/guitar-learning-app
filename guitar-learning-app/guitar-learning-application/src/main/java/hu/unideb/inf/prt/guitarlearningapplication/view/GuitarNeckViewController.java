package hu.unideb.inf.prt.guitarlearningapplication.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.prt.guitarlearningapplication.Main;
import hu.unideb.inf.prt.guitarlearningapplication.model.Chord;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Class for the controller of the GuitarNeckView.fxml.
 * 
 * @author Dávid Kistamás
 */
public class GuitarNeckViewController {

	/**
	 * A logger object used for logging.
	 */
	private Logger logger = LoggerFactory.getLogger(GuitarNeckViewController.class);

	/**
	 * The guitar's neck image view.
	 */
	@FXML
	private ImageView imageView;

	private Chord chord;

	private GridPane gridPane;

	private List<List<String>> notesDisplay = new ArrayList<List<String>>();

	private List<List<Integer>> activePositions = new ArrayList<List<Integer>>();

	/**
	 * Reference to the main application.
	 */
	private Main main;

	/**
	 * The constructor that is called before the initialize() method.
	 */
	public GuitarNeckViewController() {
		notesDisplay.add(Arrays.asList("F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E"));
		notesDisplay.add(Arrays.asList("C", "C#", "D", "D#", "E", "F2", "F#", "G", "G#", "A", "A#", "B"));
		notesDisplay.add(Arrays.asList("G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G"));
		notesDisplay.add(Arrays.asList("D#", "E", "F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D"));
		notesDisplay.add(Arrays.asList("A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A"));
		notesDisplay.add(Arrays.asList("F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E"));

		for (int i = 0; i < 6; i++) {
			activePositions.add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
		}
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		Image image = new Image(getClass().getResourceAsStream("/images/GuitarNeckUpgraded.png"));
		imageView.setImage(image);
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param main the main application
	 */
	public void setMainApp(Main main) {
		this.main = main;
	}

	/**
	 * ShowGuitarNeckView method.
	 * 
	 * @param chord the chord
	 * @param lowerFretTreshold the lower fret's number
	 * @param upperFretTreshold the upper fret's number
	 */
	public void showGuitarNeckView(Chord chord, int lowerFretTreshold, int upperFretTreshold) {
		if (chord != null && (lowerFretTreshold != 0 || upperFretTreshold != 0)) {
			showNotes(chord, lowerFretTreshold, upperFretTreshold);
		} else {
			Image image = new Image(getClass().getResourceAsStream("/images/GuitarNeckUpgraded.png"));
			imageView.setImage(image);
			logger.info("showGuitarNeckView method was called without a Chord!");
		}
	}

	private void showNotes(Chord chord, int lowerFretTreshold, int upperFretTreshold) {
		gridPane = new GridPane();

		for (int i = 0; i < notesDisplay.get(0).size(); i++) {
			ColumnConstraints colConstraints = new ColumnConstraints();
			colConstraints.setPrefWidth(60.0);
			colConstraints.setHgrow(Priority.SOMETIMES);
			gridPane.getColumnConstraints().add(colConstraints);
		}

		for (int i = 0; i < notesDisplay.size(); i++) {
			RowConstraints rowConstraints = new RowConstraints();
			rowConstraints.setVgrow(Priority.SOMETIMES);
			rowConstraints.setPrefHeight(40.0);
			gridPane.getRowConstraints().add(rowConstraints);
		}

		setDisplayPoints(chord, lowerFretTreshold, upperFretTreshold);

		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 6; j++) {
				addPane(i, j);
			}
		}

		gridPane.setStyle("-fx-background-image: url('/images/GuitarNeckUpgraded.png');");
	}

	private void setDisplayPoints(Chord chord, int lowerFretTreshold, int upperFretTreshold) {
		AtomicInteger rowCounter = new AtomicInteger(0), noteCounter = new AtomicInteger(0);

		chord.getNotes().stream().forEach(chordNote -> {
			rowCounter.set(0);
			notesDisplay.stream().forEach(noteRow -> {
				noteCounter.set(0);
				noteRow.stream().forEach(note -> {
					if (note.equals(chordNote.getName())
							&& isBetween(noteCounter.get(), lowerFretTreshold, upperFretTreshold)) {
						activePositions.get(rowCounter.get()).set(noteCounter.get(), Integer.valueOf(1));
					}
					noteCounter.incrementAndGet();
				});
				rowCounter.incrementAndGet();
			});
		});
	}

	private boolean isBetween(int counter, int first, int second) {
		if (counter >= first - 1 && counter <= second - 1) {
			return true;
		} else {
			return false;
		}
	}

	private void addPane(int colIndex, int rowIndex) {
		rePaint(gridPane, colIndex, rowIndex);
		gridPane.setVgap(0);
		gridPane.setHgap(0);
	}

	private void rePaint(GridPane gridPane, int i, int j) {
		if (activePositions.get(j).get(i) == 1) {
			Label lblChordName = new Label();
			lblChordName.setText(notesDisplay.get(j).get(i));
			lblChordName.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
			lblChordName.setTextFill(Color.web("#FFFFFF"));
			Circle circle = new Circle();
			circle.setRadius(20);
			GridPane.setHalignment(circle, HPos.CENTER);
			GridPane.setHalignment(lblChordName, HPos.CENTER);
			circle.setFill(Paint.valueOf("RED"));
			gridPane.setAlignment(Pos.CENTER);
			gridPane.add(circle, i, j);
			gridPane.add(lblChordName, i, j);
		}
	}

	/**
	 * public setter for Chord.
	 * 
	 * @param chord the chord
	 */
	public void setChord(Chord chord) {
		this.chord = chord;
	}

	/**
	 * public getter for Chord.
	 * 
	 * @return Chord
	 */
	public Chord getChord() {
		return chord;
	}

	/**
	 * public setter for the GridPane.
	 * 
	 * @param gridPane the GridPane
	 */
	public void setGridPane(GridPane gridPane) {
		this.gridPane = gridPane;
	}

	/**
	 * public getter for GridPane.
	 * 
	 * @return GridPane
	 */
	public GridPane getGridPane() {
		return gridPane;
	}
}
