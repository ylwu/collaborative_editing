/**
 * 
 */
package model;

import java.io.Serializable;

import javax.swing.event.DocumentEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AbstractDocument.DefaultDocumentEvent;
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
	
	public static EventPackage DocumentEventToEventPackage(DocumentEvent e) {
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
			return new EventPackage(ee.getType(),ee.getLength(),ee.getOffset(),inserted);
		}
		else if (ee.getType()==DocumentEvent.EventType.REMOVE){
			return new EventPackage(ee.getType(),ee.getLength(),ee.getOffset(),"");
		}
		//shouldn't be here
		return new EventPackage(ee.getType(),ee.getLength(),ee.getOffset(),"");
		
		// then in client side 
		//insertString(int offs, String str, AttributeSet a) 
		//remove(int offs, int len) 
	}
	
	
	public AbstractDocument getDoc(){
	    return doc;
	}
	
	public void changeDoc(AbstractDocument doc){
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
    public void update(AbstractDocument doc) {
	    this.doc = doc;
	    
    	// at the end 
    	c.updateFontEnd();
    }


//    public void removeUpdate(DefaultDocumentEvent event) {
//        doc.postRemoveUpdate(event);
//        
//    }

}
