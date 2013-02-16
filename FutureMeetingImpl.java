import java.util.Calendar;
import java.util.Set;
import java.io.*;


public class FutureMeetingImpl  implements FutureMeeting,Serializable,Comparable<FutureMeetingImpl>
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

	public FutureMeetingImpl(Set<Contact> conta,Calendar date)
	{
		this.contacts=conta;
		this.date=date;
		
	}
	
	public int getId()
	{
	  return this.id;
	
	}

	/**
	*This method has been include to avoid that a meeting with a different id but the same contacts at a same date is added.
	**/
	/*
	@Override
	public boolean equals (Object o)
	{
		if(o!=null && o instanceof FutureMeetingImpl)
		{
			FutureMeetingImpl fm=(FutureMeetinImpl)o;
			
			if(this.id==fm.getId() && this.contacts.containsAll(fm.getContacts()))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
		

	}
	
	@Override
	public int hashCode()
	{
	  return this.id;
	
	
	}
	*/
	public Calendar getDate()
	{
	
		
		return this.date;
	
	}

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