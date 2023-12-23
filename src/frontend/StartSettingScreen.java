/*
 * File name: StartSettingScreen.java
 * Student: Nam Do
 * Class: CSc335, Fall 2022
 * Assignment: A1, Concentration
 * 
 * Class Description: This class inherits the Screen class and represents the
 * the screen where the player can configure the game they will play. It has 
 * all the drawing components needed to populate the interface.
 * 
 * Notice: Please go to Screen.java for descriptions of the methods that do not
 * have function comments.
 */
package frontend;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;

public class StartSettingScreen extends Screen implements ActionListener {
	private JButton start = new JButton("Start Game");
	private JButton exit = new JButton("Go back to Title Screen");
    
	// index 0 = player header, 1 = seconds header, 2 = deck header, 3 = mode header
	private JLabel[] headerTexts = new JLabel[4];
	private JLabel amountText = new JLabel("Amounts of cards:");
	
	private JSlider slidePlayers = new JSlider(JSlider.HORIZONTAL, 2, 10, 2);
	private JSlider slideSeconds = new JSlider(JSlider.HORIZONTAL, 10, 300, 15);
	private JSlider slideCards = new JSlider(JSlider.HORIZONTAL, 4, 108, 36);
	
	// index 0 = drink deck, 1 = fruit, 2 = haunted, 3 = custom
	private JRadioButton[] decksButton = new JRadioButton[4];
	private ButtonGroup buttons = new ButtonGroup();
	
	private JCheckBox randomSecond = new JCheckBox("Random seconds per turn (10 - 60 seconds)");
	private JCheckBox randomTurn = new JCheckBox("Randomize player turn");
	private JCheckBox noMatchTurn = new JCheckBox("No extra turn for matching cards");
	
	private JTextArea instruction = new JTextArea("Please select the amount of players, the "
			+ "seconds a player (this can be randomized), a deck theme, and if desired,"
			+ " a variation of the game.");  
	
	private int finalPlayer; private int finalSecond;
	private String deckTheme = "drinks"; private int finalAmount;
	private boolean finalRandomTurn = false; 
	private boolean finalNoExtraTurn = false; 	
	
	/*
	 * Constructor for the StartSettingScreen class.
	 */
	public StartSettingScreen() {	    
        configureComponents();
        setEvents();
        addPanel();
	}
	
	protected void addPanel() {
		Component comps[] = new Component[]{instruction, start, exit, slidePlayers,
				slideSeconds, amountText, slideCards, randomSecond, randomTurn, noMatchTurn};
		for (Component comp : comps)
			panel.add(comp);
		for (int i = 0; i < 4; i++) {
			panel.add(headerTexts[i]);
			panel.add(decksButton[i]);
		}
	}
	
	protected void configureComponents() {
		setSliders();
		
	    instruction.setBounds(50, 10, 1190, 80);
		instruction.setFont(new Font("Arial", Font.PLAIN, 18));
	    
		start.setBounds(750, 600, 100, 50);
		exit.setBounds(250, 600, 200, 50);

		randomSecond.setBounds(200, 200, 500, 100);
		randomSecond.setOpaque(false);
		randomSecond.addActionListener(this);

		for (int i = 0; i < 4; i++) {
			headerTexts[i] = new JLabel();
			headerTexts[i].setBounds(100, 120 + (i * 110), 100, 50);
			headerTexts[i].setFont(new Font("Arial", Font.PLAIN, 18));
			
			decksButton[i] = new JRadioButton();
			decksButton[i].setBackground(Color.white);
			decksButton[i].addActionListener(this);
			decksButton[i].setBounds(250 + (i * 230), 320, 140, 40);
			buttons.add(decksButton[i]);
			
		}
		headerTexts[0].setText("Player:"); headerTexts[1].setText("Second:");
		headerTexts[2].setText("Deck:"); headerTexts[3].setText("Mode:");

		decksButton[0].setSelected(true); 
		decksButton[0].setText("Drinks"); decksButton[1].setText("Fruits"); 
		decksButton[2].setText("Haunted"); decksButton[3].setText("Custom");

		amountText.setBounds(200, 360, 150, 20);
		amountText.setFont(new Font("Arial", Font.PLAIN, 18));
		
		randomTurn.setBounds(180, 520, 250, 20);
		randomTurn.setFont(new Font("Arial", Font.PLAIN, 18));
		randomTurn.addActionListener(this); randomTurn.setOpaque(false);

		noMatchTurn.setBounds(480, 520, 300, 20);
		noMatchTurn.setFont(new Font("Arial", Font.PLAIN, 18));
		noMatchTurn.addActionListener(this); noMatchTurn.setOpaque(false);
		
		amountText.setVisible(false);
	}

	/*
	 * A method to configure the drawable sliders components
	 * for the screen.
	 */
	private void setSliders() {
		slidePlayers.setBounds(200, 100, 200, 100);
		slidePlayers.setBackground(Color.white);
		slidePlayers.setPaintTicks(true); slidePlayers.setPaintLabels(true);
		slidePlayers.setMinorTickSpacing(0); slidePlayers.setMajorTickSpacing(1);
		
		slideSeconds.setBounds(500, 200, 500, 100);
		slideSeconds.setBackground(Color.white);
		slideSeconds.setPaintTicks(true); slideSeconds.setPaintLabels(true);
		slideSeconds.setMinorTickSpacing(25); slideSeconds.setMajorTickSpacing(50);
		
		slideCards.setBounds(200, 380, 400, 100);
		slideCards.setBackground(Color.white);
		slideCards.setPaintTicks(true); slideCards.setPaintLabels(true);
		slideCards.setMinorTickSpacing(6); slideCards.setMajorTickSpacing(12);
		slideCards.setVisible(false);
	}
	
	protected void setEvents() {
		start.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent event){
	    			finalPlayer = slidePlayers.getValue();
	    			finalSecond = slideSeconds.getValue();
	    			
	    			if (randomSecond.isSelected())
	    				finalSecond = -1;
	
	    			finalRandomTurn = randomTurn.isSelected(); 
	    			finalNoExtraTurn = noMatchTurn.isSelected();
	    			finalAmount = (deckTheme != "custom") ? 18 : slideCards.getValue() / 2;
	    			
	    			screenOutput = String.format("%d|%d|%s|%d|%b|%b", finalPlayer, finalSecond,
	    					deckTheme, finalAmount, finalRandomTurn, finalNoExtraTurn);
	    	        }
	    	    });  	
		
		exit.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent event){
	    			screenOutput = "2";
	    	        }
	    	    });
		}
	
	/*
	 * Method to handle the game flow after the player clicks on a card.
	 * 
	 * event, an ActionEvent obj that helps keep track of the player's movement.
	 */
	public void actionPerformed(ActionEvent event){		
		slideSeconds.setVisible(!randomSecond.isSelected());
		
		if (decksButton[0].isSelected())
			deckTheme = "drinks";
		else if (decksButton[1].isSelected())
			deckTheme = "fruits";
		else if (decksButton[2].isSelected())
			deckTheme = "haunted";
		else if (decksButton[3].isSelected()) {
			deckTheme = "custom";
			amountText.setVisible(true);
			slideCards.setVisible(true);
		}
		
		if (!decksButton[3].isSelected()) {
			amountText.setVisible(false);
			slideCards.setVisible(false);
		}
	}
}
