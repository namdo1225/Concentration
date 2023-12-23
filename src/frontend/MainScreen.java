/*
 * File name: MainScreen.java
 * Student: Nam Do
 * Class: CSc335, Fall 2022
 * Assignment: A1, Concentration
 * 
 * Class Description: This class inherits the Screen class and represents the
 * main title screen the player first see. It has all the drawing components 
 * needed to populate the interface.
 * 
 * Notice: Please go to Screen.java for descriptions of the methods that do not
 * have function comments.
 */
package frontend;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import java.awt.event.ActionEvent;

public class MainScreen extends Screen {
	private JButton start = new JButton("Start");
	private JButton exit = new JButton("Exit");
	private JButton update = new JButton("Update latest winner(s)");
	private JLabel title = new JLabel("Concentration");  
    private JTextArea winner = new JTextArea("Winner: No games played yet.");	
	
	private JLabel infoText = new JLabel("Icons by Icons8 (click here).");

    /*
     * Constructor for the MainScreen class.
     */
	public MainScreen() {		
		configureComponents();
		setEvents();
		addPanel();
	}
	
	protected void addPanel() {
		Component comps[] = new Component[]{title, start, exit, winner, update, infoText};
		for (Component comp : comps)
			panel.add(comp);
	}
	
	protected void configureComponents() {
		start.setBounds(530,500,100,40);

		exit.setBounds(530,600,100,40);
		exit.addActionListener((event) -> System.exit(0));		
		
		title.setBounds(430, 200, 400,120);
		title.setFont(new Font("Arial", Font.PLAIN, 48));

		winner.setBounds(100, 100, 900, 100);
		winner.setFont(new Font("Arial", Font.PLAIN, 36));
		
		update.setBounds(450, 350, 250, 40);
		update.setText("Update latest game's winner");

		infoText.setBounds(500, 420, 250, 20);
		infoText.setFont(new Font("Arial", Font.PLAIN, 12));
	}
	
	protected void setEvents() {
		start.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){
	    			screenOutput = "1";
	    	        }
	    	    });  
		
		update.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){
	    			if (!screenOutput.equals("0"))
	    				winner.setText(screenOutput);
	    			}
	    	    });
		
		infoText.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent event) {
			    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			        try {
			            desktop.browse(new URI("https://icons8.com"));
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			    }
			}

			@Override
			public void mousePressed(MouseEvent e) {				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
			
		});
	}
}
