/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import FileSystem.File;
import FileSystem.FileSystem;
import client.Client;
import client.UpdateListener;

public class GUI extends JFrame  {

	/*zhengshu: added more features to the GUI*/
	private String newline = "\n";
	private final JPanel gui;
	private final JLabel guiTitle; // entitles the GUI
	private final JLabel documentName; //displays document name
	private final JTextField documentNameField; // displays document name
	private final JTextPane editArea; // a styled editable area in the GUI
	private final JTextArea editHistory; //the edit history for the client	
	private final JButton createNew;
	private final JComboBox fileList;
	private final JFileChooser fc;
	private final JTextArea log;
	private final JButton openButton;
	private String docName;//name of the document(that a user can edit)
	HashMap<Object, Action> actions;
	
    private Integer docNum = 1; // initialize document number to 1
	
	private AbstractDocument document;
	
	private final FileSystem fileSystem; // in case you need this
	private final Client client;
	private File model;

	public GUI(FileSystem controller, Client client) {

		this.setTitle("Collaborative Editor");
		this.client = client;
		this.fileSystem = controller;
		this.model= controller.getModels().get(controller.getModels().size()-1);
		this.document = model.getDoc();
		this.docName = model.getDocName();

		// create GUI title
		guiTitle = new JLabel("Welcome to Collaborative Editor!");
		getContentPane().add(guiTitle);
		
		// create GUI Image
		ImageIcon icon1 = new ImageIcon("image/writing-2.jpg","Collaborative Editing");
		JLabel guiPicture = new JLabel(icon1);
		
		
		// create JButton
		ImageIcon newIcon = new ImageIcon("image/new.png");
		createNew = new JButton(newIcon);
		getContentPane().add(createNew);
		createNew.addActionListener(new createDocListener());
		
		// create drop-down box
		// TODO: take in a list of files as arguments; ADD LISTENER
		JLabel dropDownHeader = new JLabel("-Select Document-");
		fileList = new JComboBox();
		fileList.addItem(docName);
		fileList.addActionListener(new dropDownListener());
		getContentPane().add(dropDownHeader);
		getContentPane().add(fileList);

		// display document name
		documentName = new JLabel("-You are Editing Document-");
		getContentPane().add(documentName);
		documentNameField = new JTextField(docName); 
		documentNameField.setEditable(false);

		// create an editor pane
		editArea = new JTextPane();
		editArea.setDocument(document);
		editArea.setCaretPosition(0); // text-insertion point
		JScrollPane editScrollPane = new JScrollPane(editArea);
		editScrollPane.setPreferredSize(new Dimension(500, 300));
		getContentPane().add(editScrollPane);

		// create a edit history table
		editHistory = new JTextArea();
		editHistory.setEditable(false);
		JScrollPane historyScrollPane = new JScrollPane(editHistory);
		historyScrollPane.setPreferredSize(new Dimension(500, 80));
		getContentPane().add(historyScrollPane);

		// Set up the menu bar
		actions = createActionTable(editArea);
		JMenu editmenu = new JMenu("Edit");
		
		Action cutAction = new DefaultEditorKit.CutAction();
		cutAction.putValue(Action.NAME, "Cut");
		editmenu.add(cutAction);

		Action copyAction = new DefaultEditorKit.CopyAction();
		copyAction.putValue(Action.NAME, "Copy");
		editmenu.add(copyAction);

		Action pasteAction = new DefaultEditorKit.PasteAction();
		pasteAction.putValue(Action.NAME, "Paste");
		editmenu.add(pasteAction);

		editmenu.addSeparator();
		
		Action backAction = getActionByName(DefaultEditorKit.backwardAction);
		backAction.putValue(Action.NAME, "Caret Back");
		editmenu.add(backAction);
		
		Action forwardAction = getActionByName(DefaultEditorKit.forwardAction);
		forwardAction.putValue(Action.NAME, "Caret Forward");
		editmenu.add(forwardAction);
		
		Action upAction = getActionByName(DefaultEditorKit.upAction);
		upAction.putValue(Action.NAME, "Caret Up");
		editmenu.add(upAction);
		
		Action downAction = getActionByName(DefaultEditorKit.downAction);
		downAction.putValue(Action.NAME, "Caret Down");
		editmenu.add(downAction);
		

		editmenu.addSeparator();
		
		Action selectAllAction = getActionByName(DefaultEditorKit.selectAllAction);
		selectAllAction.putValue(Action.NAME, "Select All");
		editmenu.add(selectAllAction);
		
		JMenuBar mb = new JMenuBar();
		mb.add(editmenu);
		setJMenuBar(mb);
		
		
		// set up file chooser
		log = new JTextArea (5,20);
		log.setMargin(new Insets(5,5,5,5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);
		
		fc = new JFileChooser(); // FILES_ONLY
		ImageIcon openIcon = new ImageIcon("image/open.png");
		openButton = new JButton(openIcon);
		openButton.addActionListener(new loadDocListener());
		
		
		

		// Add hot-key commands
		addHotKey();

		// add listeners
		JPanel statusPane = new JPanel(new GridLayout(1, 1));
		CaretListenerLabel caretListenerLabel = new CaretListenerLabel(
				"Caret Status");
		statusPane.add(caretListenerLabel);
		editArea.addCaretListener(caretListenerLabel);
		document.addDocumentListener(new MyDocumentListener());

		// set layout
		
		// 1. parent window
		gui = new JPanel(new BorderLayout(5,5));
		gui.setBorder(new TitledBorder("Azure v1.2"));
		
		// top panel: document name, create new document, change theme
		JPanel plafComponents = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JPanel displayDocName = new JPanel(new BorderLayout());
		displayDocName.add(documentName,BorderLayout.NORTH);
		displayDocName.add(documentNameField,BorderLayout.SOUTH);
		
		plafComponents.add(createNew);
		plafComponents.add(openButton);
		plafComponents.add(displayDocName);
		
		JPanel plafSubComp = new JPanel(new BorderLayout(3,3));
		
		plafSubComp.setBorder(
                new TitledBorder("Choose a Theme for your Editor"));
		
		final UIManager.LookAndFeelInfo[] plafInfos = UIManager
				.getInstalledLookAndFeels();
		String[] plafNames = new String[plafInfos.length];
		for (int ii = 0; ii < plafInfos.length; ii++) {
			plafNames[ii] = plafInfos[ii].getName();
		}
		final JComboBox plafChooser = new JComboBox(plafNames);
		plafSubComp.add(plafChooser,BorderLayout.WEST);

		final JCheckBox pack = new JCheckBox("Pack on the New Theme", true);
		plafSubComp.add(pack,BorderLayout.EAST);
		
		plafComponents.add(plafSubComp);
		

		plafChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int index = plafChooser.getSelectedIndex();
				try {
					UIManager.setLookAndFeel(plafInfos[index].getClassName());
					SwingUtilities.updateComponentTreeUI(getRootPane());
					if (pack.isSelected()) {
						pack();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		JPanel upperPortion = new JPanel(new BorderLayout());
		upperPortion.add(guiPicture,BorderLayout.NORTH);
		upperPortion.add(plafComponents,BorderLayout.SOUTH);

		gui.add(upperPortion, BorderLayout.NORTH);

		// left panel
		JPanel dynamicLabels = new JPanel(new BorderLayout(4, 4));
		dynamicLabels.setBorder(new TitledBorder("Control Panel"));
		gui.add(dynamicLabels, BorderLayout.WEST);
       
		
		JPanel selectDoc = new JPanel(new BorderLayout());
		selectDoc.add(dropDownHeader,BorderLayout.NORTH);
		selectDoc.add(fileList,BorderLayout.SOUTH);
		
		JPanel controlButtons = new JPanel(new GridLayout(0,1));
		JButton cutButton = new JButton(cutAction);
		controlButtons.add(cutButton);
		
		JButton copyButton = new JButton(copyAction);
		controlButtons.add(copyButton);
		
		JButton pasteButton = new JButton(pasteAction);
		controlButtons.add(pasteButton);
		
		JButton selectAllButton = new JButton(selectAllAction);
		controlButtons.add(selectAllButton);
		
		
		dynamicLabels.add(selectDoc,BorderLayout.NORTH);
		dynamicLabels.add(controlButtons,BorderLayout.SOUTH);
		

		
		
		
            
		
		// right panel
		JSplitPane splitPane = new JSplitPane(
				JSplitPane.VERTICAL_SPLIT,
				editScrollPane,
				historyScrollPane);
		gui.add(splitPane, BorderLayout.CENTER);
		
		
		
		// set background color
		Color color = new Color(240,248,255);
		documentName.setBackground(color);
		upperPortion.setBackground(color);
		plafSubComp.setBackground(color);
		dropDownHeader.setBackground(color);
		dynamicLabels.setBackground(color);
		gui.setBackground(color);
		splitPane.setBackground(color);
		plafComponents.setBackground(color);
		//guiPicture.setBackground(color);
		
		//this.client.updateServer(eventPackage)
		Thread t=new UpdateListener(client);
		t.start(); 
		// at the end
		this.setContentPane(gui);
		this.pack();

        this.setLocationRelativeTo(null);
        try {
            // after version 1.6; might have compatibility issue
            this.setLocationByPlatform(true);
            this.setMinimumSize(this.getSize());
        } catch(Throwable ignoreAndContinue) {
        }

		this.setVisible(true);
		// implement close for gui without close the whole program
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}
	
	// Listener for uploading new document
	protected class loadDocListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if (e.getSource()==openButton){
				int returnVal = fc.showOpenDialog(gui);
				if (returnVal == JFileChooser.APPROVE_OPTION){
					java.io.File file = fc.getSelectedFile();// confuse File with java.io.fileList
					log.append("Opening: " + file.getName()+"."+ newline);		
				}else{
					log.append("Open command cancelled by user." + newline);
				}
				log.setCaretPosition(log.getDocument().getLength());
			}
		}
	}
	
	// Listener for creating new document
	protected class createDocListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e){
        //TODO: implement Create New Document Action
		}
	}
	
	// Listener for drop-down box of file list
	protected class dropDownListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e){
			//TODO: implement listener
		}
	}

	// Listener for Caret movement
	protected class CaretListenerLabel extends JLabel implements CaretListener {
		public CaretListenerLabel(String label) {
			super(label);
		}

		// Might not be invoked from the event dispatch thread.
		public void caretUpdate(CaretEvent e) {
			displaySelectionInfo(e.getDot(), e.getMark());
		}

		// This method can be invoked from any thread. It
		// invokes the setText and modelToView methods, which
		// must run on the event dispatch thread. We use
		// invokeLater to schedule the code for execution
		// on the event dispatch thread.
		protected void displaySelectionInfo(final int dot, final int mark) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (dot == mark) { // no selection
						try {
							Rectangle caretCoords = editArea.modelToView(dot);
							// Convert it to view coordinates.
							setText("caret: text position: " + dot
									+ ", view location = [" + caretCoords.x
									+ ", " + caretCoords.y + "]" + newline);
						} catch (BadLocationException ble) {
							setText("caret: text position: " + dot + newline);
						}
					} else if (dot < mark) {
						setText("selection from: " + dot + " to " + mark
								+ newline);
					} else {
						setText("selection from: " + mark + " to " + dot
								+ newline);
					}
				}
			});
		}
	}

	// Listener for Document changes

	protected class MyDocumentListener implements DocumentListener {
		public void insertUpdate(DocumentEvent e) {
			try {
                client.updateServer(model.DocumentEventToEventPackage(e));
            } catch (IOException e1) {
                e1.printStackTrace();
           }
		}

		public void removeUpdate(DocumentEvent e) {

		    try {
                client.updateServer(model.DocumentEventToEventPackage(e));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
			displayEditInfo(e);
		}

		public void changedUpdate(DocumentEvent e) {
			System.out.println("change");
			displayEditInfo(e);
		}

		private void displayEditInfo(DocumentEvent e) {
			Document document1 = e.getDocument();
			int changeLength = e.getLength();
			editHistory
					.append(e.getType().toString() + ": " + changeLength
							+ " character"
							+ ((changeLength == 1) ? ". " : "s. ")
							+ " Text length = " + document1.getLength() + "."
							+ newline);
		}
	}

	// Add a couple of emacs key bindings for navigation.
	protected void addHotKey() {
		InputMap inputMap = editArea.getInputMap();

		// Ctrl-b to go backward one character
		KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK);
		inputMap.put(key, DefaultEditorKit.backwardAction);

		// Ctrl-f to go forward one character
		key = KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK);
		inputMap.put(key, DefaultEditorKit.forwardAction);

		// Ctrl-p to go up one line
		key = KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK);
		inputMap.put(key, DefaultEditorKit.upAction);

		// Ctrl-n to go down one line
		key = KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK);
		inputMap.put(key, DefaultEditorKit.downAction);
	}

	// Create the edit menu.
	protected JMenu createEditMenu() {
		JMenu menu = new JMenu("Edit");
		
		Action cutAction = new DefaultEditorKit.CutAction();
		cutAction.putValue(Action.NAME, "Cut");
		menu.add(cutAction);

		Action copyAction = new DefaultEditorKit.CopyAction();
		copyAction.putValue(Action.NAME, "Copy");
		menu.add(copyAction);

		Action pasteAction = new DefaultEditorKit.PasteAction();
		pasteAction.putValue(Action.NAME, "Paste");
		menu.add(pasteAction);

		menu.addSeparator();
		
		Action backAction = getActionByName(DefaultEditorKit.backwardAction);
		backAction.putValue(Action.NAME, "Caret Back");
		menu.add(backAction);
		
		Action forwardAction = getActionByName(DefaultEditorKit.forwardAction);
		forwardAction.putValue(Action.NAME, "Caret Forward");
		menu.add(forwardAction);
		
		Action upAction = getActionByName(DefaultEditorKit.upAction);
		upAction.putValue(Action.NAME, "Caret Up");
		menu.add(upAction);
		
		Action downAction = getActionByName(DefaultEditorKit.downAction);
		downAction.putValue(Action.NAME, "Caret Down");
		menu.add(downAction);
		

		menu.addSeparator();
		
		Action selectAllAction = getActionByName(DefaultEditorKit.selectAllAction);
		selectAllAction.putValue(Action.NAME, "Select All");
		menu.add(selectAllAction);

		return menu;
	}

	// The following two methods allow us to find an
	// action provided by the editor kit by its name.
	private HashMap<Object, Action> createActionTable(
			JTextComponent textComponent) {
		HashMap<Object, Action> actions = new HashMap<Object, Action>();
		Action[] actionsArray = textComponent.getActions();
		for (int i = 0; i < actionsArray.length; i++) {
			Action a = actionsArray[i];
			actions.put(a.getValue(Action.NAME), a);
		}
		return actions;
	}

	private Action getActionByName(String name) {
		return actions.get(name);
	}

//
//	/**
//	 * @return
//	 * 
//	 *         get text in textField
//	 */
//	public String getText() {
//		// TODO convert AbstractDocument document to String!
//		return null;
//	}
//
//	/**
//	 * @param text
//	 * 
//	 *            update textField
//	 */
//	public void setText(String text) {
//		// TODO write this part after the controller is finalized
//
//	}

}
