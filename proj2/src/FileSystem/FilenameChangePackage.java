package FileSystem;

import java.io.Serializable;

/**
 * 
 * This is the event package for chaning filename. 
 * 
 * Fields include document number and the new document name. 
 */
@SuppressWarnings("serial")
public class FilenameChangePackage implements Serializable{
    public final int docNum;
    public final String newFileName;
    
    public FilenameChangePackage(int docNum, String newFileName){
        this.docNum = docNum;
        this.newFileName = newFileName;
    }

}
