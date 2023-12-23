/*
 * File name: CardCollection.java
 * Student: Nam Do
 * Class: CSc335, Fall 2022
 * Assignment: A1, Concentration
 * 
 * Class Description: The CardCollection class represents the entire
 * card deck. It creates and loads card icons and helps keep track 
 * of which card has been revealed or not.
 */
package backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardCollection {
	private int cardAmount; private int pairsLeft;
	private String deck;
	
	private List<Integer> cardPos = new ArrayList<Integer>();
	
	private int card1 = -1; private int card2 = -1;
	
	private String instruction;
	private int instrCard1; private int instrCard2;
	private int instrAllCardInd1; private int instrAllCardInd2;
	
	/*
	 * Constructor for the CardCollection class.
	 * 
	 * Param: collection, a String representing deck theme the player
	 *        has selected.
	 *        totalCards, an int containing the total of card images
	 *        available for the game.
	 *        amount, an int representing the amount of cards the player
	 *        selected.
	 */
	public CardCollection(String collection, int totalCards, int amount) {
		deck = collection; cardAmount = amount; pairsLeft = amount;
		
		int minCard = 0;
		if (deck.equals("fruits"))
			minCard = 18;
		else if (deck.equals("haunted"))
			minCard = 36;
		
		if (!deck.equals("custom"))
			for (int i = minCard; i < (cardAmount * 2) + minCard; i++)
				cardPos.add((i % cardAmount) + minCard);
		else {
			for (int i = 0; i < totalCards; i++)
				cardPos.add(i);
			
	        Collections.shuffle(cardPos);
			for (int i = 0; i < (totalCards - cardAmount); i++)
				cardPos.remove(0);
				
			int size = cardPos.size();
			for (int i = 0; i < size; i++)
				cardPos.add(cardPos.get(i));
		}
		
        Collections.shuffle(cardPos);
	}
	
	/*
	 * Method to handle the game flow after the player clicks on a card.
	 * 
	 * Param: cardIndex, an int representing the card the player selected.
	 */
    public void processCardSelected(int cardIndex) {
    	if (cardIndex == -1)
    		return;
    	
		// select the first card
    	if (card1 == -1) {
			card1 = instrCard1 = cardIndex;
			instrAllCardInd1 = cardPos.get(card1);
			instrCard2 = instrAllCardInd2 = -1;
			instruction = "show";
		}
		// select card2 if card1 is chosen and card1 != card2.
		else if (card1 != -1 && cardIndex != card1 && card2 == -1) {
			card2 = cardIndex;

			// if player chooses the right pair
			if (card2 != -1 && cardPos.get(card1) == cardPos.get(card2)) {
				pairsLeft--;
				instruction = (pairsLeft == 0) ? "end" : "disable";
				instrCard1 = card1; instrCard2 = card2;
				instrAllCardInd1 = cardPos.get(card1); instrAllCardInd2 = cardPos.get(card2);
			}

			// if player chooses the wrong pair
			else if (card2 != -1 && cardPos.get(card1) != cardPos.get(card2)) {
				instruction = "hide";
				instrCard2 = card2;
				instrAllCardInd2 = cardPos.get(card2);
				card1 = card2 = instrCard1 = instrAllCardInd1 = -1;
			}
		}
    }
    
    /*
     * Method to reset some variables so that the class object knows that
     * the player currently has no cards selected.
     */
    public void resetCardTurn() { card1 = card2 = -1; }
    
    /*
     * Method to send an instruction so the game state could be updated on the UI
     * side.
     * 
     * Return: A String representing the entire instruction is returned.
     * Template of string: 
     * 		<instruction>|<instruCard1>|<instruCard2>|<instrAllCardInd1>|<instrallCardInd2>
     * 
     * 		where instruction could be "show", "disable", "end", or "hide" and other variables
     * 		are int representing the first and second card indices and the index of the
     * 		images they're pointing to.
     */
    public String sendInstruction() {
    	return String.format("%s|%d|%d|%d|%d", instruction, instrCard1, instrCard2, instrAllCardInd1, instrAllCardInd2);
    }
}
