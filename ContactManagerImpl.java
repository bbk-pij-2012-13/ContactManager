import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
*@author Isabel Reig
*
*ContactManagerImpl class
*A class to manage  contacts and meetings.
*/
public class ContactManagerImpl implements ContactManager
{		
	private  Set <Contact> contacts_set;
	private final String path="G:\\Agenda4.obj";
	private  Map < Integer,Meeting> agenda;
		
	/**
	*Constructor class
	*@param contacts_set: HashSet that stores all the contacts.
	*@param agenda:  HashMap that stores all the meetings.
	*@param paths: String with the address where the file is saved.
	* @throws IOException,ClassNotFoundException.
	**/		
	public ContactManagerImpl()throws IOException,ClassNotFoundException
	{
		FileInputStream fi=null;
		ObjectInputStream oi=null;
		try
		{
			fi = new FileInputStream (path);
			oi=new ObjectInputStream(fi);
			agenda=(HashMap<Integer,Meeting>)oi.readObject();
			contacts_set=(HashSet <Contact>)oi.readObject();
			oi.close();
		}
		catch(FileNotFoundException e)
		{
			agenda= new HashMap<Integer,Meeting>();
			contacts_set= new HashSet <Contact>();			
		}
	}
	
	
	/**
	* Add a new meeting to be held in the future.
	* The program will inform if it's attempted to intoduce a meeting with the same contacts 
	* at the same time ,as nothing will prevent adding a meeting with the same features.
	*
	* @param contacts a list of contacts that will participate in the meeting
	* @param date the date on which the meeting will take place
	* @return the ID for the meeting
	* @throws IllegalArgumentException if the meeting is set for a time in the past,
	* of if any contact is unknown / non-existent
	*/		
	public int addFutureMeeting(Set<Contact> contacts, Calendar date)
	{			  
		Contact temp1=null;	 
		
		for(Iterator<Contact> it=contacts.iterator();it.hasNext();)
		{					
			temp1=it.next();
			if (!contacts_set.contains(temp1))
			{
				throw new IllegalArgumentException(    "Client not found" );	 
			}	
		}
		if ( date.before(Calendar.getInstance())) 
		{		
				
			throw new IllegalArgumentException(    "Error:Date before the current date " );		
		}	
		else 
		{			  
			Set set=agenda.keySet();
			Iterator<Integer> it=set.iterator();
			Integer id=null;
			Meeting m=null;					
			while(it.hasNext())
			{				
				id=(Integer)it.next();
				m=agenda.get(id);
				if (m instanceof FutureMeetingImpl)
				{					         				
				  if (m.getDate().getTime().toString().equals(date.getTime().toString()))
					{
					   if( m.getContacts().containsAll(contacts))
					   {
						System.out.println("You're trying to add a Meeting that already exists");
					   }
					}	  
				}					
			}			  
			int j=generate_id_meeting();
			Meeting tempmeeting= new FutureMeetingImpl(contacts,date,j);
			agenda.put(j,tempmeeting);
			return tempmeeting.getId();
		}
	}
	
		
	/**
	* Returns the PAST meeting with the requested ID, or null if it there is none.
	* @param id the ID for the meeting
	* @return the meeting with the requested ID, or null if it there is none.
	* @throws IllegalArgumentException if there is a meeting with that ID happening in the future
	*/
	public PastMeeting getPastMeeting(int id)
	{
	  
		PastMeeting pm=null;
		if (!agenda.containsKey(id))
		{
			return pm;
		}
		else if(agenda.get(id).getDate().after(Calendar.getInstance()))
		{
			throw new IllegalArgumentException("Error,there is a meeting with that ID happening in the future" );
		}
		else
		{
			 pm=(PastMeeting)agenda.get(id);
		}
		return pm;   		  
	}
			
		
	/**
	* Returns the FUTURE meeting with the requested ID, or null if there is none.
	*
	* @param id the ID for the meeting
	* @return the meeting with the requested ID, or null if it there is none.
	* @throws IllegalArgumentException if there is a meeting with that ID happening in the past
	*/
	public FutureMeeting getFutureMeeting(int id)
	{
		FutureMeeting fmeet= null;
		if (!agenda.containsKey(id))
		{
			return fmeet;
		}
		else 
		{
			Meeting m= (Meeting)agenda.get(id);
			if (m.getDate().before(Calendar.getInstance()))
			{
			  throw new IllegalArgumentException( "There's a meeting with this Id that happened in the past" );
			}
			else
			{	
				if (m instanceof FutureMeeting)
				{
					fmeet=(FutureMeeting)m;
				}
			}
		}	
		return fmeet;
	}
			
	
	/**
	* Returns the meeting with the requested ID, or null if it there is none.
	*
	* @param id the ID for the meeting
	* @return the meeting with the requested ID, or null if it there is none.
	*/
	public Meeting getMeeting(int id)
	{		
		Meeting temp=null; 
		if (agenda.containsKey(id))
		   {
			 temp=(Meeting)agenda.get(id);
		   }		
		return temp;				
	}

		
	/**
	* Return the list of future meetings that are scheduled for this contact.
	* If there are none, the returned list will be empty. Otherwise,
	* the list will be chronologically sorted and will not contain any
	* duplicates.
	*
	* @param contact one of the user’s contacts
	* @return the list of future meeting(s) scheduled with this contact (maybe empty).
	* @throws IllegalArgumentException if the contact does not exist
	*/
	public List<Meeting> getFutureMeetingList(Contact contact)
	{  
		if(!contacts_set.contains(contact))
		{
				 throw new IllegalArgumentException(    "Id doesn't correspond to a real contact" );
		}
		List<FutureMeetingImpl> temp=new ArrayList<FutureMeetingImpl>();
		List<Meeting> temp2=new ArrayList<Meeting>();
		Set set=agenda.keySet();
		Iterator<Integer> it=set.iterator();
		Integer id=null;
		Meeting m=null;
		FutureMeetingImpl fm=null;
		int i=0;
		while(it.hasNext())
		{
			//id of the meeting
			id=(Integer)it.next(); 
			m= (Meeting)agenda.get(id);
			if (m instanceof FutureMeetingImpl)
			{
				fm=(FutureMeetingImpl)m;
				//COntact set of each meeting
				Set st=fm.getContacts();
				if (st.contains(contact))
				{
				  temp.add(i,fm);
				  i++;
				}
			}
			else
			{
			 m=null;
			}
		}
		if (temp!=null)
		{
			Collections.sort(temp);		
			for (int j=0;j<temp.size();j++)
			{
			 temp2.add((Meeting)temp.get(j));
			}
		}
		return temp2;
	}

	
	/**
	* Returns the list of meetings that are scheduled for, or that took
	* place on, the specified date.
	* If there are none, the returned list will be empty. Otherwise,
	* the list will be chronologically sorted and will not contain any
	* duplicates.
	* @param date the date
	* @return the list of meetings
	*/
	public List<Meeting> getFutureMeetingList(Calendar date)
	{	
		Set<Integer> set=agenda.keySet();
		Iterator<Integer> it=set.iterator();
		List <FutureMeetingImpl> fm =new ArrayList <FutureMeetingImpl>();
		List<Meeting> result=new ArrayList<Meeting>();
		while(it.hasNext())
		{
			int i=it.next();
			Meeting m=agenda.get(i);
			FutureMeetingImpl f;
			if (m instanceof FutureMeetingImpl && m.getDate().get(Calendar.YEAR)==date.get(Calendar.YEAR) && m.getDate().get(Calendar.MONTH)==date.get(Calendar.MONTH) && m.getDate().get(Calendar.DAY_OF_MONTH)==date.get(Calendar.DAY_OF_MONTH) && m.getDate().get(Calendar.MINUTE)==date.get(Calendar.MINUTE))
			{
				f=(FutureMeetingImpl)m;
				fm.add(f);	
			}
		}
		if(fm!=null)
		{
			Collections.sort(fm);
			for(int i=0;i<fm.size();i++)
			{
				result.add((Meeting)fm.get(i));
			}
		}
		return result;
	}
	
	
	/**
	* Returns the list of past meetings in which this contact has participated.
	*
	* If there are none, the returned list will be empty. Otherwise,
	* the list will be chronologically sorted and will not contain any
	* duplicates.
	*
	* @param contact one of the user’s contacts
	* @return the list of future meeting(s) scheduled with this contact (maybe empty).
	* @throws IllegalArgumentException if the contact does not exist
	*/
	public List<PastMeeting> getPastMeetingList(Contact c)
	{
		List<PastMeetingImpl> lspast=new ArrayList<PastMeetingImpl>();
		List<PastMeeting> lspast2=new ArrayList<PastMeeting>();
		if(!contacts_set.contains(c))
		{
			throw new IllegalArgumentException(    "Id doesn't correspond to a real contact" ); 
		}
		else if(!agenda.isEmpty())
		{
			//Añadir para el caso de que la agenda esté vacía.
			Set set=agenda.keySet();
			Iterator<Integer> it=set.iterator();
			Integer id=null;
			Meeting m=null;
			PastMeetingImpl pm=null;
			
			while(it.hasNext())
			{
				id=(Integer)it.next();
				m=(Meeting)agenda.get(id);
				if (m instanceof PastMeetingImpl)
				{
					if ((m.getContacts()).contains(c))
					{
						pm=(PastMeetingImpl)m;						 
						lspast.add(pm);
					}
				} 
			}
			if(lspast!=null)
			{
				Collections.sort(lspast);
				for (int i=0;i<lspast.size();i++)
				{
					lspast2.add((PastMeeting)lspast.get(i));	
				}
			}			
			
		}
		return lspast2;
	}
	
	
	/**
	* Create a new record for a meeting that took place in the past.
	*
	* @param contacts a list of participants
	* @param date the date on which the meeting took place
	* @param text messages to be added about the meeting.
	* @throws IllegalArgumentException if the list of contacts is
	* empty, or any of the contacts does not exist
	* @throws NullPointerException if any of the arguments is null
	*/		
	public void addNewPastMeeting(Set<Contact> contacts,Calendar date,String text)
	{
		Meeting m;
		Contact temp1=null;	
		if(contacts==null ||date==null||text==null)
		{
			 throw new NullPointerException("Some of the arguments are null");
		}
		else if(!contacts_set.isEmpty())
		{
			for(Iterator<Contact> it=contacts.iterator();it.hasNext();)
			{
				temp1=it.next();
			   if (!contacts_set.contains(temp1))
				{
					throw new IllegalArgumentException(    "Client not found" );
				}						
			}
			int j=generate_id_meeting();
			m= new PastMeetingImpl(j,date,contacts,text);
			agenda.put(j,m);
		}
	}
	
	
	/**
	* Add notes to a meeting.
	*
	* This method is used when a future meeting takes place, and is
	* then converted to a past meeting (with notes).
	*
	* It can be also used to add notes to a past meeting at a later date.
	* @param id the ID of the meeting
	* @param text messages to be added about the meeting.
	* @throws IllegalArgumentException if the meeting does not exist
	* @throws IllegalStateException if the meeting is set for a date in the future
	* @throws NullPointerException if the notes are null
	*/
	public void addMeetingNotes(int id, String text)
	{
		if (text==null)
		{
			throw new NullPointerException("The notes are null");			
		}
		if (!agenda.isEmpty())
		{
			if(!agenda.containsKey(id))
			{
				throw new IllegalArgumentException(  "This meeting, Id, doesn't exist");
			}
			else if (agenda.get(id).getDate().after(Calendar.getInstance()))	 
			{
				throw new IllegalArgumentException(  "This meeting is set for date in the future");
			}
			else if (agenda.get(id) instanceof PastMeetingImpl)				
			{
				Meeting m=(Meeting)agenda.get(id);
				PastMeetingImpl p= (PastMeetingImpl)m;
				p.setNotes(text);	 
			}
			else if (agenda.get(id) instanceof FutureMeeting)
			{
				Meeting m=agenda.get(id);
				Calendar date=m.getDate();
				Set<Contact> contacts=m.getContacts();
				Meeting pastmeet= new PastMeetingImpl(id,date,contacts,text);
				agenda.remove(id);
				agenda.put(id,pastmeet);
			}	 
		}			  
	}	

	
	/**
	* Create a new contact with the specified name and notes.
	*
	* @param name the name of the contact.
	* @param notes notes to be added about the contact.
	* @throws NullPointerException if the name or the notes are null
	*/
	public void addNewContact(String name, String notes)
	{	
		int j=generate_id_contact();
		Contact c= new ContactImpl(name,notes,j);
		contacts_set.add(c); 
	}

		
		
