/**
 * 
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;

import model.EventPackage;
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
            fromClient = new ObjectInputStream(socket.getInputStream());

        } 
        catch (Exception e){
        	e.printStackTrace();
        }


    }
    
    private void handleConnection(Socket socket) throws IOException{
            while(true){
                try {
                	EventPackage event = (EventPackage)fromClient.readObject();
                    System.out.println("received update from client");
                    try {
                        server.controller.getModel().getDoc().insertString(event.offset, event.inserted, new SimpleAttributeSet());
                        System.out.println(server.controller.getModel().getDoc().getText(0,100));
                    } catch (BadLocationException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //this.controller.getModel().removeUpdate(event);
                    
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
    }
    


}
