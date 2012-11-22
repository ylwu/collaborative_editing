import gui.GUI;

import javax.swing.SwingUtilities;



public class Main {
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI gui = new GUI();
				gui.pack();
				gui.setVisible(true);
			}
		});
	}
}
