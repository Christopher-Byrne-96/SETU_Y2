package CA_prep;

public class Book 
{
	
//----------------------------------------------------------------------------------
//-------Variable declaration-------------------------------------------------------
//----------------------------------------------------------------------------------
	
	private int isbn;
	static int numOfBooks = 0;
	private String bookName;
	private String author;
	private String genre;
	private char status;
	
//----------------------------------------------------------------------------------
//-------OBJECT CONSTRUCTER---------------------------------------------------------
//----------------------------------------------------------------------------------
	
	public Book(String name, String auth, String gen)
	{
		bookName = name;
		author = auth;
		genre = gen;
		status = 'A';
		numOfBooks++;
		isbn = numOfBooks;
	}
	
	
//----------------------------------------------------------------------------------
//-------SETTER METHODS-------------------------------------------------------------
//----------------------------------------------------------------------------------
	
	public void setBookName(String bName)
	{
		bookName = bName;
	}
	
	public void setAuthor(String bookAuthor)
	{
		author = bookAuthor;
	}
	
	public void setGenre(String bGenre)
	{
		genre = bGenre;
	}
	
	public void borrow()
	{
		status = 'B';
	}
	
	public void returned()
	{
		status = 'A';
	}
	
//----------------------------------------------------------------------------------
//-------GETTER METHODS-------------------------------------------------------------
//----------------------------------------------------------------------------------
	
	public String getBookName()
	{
		return bookName;
	}
	
	public String getGenre()
	{
		return genre;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public char getStatus()
	{
		return status;
	}
	
	public int getIsbn()
	{
		return isbn;
	}
	public static int numOfBooks()
	{
		return numOfBooks;
	}
	
//----------------------------------------------------------------------------------
//-------OBJECT CONSTRUCTER---------------------------------------------------------
//----------------------------------------------------------------------------------
	
	public String toString()
	{
		return "\r\n" +
			   "Book Name :: " + getBookName() +"\r\n" +
			   "Author    :: " + getAuthor() +"\r\n" + 
			   "Genre     :: " + getGenre() +"\r\n" +
			   "ISBN      :: " + getIsbn() +"\r\n" +
			   "Status    :: " + getStatus() +"\r\n" +
			   "\r\n";
	}

}
