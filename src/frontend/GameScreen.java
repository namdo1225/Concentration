/*
 * File name: GameScreen.java
 * Student: Nam Do
 * Class: CSc335, Fall 2022
 * Assignment: A1, Concentration
 * 
 * Class Description: This class inherits the Screen class and represents the
 * game screen where the players will play the game itself. It has all the 
 * drawing components needed to populate the interface.
 * 
 * Notice: Please go to Screen.java for descriptions of the methods that do not
 * have function comments.
 */
package frontend;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class GameScreen extends Screen {

	private JLabel currentPlayerText = new JLabel("Current Player:");
	private JLabel timerText = new JLabel("Seconds left:");
	private JLabel playerText = new JLabel("Players:");
	private JLabel scoreText = new JLabel("Scores:");
	
	private JButton end = new JButton("End Current Game");
	
	/*
	 * Constructor for the GameScreen class.
	 */
	public GameScreen() {
		configureComponents();
		setEvents();
		addPanel();
	}
	
	protected void addPanel() {
		Component comps[] = new Component[]{currentPlayerText, timerText, playerText, scoreText, end};
		for (Component comp : comps)
			panel.add(comp);
	}
	
	protected void configureComponents() {
		panel.setOpaque(false);
		currentPlayerText.setBounds(10, 10, 130, 20);
		currentPlayerText.setFont(new Font("Arial", Font.PLAIN, 18));
		
		timerText.setBounds(10, 50, 200, 20);
	    timerText.setFont(new Font("Arial", Font.PLAIN, 18));
	    
	    playerText.setBounds(10, 90, 100, 20);
	    playerText.setFont(new Font("Arial", Font.PLAIN, 18));
	    
	    scoreText.setBounds(140, 90, 100, 20);
	    scoreText.setFont(new Font("Arial", Font.PLAIN, 18));
		
		end.setBounds(120, 720, 150, 20);
	}
	
	protected void setEvents() {	
		end.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){
	    			screenOutput = "3";
	    	        }
	    	    }); 
	}
	
}
