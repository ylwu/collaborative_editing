package File;

import gui.GUI;
import FileSystem.FileSystem;
import client.Client;


/**
* Testing Strategy for the Model
* 
* (1) Test that the model can be correctly constructed with any
*     initial texts in the editable text field
*     
* (2) Test that an edit in the view by any client will be reflected
*     instantaneous in the model
*
* (3) Test specific methods in the Model.class
*     i) getDoc (get document)
*     ii) getDocName (get the name of the document)
*
*/

public class ModelTest{
	public static void main(final String[] args) {
		final FileSystem c=new FileSystem();
		final Client cl = new Client();
        new GUI(c,cl);
		
	}	
}