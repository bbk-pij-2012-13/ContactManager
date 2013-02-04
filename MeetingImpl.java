import java.util.Calendar;
import java.util.Set;
import java.util.*;

public class MeetingImpl implements Meeting
{

	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	private static int cont=0;

	public Meeting( Set<Contact> conta,Calendar d,int id)
	{
		contacts= new HashSet<Contact>();
		for(Iterator<Contact> it= conta.iterator();it.hasNext();)
		{
		 contacts.add(it.next());
		
		}
		
		this.date=d;
		this.id=id;
		
	
	
	}
	public Meeting( Set<Contact> conta,Calendar d)
	{
		contacts= new HashSet<Contact>();
		for(Iterator<Contact> it= conta.iterator();it.hasNext();)
		{
		 contacts.add(it.next());
		
		}
		
		this.date=d;
		this.id=cont;
		cont ++;
	
	
	}

	public generateId()

	{


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