	/**
	* Returns a list containing the contacts that correspond to the IDs.
	*
	* @param ids an arbitrary number of contact IDs
	* @return a list containing the contacts that correspond to the IDs.
	* @throws IllegalArgumentException if any of the IDs does not correspond to a real contact
	*/				
	public Set<Contact> getContacts(int... ids)
	{
		
		Set <Contact> result= new HashSet <Contact>();
		int length=ids.length;
		boolean []  found= new boolean [length];
		for (int m=0;m<length;m++)
		{
		 found [m]=false;
		}
		int lengthset=contacts_set.size();
		for( int i=0;i<length;i++)
		{	  
			Iterator<Contact> it=contacts_set.iterator();
			Contact temp;
			int k=0;
			while(it.hasNext())
			{ 
				temp=it.next();
				if( ids[i]==temp.getId())
				{
					found[i]=true;
					result.add(temp);
				}
				else if (k==lengthset-1 && !found[i])
				{
					throw new IllegalArgumentException(    "Id doesn't correspond to a real contact" +ids[i]);
				}
				k++;
			}
		}
		return result;
	}
	
	/**
	* Returns a list with the contacts whose name contains that string.
	*
	* @param name the string to search for
	* @return a list with the contacts whose name contains that string.
	* @throws NullPointerException if the parameter is null
	*/
	public Set<Contact> getContacts(String name)
	{
	//trial not definitive
		Set<Contact> result=new HashSet<Contact> ();
		if (name!=null)
		{
			Iterator<Contact> it=contacts_set.iterator();
			Contact c;
			while(it.hasNext())
			{
				c=it.next();
				if(c.getName().equals(name))
				{
					result.add(c);				
				}
			}
		}
		else
		{
			throw new NullPointerException("The parameter is  null");
		}
		return result;	
	}
		
		
		
