// Written by	:   Christopher Byrne & Ciaran Whyte
// Date			:	21/02/2023
// Description	: 	A walking tour style text based app that reads information from a text file and allows the user to view that information as a walking guide.








package WalkingTour;

import java.util.Scanner;

public class WTDriver {

	public static void main(String[] args) {
	
		Scanner scan = new Scanner(System.in);
		int option;
		Locations[] locations = new Locations[10];		// Created an array of location objects to store data about each location. A size of 10 was chose as we wont be reading more than 10 in this program 
		for(int i=0; i<locations.length; i++) {			//Initializing the array to empty 
			locations[i] = new Locations();
		}
		String[][] matrix = new String[10][10];			//Creating the array for the matrix
		
		
		// This Loop will run until program is exited by the user 
		loop: while(true) {
			
			
			Methods.mainMenu(); // Calling the main menu method from class methods
			option = scan.nextInt(); // setting up for using scanner in the switch case
			scan.nextLine();
			
			switch(option) {
			case 1:
				System.out.println("Enter the absalute path of the file you want to read :");
				String fileName = scan.nextLine();				// Reading in the file path to fileName variable
				scan.nextLine();								// Flushing the scanner
				Methods.readFile(locations, fileName, matrix); 	// Calling the readFile method from Methods to scan the file and create the matrix and location objects
				break;
			
			case 2:
				System.out.println("Choose a location to see more info about it! Press 0 to go back!");
				Methods.printLocations(locations);	//This prints the locations from the locations array
				int choice = scan.nextInt();
				if(choice != 0) { // Making sure choice is not 0 as choice 0 is used as an escape to main menu
					Methods.showMore(locations[choice-1]); //Calling the show more method which displays information about the chosen site
					Methods.showAdjacentNodes(locations, matrix, choice); //Calling the showAdjacentNodes method this method finds nodes with an edge connected to the selected node
				}//End of if											  // Also will print the closest node(s)
				break;
				
			case 3:
				Methods.printLocations(locations);	//Calling printLocations again tp show list of nodes for user to choose from
				System.out.println("Chose the first site!");
				int first = scan.nextInt(); // Picking the first node to be used in the edge search
				scan.nextLine();
				System.out.println("Chose the second site!");
				int second = scan.nextInt(); // Choosing the second node to be used in the edge search
				scan.nextLine();
				Methods.addSearchEdges(first, second, matrix, locations); // Calling method addSearchEdges to allow user to see if an edge exists or add one if it doesn't
				break;
				
			case 4:
				Methods.printMatrix(locations, matrix); // This method simply prints the matrix out on the screen
				break;
			
			case 0:
				break loop; // Breaks the while loop and exits the program
			}// Main menu switch case
		}// loop
		

	}

}
