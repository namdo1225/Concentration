/*
 * File name: Concentration_JUnitTest.java
 * Student: Nam Do
 * Class: CSc335, Fall 2022
 * Assignment: A1, Concentration
 * 
 * Class Description: JUnit testcases to test any classes
 * that do not rely heavily on graphics.
 * 
 */
package testcases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import backend.CardCollection;
import backend.PlayerGroup;

class Concentration_JUnitTest {

	// test PlayerGroup class
	@Test
	/*
	 * Check the PlayerGroup class' addScore() method
	 * adds score for players correctly.
	 * 
	 * It also checks the getCurrentScore() as well by
	 * calling on it return the scores.
	 */
	void testAddScore() {
		PlayerGroup pl = new PlayerGroup(5);
		
		// P1
		pl.nextTurn();
		pl.addScore();
		pl.addScore();
		pl.addScore();
		
		assertEquals(pl.getCurrentScore(), 3);
		
		// P3
		pl.nextTurn();
		pl.nextTurn();
		assertEquals(pl.getCurrentScore(), 0);
	
		// P1
		pl.nextTurn();
		pl.nextTurn();
		pl.nextTurn();
		pl.addScore();
		assertEquals(pl.getCurrentScore(), 4);
	}

	@Test
	/*
	 * Check the PlayerGroup class' getWinnerAndScore()
	 * method to make sure it returns the correct winner
	 * and score.
	 */
	void testGetWinnerAndScore() {
		PlayerGroup pl = new PlayerGroup(5);

		// P3
		pl.nextTurn();
		pl.nextTurn();
		pl.addScore();
		pl.addScore();
		pl.addScore();
		pl.addScore();
		pl.addScore();
		pl.addScore();

		// P4
		pl.nextTurn();
		pl.addScore();
		
		// P5
		pl.nextTurn();
		pl.addScore();
		pl.addScore();
		pl.addScore();
		pl.addScore();
		pl.addScore();
		pl.addScore();

		// P1
		pl.nextTurn();
		pl.addScore();
		
		String result = pl.getWinnerAndScore();
		
		assertTrue(result.contains("Winner(s): "));
		assertTrue(result.contains("6"));
		assertTrue(result.contains("3"));
		assertTrue(result.contains("5"));
		assertTrue(!result.contains("2"));

	}

	@Test
	/*
	 * Check the PlayerGroup class' getCurrentPlayer()
	 * method to make sure it returns the correct player
	 * who has a turn.
	 * 
	 * It also checks the nextTurn() method by changing
	 * player turns for the test as well.
	 */
	void testGetCurrentPlayer() {
		PlayerGroup pl = new PlayerGroup(5);
		assertEquals(pl.getCurrentPlayer(), 1);
		
		pl.nextTurn();
		pl.nextTurn();
		assertEquals(pl.getCurrentPlayer(), 3);
		
		pl.nextTurn();
		pl.nextTurn();
		assertEquals(pl.getCurrentPlayer(), 5);
		
		pl.nextTurn();
		assertEquals(pl.getCurrentPlayer(), 1);		}

	@Test
	/**
	 * Test CardCollection class. Clearly, I can't test out specific
	 * conditions when card orders are randomized. But being able to
	 * see the printed message is helpful as well.
	 */
	void testSendInstruction() {
		CardCollection cards = new CardCollection("drinks", 66, 36);
	
		cards.processCardSelected(0);
		assertEquals(cards.sendInstruction().contains("show|"), true);
		
		cards.processCardSelected(1);
		assertEquals(cards.sendInstruction().contains("hide|"), true);

	}
	
}
