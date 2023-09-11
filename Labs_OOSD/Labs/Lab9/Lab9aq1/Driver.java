package Lab9aq1;

public class Driver {

	public static void main(String[] args) {
		
		LibraryItem[] myArr = new LibraryItem[] { new Book("xyz", "Mick", "The big Book of nothing", 269), new CD("ABC", "The Loud Cunts", "Louder than ever", 22)};
		
		System.out.println(myArr[0].toString());
		System.out.println("Price :: " + myArr[0].calculatePrice());
		System.out.println(myArr[1].toString());
		System.out.println("Price :: " + myArr[1].calculatePrice());

	}

}
