package FileSystem;

import java.io.File;
import java.io.Serializable;

import javax.swing.event.DocumentEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AbstractDocument.DefaultDocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;

/**
 * 
 * The Class MyFile implements the individual file stored in the file system.
 * 
 * Specifications for individual method can be found at their respective locations. 
 * 
 */

@SuppressWarnings("serial")
public class MyFile implements Serializable {
	private final FileSystem f;
	private AbstractDocument doc;
	public String docName;
	public Integer docNum;

	/**
	 * Constructor based on fileSystem only. Called only when a new untitled file
	 * has been created. 
	 * @param fileSystem
	 */
	public MyFile(FileSystem fileSystem) {
		f = fileSystem;
		docNum = f.getCurDocNum();
		doc = new DefaultStyledDocument();
		initDocument();
		docName = "New Document " + Integer.toString(docNum);

	}


	/**
	 * Constructor based on fileSystem, file, and file content (i.e., text)
	 * @param fileSystem
	 * @param file
	 * @param content
	 */
	public MyFile(FileSystem fileSystem, File file, String content) {
		f = fileSystem;
		docNum = f.getCurDocNum();
		doc = new DefaultStyledDocument();
		docName = file.getName();
		try {
			doc.insertString(0, content, new SimpleAttributeSet());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * From the edit actions of the clients, generate a event package to be
	 * sent to the server (and update the server's File System accordingly)
	 * @param e Edit actions on the current document
	 * @return the event package to be submitted to server
	 */
	public EventPackage DocumentEventToEventPackage(DocumentEvent e) {
		DefaultDocumentEvent ee = (DefaultDocumentEvent) e;
		if (ee.getType() == DocumentEvent.EventType.INSERT) {
			String inserted = "";
			try {
				inserted = ee.getDocument().getText(ee.getOffset(),
						ee.getLength());
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			return new EventPackage(docNum, ee.getType().toString(),
					ee.getLength(), ee.getOffset(), inserted, doc.getLength());
		} else if (ee.getType() == DocumentEvent.EventType.REMOVE) {
			return new EventPackage(docNum, ee.getType().toString(),
					ee.getLength(), ee.getOffset(), "", doc.getLength());
		}
		return new EventPackage(docNum, ee.getType().toString(),
				ee.getLength(), ee.getOffset(), "", doc.getLength());
	}

	/**
	 * 
	 * @return the current document being edited
	 */
	public AbstractDocument getDoc() {
		return doc;
	}

	/**
	 * update the content of the current document based on the event package
	 * received from the server
	 * @param eventPackage 
	 *         event package from the server to the client
	 */
	public synchronized void updateDoc(EventPackage eventPackage) {
		if (eventPackage.eventType.equals("INSERT")) {
			try {
				doc.insertString(eventPackage.offset, eventPackage.inserted,
						new SimpleAttributeSet());
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			f.docPosMoved(docNum, eventPackage.offset, eventPackage.len);
		} else if (eventPackage.eventType.equals("REMOVE")) {

			try {
				doc.remove(eventPackage.offset, eventPackage.len);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			f.docPosMoved(docNum, eventPackage.offset, -eventPackage.len);
		}
	}

	/**
	 * Change the current document to the specified document
	 * @param doc: the specified file
	 */
	public void changeDoc(AbstractDocument doc) {
		this.doc = doc;
	}

	/**
	 * 
	 * @return the name of the current document
	 */
	public String getDocName() {
		return docName;
	}

	/**
	 * initialize a new document. Called only when the user creates a new
	 * untitled document
	 */
	private void initDocument() {
		// put some initial text to indicate a new file has been created
		String initString = "[Start this document]";
		SimpleAttributeSet attributes = new SimpleAttributeSet();
		try {
			doc.insertString(0, initString, attributes);
		} catch (BadLocationException ble) {
			System.err.println("Text Insertion Failure!");
		}
	}

}
