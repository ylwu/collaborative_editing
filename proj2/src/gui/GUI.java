/**
 * 
 */
package gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import controller.Controller;

public class GUI extends JFrame  {
	
	private final JTextField mainTextField;
	private final Controller c; // in case you need this
	
	public GUI(Controller c){
		this.setTitle("Collaborative Editing");
		// then write something like
		this.c=c;
		mainTextField=new JTextField();
		mainTextField.addActionListener(c);
		
		//at the end
		this.pack();
		this.setVisible(true);
	}

	/**
	 * @return
	 * 
	 * get text in textField
	 */
    public String getText() {
	    // TODO Auto-generated method stub
	    return null;
    }

	/**
	 * @param text
	 * 
	 * update textField
	 */
    public void setText(String text) {
	    // TODO Auto-generated method stub
	    
    }
	
	

}
