package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import FileSystem.FileSystem;

import client.Client;

/**
 * start window
 */

public class startWindow extends JFrame {
	public static String ip;
	private final JTextField ipAddress;
	private final JLabel typeIP;
	
	public startWindow (){
		ipAddress = new JTextField("");
		getContentPane().add(ipAddress);
		ipAddress.addActionListener(new ipListener());
		
		
		typeIP = new JLabel("-Please Type an IP Address Below-");
		getContentPane().add(typeIP);
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(layout.createParallelGroup()
        		.addComponent(typeIP,GroupLayout.Alignment.CENTER)
        		.addComponent(ipAddress,GroupLayout.Alignment.CENTER));
        

        layout.setVerticalGroup(layout.createSequentialGroup()
        		.addComponent(typeIP)
        		.addComponent(ipAddress));
        setSize(getPreferredSize());
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	
	// listener for ipAddress
	public class ipListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			ip = ipAddress.getText();
			System.out.println(ip);
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					Client client = new Client(ip);
					
				}
			});
            
				

		}
	}
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				startWindow window = new startWindow();
				window.setTitle("IP");
				window.pack();
				window.setVisible(true);
			}
		});
	}
}
				


	

	
