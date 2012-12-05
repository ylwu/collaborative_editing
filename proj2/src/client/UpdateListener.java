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
    	System.out.println("create listener thread");
    	client=c;
    }
	
    public void run(){
    	while (true){
    	System.out.println("here");
    	client.getUpdates();}
    }

}