	/**
	* This methods prints all the contacts in the contacts_set.
	*
	*/	
	public void printcontacts()
	{
		
		Iterator<Contact> it=contacts_set.iterator();
		Contact c= null;
		int i=1;
		while(it.hasNext())
		{
			c= it.next();
			System.out.println(c.getId()+"...."+i+"-------"+c.getName());
			i++;
		}
	}

	
	/**
	* This methods prints all the meetings in the agenda.
	*
	*/
	public void printMeeting()
	{
		Set set=agenda.keySet();
		Iterator<Integer> it=set.iterator();
		Integer id=null;
		Meeting m;
		
		while(it.hasNext())
		{
			int k=0;
			System.out.println();
			id=(Integer)it.next();
			m=(Meeting)agenda.get(id);
			System.out.println();
			System.out.println(m.getId()+"...."+m.getDate().getTime());
			Set st=m.getContacts();
			Iterator<Contact> it2=st.iterator();
			Contact c=null;
			while (it2.hasNext())
			{
				c=it2.next();
				System.out.print( "Contact: "+k+"   "  +c.getName()+"..."+c.getId()+"      ");
				k++;
			}
			
		
		}
	}
	
		
	/**
	*This method generates a random contact id between 0 and 1000.
	*For simplicity it has been considered a maximum amount of 1000 contacts. 
	*If it is attempted to create more than 1000 
	*the method will enter an infinite loop.
	*
	*@return contact_id,int.
	**/	
	
