/*
 * File name: PlayerRenderer.java
 * Student: Nam Do
 * Class: CSc335, Fall 2022
 * Assignment: A1, Concentration
 * 
 * Class Description: The PlayerRenderer class's purpose is to render all the UI
 * elements related to player information.
 */
package frontend;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerRenderer {
	private JPanel panel = new JPanel();
	private JLabel currentPlayer = new JLabel();
	private JLabel secondsDraw = new JLabel();
	
	private List<JLabel> players = new ArrayList<JLabel>();
	private List<JLabel> scoreLabels = new ArrayList<JLabel>();
	
	/*
	 * Constructor for the PlayerRenderer class.
	 * 
	 * Param: amountOfPlayers, an int representing the amount of players
	 *        who will play the game.
	 */
	public PlayerRenderer(int amountOfPlayers) {
		secondsDraw.setBounds(150, 50, 60, 20);
		secondsDraw.setFont(new Font("Arial", Font.PLAIN, 18));
		secondsDraw.setBackground(Color.white);
		
		currentPlayer.setBounds(150, 10, 40, 20);
		currentPlayer.setFont(new Font("Arial", Font.PLAIN, 18));
		currentPlayer.setText("P1");
		
        panel.setLayout(null);  
		panel.setBounds(10, 120, 260, 550);    
		panel.setBackground(Color.white);
				
		for (int i = 0; i < amountOfPlayers; i++) {
			players.add(new JLabel("P" + (i + 1)));
			players.get(i).setBounds(10, 10 + (40 * i), 40, 20);
			players.get(i).setFont(new Font("Arial", Font.PLAIN, 18));
			
			scoreLabels.add(new JLabel("0"));
			scoreLabels.get(i).setBounds(160, 10 + (40 * i), 40, 20);
			scoreLabels.get(i).setFont(new Font("Arial", Font.PLAIN, 18));

			panel.add(players.get(i));
			panel.add(scoreLabels.get(i));
		}
	}
	
	/*
	 * This method updates the drawable component to keep tab of the seconds left
	 * in the game.
	 * 
	 * Param: seconds, a double representing the seconds left in the game.
	 */
	public void setCountdown(double seconds) { secondsDraw.setText(String.format("%.0f", seconds/1000)); }
	
	/*
	 * This method updates a drawable component that keeps track of the players' score.
	 * 
	 * Param: player, an int representing the player who will have their score
	 *        updated.
	 *        score: an int that will be set as the player's current score.
	 */
	public void setPlayersAndScores(int player, int score) { 
		scoreLabels.get(player - 1).setText(String.valueOf(score));
	}
	
	/*
	 * This method updates a drawable component that displays whose player's turn it is.
	 * 
	 * Param: player, an int representing the player who will be set as the current player.
	 * 
	 */
	public void setCurrentPlayer(int player) { currentPlayer.setText("P" + String.valueOf(player)); }
	
	/*
	 * This method returns a JLabel of the countdown so that it can be drawn.
	 * 
	 * Return: secondsDraw, a JLabel representing the countdown.
	 */
	public JLabel getCountdown() { return secondsDraw; }
	
	/*
	 * This method returns a JPanel of all players' information and scores.
	 * 
	 * Return: panel, a JPanel representing all players' information 
	 * and scores.
	 */
	public JPanel getPlayersAndScores( ) { return panel; }
	
	/*
	 * This method returns a JLabel of the current player.
	 * 
	 * Return: currentPlayer, a JLabel representing the current player.
	 */
	public JLabel getCurrentPlayer( ) { return currentPlayer; }
}
