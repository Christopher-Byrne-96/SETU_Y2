package Laba3q3;

import java.util.Scanner;

public class SavingsAccountDriver {
	
	//Student: Christopher Byrne
	//Student NUmber: c00276260
	//Date: OCT 2022

	public static void main(String[] args) {
		
		// Create Variable and Customer Array 
		Scanner scan = new Scanner(System.in);
		int choice;
		int totalCustomers = 0;
		BankCustomer[] customers = new BankCustomer[100];
		
		//Main Menu Loop will cycle as long as loop is active case 0 will break loop and close the program 
		loop: while(true) {
			
			System.out.println(BankCustomer.mainMenu());
			choice = scan.nextInt();
			scan.nextLine();
			
			// Switch case for main menu option from user input
			switch(choice) {
			
				//Main Menu Option 1 create a new customer object
				case 1:
					totalCustomers++;
					System.out.println("Enter Customer " + totalCustomers + "'s Details!");
					System.out.println("Customer " + totalCustomers + "'s Name :: (First and Last)");
					String name  = scan.nextLine();
					System.out.println("Customer " + totalCustomers + "'s Address :: ");
					String address  = scan.nextLine();
					customers[totalCustomers-1] = new BankCustomer(name, address);
					System.out.println("There are now " + totalCustomers + " Customers in the system!");
				
					break;
				
				//Main Menu Option 2 Choose an existing customer
				case 2:
					
					// While loop for choosing a customer 
					//boolean bool = true;
					custloop: while(true) {
							System.out.println("Choose a Customer!");
							
							//Loop prints existing customers to the screen to be chosen 
							for(int i = 0; i < totalCustomers; i++) {
								System.out.println("| " + (i+1) + " |" + customers[i].getName() + "\r\n");
							}
							System.out.println("| " + 0 + " | EXIT" + "\r\n");
							int custChoice = scan.nextInt();
							
							//If no customer is chosen then this will break the customer choice loop and go back to the main menu
							if( custChoice == 0){
								//bool = false;
								break custloop;
							}
							
							// This loop will provide options for to perform on the chosen customer object
							custMenuLoop: while(true) {
								System.out.println(BankCustomer.customerMenu());
								choice = scan.nextInt();
								scan.nextLine();
							
									//Creating a new savings account 
									switch(choice) {
										case 1:
											System.out.println("What would you like to name the account?");
											String x = scan.nextLine();
											System.out.println("Do you want to make an initial deposit? (Y/N)");
											char ans = scan.nextLine().charAt(0);
											ans = Character.toUpperCase(ans);
				
											//Allows user to open an account with an initial balance
											if(ans == 'Y') {
												System.out.println("How much do you want to deposit? ");
												double deposit = scan.nextDouble();
												scan.nextLine();
												customers[custChoice -1].addAccount(new SavingsAccount(deposit,x));
												ans = ' ';
											}
											
											else {
												customers[custChoice -1].addAccount(new SavingsAccount(x));
											}
											System.out.println("A new savings account has been created!" + "\r\n");
											
											break;
											
										// Viewing the balance of the accounts 
										case 2:
											System.out.println(customers[custChoice-1].getName() + " has " + customers[custChoice-1].getAccountsOwned() + " active Savings Accounts!");
											for(int i = 0; i < customers[custChoice-1].getAccountsOwned(); i++ ) {
												System.out.println( "Account: " + customers[custChoice - 1].accountID(i) + " has a total balance of €" + customers[custChoice - 1].balanceAccount(i));
											}
											System.out.println("The total savings for " + customers[custChoice-1].getName() + " is €" + customers[custChoice-1].balance() + "\r\n");
		
											break;
											
										// Allows customer to make a deposit to one of the chosen accounts 
										case 3:
											System.out.println("Choose an account to deposit into! " + "\r\n");
											for(int i=0; i < customers[custChoice-1].getAccountsOwned(); i++) {
												System.out.println("| "  + (i+1) + " |" + customers[custChoice-1].accountID(i));
												}
											int choice3 = scan.nextInt();
											scan.nextLine();
											
											System.out.println("How much would you like to deposit?");
											double deposit = scan.nextDouble();
											scan.nextLine();
											
											customers[custChoice-1].deposit(choice3-1, deposit);
											break;
											
										// Allows customer to make a withdrawal from one of the accounts 	
										case 4:
											System.out.println("Choose an account to withdraw from! " + "\r\n");
											for(int i=0; i < customers[custChoice-1].getAccountsOwned(); i++) {
												System.out.println("| "  + (i+1) + " |" + customers[custChoice-1].accountID(i));
												}
											int choice4 = scan.nextInt();
											scan.nextLine();
											
											System.out.println("How much would you like to withdraw?");
											double withdraw = scan.nextDouble();
											scan.nextLine();
											
											customers[custChoice-1].withdraw(choice4-1, withdraw);
											break;
										
										// Prints to screen the accounts associated with the chosen customer
										case 5:
											System.out.println(customers[custChoice - 1].getName() + " has " + customers[custChoice - 1].getAccountsOwned() + " open savings accounts!" + "\r\n");
											for(int i=0; i < customers[custChoice-1].getAccountsOwned(); i++) {
												System.out.println("| "  + (i+1) + " | Account ID :: " + customers[custChoice-1].accountID(i));
												}
											break;
											
										case 0:
											break custMenuLoop;			
									}//switch case in custMenuLoop			
							}//custMenuLoop
						}//custLoop
					
				// Main Menu option 3 View Current Customers
				case 3:
					System.out.println("Current Customers are");
					//Loop prints existing customers details to the screen
					for(int i = 0; i < totalCustomers; i++) {
						System.out.println(customers[i].toString());
					}
					break;
					
				case 0:
					break loop;
				
				
			}//main menu
		}//loop
	scan.close();
	}//main
}//class
