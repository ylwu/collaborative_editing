/**
 * 
 */
package controller;

import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import model.Model;
import gui.GUI;


/**
 * @author gyz
 *
 */
public class BackEndThread extends Thread {
	private final ActionEvent e;
	private final Model model;

	public BackEndThread(ActionEvent e, Model model) {
		this.model=model;
		this.e=e;
	}
	
	public void run() {
		JTextField t=(JTextField)(e.getSource());
		String text=t.getText();
		if (text!=model.getText()){
		model.update(text);}
		
	}
}
