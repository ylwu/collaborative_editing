/**
 * 
 */
package gui;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.InputMap;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import controller.Controller;

public class GUI extends JFrame  {
	/*zhengshu: added more features to the GUI*/
	private String newline = "\n";
	private final JLabel guiTitle; // entitles the GUI
	private final JLabel documentName; //displays document name
	private final JTextField documentNameField; // displays document name
	private final JTextPane editArea; // a styled editable area in the GUI
	private final JTextArea editHistory; //the edit history for the client	
	HashMap<Object, Action> actions;
	
	
	//TODO: should grab document from remote server!!! instead of this place-holder doc
	AbstractDocument document;
	
	// private final JTextField mainTextField;
	private final Controller c; // in case you need this

	public GUI(Controller c) {
		this.setTitle("Collaborative Editor");
		this.c = c;


		// create GUI title
		guiTitle = new JLabel("Welcome to Collaborative Editor!");
		getContentPane().add(guiTitle);

		// display document name
		documentName = new JLabel("You are editing Document: ");
		getContentPane().add(documentName);
		//TODO: place-holder for now, need to load actual name from the model!
		documentNameField = new JTextField("Place Holder.doc"); 
		documentNameField.setEditable(false);

		// create an editor pane
		editArea = new JTextPane();
		editArea.setCaretPosition(0); // text-insertion point
		StyledDocument styledDoc = editArea.getStyledDocument();
		if (styledDoc instanceof AbstractDocument) {
			document = (AbstractDocument) styledDoc;
		} else {
			System.err
					.println("Text pane's document isn't an AbstractDocument!");
			System.exit(-1);
		}
		JScrollPane editScrollPane = new JScrollPane(editArea);
		editScrollPane.setPreferredSize(new Dimension(500, 280));
		getContentPane().add(editScrollPane);

		// create a edit history table
		editHistory = new JTextArea();
		editHistory.setEditable(false);
		JScrollPane historyScrollPane = new JScrollPane(editHistory);
		historyScrollPane.setPreferredSize(new Dimension(300, 100));
		getContentPane().add(historyScrollPane);

		// Set up the menu bar
		actions = createActionTable(editArea);
		JMenu editMenu = createEditMenu();
		JMenuBar mb = new JMenuBar();
		mb.add(editMenu);
		setJMenuBar(mb);

		// Add hot-key commands
		addHotKey();

		// put some initial text
		String initString = "Styled document, please click to edit!";
		SimpleAttributeSet attributes = new SimpleAttributeSet();
		StyleConstants.setBold(attributes, true);
		StyleConstants.setItalic(attributes, true);
		try {
			document.insertString(0, initString, attributes);
		} catch (BadLocationException ble) {
			System.err.println("Text Insertion Failure!");
		}

		// add listeners
		JPanel statusPane = new JPanel(new GridLayout(1, 1));
		CaretListenerLabel caretListenerLabel = new CaretListenerLabel(
				"Caret Status");
		statusPane.add(caretListenerLabel);
		editArea.addCaretListener(caretListenerLabel);
		document.addDocumentListener(new MyDocumentListener());

		// set layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(guiTitle,GroupLayout.Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
						.addComponent(documentName)
						.addComponent(documentNameField))
				.addComponent(editScrollPane)
				.addComponent(historyScrollPane)
				.addComponent(statusPane));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(guiTitle)
				.addGroup(layout.createParallelGroup()
						.addComponent(documentName)
						.addComponent(documentNameField))
				.addComponent(editScrollPane)
				.addComponent(historyScrollPane)
				.addComponent(statusPane));

		setSize(getPreferredSize());

		// at the end
		this.pack();
		this.setVisible(true);
		// implement close for gui without close the whole program
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
	// TODO: (for yunzhi) please fire the change to the controller (and to the
	// model)
	protected class MyDocumentListener implements DocumentListener {
		public void insertUpdate(DocumentEvent e) {
			displayEditInfo(e);
		}

		public void removeUpdate(DocumentEvent e) {
			displayEditInfo(e);
		}

		public void changedUpdate(DocumentEvent e) {
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

		// These actions come from the default editor kit.
		// Get the ones we want and stick them in the menu.
		menu.add(getActionByName(DefaultEditorKit.cutAction));
		menu.add(getActionByName(DefaultEditorKit.copyAction));
		menu.add(getActionByName(DefaultEditorKit.pasteAction));

		menu.addSeparator();

		menu.add(getActionByName(DefaultEditorKit.selectAllAction));
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

	/**
	 * @return
	 * 
	 *         get text in textField
	 */
	public String getText() {
		// TODO convert AbstractDocument document to String!
		return null;
	}

	/**
	 * @param text
	 * 
	 *            update textField
	 */
	public void setText(String text) {
		// TODO write this part after the controller is finalized

	}

	// for testing single gui (separate user, separate gui)
	public static void main(final String[] args) {
		final Controller controller = new Controller();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI gui = new GUI(controller);

			}
		});
	}
}
