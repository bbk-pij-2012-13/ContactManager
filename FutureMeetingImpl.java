import java.util.Calendar;
import java.util.Set;
import java.io.*;
/**
**@author Isabel Reig 
*class,PastMeetingImpl
*A meeting that will be held in the future.
*/

public class FutureMeetingImpl  implements FutureMeeting,Serializable,Comparable<FutureMeetingImpl>
{

	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	
	/**
	*Constructor class.
	*@param contacts: Set of contacts of the meeting.
	*@param date:  Calendar date of the meeting.
	*@param id: int id.
	**/
	public FutureMeetingImpl(Set<Contact> conta,Calendar date,int id)
	{
		this.contacts=conta;
		this.date=date;
		this.id=id;
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
	*This methods is created to sort out the lists by date.
	**/
 @Override
    public int compareTo(FutureMeetingImpl o) 
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