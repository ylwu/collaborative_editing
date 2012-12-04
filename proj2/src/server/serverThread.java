/**
 * 
 */
package server;

import gui.GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;



import controller.Controller;

/**
 * @author gyz
 *
 */
public class serverThread extends Thread{

	/**
	 * @param server
	 * @param socket
	 * @param controller
	 */
	
    private final Socket socket;
    private final Server server;
    private Controller controller;
    private ObjectOutputStream toClient;
    private ObjectInputStream fromClient;
    
    
    public serverThread(Server server, Socket socket, Controller c) {
        this.socket = socket;
        this.server = server;
        this.controller=c;
    }
    
    /**
     * Run the server, listening for client connections and handling them.  
     * Never returns unless an exception is thrown.
     * @throws IOException if the main server socket is broken
     * (IOExceptions from individual clients do *not* terminate serve()).
     */
    //thread object need to have "run" method
    public void run(){

        try {
            initialize(socket);
            handleConnection(socket);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void initialize(Socket socket) throws IOException {

        try {
            toClient = new ObjectOutputStream(socket.getOutputStream());
            toClient.writeObject(controller);
            toClient.flush();

        } 
        catch (Exception e){
        	e.printStackTrace();
        }


    }
    
    private void handleConnection(Socket socket) throws IOException{
            while(true){
                try {
                    AbstractDocument d = (AbstractDocument) fromClient.readObject();
                    System.out.println("received update from client");
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
    }
    


}
