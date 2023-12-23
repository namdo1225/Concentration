/*
 * File name: PlayerGroup.java
 * Student: Nam Do
 * Class: CSc335, Fall 2022
 * Assignment: A1, Concentration
 * 
 * Class Description: The PlayerGroup class represents the players as a whole.
 * It keeps track of the current player's turn, get the next turn, their scores. 
 * It also decides on the winner.
 */
package backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PlayerGroup {
	private int playersAmount;
	
	// if there's 4 players, the first player is 0. The last is 3.
	private int playerTurn = 0;
	
	private List<Integer> scores = new ArrayList<Integer>();
    private Random randomPlayer = new Random();
	
    /*
     * Constructor for the PlayerGroup class.
     */
	public PlayerGroup(int amountOfPlayers) {
		playersAmount = amountOfPlayers;
		
		for (int i = 0; i < amountOfPlayers; i++)
			scores.add(0);
	}
	
	/*
	 * This method increment a player's score.
	 */
	public void addScore() { scores.set(playerTurn, scores.get(playerTurn) + 1); }
		
	/*
	 * This method returns the winners(s) and the winning score.
	 * 
	 * Return: returnString, a String representing all the winners and
	 *         the winning score. It also has a message asking the player if
	 *         they want to play again.
	 */
	public String getWinnerAndScore() {
		int maxScore = Collections.max(scores);
		String returnString = String.format("Winner(s): %d pts. Players: ", maxScore);
		
		for (int i = 0; i < scores.size() - 1; i++)
			if (maxScore == scores.get(i))
				returnString += String.format("%d, ", i + 1);
		if (maxScore == scores.get(scores.size() - 1))
			returnString += String.format("%d.", scores.size());
		
		return returnString;
	}

	/*
	 * This method sets the next player's turn in sequential order.
	 */
	public void nextTurn() { playerTurn = (playerTurn + 1) % playersAmount; }
	
	/*
	 * This method sets the next player's turn randomly.
	 */
	public void randomTurn() { playerTurn = randomPlayer.nextInt(playersAmount); }
	
	/*
	 * This method returns the player number of the player who has a turn.
	 * 
	 * Return: playerTurn + 1, an int representing the player number as seen be the
	 * 		   player.
	 */
	public int getCurrentPlayer() { return playerTurn + 1; }
	
	/*
	 * This method returns the current score of the player who has a turn.
	 * 
	 * Return: scores.get(playerTurn), an int representing the current score of a
	 *         player.
	 */
	public int getCurrentScore() { return scores.get(playerTurn); }
}
