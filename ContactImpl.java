public class ContactImpl
{
	private int id;
	private String name;
	private String notes;
	private int cont=0;

	public Contact(String n)
	{
		 this.name=n;
		 this.id=cont;
		 cont ++;

	}
	
	public Contact(String n,String  not)
	{
		 this.name=n;
		 this.notes= not;
		 this.id=cont;
		 cont ++;

	}


	
	public int getId()
	{
	 return this.id;
	
	}
		
	public String  getName()
	{
	
	 return this.name;
	}

	public String  getNotes()
	{
	
	 return this.notes;
	}
	
	public void addNotes(String n)
	{
		this.notes=n;
	
	}
	
}
