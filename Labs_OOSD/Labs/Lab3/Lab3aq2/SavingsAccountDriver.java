package Lab3aq2;

import java.util.Scanner;

public class SavingsAccountDriver {
	
	//Student: Christopher Byrne
	//Student NUmber: c00276260
	//Date: OCT 2022

	public static void main(String[] args) {
		
		SavingsAccount saver1 = new SavingsAccount(2000.101010101); //Creating instances
		SavingsAccount saver2 = new SavingsAccount(3000.202030203);
		
		System.out.println(saver1); // Print out to validate 
		System.out.println(saver2);
		
		SavingsAccount.setInterestRate(4); // Set interest rate
		
		saver1.calculateMonthlyInterest(); // Calculate the monthly interest
		saver2.calculateMonthlyInterest();
		
		System.out.println(saver1); // Print out
		System.out.println(saver2);
		
		SavingsAccount.setInterestRate(5); // Set new interest rate
		
		saver1.calculateMonthlyInterest(); // Calculate new Interest rate
		saver2.calculateMonthlyInterest();
		
		System.out.println(saver1); // Print out
		System.out.println(saver2);
		
		saver1.makeDeposit(200.45); // Test method make deposit
		saver2.makeDeposit(300.45);
		
		System.out.println(saver1); // Print out to validate
		System.out.println(saver2);
		
		saver1.makeWithdrawal(1508.97); // Test method withdraw
		saver2.makeWithdrawal(1234.56);
		
		System.out.println(saver1); // Validation
		System.out.println(saver2);
		
		
		

	}

}
