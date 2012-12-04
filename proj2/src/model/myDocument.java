/**
 * 
 */
package model;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;

/**
 * @author gyz
 *
 */
public class myDocument extends DefaultStyledDocument{

	public void insertUpdate(AbstractDocument.DefaultDocumentEvent chng, 
			AttributeSet attr) {
		super.insertUpdate(chng, attr);
	}
	
	public void removeUpdate(AbstractDocument.DefaultDocumentEvent chng) {
		super.removeUpdate(chng);
	}
}
