package client;

import javax.swing.text.BadLocationException;

/**
 * @author gyz
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
