/**
 * 
 */
package FileSystem;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import javax.swing.event.DocumentEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AbstractDocument.DefaultDocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;

import FileSystem.FileSystem;


public class MyFile implements Serializable{
	private final FileSystem f;
	private AbstractDocument doc;
	private String docName;
	private Integer docNum;
	public MyFile(FileSystem fileSystem){
		f=fileSystem;
		docNum = f.getCurDocNum();
		doc= new DefaultStyledDocument();
		initDocument();
		docName = "New Document " + Integer.toString(docNum);
		
	}
	
	public MyFile(FileSystem fileSystem, File file){
		f=fileSystem;
		docNum = f.getCurDocNum();
		doc= new DefaultStyledDocument();
		initDocument();
		docName = "New Document " + Integer.toString(docNum);
		DataInputStream in = null;
        try {
        	docName=file.getName();
        	String filename=file.getAbsolutePath();
        
            final FileInputStream fstream = new FileInputStream(filename);
            in = new DataInputStream(fstream);
            String str=in.readUTF();
            doc.insertString(0, str, new SimpleAttributeSet());
        } catch (FileNotFoundException e) {
	        e.printStackTrace();
        } catch (IOException e) {
	        e.printStackTrace();
        } catch (BadLocationException e) {
	        e.printStackTrace();
        }finally{
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("can't close");
            }
        }

	}
	public EventPackage DocumentEventToEventPackage(DocumentEvent e) {
		DefaultDocumentEvent ee=(DefaultDocumentEvent) e;
		if(ee.getType()==DocumentEvent.EventType.INSERT){
			String inserted="";
			try {
				inserted=ee.getDocument().
				getText(ee.getOffset(), ee.getLength());
	        } catch (BadLocationException e1) {
	            // TODO Auto-generated catch block
	            //e1.printStackTrace();
	        }
			return new EventPackage(docNum,ee.getType().toString(),ee.getLength(),ee.getOffset(),inserted,doc.getLength());
		}
		else if (ee.getType()==DocumentEvent.EventType.REMOVE){
			return new EventPackage(docNum,ee.getType().toString(),ee.getLength(),ee.getOffset(),"",doc.getLength());
		}
		//shouldn't be here
		return new EventPackage(docNum,ee.getType().toString(),ee.getLength(),ee.getOffset(),"",doc.getLength());
		
		// then in client side 
		//insertString(int offs, String str, AttributeSet a) 
		//remove(int offs, int len) 
	}
	
	
	public AbstractDocument getDoc(){
	    return doc;
	}
	
	public synchronized void  updateDoc(EventPackage eventPackage) throws BadLocationException{
	    //doc.readLock();
	    if (eventPackage.eventType.equals("INSERT")) {
            doc.insertString(eventPackage.offset, eventPackage.inserted,
                    new SimpleAttributeSet());
        } else if (eventPackage.eventType.equals("REMOVE")) {

            doc.remove(eventPackage.offset, eventPackage.len);
        }
        System.out.println("client updated");
        //doc.readUnlock();
    }
	
	public void changeDoc(AbstractDocument doc){
	    this.doc = doc; 
	}
	
	public String getDocName(){
	    return docName;
	}
	private void initDocument(){
	 // put some initial text
        String initString = "Start this document";
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        //StyleConstants.setBold(attributes, true);
        //StyleConstants.setItalic(attributes, true);
        try {
            doc.insertString(0, initString, attributes);
        } catch (BadLocationException ble) {
            System.err.println("Text Insertion Failure!");
        }
	}

}