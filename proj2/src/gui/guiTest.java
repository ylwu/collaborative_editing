package gui;

import javax.swing.SwingUtilities;

import client.Client;

/**
 * Testing Strategy for GUI
 * 
 * 
 * Part 1: Summary
 * The GUI is the "gateway" of the editor, receiving direct input from the client
 * and leading the client to form a first expression of our program based on
 * the user interface and its usability. To ensure that our GUI is both functional
 * and usable, we have decided to use a primarily user-centered manual testing 
 * strategy for the particular purpose. 
 * 
 * Specifically, for every JComponent implemented in the GUI, we test that 
 * 
 * (1) the particular component has met the desired spec;
 * (2) the particular component interact well with other components, if any;
 * (3) a client, even without any knowledge of the underlying design paradigm of 
 *     our program, will be able to execute the function of the component in a 
 *     straight forward way - that is, our GUI is not only coder-friendly, but is
 *     also user-friendly. 
 *     
 * Part 2: Testing Each JComponent
 * 
 * We hereby describe our testing strategy for each of the GUI component below. 
 * 
 * (1) Main Editor Panel
 * i.  Open up multiple clients/GUIs with the same document displayed in the editor 
 *     panel; test that an edit action by any one party will be instantly reflected
 *     across all the clients.  
 * ii. Test that the editor panel support common hot-key/accelerator commands, 
 *     including copy, cut, paste, select all, caret up/down/left/write, etc. 
 * iii.Test that the caret position is correctly updated across all the clients.
 * iv. Test for the correct behavior of the scroll-bar in the case of lengthy files. 
 * 
 * (2) Edit History Panel
 * i.  Test that the information shown in the history is correct and reflects the 
 *     action history of the individual client. A client/user's history panel should
 *     not update when the document has been changed by some other client/clients. 
 * ii. Test for the correct behavior of the scroll-bar for extended series of edits.
 * 
 * (3) Create New File Button
 * i.  Test that a new document entitled "New Document X" will be generated and stored
 *     in the document list, where X = (number of current document) - 1
 * ii. Test that continuously pushing the button (by a single client as well as by 
 *     multiple clients) will still generate the desired update in the document list.
 *     That is, the push button exhibit atomic behaviors. 
 *     
 *     *Note: the latest state of the document list can be visualized in the drop-down
 *      box in the GUI control panel. 
 *      
 * (4) Load Local File Button
 * i.  Test for expected behaviors of the "Open File" dialog box - the common functions
 *     (Home, One Level Up, Create New Folder, Open, Cancel, etc.) are valid.
 * ii. Test that the file loading engine can handle relatively long text files. We have
 *     stored the novel KitRunner.txt to test the loading of large-sized files. 
 * iii.Test that the text loaded into the editor panel preserves the format of the
 *     original text. All the ASCII characters should be correctly displayed within 
 *     main-stream operating system such as Windows, MacOX, and UBuntu. Also, the 
 *     positions of all the line separators should be preserved. 
 *  
 * (5) Delete File Button
 * i.  Test that the specified document is instantaneously deleted in the document list
 *     upon pushing the button. 
 * ii. Test that such deletion is reflected across all the connected users. Once a file
 *     is deleted by one user, the editor panel should no longer display that document
 *     for all users editing that particular file. 
 *     
 * (6) "SAVE AS" Button
 * i.  Test for expected behaviors of the "Save" dialog box. In addition to the common
 *     function expected of a file chooser, we also test that a confirm window pops up
 *     if the user wants to overwrite a pre-existing file. 
 * ii. Test that the document displayed in the editor panel (both in terms of content
 *     and format) is correctly saved in the local destination after executing the
 *     SAVE AS button. 
 *     
 * (7) Rename Button
 * i.  Test that the new file name is instantly updated in the document list and 
 *     reflected across all the connected users. Specifically, the drop-down document
 *     lists for all the users should show the updated file name. 
 * ii. Test for concurrency: 
 *     multiple users rename a file at (roughly) the same time
 *     multiple users rename different files at the same time
 *     
 * (8) Other Components
 * 
 * i.  Test that the user can switch to the preferred theme via the drop-down menu 
 *     "Choose Theme"
 * ii. Test that all the items in menu bar behave correctly and can be executed via 
 *     their respective hot-keys. 
 *     
 *     
 * Part 3: Conclusion
 * 
 * The tests specified above achieve two primary purposes. 
 * 
 * (1) Each component behaves correctly in response to the client's request 
 * 
 * (2) Each component interacts with all the other pertinent portions of the GUI in 
 *     an expected fashion. 
 *     
 * Therefore, our testing strategy strives to combine unit-testing (component-by-
 * component) with a general system-level usability test on the GUI. 
*/

// The following code opens up a client/GUI. Multiple execution opens multiple clients. 
public class guiTest{
	public static void main(final String[] args) {
		final String ip = "localhost";
		final Integer port = 4444;
		final Client c = new Client(ip, port);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
			    new GUI(c);

			}
		});		
	}
}

