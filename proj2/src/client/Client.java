package client;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import FileSystem.EventPackage;
import FileSystem.FilePackage;
import FileSystem.FileSystem;
import FileSystem.FilenameChangePackage;
import FileSystem.MyFile;

/*
 * collaborative_editing client
 * 
 * fields
 * toServer: socket's output stream
 * fromServer: socket's input stream
 * fileSystem: file system for this client
 * imcomingPackage: the current package we got
 * 
 * methods:
 * constructor: takes in ipAddress, at the same time, 
 * 				get a message package containing the initialization data
 * 				then, use initialize() to do initialization
 * updateServer: takes in eventPackage, send it to server
 * createNewFileOnServer: tell server to create new file
 * deleteFileOnServer: takes in document number,
 * 						tells server to delete the file corresponding 
 * 						to this document number
 * uploadFiletoServer: takes in filename information and content, form a file package
 *						and tell server to upload the corresponding file
 *changeFileNameonServer: takes in document number and document name
 *						tells server to change the name to the new document name
 *						for given document
 *getUpdates: get update from server, controlled by a thread, such that we can give 
 *			Information from the server in real time, then update according to 
 *			different situations
 */
public class Client {
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	public FileSystem fileSystem;
	public static String ip;
	public EventPackage incomingPackage;
	
	public Client(String ipAddress) {
		incomingPackage = new EventPackage();
		ip = ipAddress;
	}

	public void updateServer(EventPackage eventPackage) throws IOException {
		if (!incomingPackage.equals(eventPackage)) {
			toServer.writeObject(eventPackage);
			toServer.flush();
		}
	}

	public void createNewFileOnServer() throws IOException {
		toServer.writeObject(("new file"));
		toServer.flush();
	}

	public void deleteFileOnServer(int docNum) throws IOException {
		toServer.writeObject(("delete" + docNum));
		toServer.flush();
	}

	public void uploadFiletoServer(File file, String content)
	        throws IOException {
		toServer.writeObject(new FilePackage(file, content));
		toServer.flush();
	}

	public void changeFileNameonServer(int docNum, String newFileName)
	        throws IOException {
		toServer.writeObject(new FilenameChangePackage(docNum, newFileName));
		toServer.flush();
	}

	public void getUpdates() throws IOException, ClassNotFoundException{
			Object o = fromServer.readObject();
			if (o instanceof EventPackage) {
				EventPackage eventPackage = (EventPackage) o;
				incomingPackage = eventPackage;
				MyFile f = fileSystem.getFile().get(eventPackage.docNum);
				f.updateDoc(eventPackage);
			} else if (o instanceof FilePackage) {
				fileSystem.addFile((FilePackage) o);
			} else if (o instanceof FilenameChangePackage) {
				FilenameChangePackage fc = (FilenameChangePackage) o;
				fileSystem.changeFileName(fc.docNum, fc.newFileName);
			} else if (o instanceof String) {
				String str = (String) o;
				if (str.equals("new file")) {
					fileSystem.addEmptyFile();
				} else if (str.substring(0, 6).equals("delete")) {
					int docNum = Integer.parseInt(str.substring(6));
					fileSystem.deleteDoc(docNum);
				}

			}
	}

	public void initialize() throws UnknownHostException, IOException,
	        ClassNotFoundException {
		final Socket socket = new Socket(ip, 4441);
		toServer = new ObjectOutputStream(socket.getOutputStream());
		fromServer = new ObjectInputStream(socket.getInputStream());
		fileSystem = (FileSystem) fromServer.readObject();
	}

}
