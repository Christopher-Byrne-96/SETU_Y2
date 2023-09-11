package Lab9aq1;

public class LibraryItem implements LoanItem{
	
	private String type;
	private String iD;
	
	public LibraryItem( String type, String iD) {
		this.type = type;
		this.iD = iD;
	}

	public String getID() {
		return this.iD;
	}
	@Override
	public double calculatePrice() {
		// TODO Auto-generated method stub
		return 0;
	}


}
