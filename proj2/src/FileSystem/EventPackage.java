package FileSystem;

import java.io.Serializable;

/*
 * event package for document edition information, contains
 * document number, eventType (insert, delete, or change)
 * length of edition, offset, the String inserted, and document length
 */

@SuppressWarnings("serial")
public class EventPackage implements Serializable {
	public final String eventType;
	public final int len;
	public final int offset;
	public final String inserted;
	public final int docLength;
	public final int docNum;

	public EventPackage(int docNum, String eventType, int len, int offset,
	        String inserted, int docLength) {
		this.docNum = docNum;
		this.eventType = eventType;
		this.len = len;
		this.offset = offset;
		this.inserted = inserted;
		this.docLength = docLength;
	}

}
