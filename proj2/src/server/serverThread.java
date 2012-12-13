/**
 * 
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;

import FileSystem.EventPackage;
import FileSystem.FilePackage;
import FileSystem.FileSystem;
import FileSystem.FilenameChangePackage;

/**
 * The serverThread class. Each thread corresponds to a particular client
 * connected
 * 
 * Fields
 * 
 * toClient: 
 *         socket's output stream        
 * fromClient: 
 *         socket's input stream
 * fileSystem: 
 *         file system for this client 
 *         
 * Please see method specifications at the start of individual method below. 
 * 
 */
public class serverThread extends Thread {

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
		this.fileSystem = c;
	}

	public Socket getSocket() {
		return socket;
	}

	/**
	 * Run the server, listening for client connections and handling them. Never
	 * returns unless an exception is thrown.
	 * 
	 * @throws IOException
	 *             if the main server socket is broken (IOExceptions from
	 *             individual clients do *not* terminate serve()).
	 */
	// thread object need to have "run" method
	public void run() {

		try {
			initialize(socket);
			try {
				handleConnection(socket);
			} catch (Exception e) {
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

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * update server; controlled by a thread such that we update the server 
	 * in real time regarding the change made on the client side. 
	 * accordingly
	 * @param eventPackage
	 * @throws BadLocationException
	 */
	private void updateServer(EventPackage eventPackage)
			throws BadLocationException {
		AbstractDocument d = server.fileSystem.getFile()
				.get(eventPackage.docNum).getDoc();
		System.out.println("Server is updating doc #" + eventPackage.docNum);
		if (eventPackage.eventType.equals("INSERT")) {
			d.insertString(eventPackage.offset, eventPackage.inserted,
					new SimpleAttributeSet());
		} else if (eventPackage.eventType.equals("REMOVE")) {

			d.remove(eventPackage.offset, eventPackage.len);
		}
	}

	private void updateClient(EventPackage eventPackage) throws Exception {
	    System.out.println("Server is updating cleint with doc#"+ eventPackage.docNum);
		for (serverThread t : server.threadlist) {
			System.out.println(server.threadlist.size());
			if (!this.equals(t)) {
				t.toClient.writeObject(eventPackage);
				t.toClient.flush();
			}
		}
	}

	/** send a FilePackage to all the clients connected to server
	 * @param fp the file package to be sent
	 * @throws IOException
	 */
	private void updateClient(FilePackage fp) throws IOException {
		for (serverThread t : server.threadlist) {
			t.toClient.writeObject(fp);
			t.toClient.flush();
		}

	}

	/**
	 * send a FilenameChangePackage to all the clients connected to server
	 * @param f the FilenameChangePackage to be sent
	 * @throws IOException
	 */
	private void updateClient(FilenameChangePackage f) throws IOException {
		for (serverThread t : server.threadlist) {
			t.toClient.writeObject(f);
			t.toClient.flush();
		}
	}

	/**
	 * interpret the message received from the client. 
	 * 
	 * 4 cases:
	 * (1) the message indicates an edit on a document
	 * (2) the message indicates the addition of a new file
	 * (3) the message indicates a change of file name
	 * (4) the message indicates the deletion of a file
	 * 
	 * @param socket
	 * @throws IOException
	 * @throws Exception
	 */
	private void handleConnection(Socket socket) throws IOException, Exception {
		while (true) {
			Object o = fromClient.readObject();
			if (o instanceof EventPackage) {
				EventPackage eventPackage = (EventPackage) o;
				updateServer(eventPackage);
				updateClient(eventPackage);
			} else if (o instanceof FilePackage) {
				FilePackage fp = (FilePackage) o;

				server.fileSystem.addFile(fp);
				updateClient(fp);
				System.out.println("received file from client");

			} else if (o instanceof FilenameChangePackage) {
				FilenameChangePackage f = (FilenameChangePackage) o;
				server.fileSystem.changeFileName(f.docNum, f.newFileName);
				System.out.println("received change name request from client");
				updateClient(f);

			} else if (o instanceof String) {

				String str = (String) o;
				if (o.equals("new file")) {
					String s = (String) o;
					server.fileSystem.addEmptyFile();
					updateClientwithEmptyFile(s);
					System.out.println("New Document");
				} else if (str.substring(0, 6).equals("delete")) {
					System.out
							.println("serverThread: client delete file on server");
					int docNum = Integer.parseInt(str.substring(6));
					server.fileSystem.deleteDoc(docNum);
					updateClientwithFileDeletion(str);
				}
			}

		}
	}

	/**
	 * notify the client that a file has been deleted
	 * @param str the name of the deleted file
	 */
	private void updateClientwithFileDeletion(String str) {
		for (serverThread t : server.threadlist) {
			try {
				t.toClient.writeObject(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				t.toClient.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * notify the client that an empty file has been created
	 * @param s the name of the (empty) file generated
	 * @throws IOException
	 */
	private void updateClientwithEmptyFile(String s) throws IOException {
		for (serverThread t : server.threadlist) {
			t.toClient.writeObject(s);
			t.toClient.flush();
		}

	}

}