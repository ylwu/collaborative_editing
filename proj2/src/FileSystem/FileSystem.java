/**
 * 
 */
package FileSystem;

import gui.GUI;

import java.io.Serializable;
import java.util.List;

import File.File;

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
	private final File file;
    private Integer docNum;
	
	private List<GUI> views;

	public FileSystem() {
		this.docNum = 1;
		this.file = new File(this);

	}
	
	public FileSystem(Integer docNum) {
        this.docNum = docNum;
        this.file = new File(this);
	}
	


	public File getModel() {
		return this.file;
	}

	public void addView(GUI v) {
		views.add(v);
	}
	
	public void setDocNum(Integer docNum){
		this.docNum = docNum;
	}
	
	public int getDocNum(){
		return this.docNum;
	}
	
	public void increaseDocNum(){
		this.docNum ++;
	}

	
}