	public  int generate_id_contact()
	{			
		int j=(int)(Math.random()*1000+1);
		Iterator<Contact> it=contacts_set.iterator();
		Contact c;
		int lengthset=contacts_set.size();
		int k=0;
		while(it.hasNext())
		{
			c= it.next();
			if (c.getId()==j)
			{
				generate_id_contact();
			}
			else if (k==lengthset-1)
			{
			  break;
			}
			k++;
		}
		return j;
	}
	
		
		
	/**
	*This method generates a random meeting id between 0 and 1000.
	*For simplicity it has been considered a maximum amount of 1000 meetings. If it is attempted
	*to create more than 1000 
	*the method will enter an infinite loop.
	*
	*@return contact_id,int.
	**/
	public  int generate_id_meeting()
	{
		int j=(int)(Math.random()*1000+1);
		if(agenda.containsKey(j))
		{
			generate_id_meeting();
		}
		if (!agenda.containsKey(j))
		{
			return j;
		}
		return j;
	}
		 

	/**
	* Save all data to disk.
	*
	* This method is  executed when the program is
	* closed and when/if the user requests it.
	*
	*@throws IOException if there's a problem with IO.
	*/	
	
	public void flush() 
	{
		FileOutputStream fo;
		 ObjectOutputStream os;
		try{
		fo= new FileOutputStream(path);
		os= new ObjectOutputStream(fo);
		if (os!=null)
		{
			os.writeObject(agenda);
			os.writeObject(contacts_set);
			os.flush();
			os.close();
		}
		} catch (IOException ex) { 
		}
	}
		
