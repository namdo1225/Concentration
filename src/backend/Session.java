/*
 * File name: Session.java
 * Student: Nam Do
 * Class: CSc335, Fall 2022
 * Assignment: A1, Concentration
 * 
 * Class Description: This class represents a single Concentration game session.
 * It helps keep tracks of the timer, set what happen when player clicks on a 
 * card, and switch player's turn.
 */
package backend;

import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import frontend.CardRenderer;
import frontend.PlayerRenderer;

public class Session {
	private int secondsPerTurn; private double secondsLeft;
	private double startTime = -1; private double endTime = -1; private double differenceTime;
	private Random randSec = new Random();
		
	private boolean sessionFinished = false;
	
	private boolean randomPlayerTurn;
	private boolean noExtraTurnMatch;
	
    private PlayerRenderer playerRenderer;
	private PlayerGroup players;
	
	private CardCollection cards;
	private CardRenderer cardRenderer;
	
	/*
	 * Constructor for the Session class.
	 * 
	 * Param: commands, a String with the information the player adjusted
	 *                  to configure their own game. Individual game settings
	 *                  are separated by '|'.
	 */
	public Session(String commands) {
		String[] commandArray = commands.split("\\|");
		
		players = new PlayerGroup(Integer.parseInt(commandArray[0]));
		secondsPerTurn = Integer.parseInt(commandArray[1]);
		secondsLeft = (double)Integer.parseInt(commandArray[1]) * 1000;
		playerRenderer = new PlayerRenderer(Integer.parseInt(commandArray[0]));
		
		int amountCardSession = Integer.parseInt(commandArray[3]);
		
		cardRenderer = new CardRenderer(amountCardSession);
		cards = new CardCollection(commandArray[2], CardRenderer.numberOfAllCards(), amountCardSession);

		randomPlayerTurn = (commandArray[4].equals("true")) ? true : false;
		noExtraTurnMatch = (commandArray[5].equals("true")) ? true : false;
		
		if (randomPlayerTurn)
			players.randomTurn();
		
		if (secondsPerTurn == -1)
			secondsLeft = (randSec.nextInt(50) + 10) * 1000;
	}
	
	/*
	 * This method changes/progresses the state of the game depending on the
	 * player's action. For example, if the countdown reaches 0, the method
	 * will move to the next player's turn.
	 */
	public void turn() {
		if (secondsLeft < 0) {
			cards.resetCardTurn();
			cardRenderer.hideAllCards();			
			setNextTurn(false);
			setSecondsLeft();
		}

		int cardIndex = cardRenderer.getCardIndex();
		
		if (cardIndex == -1)
			return;
		
		cards.processCardSelected(cardIndex);
		String instruction = cards.sendInstruction();
		String[] instrArr = instruction.split("\\|");
		
		String textCommand = instrArr[0];
		int card1 = Integer.parseInt(instrArr[1]); int card2 = Integer.parseInt(instrArr[2]);
		int allCardInd1 = Integer.parseInt(instrArr[3]); int allCardInd2 = Integer.parseInt(instrArr[4]);
		if (textCommand.equals("disable")) {
			players.addScore();
			playerRenderer.setPlayersAndScores(players.getCurrentPlayer(), players.getCurrentScore());
			cardRenderer.receiveInstruction(textCommand, card1, card2, allCardInd1, allCardInd2);
			cards.resetCardTurn();
			setNextTurn(true);
			setSecondsLeft();
		} else if (textCommand.equals("show"))
			cardRenderer.receiveInstruction(textCommand, card1, card2, allCardInd1, allCardInd2);
		else if (textCommand.equals("hide")) {
			cardRenderer.receiveInstruction(textCommand, card1, card2, allCardInd1, allCardInd2);
			try {Thread.sleep(300);	} catch (InterruptedException e) {}
			cards.resetCardTurn();
			cardRenderer.hideAllCards();
			setNextTurn(false);
			setSecondsLeft();
		} else if (textCommand.equals("end"))
			sessionFinished = true;
		
		setSecondsLeft();
	}
	
	/*
	 * Method to return the panel of all cards to be drawn.
	 * 
	 * Return: cardRenderer.getIcons(), a JPanel of all card image icons and their states
	 *         (whether they are flipped or not).
	 */
	public JPanel getCards() { return cardRenderer.getIcons(); }
	
	/*
	 * Method to update the countdown of the current player's turn and return it.
	 * 
	 * Return: playerRenderer.getCountdown(), a JLabel to return the seconds (
	 *         a whole number) left in the player. It can be drawn on the game UI.
	 */
	public JLabel getCountdown() {
		if (startTime == -1)
			startTime = System.currentTimeMillis();
		else {
			endTime = System.currentTimeMillis();
			differenceTime = endTime - startTime;
			startTime = System.currentTimeMillis();
		}

		secondsLeft -= differenceTime;
		playerRenderer.setCountdown(secondsLeft);
		return playerRenderer.getCountdown();
	}

	/*
	 * Method to get a boolean representing whether the current game session is
	 * finished.
	 * 
	 * Return: sessionFinished, a boolean to see whether the current game is
	 *         completed.
	 */
	public boolean getSessionFinish() { return sessionFinished; }
	
	/*
	 * Method to return the winner(s) and their score.
	 * 
	 * Return: players.getWinnerAndScore(), a String representing the
	 *         player(s) who have won and the winning score.
	 */
	public String getWinner() { return players.getWinnerAndScore(); }

	/*
	 * Method to get the next player's turn.
	 * 
	 * Param: matchFound, a boolean representing whether the current player
	 *        has found a matching card pair.
	 */
	private void setNextTurn(boolean matchFound) {
		if (randomPlayerTurn && (noExtraTurnMatch || !matchFound))
			players.randomTurn();
		else if (noExtraTurnMatch || !matchFound)
			players.nextTurn();
		playerRenderer.setCurrentPlayer(players.getCurrentPlayer());
	}

	/*
	 * Method to set how many seconds are left in the current turn into a variable.
	 * This variable is useful for keeping track of when the player's turn end.
	 */
	private void setSecondsLeft() {
		secondsLeft = (secondsPerTurn != -1) ? secondsPerTurn * 1000 : (randSec.nextInt(50) + 10) * 1000;
	}
	
	/*
	 * Method to return the JPanel of all the players and their current scores.
	 * 
	 * Return: playerRenderer.getPlayersAndScores(), a JPanel to list all the players
	 *         and their corresponding scores.
	 */
	public JPanel getPlayersAndScores() { return playerRenderer.getPlayersAndScores(); }
	
	/*
	 * Method to return the JLabel of the current player who is supposed to play.
	 * 
	 * Return: playerRenderer.getCurrentPlayer(), a JLabel to display who the current player
	 *         should be.
	 */
	public JLabel getCurrentPlayer() { return playerRenderer.getCurrentPlayer(); }
}
