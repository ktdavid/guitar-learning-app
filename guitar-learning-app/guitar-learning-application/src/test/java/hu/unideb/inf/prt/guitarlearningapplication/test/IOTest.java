package hu.unideb.inf.prt.guitarlearningapplication.test;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.BeforeClass;
import org.junit.Test;

import hu.unideb.inf.prt.guitarlearningapplication.Main;
import hu.unideb.inf.prt.guitarlearningapplication.controller.ChordCreatorController;
import hu.unideb.inf.prt.guitarlearningapplication.controller.IOController;

public class IOTest {

	private static ChordCreatorController chordCreatorController;
	private static IOController IOcontroller;
	private static Main main = new Main();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		IOcontroller = new IOController();
		IOcontroller.setMainApp(main);
		chordCreatorController = new ChordCreatorController();
		chordCreatorController.setMainApp(main);
	}
	
	@Test
	public void testLoadChordsXmlFile() {
				
		try {
			IOcontroller.loadChordsFromFile(new File(getClass().getResource("/chords.xml").toURI()));
		} catch (URISyntaxException e) {
			fail("Resource named chords.xml cannot be opened!");
		}
	}
	
	@Test(expected = NullPointerException.class)
	public void testLoadNotExistingXmlFile() {
		
		try {
			IOcontroller.loadChordsFromFile(new File(getClass().getResource("/chords123.xml").toURI()));
		} catch (URISyntaxException e) {
			fail("Resource named chords123.xml cannot be opened!");
		}
	}
}
