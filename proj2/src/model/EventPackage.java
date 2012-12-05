/**
 * 
 */
package model;

import javax.swing.event.DocumentEvent.EventType;

/**
 * @author gyz
 *
 */
public class EventPackage {
	final EventType eventType;
	final int len;
	final int offset;
	final String inserted;
	public EventPackage(EventType eventType, int len, int offset, String inserted){
		this.eventType=eventType;
		this.len=len;
		this.offset=offset;
		this.inserted=inserted;
		
	}
	

}
