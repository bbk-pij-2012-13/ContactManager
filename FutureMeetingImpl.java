import java.util.Calendar;
import java.util.Set;
import java.io.*;


public class FutureMeetingImpl  implements FutureMeeting,Serializable
{

	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	
	public FutureMeetingImpl(Set<Contact> conta,Calendar date,int id)
	{
		this.contacts=conta;
		this.date=date;
		this.id=id;
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



}