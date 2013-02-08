import java.util.*;
import java.io.*;
 class ContactManagerImpl implements ContactManager
{
		
		private  Map <Integer,Contact> contacts_set;
		private final String path="F:\\Agenda4.obj";
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

	/**
	*Return the list of future meetings that are scheduled for this contact.
	*@temp is a List of FutureMeetingImpl as it is created in order to sort the list by date (as we can´t make Meeting implements Comparable since we aren not allowed to modify the interfaces ).
	*@temp2 is a List<Meeting>,downcast of temp, due to of the requirement of returning List<Meeting> 
	*first we sort temp by date and after these "items" are copied and casted to Meeting in the new List<Meeting> temp
	**/
	
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
				PastMeetingImpl pm=null;
				List<PastMeetingImpl> lspast=new ArrayList<PastMeetingImpl>();
				List<PastMeeting> lspast2=new ArrayList<PastMeeting>();
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
				Collections.sort(lspast);
				
				for (int i=0;i<lspast.size();i++)
				{
					lspast2.add((PastMeeting)lspast.get(i));
				
				}
				
				
				
				return lspast2;
			}
		}
		
		
		
		public void addNewPastMeeting(Set<Contact> contacts,Calendar date,String text)
		{
			Meeting m;
			Contact temp1=null;
			
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

		
		public void addMeetingNotes(int id, String text)
		{
			
			if (text==null)
			{
				throw new NullPointerException("The notes are null");			
			}
			if(!agenda.containsKey(id))
			{
				throw new IllegalArgumentException(  "This meeting, Id, doesn't exist");
			}
			
			else if (agenda.get(id).getDate().after(Calendar.getInstance()))	 
			{
				throw new IllegalArgumentException(  "This meeting is set for date in the future");
			}
			
			//PastMeeting or PastMeetingImpl??
			else if (agenda.get(id) instanceof PastMeeting)				
			{
			   
			   ((PastMeetingImpl)agenda).setNotes(text);
			 
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
		//trial not definitive
			Set<Contact> result=new HashSet<Contact> ();
			Set set=contacts_set.keySet();
			Iterator<Integer> it=set.iterator();
			Integer j=null;
			Contact c= null;
			while(it.hasNext())
			{
				j=(Integer)it.next();
				c=(Contact)contacts_set.get(j);
				if(c.getName().equals(name))
				{
					result.add(c);
					
				}
			  
			}
			return result;
			
		
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
			} catch (IOException ex) { }
		
		}
		
		
		
		public static void main(String[] args) throws IOException,ClassNotFoundException
		{
       
			ContactManagerImpl cont=new ContactManagerImpl();
			/*
			cont.printMeeting();
			cont.printcontacts();
			
			
			Calendar date = Calendar.getInstance();
			date.set(2018,10,21);
			Calendar date2 = Calendar.getInstance();
			date2.set(2014,2,19);
			Calendar date3 = Calendar.getInstance();
			date3.set(2013,9,22);
			Calendar date4 = Calendar.getInstance();
			date4.set(2015,5,30);
		
			Calendar date5 = Calendar.getInstance();
			date5.set(2001, 2, 29,01, 27);

			Calendar date6 = Calendar.getInstance();
			date6.set(2003, 1, 01,13, 27);
			Calendar date7 = Calendar.getInstance();
			date7.set(2007, 10, 11,22, 27);
			Calendar date8 = Calendar.getInstance();
			date8.set(2006, 6, 15,11,30);
			Set<Contact> setpast=cont.getContacts(60,905,684);
			Set<Contact> setpast2=cont.getContacts(800,16);
			Set<Contact> setpast3=cont.getContacts(393,111,617);
			Set<Contact> setpast4=cont.getContacts(638,778,617);
			Set<Contact> setpast5=cont.getContacts(905,16);
			cont.addNewPastMeeting(setpast3,date5,"Esta es la primera prueba addNewPastMewegin");
			cont.addNewPastMeeting(setpast,date6,"Esta es la ssegunda prueba addNewtPastMewegin");
			cont.addNewPastMeeting(setpast4,date7,"Esta es la tercera prueba addNewPastMewegin");
			cont.addNewPastMeeting(setpast5,date8,"Esta es la cuarta prueba addNewPastMewegin");
			cont.addFutureMeeting(setpast2,date);
			cont.addFutureMeeting(setpast3,date3);
			cont.addFutureMeeting(setpast,date2);
			cont.addFutureMeeting(setpast2,date4);
			
			
			
			*/
			
			Set contact=cont.getContacts(905);
			Contact isa=null;
			Iterator<Contact> it=contact.iterator();
			while(it.hasNext())
			{
			 isa= it.next();
			}
			//AQUI PUSE LIST L=..... HAY QUE PONER SIEMPRE EL TIPO LIST<MEETING> SINO HACER LA CONVERSION.
			List<PastMeeting> l=cont.getPastMeetingList(isa);
			for (int i=0;i<l.size();i++)
			{
				System.out.println("Date of the meeting:   "+l.get(i).getDate().getTime()+"   Meeting ID: "+l.get(i).getId());
			
			}
				
				
			
			Contact domi=null;
			Iterator<Contact> it2=contact.iterator();
			while(it2.hasNext())
			{
			 domi= it2.next();
			}
			List<Meeting> l2=cont.getFutureMeetingList(domi);
			for (int j=0;j<l2.size();j++)
			{
				System.out.println("Date of the meeting:   "+l2.get(j).getDate().getTime()+"   Meeting ID: "+l2.get(j).getId());
			
			}
			
			
				cont.flush();
			
			}

}