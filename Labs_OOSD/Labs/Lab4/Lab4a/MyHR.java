package Lab4a;

import java.util.Scanner;

public class MyHR {
	
	//Student :: Christopher Byrne
	//Student Number :: c00276260
	//Date :: October 2022
	//Program :: A rudimentary HR system to help with growing employee numbers

	public static void main(String[] args) {
		
		// Creating the variables and Arrays needed for the program
		int employeesCreated = 0;
		int officesFull = 0;
		Scanner input = new Scanner(System.in);
		Employee[] employees = new Employee[5];
		Address[] addresses = new Address[5];
		Office[] offices = new Office[3];
		for(int i = 0; i < 3; i++) {
			offices[i] = new Office();
		}
		
		// While loop to run the menu until user chooses to exit 
		main: while(true) {
			int choice;
			
			//Print to screen the menu 
			System.out.println("---------MY HR---------" + "\r\n" + "\r\n" +
							   "!1! List all offices   " + "\r\n" +
							   "!2! Create new employee" + "\r\n" +
							   "!3! List all employees " + "\r\n" +
							   "!0! EXIT               ");
			choice = input.nextInt();
			input.nextLine();
			
			switch(choice){
			
			// Print out all office records
			case 1:
				for(int i = 0; i < 3; i++) {
					System.out.println("\r\n" + "Office " + (i+1) + "\r\n" + offices[i].toString());
				}
				break;
				
			// Creating the new Employee
			case 2:
				
				//The if statement checks that the max of 5 employees has not been created yet
				if(employeesCreated < 5) {
					
				//Creating a new Employee and Address object
				employees[employeesCreated] = new Employee();
				addresses[employeesCreated] = new Address();
				
				//Assigning name 
				System.out.println("Enter the new Employees name: ");
				employees[employeesCreated].setName(input.nextLine());
				
				//Assigning Position
				System.out.println("Enter the new Employees Position:  (staff / manager)");
				employees[employeesCreated].setType(input.nextLine());
				
				//Assigning address
				System.out.println("Enter the new Employees address ");
				System.out.println("Street:");
				addresses[employeesCreated].setStreet(input.nextLine());
				System.out.println("City/Town:");
				addresses[employeesCreated].setCityTown(input.nextLine());
				System.out.println("County:");
				addresses[employeesCreated].setCounty(input.nextLine());
				employees[employeesCreated].setAddress(addresses[employeesCreated]);
				
				//Checks if Office is full and if it is moves to nect office 
				if(offices[officesFull].getNumofEmployees() == 2) {
					officesFull++;
				}
				
				//Adds employee to this office
				offices[officesFull].setEmployee(employees[employeesCreated]);
				System.out.println("A new employee record has been created!");
				employeesCreated++;
					}
				
				//Tells user max employees has been created
				else {
					System.out.println("The maximum ammount of employees has been reached");
				}
				
				break;
				
				//Prints to screen the employee records
			case 3:
				for(int i = 0; i < employeesCreated; i++) {
					System.out.println(employees[i].toString());	
					}
				break;
				
				//Exits the program 
			case 0:
				break main;
			}
		}

	}

}
