/**
 * 
 */
package client;

import gui.GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;

import controller.Controller;

/**
 * @author gyz
 *
 */
public class Client {
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;
    private static Controller controller;
    private static String ip;
    private Socket socket;
    
    public Client(){
        
    }
    
    public void initialize() throws UnknownHostException, IOException, ClassNotFoundException{
        System.out.println("connecting to server");
        socket=new Socket(ip,4441);
        System.out.println("connected to server");       
        fromServer = new ObjectInputStream(socket.getInputStream()); 
        controller = (Controller)fromServer.readObject();
        System.out.println("got controller");
    }
    
    
	public static void main(final String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		ip=args[0];
		final Client c = new Client();
		c.initialize();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//testing purpose
	            System.out.println("creating new gui");
			    new GUI(controller,c);

			}
		});
		
		
	}
}
