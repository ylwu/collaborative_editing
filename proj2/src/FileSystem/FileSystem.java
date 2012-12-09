/**
 * 
 */
package FileSystem;

import gui.GUI;

import java.io.File;
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
	public List<MyFile> files=new ArrayList<MyFile>();
	private int docNum=-1;
    
	
	private List<GUI> views=new ArrayList<GUI>();

	public FileSystem() {
		docNum++;
		MyFile newFile = new MyFile(this);
		files.add(newFile);
		for (GUI v:views){
			v.addFile(newFile.docName,this.docNum);
			}
	}
	
	
	public void addFile(FilePackage o){
		File file=o.file;
		String content=o.content;
		docNum++;
		MyFile newFile = new MyFile(this,file, content);
		files.add(newFile);	
		for (GUI v:views){
			v.addFile(newFile.docName,this.docNum);
			}
	}
	
	public void addEmptyFile(){
	    MyFile newFile = new MyFile(this);
	    files.add(newFile);
		for (GUI v:views){
			v.addFile(newFile.docName, this.docNum);
			}
	}



	public List<MyFile> getFile() {
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


	/**
	 * @param docNum2
	 */
    public void deleteDoc(int docNum2) {
    	try{
    		String docname=files.get(docNum2).docName;
	    files.set( docNum2, null);
		for (GUI v:views){
			v.deleteFile(docname);
			}
    	
    	}
    	catch( ArrayIndexOutOfBoundsException e){
    		e.printStackTrace();
    		System.out.println("trying to delete a file that is not exist!");
    	}
	    
    }
	
	

	
}
