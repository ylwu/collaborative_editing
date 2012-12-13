
package client;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.text.BadLocationException;

import FileSystem.EventPackage;
import FileSystem.FilePackage;
import FileSystem.FileSystem;
import FileSystem.FilenameChangePackage;
import FileSystem.MyFile;

/**
 * @author gyz
 * 
 * Collaborative Editor: Client
 * 
 * 
 * Fields:
 * 
 * toServer: 
 *         socket's output stream        
 * fromServer: 
 *         socket's input stream
 * fileSystem: 
 *         file system for this client 
 * incomingPackage: 
 *         the current package received
 * 
 * 
 * 
 * Methods:
 *         
 * constructor: 
 *         takes in ipAdress and port number, then use initialize() to do initialization; 
 *         get a message package containing the initialization data
 * 
 * updateServer: 
 *         takes in eventPackage and sent it to server
 * 
 * createNewFileOnServer: 
 *         notifies the server to create a new file
 * 
 * deleteFileOnServer: 
 *         takes in document number and notifies server to delete the file corresponding 
 *         to this document number
 * 
 * uploadFiletoServer: 
 *         takes in filename and content, create a file package, and tell server to upload 
 *         the corresponding file
 * 
 * changeFileNameonServer: 
 *         takes in document number and (new) document name tells server to rename the 
 *         corresponding file
 * 
 * getUpdates: 
 *         get update from server; controlled by a thread such that we can give information 
 *         from the server in real time and update the current client accordingly
 */
public class Client {
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	public FileSystem fileSystem;
	public static String ip;
	public Integer port;
	private Socket socket;
	public EventPackage incomingPackage;

	public Client() {
		incomingPackage = new EventPackage();
	}

	public Client(String ipAddress, Integer p) {
		incomingPackage = new EventPackage();
		ip = ipAddress;
		port = p;
	}

	/**
	 * 
	 * @param eventPackage
	 * @throws IOException
	 * 
	 * takes in eventPackage and send it to server
	 */
	public void updateServer(EventPackage eventPackage) throws IOException {
		if (!incomingPackage.equals(eventPackage)) {
			toServer.writeObject(eventPackage);
			toServer.flush();
		}
	}

	/**
	 * create a new file in the File System in server
	 * @throws IOException
	 */
	public void createNewFileOnServer() throws IOException {
		toServer.writeObject(("new file"));
		toServer.flush();
		System.out.println("create new file on server");

	}

	/**
	 * 
	 * @param docNum: the numbering of the document to be deleted
	 * @throws IOException
	 * delete the specified file
	 */
	public void deleteFileOnServer(int docNum) throws IOException {
		toServer.writeObject(("delete" + docNum));
		toServer.flush();
	}

	/**
	 * 
	 * @param file
	 * @param content the content of the file
	 * @throws IOException
	 */
	public void uploadFiletoServer(File file, String content)
			throws IOException {
		toServer.writeObject(new FilePackage(file, content));
		toServer.flush();
	}

	/**
	 * 
	 * @param docNum: numbering/index of the file to be renamed
	 * @param newFileName
	 * @throws IOException
	 * rename the specified file with the newFileName
	 */
	public void changeFileNameonServer(int docNum, String newFileName)
			throws IOException {
		toServer.writeObject(new FilenameChangePackage(docNum, newFileName));
		toServer.flush();
	}


	/**
	 * get update from server; controlled by a thread such that we can get
	 * information from the server in real time and update the current client
	 * accordingly
	 * 
	 * @throws BadLocationException
	 */
	public void getUpdates() throws BadLocationException {
		try {
			Object o = fromServer.readObject();
			if (o instanceof EventPackage) {
				EventPackage eventPackage = (EventPackage) o;
				incomingPackage = eventPackage;
				System.out.println("received update for doc#"
						+ eventPackage.docNum);
				MyFile f = fileSystem.getFile().get(eventPackage.docNum);
				f.updateDoc(eventPackage);
			} else if (o instanceof FilePackage) {
				fileSystem.addFile((FilePackage) o);
				System.out.println("received non-empty document");
			} else if (o instanceof FilenameChangePackage) {
				FilenameChangePackage fc = (FilenameChangePackage) o;
				fileSystem.changeFileName(fc.docNum, fc.newFileName);
				System.out.println("updated docName on client");
			} else if (o instanceof String) {
				String str = (String) o;
				if (str.equals("new file")) {
					fileSystem.addEmptyFile();
					System.out.println("received empty document");
				} else if (str.substring(0, 6).equals("delete")) {

					int docNum = Integer.parseInt(str.substring(6));
					fileSystem.deleteDoc(docNum);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * When called, initialize a new socket, a new output stream and a new input stream
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void initialize() throws UnknownHostException, IOException,
			ClassNotFoundException {
		socket = new Socket(ip, port);
		toServer = new ObjectOutputStream(socket.getOutputStream());
		fromServer = new ObjectInputStream(socket.getInputStream());

		fileSystem = (FileSystem) fromServer.readObject();

	}

}
