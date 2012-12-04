/**
 * 
 */
package model;

import java.io.Serializable;

import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import controller.Controller;


public class Model implements Serializable{
	private final Controller c;
	private AbstractDocument doc;
	private String docName;
	private Integer docNum;
	public Model(Controller controller){
		c=controller;
		docNum = c.getDocNum();
		doc= new DefaultStyledDocument();
		initDocument();
		docName = "New Document " + Integer.toString(docNum);
		
	}
	
	
	public AbstractDocument getDoc(){
	    return doc;
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
    public void update(AbstractDocument doc) {
	    this.doc = doc;
	    
    	// at the end 
    	c.updateFontEnd();
    }

}
