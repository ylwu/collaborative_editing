package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

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
			ip=ipAddress.getText();
			setVisible(false);
			System.out.println(ip);
			final Client c = new Client(ip);
			try {
				c.initialize();
			} catch (UnknownHostException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
			    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			        if ("Nimbus".equals(info.getName())) {
			            UIManager.setLookAndFeel(info.getClassName());
			            break;
			        }
			    }
			} catch (UnsupportedLookAndFeelException e1) {
			    // handle exception
			} catch (ClassNotFoundException e1) {
			    // handle exception
			} catch (InstantiationException e1) {
			    // handle exception
			} catch (IllegalAccessException e1) {
			    // handle exception
			}
			
			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					//testing purpose
		            System.out.println("creating new gui");
				    new GUI(c);

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
				


	

	
