package client;

import gui.GUI;

import javax.swing.SwingUtilities;

/**
 * 
 * Testing Strategy for Client
 * 
 * A key part for our test is to verify that any client, given a correct combination
 * of port number and IP address, will be able to connect to server. Since our GUI 
 * has been written such that it will be displayed only after the client has 
 * successfully connected to the server, we seek to test two aspects for the client
 * side. Specifically, 
 * 
 * (1) test for valid server connection given the correct (ip, port) combination
 * (2) test for correct initiation of the GUI associated with this client. 
 */

// after opening the server, we run the following main method to check if the client
// has successfully connected to server and initiated the GUI. 
public class clientTest{
	public static void main(final String[] args) {
		final String ip = "localhost";
		final Integer port = 6666;
		final Client c = new Client(ip, port);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
			    new GUI(c);
			}
		});		
	}
}