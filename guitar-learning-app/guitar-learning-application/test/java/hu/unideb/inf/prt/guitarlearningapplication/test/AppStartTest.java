package hu.unideb.inf.prt.guitarlearningapplication.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import hu.unideb.inf.prt.guitarlearningapplication.Main;
import hu.unideb.inf.prt.guitarlearningapplication.model.Chord;
import hu.unideb.inf.prt.guitarlearningapplication.model.Note;
import hu.unideb.inf.prt.guitarlearningapplication.view.BottomNoteButtonsViewController;
import javafx.event.ActionEvent;

public class AppStartTest {

	private static BottomNoteButtonsViewController controller;
	private static Main main;
	
	public AppStartTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
			    
	    controller = new BottomNoteButtonsViewController();
	    main = new Main();
	}
	
	@Test
	public void testCreateChord() {
		controller.setMainApp(main);
		controller.setSelectedNoteName("C");
		controller.setSelectedChordType("MAJOR");
		controller.createMyChordButtonAction(new ActionEvent());
		assertNotNull(controller.getReadyChord());
		Chord readychord = controller.getReadyChord();
		assertNotNull(readychord);
		assertEquals("C", readychord.getName());
		assertEquals("MAJOR", readychord.getChordType().toString());
	}
	
	@Test
	public void testNotesInChord() {
		controller.setMainApp(main);
		controller.setSelectedNoteName("C");
		controller.setSelectedChordType("MAJOR");
		controller.createMyChordButtonAction(new ActionEvent());
		assertNotNull(controller.getReadyChord());
		
		Chord readychord = controller.getReadyChord();
		assertNotNull(readychord);
		
		List<Note> actualNoteArray = readychord.getNotes();
		List<Note> expectedNoteArray = Arrays.asList(new Note(1, "C"), new Note(5, "E"), new Note(8, "G"));
		
		for (Note expectedNote : expectedNoteArray) {
			if(actualNoteArray.stream()
					.noneMatch(actualNote -> actualNote.getName().equals(expectedNote.getName())
							&& actualNote.getPosition() == expectedNote.getPosition())) {
				fail("Tested Notes name or position does not match!");
			}
		}
	}
	
	@Test
	public void testNumberParserWithEmptyString() {
		int result = controller.parseAndFormatNumber("");
		assertEquals("Result for empty String expected 0.", 0, result);
	}
	
	@Test
	public void testNumberParserWithZeroValuedString() {
		int result = controller.parseAndFormatNumber("0");
		assertEquals("Result for String: \"0\" expected 0.", 0, result);
	}
	
	@Test
	public void testNumberParserWithPositiveNumber() {
		int result = controller.parseAndFormatNumber("12");
		assertEquals(12, result);
	}
	
	@Test
	public void testNumberParserWithNegativeNumber() {
		int result = controller.parseAndFormatNumber("-1");
		assertEquals(-1, result);
	}
	
	@Test
	public void testNumberParserWithText() {
		int result = controller.parseAndFormatNumber("abc");
		assertEquals("Result for String: \"abc\" expected 0, because NumberFormatException is handled in controller class.", 0, result);
	}
	
	@Test
	public void testChordsXMLFile() {
		File file = new File("chords.xml");
		assertNotNull("chords.xml is not found", file);
	}
}
