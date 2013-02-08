import java.util.Calendar;
import java.util.Set;
import java.io.*;

public class PastMeetingImpl implements PastMeeting,Serializable
{
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	String notes;
	
	public PastMeetingImpl(Set<Contact> contacts,Calendar date)
	{
		this.contacts=contacts;
		this.date=date;
		
	}
	public PastMeetingImpl(Set<Contact> contacts,Calendar date,String text)
	{
		this.contacts=contacts;
		this.date=date;
		this.notes=text;
	}
	
	public int getId()
	{
	  return this.id;
	
	}
	
	public Calendar getDate()
	{
	
		return this.date;
	
	}
	
	public Set<Contact> getContacts()
	{
	
	 return contacts;
	
	}

	public String getNotes()
	{
      return this.notes;
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
		
		
		//return (this.getDate().before(o.getDate()) ? 1 : (this.getDate().after(o.getDate())) ? -1 : 0);
    }	
	
	
	
}

