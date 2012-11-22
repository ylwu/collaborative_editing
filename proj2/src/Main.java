import gui.GUI;

import javax.swing.SwingUtilities;

import controller.Controller;



public class Main {
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Controller c=new Controller();
				
				
			}
		});
	}
}
