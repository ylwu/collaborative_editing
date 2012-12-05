/**
 * 
 */
package client;

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
    	client.getUpdates();
    }

}
