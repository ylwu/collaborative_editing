/**
 * 
 */
package FileSystem;

import gui.GUI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author gyz
 * 
 *         Controller class, every time a action performed, open an new thread
 *         to preform action
 * 
 *         I will start with a slow implementation (read all string in the text
 *         field every time)
 * 
 * 
 */
public class FileSystem implements Serializable {
	private List<File> files=new ArrayList<File>();
	private int docNum=-1;
    
	
	private List<GUI> views;

	public FileSystem() {
		docNum++;
		File newFile = new File(this);
		files.add(newFile);
	}



	public List<File> getModels() {
		return this.files;
	}

	public void addView(GUI v) {
		views.add(v);
	}
	
	
	public int getCurDocNum(){
		return this.docNum;
	}
	
	
	//not used
	public int getNextDocNum(){
		return this.docNum+1;
	}
	
	

	
}
