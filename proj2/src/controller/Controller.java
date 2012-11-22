/**
 * 
 */
package controller;

import gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.Model;

/**
 * @author gyz
 * 
 *         Controller class, every time a action performed, open an new thread
 *         to preform action
 * 
 *         I will start with a slow implementation (read all string in the text
 *         field every time)
 * 
 * 
 */
public class Controller implements ActionListener {
	private final Model model;
	private List<GUI> views;

	public Controller() {
		this.model = new Model();
	}

	public Model getModel() {
		return this.model;
	}
	
	public void addView(GUI v){
		views.add(v);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("update from viewer")) {
			BackEndThread thread = new BackEndThread(e, model);
			thread.start();
		}else if (e.getActionCommand().equals("update from model")){
			for (GUI v:views){
				FrontEndThread thread = new FrontEndThread(v, model);
				thread.start();
			}
		}
		

	}
}
