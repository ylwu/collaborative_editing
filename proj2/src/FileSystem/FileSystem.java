/**
 * 
 */
package FileSystem;

import gui.GUI;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * The file system stores all the documents that have been created and/or edited
 * by the user.
 * 
 * The central server and each client keeps a copy of the File System. When a
 * client makes an edit, create a new file, or insert/delete file, the client
 * will send a message (an EventPackage, a String, or a FilePackage) to the
 * server. The server updates its File System accordingly. The server will then
 * send the same event package back to all the connected clients, and update the
 * state of their respective local File Systems. Finally, the change of local
 * File System will be reflected on each of the local GUIs.
 * 
 * In this way, each client/editor will be able to see the instantaneous change
 * made on the working document (as well as the File System) whenever any of the
 * co-editors has modified a single document or the multi-document set, hence
 * achieving the collaborative functionality of the program.
 * 
 */
@SuppressWarnings("serial")
public class FileSystem implements Serializable {
	public List<MyFile> files = new ArrayList<MyFile>();
	private int docNum = -1;

	private List<GUI> views = new ArrayList<GUI>();

	public FileSystem() {
		docNum++;
		MyFile newFile = new MyFile(this);
		files.add(newFile);
		for (GUI v : views) {
			v.addFile(newFile.docName, this.docNum);
		}
	}

	/**
	 * Add a new file with file and text content specified by the event package
	 * from the client. 
	 * @param o
	 */
	public void addFile(FilePackage o) {
		File file = o.file;
		String content = o.content;

		docNum++;
		MyFile newFile = new MyFile(this, file, content);
		files.add(newFile);
		for (GUI v : views) {
			v.addFile(newFile.docName, this.docNum);

		}
	}

	/**
	 * Add a new untitled file to the file system. 
	 * 
	 * The new state of the file system is reflected in the GUIs of all the 
	 * connected clients.
	 */
	public void addEmptyFile() {
		docNum++;
		MyFile newFile = new MyFile(this);
		files.add(newFile);
		for (GUI v : views) {
			v.addFile(newFile.docName, this.docNum);
		}
	}

	/**
	 * 
	 * @return a list of files in the file system
	 */
	public List<MyFile> getFile() {
		return this.files;
	}

	/**
	 * Add a new client.GUI to the file system
	 * @param v 
	 */
	public void addView(GUI v) {
		views.add(v);
	}

	/**
	 * 
	 * @return the index/numbering of the current document
	 */
	public int getCurDocNum() {
		return this.docNum;
	}

	/**
	 * 
	 * @return the index/numbering of the next document
	 */
	public int getNextDocNum() {
		return this.docNum + 1;
	}

	/** Delete a specified file from the file system
	 * @param docNum2
	 *          the index/numbering of the document to be deleted
	 */
	public void deleteDoc(int docNum2) {
		try {
			String docname = files.get(docNum2).docName;
			files.set(docNum2, null);
			
			for (GUI v : views) {
				v.deleteFile(docname);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("trying to delete a file that is not exist!");
		}

	}

	/**
	 * Rename a specified document
	 * @param docNum 
	 *         the index/numbering of the file to be renamed
	 * @param newFileName
	 */
	public void changeFileName(int docNum, String newFileName) {
	    files.get(docNum).docName = newFileName; 
		for (GUI v : views) {
			v.changeFileName(docNum, newFileName);
		}

	}


	/**
	 * updated each connected client's GUI with all the current files
	 * in the file system
	 */
	public void guiWantDoc() {
		for (GUI v : views) {
			for (MyFile f : files) {
			    if (f!= null){
				v.addFile(f.docName, f.docNum);
			    }}
		}

	}

	/**update the caret position in each GUI 
	 * @param docNum2
	 *         the index of the document being viewed/edited
	 * @param offset 
	 *         the offset of caret position
	 * @param len
	 */
	public void docPosMoved(Integer docNum2, int offset, int len) {
		for (GUI v : views) {
			System.out.println(v.curDocNum());
			if (v.curDocNum().equals(docNum2)) {

				v.CaretPosition(offset, len);
			}

		}

	}

}
