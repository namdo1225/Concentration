/*
 * File name: Screen.java
 * Student: Nam Do
 * Class: CSc335, Fall 2022
 * Assignment: A1, Concentration
 * 
 * Class Description: This abstract class is the base class from where the
 * other the UI derived classes will be created.
 * 
 */
package frontend;

import java.awt.Color;

import javax.swing.JPanel;

public abstract class Screen {
	protected JPanel panel;
	protected String screenOutput = "0";	
	
	/* Constructor for the Screen class */
	public Screen() {
		panel = new JPanel();
        panel.setLayout(null);  

		panel.setBounds(0,0,1200,800);    
		panel.setBackground(Color.white);
	}
		
	/* Method to add any drawing components like a text box to a panel
	 * that collects all of these components to be drawn all at once.
	 */
	abstract protected void addPanel();
	
	/*
	 * Method to configure the individual drawing components.
	 */
	abstract protected void configureComponents();
	
	/*
	 * Method to configure player events for some drawing components such
	 * as players clicking on a button.
	 */
	abstract protected void setEvents();
	
	/*
	 * This method allows the derived screen classes to send a code back to
	 * the Game class so the latter can figure out the flow of the game.
	 * 
	 * Return: screenOutput, an int to represent the current status of the screen:
	 * 
	 * 1 = finished
	 * 0 = needs completion
	 * 2 = quit, return to previous screen/title.
	 * 
	 */
	public String statusCode() { return screenOutput; }
	
	/*
	 * Method to return the panel to be drawn.
	 * 
	 * Return: panel, a JPanel containing drawing components needed to populate a screen.
	 */
	public JPanel get() { return panel; }
	
	/*
	 * Method to help send the derived screen classes a message in order
	 * to aid the flow of the game.
	 * 
	 * Param: msg, a String that represents the message.
	 */
	public void setMessage(String msg) { screenOutput = msg; }
}