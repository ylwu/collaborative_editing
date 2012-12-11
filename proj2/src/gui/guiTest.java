package gui;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;

import FileSystem.FileSystem;

import client.Client;



/**
 * Testing Strategy for GUI Test
 * 
 * (1) Test JButton "create new file"
 *     check that a new GUI window will pop up with a correct
 *     update on the document numbers
 * (2) Test JTextField "Edit History"
 *     check that each line in the edit history corresponds to
 *     its respective edit event
 * (3) Usability Testing
 *     make sure that all the interactive JComponents (i.e., drop-
 *     down box, editable text field, Jbutton, etc) correctly listen
 *     and respond to action events from the client side
 * 
 * 
*/


public class guiTest{
	public static void main(final String[] args) {
		final String ip="localhost";
		final Client c = new Client(ip);
		final FileSystem f=new FileSystem();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
			    new GUI(c);

			}
		});
		
		
	}
}