	/**
	* This methods instantiate an object of ContactManagerImpl
	* and also include the User Interface with several options
	* to add Meetings, get meetings, add new Contacts etc.
	*
	*@throws IOException ,ClassNotFoundException.
	*/		
		
	public static void main(String[] args) throws IOException,ClassNotFoundException
	{
   
		ContactManagerImpl cont=new ContactManagerImpl();
		System.out.println("Wellcome to the 2º CourseWork of Programming in Java");
		System.out.println("Please select one of the folowing functions" +"  "+" press 16 EXIT ");
		BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
		int opc;
		Set<Contact> scontacts;
		Contact c;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String date,notes,name;
		
		List<Meeting> l;
		List<PastMeeting> lp;
		ArrayList<Integer> idcontacts=new ArrayList();
		do
		{
			System.out.println("1.AddFutureMeeting");
			System.out.println("2.get PastMeeting by Id");
			System.out.println("3.get FutureMeeting by Id");
			System.out.println("4.get Meeting by Id");
			System.out.println("5.get FutureMeetingList by Contact");
			System.out.println("6.get FutureMeetingList by Date");
			System.out.println("7.get PastMeetingList by Contact");
			System.out.println("8.add NewPastMeeting ");
			System.out.println("9.add MeetingNotes ");
			System.out.println("10.add New Contact");
			System.out.println("11. get Contacts by Id");
			System.out.println("12. get Contacts by Name");
			System.out.println("13. Save");
			System.out.println("14. Prin tMeetings ");
			System.out.println("15. Print Contacts");
			System.out.println("16. Exit");
			opc=Integer.parseInt(bf.readLine());

			switch(opc)
			{
				case 1:
				Date d;
				Calendar cal=Calendar.getInstance();
				int id;
				scontacts= null;
				idcontacts.clear();
				try
				{
					 System.out.println("Please introduce the date of the meeting. Format available:'dd/MM/yyyy HH:mm'");
					 date=bf.readLine();
					 d= (Date)formatter.parse(date); 
					 cal.setTime(d);
				}
				catch (ParseException e)
				{
				  // execution will come here if the String that is given
				  // does not match the expected format.
				  e.printStackTrace();
				}		
				
				do
					{
						System.out.println("Please introduce the id of the  contact----press 0 when finishing");
						id=Integer.parseInt(bf.readLine());
						if(id!=0)
						{
						 idcontacts.add(id);
						}
					} while(id!=0);
				if(idcontacts!=null)
				{
					int[] ids= new int[idcontacts.size()];
					for (int j=0;j<idcontacts.size();j++)
					{
					 ids[j]=idcontacts.get(j);
					}
					scontacts=cont.getContacts(ids);
				}	
				cont.addFutureMeeting(scontacts,cal);
				break;
				
				case 2:
				//getPastMeetingbyId
				System.out.println("Please introduce the id of the  meeting");
				id=Integer.parseInt(bf.readLine());
				PastMeeting p=cont.getPastMeeting(id);
				System.out.println(p.getDate().getTime());
				break;
			
				case 3:
				//"3.get FutureMeeting by Id"
				System.out.println("Please introduce the id of the  meeting");
				id=Integer.parseInt(bf.readLine());
				FutureMeeting f=cont.getFutureMeeting(id);
				System.out.println(f.getDate().getTime());
				break;
				
				case 4:
				//"4.get Meeting by Id"
				System.out.println("Please introduce the id of the  meeting");
				id=Integer.parseInt(bf.readLine());
				Meeting m=cont.getMeeting(id);
				System.out.println(m.getDate().getTime());
				break;
				
				case 5:
				//"5.get FutureMeetingList by Contact"
				scontacts= null;
				System.out.println("Please introduce the id of the  contact");
				id=Integer.parseInt(bf.readLine());
				scontacts=cont.getContacts(id);
				c=(Contact)(scontacts.toArray())[0];
				l=cont.getFutureMeetingList(c);
				for (int j=0;j<l.size();j++)
				{
					System.out.println("Date of the meeting:   "+l.get(j).getDate().getTime()+"   Meeting ID: "+l.get(j).getId());
				}
				break;
				
				case 6:
				//"6.get FutureMeetingList by Date"
				Date d2;
				Calendar cal2=Calendar.getInstance();
				try
				{
					 System.out.println("Please introduce the date of the meeting. Format available:'dd/MM/yyyy HH:mm'");
					 date=bf.readLine();
					 d2= (Date)formatter.parse(date); 
					 cal2.setTime(d2);
				}
				catch (ParseException e)
				{
				  // execution will come here if the String that is given
				  // does not match the expected format.
				  e.printStackTrace();
				}
				l=cont.getFutureMeetingList(cal2);
				for (int j=0;j<l.size();j++)
				{
					System.out.println("Date of the meeting:Meeting ID:  "+l.get(j).getId());
				}
				break;
				
				case 7:
				//"7.get PastMeetingList by Contact"
				scontacts= null;
				System.out.println("Please introduce the id of the  contact");
				id=Integer.parseInt(bf.readLine());
				scontacts=cont.getContacts(id);
				c=(Contact)(scontacts.toArray())[0];
				lp=cont.getPastMeetingList(c);
				for (int j=0;j<lp.size();j++)
				{
					System.out.println("Date of the meeting:   "+lp.get(j).getDate().getTime()+"   Meeting ID: "+lp.get(j).getId());
				}
				break;
				
				case 8:
				scontacts=null;
				idcontacts.clear();
				Date d3;
				Calendar cal3=Calendar.getInstance();
				//	public void addNewPastMeeting(Set<Contact> contacts,Calendar date,String text)
				
				try
				{
					 System.out.println("Please introduce the date of the meeting. Format available:'dd/MM/yyyy HH:mm'");
					 date=bf.readLine();
					 d3= (Date)formatter.parse(date); 
					cal3.setTime(d3);
				}
				catch (ParseException e)
				{
				  // execution will come here if the String that is given
				  // does not match the expected format.
				  cal3=null;
				  e.printStackTrace();
				}
				System.out.println("Please introduce the notes, no more than a line");
				notes=bf.readLine();
				do
					{
						System.out.println("Please introduce the id of the  contact----press 0 when finishing");
						id=Integer.parseInt(bf.readLine());
						if(id!=0)
						{
						 idcontacts.add(id);
						}
					} while(id!=0);
				if(idcontacts!=null)
				{
					int[] ids= new int[idcontacts.size()];
					for (int j=0;j<idcontacts.size();j++)
					{
					 ids[j]=idcontacts.get(j);
					}
					scontacts=cont.getContacts(ids);
				}
				cont.addNewPastMeeting(scontacts,cal3,notes);
				break;
				
				case 9:
				//"9.add MeetingNotes (int id, String text)"
				System.out.println("Please introduce the id of the  meeting");
				id=Integer.parseInt(bf.readLine());
				System.out.println("Please introduce the notes, no more than a line");
				notes=bf.readLine();
				cont.addMeetingNotes(id,notes);
				break;
				
				case 10:
				//"10.add New Contact"
				System.out.println("Introuduce the name of the contact");
				name=bf.readLine();
				System.out.println("Introduce the notes of the contact");
				notes=bf.readLine();
				cont.addNewContact(name,notes);
				break;
				
				
				case 11:
				List <Integer> temp= new ArrayList();
				Set <Contact> result;
				do
					{
						System.out.println("Please introduce the id of the  contact----press 0 when finishing");
						id=Integer.parseInt(bf.readLine());
						if(id!=0)
						{
						temp.add(id);
						}
					} while(id!=0);
				int[] ids=new int[temp.size()];
				for (int i=0;i<temp.size();i++)
				{
				 ids[i]=temp.get(i);
				}
				result=cont.getContacts(ids);
				
				case 12:
				//"11. get Contacts by Name"
				System.out.println("Introuduce the name of the contact");
				name=bf.readLine();
				scontacts=cont.getContacts(name);
				Iterator<Contact> it2=scontacts.iterator();
				while(it2.hasNext())
				{
				 c= it2.next();
				 System.out.println(c.getName());
				}
				
				break;
				
				case 13:
				cont.flush();
				break;
				
				case 14:
				cont.printMeeting();
				break;
				
				case 15:
				cont.printcontacts();
				break;
				
				case 16:
				cont.flush();
				break;
				
				default: System.out.println ("Invalid option");
                break;
			}
		}while(opc!=16);
	}

}