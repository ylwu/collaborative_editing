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
	public static void main(final String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		final String ip=args[0];
		System.out.println("connecting to server");
		final Socket socket=new Socket(ip,4441);
		System.out.println("connected to server");
//		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        
//        String decodedString;
//        decodedString = in.readLine();
//        //in.close();
//        System.out.println(decodedString);
		
		
		InputStream is = socket.getInputStream();  
		ObjectInputStream ois = new ObjectInputStream(is); 
		final Controller controller = (Controller)ois.readObject();
		System.out.println("got controller");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//testing purpose
	            System.out.println("creating new gui");
			    new GUI(controller);

			}
		});
		
		
	}
}
