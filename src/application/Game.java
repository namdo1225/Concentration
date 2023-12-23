/*
 * File name: Game.java
 * Student: Nam Do
 * Class: CSc335, Fall 2022
 * Assignment: A1, Concentration
 * 
 * Program: This program implements the game of Concentration. Additional features
 * of this game include the ability to play the game with up to 10 people
 * while having their scores tracked, a countdown to move player's turn,
 * a few decks of cards with different themes, and the ability to play
 * the game with slightly modified rules.
 * 
 * Class Description: The Game class will start the entire program. In addition,
 * it essentially keep tracks of game flow and calls the appropriate UI to continue the game.
 * 
 * File/Command inputs: No arguments or files should be passed into this program.
 * These inputs won't be processed.
 */
package application;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import backend.Session;
import frontend.CardRenderer;
import frontend.GameScreen;
import frontend.MainScreen;
import frontend.Screen;
import frontend.StartSettingScreen;

public class Game {        
	// This ArrayList contains the UI for the game.
	// index 0 = the main title screen, 1 = setting up game screen,
	// 2 = the screen where players play
    private static Screen[] screens = new Screen[3];
    private static boolean[] screenSwitch = new boolean[3];
    
    private static JFrame game = new JFrame("Concentration"); 
    
    private static Session session;
    
	public static void main(String[] args) {	
		game.addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
                System.exit(0);
	        }
	    });
		
		game.setSize(1200,800);
		game.setLayout(null);
		game.setVisible(true);
		
		screens[0] = new MainScreen();
		screens[1] = new StartSettingScreen();
		screens[2] = new GameScreen();
		screenSwitch[0] = true;	
		CardRenderer.initializeAllCards();
		
		run();
	}
	
	/*
	 * This method is an infinite loop that draws and repaints the UI to ensure
	 * the UI and information are reactive to the player.
	 */
    private static void run() {    	
    	while (true) {
    		game.repaint();
 
    		for (int i = 0; i < screenSwitch.length; i++) {
    			if (screenSwitch[i] == true) {
    				if (i == 2) {
        				game.add(screens[i].get());
        				game.add(session.getCards());
        				game.add(session.getPlayersAndScores());
        				game.add(session.getCurrentPlayer());
        				session.turn();
    				}
    				else
    					game.add(screens[i].get());
    				checkScreen(i);
    				break;
    			}
    		}

    		try {Thread.sleep(500);} catch(InterruptedException e){}
    		
    		game.getContentPane().removeAll();
    		
    		if (screenSwitch[2] == true)
				game.add(session.getCountdown());

    	}
    }

    /*
     * This method check which screen the player is on and whether any actions
     * needed to be performed such as moving players to another screen.
     * 
     * Param: index, an int representing which screen the player is on.
     */
    private static void checkScreen(int index) {
    	switch (index) {
			case 0:
				if (screens[index].statusCode() == "1")
					screenSwitch[0] = false; screenSwitch[1] = true;
					
				break;
			case 1:
				if (screens[index].statusCode().length() >= 2) {
					screenSwitch[1] = false; screenSwitch[2] = true;
					session = new Session(screens[index].statusCode());

				} else if (screens[index].statusCode() == "2") {
					screenSwitch[0] = true; screenSwitch[1] = false;
					screens[0].setMessage("0"); screens[1].setMessage("0");
				}
					
				break;
			case 2:
				if (screens[index].statusCode() == "3" || session.getSessionFinish()) {
					screenSwitch[0] = true; screenSwitch[1] = screenSwitch[2] = false;
					screens[0].setMessage(session.getWinner()); 
					screens[1].setMessage("0"); screens[2].setMessage("0");
				}
					
				break;
			}
    }
	
}
