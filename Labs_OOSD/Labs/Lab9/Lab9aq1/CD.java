package Lab9aq1;

public class CD extends LibraryItem {
	
	private String band;
	private String title;
	private int numTracks;

	public CD( String iD, String band, String title, int numTracks) {
		super("CD", iD);
		this.band = band;
		this.title = title;
		this.numTracks = numTracks;
	}


	public double calculatePrice() {
		return numTracks * 0.87;
	}

	public String toString() {
		return "Type  :: CD" + "\nID    :: " + getID() + "\nBand  :: " + this.band + "\nTitle  :: " + this.title + "\nTracks  :: " + this.numTracks;
	}

}
