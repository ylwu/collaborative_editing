package client;

import java.io.IOException;

/**
 * listener for client to get update
 * 
 */
public class UpdateListener extends Thread {

	private final Client client;

	public UpdateListener(Client c) {
		client = c;
	}

	public void run() {
		while (true) {
			try {
				client.getUpdates();
			} catch (IOException e) {
				System.out.println("client/updatelistener/run: exception");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("client/updatelistener/run: exception");
				e.printStackTrace();
			}
		}
	}

}
