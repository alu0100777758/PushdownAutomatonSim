package es.ull.etsit.gradoIngenieriaInformatica.complejidad.pushdownautomaton.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import es.ull.etsit.gradoIngenieriaInformatica.complejidad.pushdownautomaton.PushdownAlphabet;

public class PushdownStructuresTest {

	@Test
	public void AlphabetShortConstructor() {
		PushdownAlphabet aplhabet = new PushdownAlphabet("\\b");
		assertNotNull(aplhabet);
	}
	@Test
	public void AlphabetLongConstructor() {
		PushdownAlphabet aplhabet = new PushdownAlphabet("\\b", new String[]{"a", "bb", "heyTest", "-+"});
		assertNotNull(aplhabet);
	}
	
	@Test
	public void AlphabetCorrectlyInitialized() {
		String [] inString = new String[]{"a", "bb", "heyTest", "-+"};
		String inWhiteStringh = "\\b";
		PushdownAlphabet alphabet = new PushdownAlphabet(inWhiteStringh, inString );
		assertTrue("Both data are initialized", alphabet.getEmptySymbol() == inWhiteStringh && alphabet.getInputAlphabet().toArray() == inString);
	}
	

}
