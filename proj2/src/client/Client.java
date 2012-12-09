/**
 * 
 */
package client;

import gui.GUI;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;

import FileSystem.EventPackage;
import FileSystem.FilePackage;
import FileSystem.FileSystem;
import FileSystem.MyFile;

/**
 * @author gyz
 *
 */
public class Client {
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;
<<<<<<< HEAD
    public  FileSystem fileSystem;
    private static String ip;
=======
    public static FileSystem fileSystem;
    public static String ip;
>>>>>>> sz2
    private Socket socket;
    public EventPackage incomingPackage;
    
    public Client(){
        incomingPackage = new EventPackage();
    }
    public Client(String ipAddress){
    	incomingPackage = new EventPackage();
    	ip = ipAddress;
    }
    
    public void updateServer(EventPackage eventPackage) throws IOException {
        if (!incomingPackage.equals(eventPackage)) {
            toServer.writeObject(eventPackage);
            toServer.flush();
            System.out.println("sent update to server");
        }
    }
   
    public void createNewFileOnServer() throws IOException{
        toServer.writeObject(("new file"));
        toServer.flush();
        System.out.println("create new file on server");
        
    }
    
    public void deleteFileOnServer(int docNum) throws IOException{
        toServer.writeObject(("delete"+docNum));
        toServer.flush();
        System.out.println("create new file on server");
        
    }
    
    public void uploadFiletoServer(File file, String content) throws IOException{
        toServer.writeObject(new FilePackage(file, content));
        toServer.flush();
        System.out.println("upload file to server");
    }
    
    // change here
    public void getUpdates() throws BadLocationException{
        try {
        	Object o =fromServer.readObject();
        	if (o instanceof EventPackage){
            EventPackage eventPackage = (EventPackage)o;
            incomingPackage = eventPackage;
            System.out.println("received!");
            MyFile f= fileSystem.getFile().get(eventPackage.docNum);
            f.updateDoc(eventPackage);}
        	else if (o instanceof FilePackage){
        		fileSystem.addFile((FilePackage) o);
        		System.out.println("received non-empty document");
        	} else if (o instanceof String){
        		String str=(String) o;
        		if (str.equals("new file")){
        	    fileSystem.addEmptyFile();
        	    System.out.println("received empty document");}
        		else if (str.substring(0, 5).equals("delete")){
        			int docNum=Integer.parseInt(str.substring(6));
        			fileSystem.deleteDoc(docNum);
        		}
        	  
        	}
        	System.out.println("There are " + fileSystem.files.size() + "files in this client");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void initialize() throws UnknownHostException, IOException, ClassNotFoundException{
        System.out.println("connecting to server");
        socket=new Socket(ip,4441);
        System.out.println("connected to server");
        toServer = new ObjectOutputStream(socket.getOutputStream());
        fromServer = new ObjectInputStream(socket.getInputStream()); 
        
        fileSystem = (FileSystem)fromServer.readObject();
        System.out.println("got controller");
    }
    
    
	
}
