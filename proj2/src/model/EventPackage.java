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
	public final String eventType;
	public final int len;
	public final int offset;
	public final String inserted;
	public EventPackage(String eventType, int len, int offset, String inserted){
		this.eventType=eventType;
		this.len=len;
		this.offset=offset;
		this.inserted=inserted;
		
	}
	
    public boolean equals(EventPackage that) {
        return ((((this.eventType.equals(that.eventType) && this.len == that.len) && this.offset == that.offset) && this.inserted
                .equals(that.inserted)));
    }
	

}
