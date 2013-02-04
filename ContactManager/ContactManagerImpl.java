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
			}
			
			Collections.sort(temp);
					
			for (int j=0;j<temp.size();j++)
			{
			 temp2.add(j,(Meeting)temp.get(j));
			
			}
			
			return temp2;
		
		}

		public List<PastMeeting> getPastMeetingList(Contact c)
		{
			if(!contacts_set.containsValue(c))
			{
			throw new IllegalArgumentException(    "Id doesn't correspond to a real contact" ); 
			
			}
			else
			{
				Set set=agenda.keySet();
				Iterator<Integer> it=set.iterator();
				Integer id=null;
				Meeting m=null;
				List<PastMeeting> lspast=new ArrayList<PastMeeting>();
				while(it.hasNext())
				{
					
					id=(Integer)it.next();
					m=(Meeting)agenda.get(id);
					if (m instanceof PastMeetingImpl)
					{
						if ((m.getContacts()).contains(c))
						{
						  lspast.add((PastMeeting)m);
						}
					}
				
				}
			
				return lspast;
			}
		}
		
		
		
		public void addNewPastMeeting(Set<Contact> contacts,Calendar date,String text)
		{
			Meeting m;
			Contact temp1=null;
			System.out.println("Im inside addNew PastMeetin");
			if(contacts==null ||date==null||text==null)
			{
			
				 throw new NullPointerException("Some of the arguments are null");
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
			 		
			System.out.println("Before creating");
			int j=generate_id_meeting();
			m= new PastMeetingImpl(j,date,contacts,text);
			System.out.println("Id meeting:"+ j);
			agenda.put(j,m);
		
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