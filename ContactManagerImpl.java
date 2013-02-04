import java.util.*;
import java.io.*;
 class ContactManagerImpl implements ContactManager
{
		
		private  Map <Integer,Contact> contacts_set;
		private final String path="H:\\Agenda.obj";
		private  Map < Integer,Meeting> agenda;
		

		public ContactManagerImpl()throws IOException,ClassNotFoundException
		{
			FileInputStream fi=null;
			ObjectInputStream oi=null;
			try
			{
				fi = new FileInputStream (path);
				oi=new ObjectInputStream(fi);
				agenda=(HashMap<Integer,Meeting>)oi.readObject();
				contacts_set=(HashMap <Integer,Contact>)oi.readObject();
				
				oi.close();
			}
			catch(FileNotFoundException e)
			{
				agenda= new HashMap<Integer,Meeting>();
				contacts_set= new HashMap <Integer,Contact>();
				
			}
			
			
		}
	
		public int addFutureMeeting(Set<Contact> contacts, Calendar date)
		{
			  //Contact c;
			  //USO DE CONTAINSALL ();
			 Contact temp1=null;
			 
			  for(Iterator<Contact> it=contacts.iterator();it.hasNext();)
				{
					
					  temp1=it.next();
					  if (!contacts_set.containsValue(temp1))
						{
							throw new IllegalArgumentException(    "Client not found" );
					 
						}
						
				}
					
				
			 if ( date.before(Calendar.getInstance())) 
				{
					
					System.out.println("no lo encuentro");
					
					throw new IllegalArgumentException(    "Error" );
					
				}
				
			  else
			  {
				int j=generate_id_meeting();
				Meeting tempmeeting= new FutureMeetingImpl(contacts,date,j);
				agenda.put(j,tempmeeting);
				return tempmeeting.getId();
			  }
				
				
		}
		
		
		
		public PastMeeting getPastMeeting(int id)
		{
		  
			PastMeeting pm=null;
			if(agenda.get(id).getDate().after(Calendar.getInstance()))
				{
					throw new IllegalArgumentException(    "Error" );
				}
		  
			else
				{
					 pm=(PastMeeting)agenda.get(id);
				}
		   
			return pm;   
		   
		  
		}
		
		
		public FutureMeeting getFutureMeeting(int id)
		{
			FutureMeeting fmeet= null;
			if(agenda.containsKey(id))
			{
				FutureMeeting m= (FutureMeeting)agenda.get(id);
				if (m instanceof FutureMeeting)
				{
					
				
					if (m.getDate().before(Calendar.getInstance()))
					{
					  throw new IllegalArgumentException(    "There's a meeting with this Id that happened in the past" );
					}
					else
					{
					  fmeet=m;
					}
				}
		   
			}	
			return fmeet;
		}
			
	

		public Meeting getMeeting(int id)
		{

		
			Meeting temp=null; 
			if (agenda.containsKey(id))
			   {
				 temp=(Meeting)agenda.get(id);
			   }
			
			return temp;
				
			
		}

	
		public List<Meeting> getFutureMeetingList(Contact contact)
		{
		  
			if(!contacts_set.containsValue(contact))
			{
					 throw new IllegalArgumentException(    "Id doesn't correspond to a real contact" );
			}
			
			List<Meeting> result=new ArrayList<Meeting>();
			Set set=agenda.keySet();
			Iterator<Integer> it=set.iterator();
			Integer id=null;
			Meeting m=null;
			
			while(it.hasNext())
			{
				//id of the meeting
				id=(Integer)it.next(); 
				
				m=(Meeting)agenda.get(id);
				
				//COntact set of each meeting
				
				Set st=m.getContacts();
				if (st.contains(contact))
				
				{
				  result.add(m);
				}
					
			
			}
		  
		  return result;
			
		}

		
		public void addNewPastMeeting(Set<Contact> contacts,Calendar date,String text)
		{
			Meeting m;
			Contact temp1=null;
			if (contacts==null)
			{
			  throw new IllegalArgumentException(    "Client not found" );
			
			}
			else 
			{
				for(Iterator<Contact> it=contacts.iterator();it.hasNext();)
				{
					temp1=it.next();
				   if (!contacts_set.containsValue(temp1))
					{
						throw new IllegalArgumentException(    "Client not found" );
				 
					}
						
				}
			}
			 
			if(contacts==null ||date==null||text==null)
			{
			
				 throw new NullPointerException("Some of the arguments are null");
			}
			m= new PastMeetingImpl(contacts,date,text);
		
		
		
		}
		
		
		
	
		public void addNewContact(String name, String notes)
		{
		
			int j=generate_id_contact();
			Contact c= new ContactImpl(name,notes,j);
			contacts_set.put(j,c); 
		}

		
		
		
		public Set<Contact> getContacts(int... ids)
			{
			 
				Set<Contact> set= new HashSet<Contact> ();
				Contact temp=null;
				
				for (int i=0;i<ids.length;i++)
				{
					if(!contacts_set.containsKey(ids[i]))
					{
									
						throw new IllegalArgumentException(    "Id doesn't correspond to a real contact" );
					}
					
					else
					{
						set.add(contacts_set.get(ids[i]));
					
					}	 
				}
				
				return set;
			
			}
		
		public Set<Contact> getContacts(String name)
		{
		
		
		}
		
		
		public void printcontacts()
		{
			
			Set set=contacts_set.keySet();
			Iterator<Integer> it=set.iterator();
			Integer j=null;
			Contact c= null;
			while(it.hasNext())
			{
				j=(Integer)it.next();
				c=(Contact)contacts_set.get(j);
				System.out.println(c.getId()+"...."+j+"-------"+c.getName());
			  
			}
			
				
		}

	
		public void printMeeting()
		{
			Set set=agenda.keySet();
			Iterator<Integer> it=set.iterator();
			Integer id=null;
			Meeting m=null;
			
			while(it.hasNext())
			{
				System.out.println();
				int k=1;
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
		
		
		
		
		public  int generate_id_contact()
		{
			
			int j=(int)(Math.random()*1000+1);
			
			if(contacts_set.containsKey(j))
			{
				generate_id_contact();
			
			}
			
			if (!contacts_set.containsKey(j))
			{
				return j;
			}
			
				return j;
		
		}
	
		
		
		public  int generate_id_meeting()
		{
			int j=(int)(Math.random()*1000+1);
			
			if(contacts_set.containsKey(j))
			{
				generate_id_contact();
			
			}
			
			if (!contacts_set.containsKey(j))
			{
				return j;
			}
			
				return j;
			
		}
		 

	
		public void flush() throws IOException
		{
		
		 FileOutputStream fo= new FileOutputStream(path);
		 ObjectOutputStream os= new ObjectOutputStream(fo);
		 os.writeObject(agenda);
		 os.writeObject(contacts_set);
		 os.close();
		
		
		}

}