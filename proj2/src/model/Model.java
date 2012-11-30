/**
 * 
 */
package model;

import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import controller.Controller;


public class Model {
	private final Controller c;
	private final AbstractDocument doc;
	private String docName;
	public Model(Controller controller){
		c=controller;
		doc= new DefaultStyledDocument();
		initDocument();
		docName = "New Document";
		
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
        StyleConstants.setBold(attributes, true);
        StyleConstants.setItalic(attributes, true);
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
    public void update(String text) {
	    // TODO Auto-generated method stub
	    
    	// at the end 
    	c.updateFontEnd();
    }

	/**
	 * @return
	 * get text saved in model
	 */
    public String getText() {
	    // TODO Auto-generated method stub
	    return null;
    }

}
