package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import FileSystem.MyFile;

import client.Client;

/**
 * Pop Up Window for Rename File. After inputting the new file name, 
 * the user can set it by either hitting enter or selecting OK. The
 * new name will be instantly reflected on the GUI, and get submitted
 * to the File System in the server. 
 */

@SuppressWarnings("serial")
public class renamePopUp extends JFrame {
	private final Client client;
	private final MyFile currentFile;
	private final JTextField reNameFrom;
	private final JTextField reNameTo;
	private final JLabel reNamePrompt;
	private final JButton confirmChange;

	// constructor
	public renamePopUp(Client c, MyFile f, JTextField t) {
		this.setTitle("Rename File");
		this.setSize(getPreferredSize());
		this.client = c;
		this.currentFile = f;
		this.reNameFrom = t;

		reNameTo = new JTextField("");
		getContentPane().add(reNameTo);
		reNameTo.addActionListener(new renameListener());

		reNamePrompt = new JLabel("-Please Reset your File Name Below-");
		getContentPane().add(reNamePrompt);

		ImageIcon okIcon = new ImageIcon("image/ok.png");
		confirmChange = new JButton(okIcon);
		confirmChange.setToolTipText("OK");
		getContentPane().add(confirmChange);
		confirmChange.addActionListener(new renameListener());

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(reNamePrompt, GroupLayout.Alignment.CENTER)
				.addComponent(reNameTo, GroupLayout.Alignment.CENTER)
				.addComponent(confirmChange, GroupLayout.Alignment.CENTER));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(reNamePrompt).addComponent(reNameTo)
				.addComponent(confirmChange));
		setSize(getPreferredSize());

		this.pack();
		this.setLocationRelativeTo(reNameFrom);
		this.setVisible(true);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}


	/**
	 * The changed file name is immediately reflected on the client,
	 * and also submitted to the server (more precisely, the File System
	 * within the server). 
	 * 
	 * Listener for the file name JTextField
	 * 
	 */
	public class renameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			final String newName = reNameTo.getText();
			try {
				client.changeFileNameonServer(currentFile.docNum, newName);
				System.out.println("Attempt to change doc #"
						+ currentFile.docNum.toString() + "to" + newName);
				reNameFrom.setText(newName);
				System.out.println(newName);
				setVisible(false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

}
