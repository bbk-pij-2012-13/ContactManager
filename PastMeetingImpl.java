import java.util.Calendar;
import java.util.Set;


public class PastMeeting extends Meeting
{

	String notes;
	
	public PastMeeting(Set<Contact> contacts,Calendar date)
	{
		super(contacts,date);
		
	}
	public PastMeeting(Set<Contact> contacts,Calendar date,String text)
	{
		super(contacts,date);
		this.notes=text;
	}

	
	
	public String getNotes()
	{
      return this.notes;
	}
}

