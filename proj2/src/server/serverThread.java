/**
 * 
 */
package server;

import gui.GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.SwingUtilities;



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
    private final Controller controller;
    
    public serverThread(Server server, Socket socket, Controller c) {
        this.socket = socket;
        this.server=server;
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
            handleConnection(socket);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void handleConnection(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        try {
            server.increaseNumPlayers();
            out.println("Welcome to edictor.  "+server.getNumPlayers()+ " people are editing inc" +
            		"luding you.  ");
            
            
            SwingUtilities.invokeLater(new Runnable() {
    			public void run() {
    				//testing purpose
    	            System.out.println("creating new gui");
    				GUI gui=new GUI(controller);
    				

    			}
    		});
            for (String line =in.readLine(); line!=null; line=in.readLine()) {}
            //testing purpose
            System.out.println("should not be here");
        } 
        catch (Exception e){
        	e.printStackTrace();
        }
        finally {  
        	
        	//testing purpose
        	System.out.println("connection closed");
            out.close();
            in.close();
            server.decreaseNumPlayers();
        }
    }
    


}
