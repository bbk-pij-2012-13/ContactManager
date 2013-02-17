import java.util.Calendar;
import java.util.Set;
import java.io.*;
/**
**@author Isabel Reig 
*class,PastMeetingImpl
*A meeting that was held in the past.
* It includes  notes about what happened and what was agreed.
*/
public class PastMeetingImpl implements PastMeeting,Serializable,Comparable<PastMeetingImpl>
{
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	private String notes;
	
	
	/**
	*Constructor class.
	*@param contacts: Set of contacts of the meeting.
	*@param date:  Calendar date of the meeting.
	*@param notes: String notes.
	*@param id: int id.
	**/
	public PastMeetingImpl(int id,Calendar date,Set<Contact> contacts,String text)
	{
		this.id=id;
		this.date=date;
		this.contacts=contacts;
		this.notes=text;
		
	}
	
	/**
	* Returns the id of the meeting.
	*
	* @return the id of the meeting.
	*/
	public int getId()
	{
	  return this.id;
	}

	
	/**
	* Return the date of the meeting.
	*
	* @return the date of the meeting.
	*/
	public Calendar getDate()
	{
		return this.date;
	}
	
	
	/**
	* Return the details of people that attended the meeting.
	*
	* The list contains a minimum of one contact (if there were
	* just two people: the user and the contact) and may contain an
	* arbitraty number of them.
	*
	* @return the details of people that attended the meeting.
	*/
	public Set<Contact> getContacts()
	{
	 return contacts;
	}

	
	/**
	* Returns the notes from the meeting.
	*
	* If there are no notes, the empty string is returned.
	*
	* @return the notes from the meeting.
	*/
	public String getNotes()
	{
      		return this.notes;
	}

	public void setNotes(String n)
	{
       		this.notes+="   "+n;
	}
	
 
 
 
 @Override
    public int compareTo(PastMeetingImpl o) 
	{
		if (this.getDate().compareTo(o.getDate())>0)
		{
			return 1;
		
		}
		if (this.getDate().compareTo(o.getDate())<0)
		{
			return -1;		
		}
		
		else
		 
		 {
		 return 0;
		 }	
   	 }		
	
}

