/**
 * 
 */
package gui;

import javax.swing.JFrame;

import controller.Controller;

public class GUI extends JFrame  {
	
	public GUI(){
		this.setTitle("Collaborative Editing");
		Controller c=new Controller(this);
		// then write something like
		//mainTextField.addActionListener(c);
	}
	
	

}
