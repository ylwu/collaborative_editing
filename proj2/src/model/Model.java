/**
 * 
 */
package model;

import java.io.Serializable;

import javax.swing.text.AbstractDocument.DefaultDocumentEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;

import controller.Controller;


public class Model implements Serializable{
	private final Controller c;
	private MyDocument doc;
	private String docName;
	private Integer docNum;
	public Model(Controller controller){
		c=controller;
		docNum = c.getDocNum();
		doc= new MyDocument();
		initDocument();
		docName = "New Document " + Integer.toString(docNum);
		
	}
	
	
	public MyDocument getDoc(){
	    return doc;
	}
	
	public void changeDoc(MyDocument doc){
	    this.doc = doc; 
	}
	
	public String getDocName(){
	    return docName;
	}
	private void initDocument(){
	 // put some initial text
        String initString = "Styled document, please click to edit!\n" 
	 + "This Document is saved in the model from the controller and shared with other GUIs";
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        //StyleConstants.setBold(attributes, true);
        //StyleConstants.setItalic(attributes, true);
        try {
            doc.insertString(0, initString, attributes);
        } catch (BadLocationException ble) {
            System.err.println("Text Insertion Failure!");
        }
	}

	/**
	 * @param text
	 * 
	 * update
	 */
    public void insertUpdate(DefaultDocumentEvent chng, 
			AttributeSet attr) {
	    doc.insertUpdate(chng, attr);
    }


    public void removeUpdate(DefaultDocumentEvent event) {
        doc.removeUpdate(event);
        
    }

}
