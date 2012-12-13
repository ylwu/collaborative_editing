package client;

import javax.swing.text.BadLocationException;

/**
 * A special thread responsible for transmitting the updates
 * from the server (more precisely, the state change of the 
 * fileSystem in the server) to the client. 
 *
 */
public class UpdateListener extends Thread{

	/**
	 * @param client
	 */
	private final Client client;
    public UpdateListener(Client c) {
    	client=c;
    }
	
    public void run(){
    	while (true){
    	try {
            client.getUpdates();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }}
    }

}
