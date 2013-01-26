import java.util.Calendar;
import java.util.Set;


public class PastMeetingImpl implements PastMeeting,Serializable
{
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	String notes;
	
	public PastMeeting(Set<Contact> contacts,Calendar date)
	{
		this.contacts=contacts;
		this.date=date;
		
	}
	public PastMeeting(Set<Contact> contacts,Calendar date,String text)
	{
		this.contacts=contacts;
		this.date=date;
		this.notes=text;
	}
	
	public int getId()
	{
	  return this.id;
	
	}
	
	
	public String getNotes()
	{
      return this.notes;
	}
}

