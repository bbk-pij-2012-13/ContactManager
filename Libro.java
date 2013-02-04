public class Libro
{
private String autor;
private String titulo;
private String isbn;

public Libro(String au,String ti,String isbn)
{
this.autor=au;
this.titulo=ti;
this.isbn=isbn;
} 

public String getAutor()
{
return this.autor;

}

public String getTitulo()
{

return this.titulo;
}

public String getIsbn()
{
return isbn;

}
}