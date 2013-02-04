import java.util.*;
public class Biblioteca
{
	List<Libro> biblio;

	public Biblioteca()
	{
		biblio= new ArrayList<Libro> ();

	}


	public void launch()
	{

		biblio.add(new Libro("Vargas LLosa", "Hola","3333"));
		biblio.add(new Libro("Vargas LLosa", "Primavera","3334"));
		biblio.add(new Libro("Vargas LLosa", "Otoño","3322"));
		biblio.add(new Libro("Heminway", "El Viejo","1933"));
		biblio.add(new Libro("Heminway", "El Mar","ww933"));
		

	}
	public List<Libro> getLibro(String aut)
	{
		Libro b;
		List<Libro> temp= new ArrayList<Libro>();
		Iterator<Libro> it= biblio.iterator();
		while(it.hasNext())
		{
				 b=it.next();
				if (b.getAutor().equals(aut))
				{
				  temp.add(b);
				
				}
		
		}

			return temp;
	}
	
	public Libro getLibrobytit(String tit)
	{
		Libro temp=null;;
		Iterator<Libro> it= biblio.iterator();
		while(it.hasNext())
		{
				Libro b= it.next();
				if (b.getTitulo().equals(tit))
				{
				  return b;
				
				}
		
		}

			return temp;

	}
	

	public static void main(String arg[])
	{
		Biblioteca b= new Biblioteca();
		b.launch();
		Libro lib=b.getLibrobytit("Otoño");
		System.out.println(lib.getAutor());
		
		List<Libro> aux=b.getLibro("Heminway");
		Iterator<Libro> it=aux.iterator();
		while(it.hasNext())
		{
			System.out.println(it.next().getTitulo());
		}
	
	
	
	}
	
	}