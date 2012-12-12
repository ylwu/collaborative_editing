package FileSystem;

import java.io.Serializable;

public class FilenameChangePackage implements Serializable{
    public final int docNum;
    public final String newFileName;
    
    public FilenameChangePackage(int docNum, String newFileName){
        this.docNum = docNum;
        this.newFileName = newFileName;
    }

}
