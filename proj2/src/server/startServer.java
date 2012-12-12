 package server;

 import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import FileSystem.FileSystem;

 /**
  * start window
  */

 public class startServer extends JFrame {
 	public static Integer port;
 	private final JTextField portNumber;
 	private final JLabel typePort;
 	
 	public startServer (){
 		this.setSize(getPreferredSize());
 		portNumber = new JTextField("");
 		getContentPane().add(portNumber);
 		portNumber.addActionListener(new portListener());
 		
 		
 		typePort = new JLabel("-Please Type a Telnet Port Below-");
 		getContentPane().add(typePort);
 		
 		GroupLayout layout = new GroupLayout(getContentPane());
 		getContentPane().setLayout(layout);
         layout.setAutoCreateGaps(true);
         layout.setAutoCreateContainerGaps(true);
         
         layout.setHorizontalGroup(layout.createParallelGroup()
         		.addComponent(typePort,GroupLayout.Alignment.CENTER)
         		.addComponent(portNumber,GroupLayout.Alignment.CENTER));
         

         layout.setVerticalGroup(layout.createSequentialGroup()
         		.addComponent(typePort)
         		.addComponent(portNumber));
         setSize(getPreferredSize());
         
         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 		
 	}
 	
 	// listener for ipAddress
 	public class portListener implements ActionListener{
 		public void actionPerformed(ActionEvent e){
 			String stringPort = portNumber.getText();
 			port= Integer.parseInt(stringPort);
 			FileSystem c = new FileSystem();
 			try  {
 				setVisible(false);
 				Server.runServer(port,c);
 			}catch(IOException e2){
 				e2.printStackTrace();
 			}
 			
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
 				startServer portWindow = new startServer();
 				portWindow.setTitle("Port Selection");
 				portWindow.setLocationRelativeTo(null);
 				portWindow.pack();
 				portWindow.setVisible(true);
 			}
 		});
 	}
 }