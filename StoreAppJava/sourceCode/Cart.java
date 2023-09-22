//Written By		::	Christopher Byrne
//Date				::	March 2023
//Purpose			::	CA3 for Dr Jason Baron SETU
//						Design a java applet to manage customers purchases

//For handling adding items to a cart to display in the gui


package CA3;


//Start of class
public class Cart {

	//Declaring variables
	private int[] items = new int[20];
	private int totalItems = 0;
	
	//Constructor method
	public Cart() {
		
	}
	
	//Add item method this adds the product id to the array for the cart 
	public void addItem(int x) {
		items[totalItems] = x;
		totalItems++;
	}
	
	//This returns the array of products to be used later
	public int[] returnList() {
		return items;
	}
	
	//This resets the cart to empty after purchase is made 
	public void reset() {
		totalItems = 0;
		for(int i=0; i<items.length; i++) {
			items[i] = 0;
		}
	}
}
