package hu.unideb.inf.prt.guitarlearningapplication.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import hu.unideb.inf.prt.guitarlearningapplication.Main;
import hu.unideb.inf.prt.guitarlearningapplication.controller.ChordCreatorController;
import hu.unideb.inf.prt.guitarlearningapplication.model.Chord;
import hu.unideb.inf.prt.guitarlearningapplication.model.Note;

public class ChordControllerTest {

	private static ChordCreatorController chordCreatorController;
	private static Main main = new Main();
	private Chord readyChord = null;
	
	public ChordControllerTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {

		chordCreatorController = new ChordCreatorController();
		chordCreatorController.setMainApp(main);
	}
	
	@Test
	public void testCreateChord() {
		readyChord = chordCreatorController.createChord(readyChord, "C", "MAJOR", 1, 3, 4, 3);
		assertNotNull(readyChord);
		assertEquals("C", readyChord.getName());
		assertEquals("MAJOR", readyChord.getChordType().toString());
	}
	
	@Test
	public void testNotesInChord() {
		readyChord = chordCreatorController.createChord(readyChord, "C", "MAJOR", 1, 3, 4, 3);
		assertNotNull(readyChord);
		
		List<Note> actualNoteArray = readyChord.getNotes();
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
		int result = chordCreatorController.parseAndFormatNumber("");
		assertEquals("Result for empty String expected 0.", 0, result);
	}
	
	@Test
	public void testNumberParserWithZeroValuedString() {
		int result = chordCreatorController.parseAndFormatNumber("0");
		assertEquals("Result for String: \"0\" expected 0.", 0, result);
	}
	
	@Test
	public void testNumberParserWithPositiveNumber() {
		int result = chordCreatorController.parseAndFormatNumber("12");
		assertEquals(12, result);
	}
	
	@Test
	public void testNumberParserWithNegativeNumber() {
		int result = chordCreatorController.parseAndFormatNumber("-1");
		assertEquals(-1, result);
	}
	
	@Test
	public void testNumberParserWithText() {
		int result = chordCreatorController.parseAndFormatNumber("abc");
		assertEquals("Result for String: \"abc\" expected 0, because NumberFormatException is handled in controller class.", 0, result);
	}
}
