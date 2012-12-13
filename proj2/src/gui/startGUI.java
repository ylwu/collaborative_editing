package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import client.Client;

/**
 * Running the main method of startGUI will connect the client 
 * to the server and initialize the GUI for this client. 
 * 
 * Specifically, a dialog window will first pop up, asking the 
 * client to specify both the IP address and port number. 
 * 
 * If the IP address and port number result in successful 
 * connection to the server, the main editor GUI will start
 * for this client
 */

@SuppressWarnings("serial")
public class startGUI extends JFrame {
	public static String ip;
	public static Integer port;
	private final JTextField ipAddress;
	private final JTextField portAddress;
	private final JLabel typeIP;
	private final JLabel typePort;
	private final JButton confirm;

	public startGUI() {
		this.setSize(getPreferredSize());

		ipAddress = new JTextField("");
		getContentPane().add(ipAddress);

		portAddress = new JTextField("");
		getContentPane().add(portAddress);
		portAddress.addActionListener(new ipPortListener());

		typeIP = new JLabel("Please Type an IP Address");
		getContentPane().add(typeIP);

		typePort = new JLabel("Please Type a Port Number");
		getContentPane().add(typePort);

		ImageIcon okIcon = new ImageIcon("image/ok.png");
		confirm = new JButton(okIcon);
		confirm.setToolTipText("Connect");
		confirm.addActionListener(new ipPortListener());

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(typeIP).addComponent(ipAddress)
				.addComponent(typePort).addComponent(portAddress)
				.addComponent(confirm, GroupLayout.Alignment.CENTER));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(typeIP).addComponent(ipAddress)
				.addComponent(typePort).addComponent(portAddress)
				.addComponent(confirm));

		setSize(getPreferredSize());

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	/**
	 * Listener for ipAddress and port number
	 * 
	 * A GUI will start for this client if the IP-Port combination is valid. 
	 */
	public class ipPortListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ip = ipAddress.getText();
			port = Integer.parseInt(portAddress.getText());
			setVisible(false);
			System.out.println(ip);
			final Client c = new Client(ip, port);
			try {
				c.initialize();
			} catch (UnknownHostException e2) {
				e2.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
			}
			try {
				for (LookAndFeelInfo info : UIManager
						.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						UIManager.setLookAndFeel(info.getClassName());
						break;
					}
				}
			} catch (UnsupportedLookAndFeelException e1) {
			} catch (ClassNotFoundException e1) {
			} catch (InstantiationException e1) {
			} catch (IllegalAccessException e1) {
			}

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new GUI(c);

				}
			});

		}
	}

	public static void main(final String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (UnsupportedLookAndFeelException e1) {
		} catch (ClassNotFoundException e1) {
		} catch (InstantiationException e1) {
		} catch (IllegalAccessException e1) {
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				startGUI window = new startGUI();
				window.setTitle("IP and Port");
				window.setLocationRelativeTo(null);
				window.pack();
				window.setVisible(true);
			}
		});
	}
}
