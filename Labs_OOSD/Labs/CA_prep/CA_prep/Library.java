package CA_prep;

import java.util.Scanner;

public class Library {

	public static void main(String[] args) 
	{
		Book[] bookArr = new Book[3];
		String author;
		String bookName;
		String genre;
		Scanner newScan = new Scanner(System.in);
		
		for(int i = 0; i < bookArr.length; i++)
			{
				
				
				System.out.println("Enter the Title of the Book!");
				bookName = newScan.nextLine();
				
				
				System.out.println("Enter the Author of the Book!");
				author = newScan.nextLine();
				
				
				System.out.println("Enter the Genre of the Book!");
				genre = newScan.nextLine();
				
				bookArr[i] = new Book(bookName, author, genre);
				
				System.out.println("Current number of books created is : " + Book.numOfBooks() );

				
			}
		newScan.close();
		
		for(int i = 0; i < bookArr.length; i++)
		{
			System.out.println(bookArr[i].toString());
		}
			
		bookArr[1].borrow();
		
		for(int i = 0; i < bookArr.length; i++)
		{
			System.out.println(bookArr[i]);
		}
			
		
		
		
	}

}
