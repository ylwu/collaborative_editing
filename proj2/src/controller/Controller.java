/**
 * 
 */
package controller;

import gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Model;


/**
 * @author gyz
 * 
 * Controller class, every time a action performed, open an new thread to preform action
 *
 */
public class Controller implements ActionListener{
	private final Model model;
	private GUI view;
	public Controller(GUI view)
	{
		this.model=new Model();
		this.view=view;
	}
	public Model getModel(){
		return this.model;
	}
	public void actionPerformed(ActionEvent e)
	{

	}
}
