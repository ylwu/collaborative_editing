/**
 * 
 */
package FileSystem;

import java.io.File;
import java.io.Serializable;

/**

 * 
 * The Class FilePackage is responsible for adding new file. 
 * 
 * Its fields include document name and document content for the new file. 
 *
 */
@SuppressWarnings("serial")
public class FilePackage implements Serializable {
	public final File file;
	public final String content;
	
	public FilePackage(File f, String c) {
		file = f;
		content = c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FilePackage other = (FilePackage) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		return true;
	}


}
