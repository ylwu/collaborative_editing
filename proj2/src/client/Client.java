/**
 * 
 */
package client;

import gui.GUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;

import FileSystem.EventPackage;
import FileSystem.File;
import FileSystem.FileSystem;

/**
 * @author gyz
 *
 */
public class Client {
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;
    private static FileSystem fileSystem;
    private static String ip;
    private Socket socket;
    public EventPackage incomingPackage;
    
    public Client(){
        incomingPackage = new EventPackage();
    }
    
    public void updateServer(EventPackage eventPackage) throws IOException {
        if (!incomingPackage.equals(eventPackage)) {
            toServer.writeObject(eventPackage);
            toServer.flush();
            System.out.println("sent update to server");
        }
    }
    
    // change here
    public void getUpdates() throws BadLocationException{
        try {
            EventPackage eventPackage = (EventPackage)fromServer.readObject();
            incomingPackage = eventPackage;
            System.out.println("received!");
            File f= fileSystem.getModels().get(eventPackage.docNum);
            f.updateDoc(eventPackage);
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
    
    
	public static void main(final String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		ip=args[0];
		final Client c = new Client();
		c.initialize();
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (UnsupportedLookAndFeelException e) {
		    // handle exception
		} catch (ClassNotFoundException e) {
		    // handle exception
		} catch (InstantiationException e) {
		    // handle exception
		} catch (IllegalAccessException e) {
		    // handle exception
		}

		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//testing purpose
	            System.out.println("creating new gui");
			    new GUI(fileSystem,c);

			}
		});
	
		
	}
}
