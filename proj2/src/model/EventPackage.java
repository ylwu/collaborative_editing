/**
 * 
 */
package model;

import javax.swing.event.DocumentEvent.EventType;
import java.io.Serializable;

/**
 * @author gyz
 *
 */
public class EventPackage implements Serializable{
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
