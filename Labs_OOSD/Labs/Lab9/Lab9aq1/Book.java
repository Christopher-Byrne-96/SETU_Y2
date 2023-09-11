package Lab9aq1;

public class Book extends LibraryItem {
	
	private String author;
	private String title;
	private int numPages;
	

	public Book( String iD, String author, String title, int numPages) {
		super("Book", iD);
		this.author = author;
		this.title = title;
		this.numPages = numPages;
	}


	public double calculatePrice() {
		
		return numPages/70;
	}
	
	public String toString() {
		return "Type  :: Book" + "\nID    :: " + getID() + "\nAuthor  :: " + this.author + "\nTitle  :: " + this.title + "\nPages  :: " + this.numPages;
	}
	

}
