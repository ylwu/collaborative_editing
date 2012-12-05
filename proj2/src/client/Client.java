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
import javax.swing.text.AbstractDocument;
import javax.swing.text.AbstractDocument.DefaultDocumentEvent;
import javax.swing.text.Document;

import model.EventPackage;
import model.Model;

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
    
    public void updateServer(EventPackage eventPackage) throws IOException{
        toServer.writeObject(eventPackage);
        toServer.flush();
        System.out.println("sent update to server");
    }
    
    public void updateRemoval(EventPackage eventPackage) throws IOException{
        toServer.writeObject(eventPackage);
        toServer.flush();
        System.out.println("sent removal update to server");
    }
    
    public void getUpdates(){
        try {
            DefaultDocumentEvent event = (DefaultDocumentEvent)fromServer.readObject();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
    }
    
    public void initialize() throws UnknownHostException, IOException, ClassNotFoundException{
        System.out.println("connecting to server");
        socket=new Socket(ip,4441);
        System.out.println("connected to server");
        toServer = new ObjectOutputStream(socket.getOutputStream());
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
