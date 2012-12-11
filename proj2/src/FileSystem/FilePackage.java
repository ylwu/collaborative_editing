package FileSystem;

import java.io.File;
import java.io.Serializable;

/*
 * event package for adding new file, contains
 * document name and document content
 */
@SuppressWarnings("serial")
public class FilePackage implements Serializable {
	public final File file;
	public final String content;

	public FilePackage(File f, String c) {
		file = f;
		content = c;
	}
}
