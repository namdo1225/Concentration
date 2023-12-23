/*
 * File name: CardRenderer.java
 * Student: Nam Do
 * Class: CSc335, Fall 2022
 * Assignment: A1, Concentration
 * 
 * Class Description: The CardRenderer class contains all graphic
 * components needed to draw the entire card deck. It handles player 
 * input when they click on the cards and sends the appropriate 
 * information to be processed.
 */
package frontend;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class CardRenderer implements ActionListener {
	
	private static List<ImageIcon> allCards = new ArrayList<ImageIcon>();
	private List<JCheckBox> cards = new ArrayList<JCheckBox>();
	
	private static ImageIcon unknownIcon;
	
	private JPanel panel = new JPanel();
		
	private int cardIndex = -1;
	private int firstIndex = -1;
	private boolean firstSelected = false;
	private boolean secondSelected = false;
		
	/*
	 * Constructor for the CardRenderer class.
	 * 
	 * Param: amount, an int representing the amount of cards the player
	 *        selected.
	 */
	public CardRenderer(int amount) {
		panel.setBounds(290, 10, 890, 740);    
		panel.setBackground(Color.white);
        panel.setLayout(null);  
		
		for (int i = 0; i < amount * 2; i++) {
			cards.add(new JCheckBox(unknownIcon, false));
			cards.get(i).setBounds(10 + (75 * (i % 12)), 20 + (75 * (i / 12)), 50, 50);
			panel.add(cards.get(i));
		}
		setEvents();
	}
	
	/*
	 * Method to load all cards and their images to prepare for a game session.
	 */
	public static void initializeAllCards() {
		unknownIcon = new ImageIcon("cardsImage/card_000.png");
		Image unknownImage = unknownIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		unknownIcon = new ImageIcon(unknownImage);
		
		int imagesCount = new File("cardsImage").listFiles().length - 1;
				
		for (int i = 1; i < imagesCount; i++) {
			ImageIcon icon = new ImageIcon(String.format("cardsImage/card_%s.png",
										String.format("%03d", i)));

			Image image = icon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(image);
						
			allCards.add(icon);
		}
	}
	
	/*
	 * Method to set an event for all the cards in a game session so that
	 * the cards could detect player's mouse input and take action as needed.
	 */
	private void setEvents() {
		for (int i = 0; i < cards.size(); i++)
			cards.get(i).addActionListener(this);
	}
	
	/*
	 * Method to handle the game flow after the player clicks on a card.
	 * 
	 * event, an ActionEvent object that helps keep track of the player's movement.
	 */
    public void actionPerformed(ActionEvent event) {
    	if (firstSelected && secondSelected)
    		return;
    	
    	JCheckBox card = (JCheckBox) event.getSource();
    	int index = cards.indexOf(card);
		if (!firstSelected) {
			cardIndex = firstIndex = index;
			firstSelected = true;
		} else if (firstSelected && firstIndex != index && !secondSelected) {
			if (cardIndex != -1)
				return;
			
			cardIndex = index;
			secondSelected = true;
		}
    }
 
    
	/*
	 * Method to return the JPanel that contains graphic components representing
	 * a canvas with all the cards the player can select.
	 * 
	 * Return: panel, a JPanel with all cards and their icons that can be displayed
	 *         on the interface.
	 */
	public JPanel getIcons() { return panel; }

    /*
     * Method to update the graphics after other classes have processed the player's
     * input and updated the game's state.
     * 
     * Param: instruction, a String representing a command like "disable" to reveal
     *        two cards that have been correctly matched and can no longer be selected.
     *        card1, an int representing the first card index of the current game's deck.
     *        card2, an int representing the second card index of the current game's deck.
     *        allCardInd1, an int pointing to what card image card1 is supposed to be.
     *        allCardInd1, an int pointing to what card image card2 is supposed to be.
     */
	public void receiveInstruction(String instruction, int card1, int card2, int allCardInd1, int allCardInd2) {
		if (instruction.equals("disable")) {
			firstSelected = secondSelected = false;
			firstIndex = -1;
			cards.get(card1).setIcon(allCards.get(allCardInd1));
			cards.get(card2).setIcon(allCards.get(allCardInd2));
		
			cards.get(card1).setSelected(false);
			cards.get(card2).setSelected(false);
			cards.get(card1).setEnabled(false);
			cards.get(card2).setEnabled(false);
		} else if (instruction.equals("show"))
			cards.get(card1).setIcon(allCards.get(allCardInd1));
		  else if (instruction.equals("hide"))
			cards.get(card2).setIcon(allCards.get(allCardInd2));
	}
	
	/*
	 * Method to hide all non-matched cards after the player either have
	 * chosen the wrong cards or their time ran out.
	 */
    public void hideAllCards() {
		firstSelected = false; secondSelected = false; firstIndex = -1;
		for (int i = 0; i < cards.size(); i++)
			if (cards.get(i).isEnabled()) {
				cards.get(i).setSelected(false);
				cards.get(i).setIcon(unknownIcon);
			}
    }
	
	/*
	 * Returns a card index of the card the player has chosen.
	 * 
	 * Return: tempIndex, an int representing this index.
	 */
	public int getCardIndex() {
		int tempIndex = cardIndex;
		cardIndex = -1;
		return tempIndex;
	}
	
	/*
	 * Returns the amount of all card images (except the graphic that 
	 * represent hidden card).
	 * 
	 * Return: allCards.size(), an int representing this amount.
	 */
	public static int numberOfAllCards() {
		return allCards.size();
	}
}
