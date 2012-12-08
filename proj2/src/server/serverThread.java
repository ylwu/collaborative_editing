/**
 * 
 */
package server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;

import FileSystem.EventPackage;
import FileSystem.FileSystem;

/**
 * @author gyz
 *
 */
public class serverThread extends Thread{

	/**
	 * @param server
	 * @param socket
	 * @param fileSystem
	 */
	
    private final Socket socket;
    private final Server server;
    private FileSystem fileSystem;
    public ObjectOutputStream toClient;
    public ObjectInputStream fromClient;
    
    
    public serverThread(Server server, Socket socket, FileSystem c) {
        this.socket = socket;
        this.server = server;
        this.fileSystem=c;
    }
    
    public Socket getSocket(){
        return socket;
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
            try {
                handleConnection(socket);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void initialize(Socket socket) throws IOException {

        try {
            toClient = new ObjectOutputStream(socket.getOutputStream());
            toClient.writeObject(fileSystem);
            toClient.flush();
            fromClient = new ObjectInputStream(socket.getInputStream());

        } 
        catch (Exception e){
        	e.printStackTrace();
        }


    }
    
    private void updateServer(EventPackage eventPackage) throws BadLocationException{
        AbstractDocument d = server.fileSystem.getFile().get(eventPackage.docNum).getDoc();
        if (eventPackage.eventType.equals("INSERT")) {
            d.insertString(eventPackage.offset, eventPackage.inserted,
                    new SimpleAttributeSet());
        } else if (eventPackage.eventType.equals("REMOVE")) {

            d.remove(eventPackage.offset, eventPackage.len);
        }
        System.out.println(d.getText(0, d.getLength()));
    }
    
    private void updateClient(EventPackage eventPackage) throws Exception{
        for (serverThread t:server.threadlist){
            System.out.println(server.threadlist.size());
            if (!this.equals(t)){
            	System.out.println("update client start");
              t.toClient.writeObject(eventPackage);
              t.toClient.flush();
              System.out.println("update client end");}
        }
    }
    
    private void handleConnection(Socket socket) throws IOException, Exception {
        while (true) {
            Object o = fromClient.readObject();
            if (o instanceof EventPackage){
            EventPackage eventPackage = (EventPackage) fromClient.readObject();
            System.out.println("received update from client");
            updateServer(eventPackage);
            updateClient(eventPackage);
            } else if (o instanceof File){
                server.fileSystem.addFile((File) o);
                System.out.println("received file from client");
            }

        }
    }

}
