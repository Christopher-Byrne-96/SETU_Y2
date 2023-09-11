package Laba3q3;



public class SavingsAccount {
	
	//------------------------------------------------------------------------------------------------------------------------------------
	//Variables
	//------------------------------------------------------------------------------------------------------------------------------------
	
	static double annualInterestRate = 0.0;
	static int accountsCreated = 0;
	private int accountNumber;
	private String accountID;
	private double savingsBalance;
	private double interestEarned;
	private double totalDeposited;
	private double totalWithdrawal;
	
	//------------------------------------------------------------------------------------------------------------------------------------
	//Constructor methods------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------------------------------------------
	
	public SavingsAccount() {
		accountsCreated++;
		accountNumber = accountsCreated;
		savingsBalance = 0.0;
		accountID = "";
		totalDeposited = 0.0;
		interestEarned = 0.0;
	}
	
	public SavingsAccount( String x) {
		accountsCreated++;
		accountNumber = accountsCreated;
		this.setBalance(0);
		this.setAccountID(x);
		totalDeposited = 0;
		interestEarned = 0;
	}
	
	public SavingsAccount(double balance, String x) {
		accountsCreated++;
		accountNumber = accountsCreated;
		this.setBalance(balance);
		this.setAccountID(x);
		totalDeposited = 0;
		interestEarned = 0;
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------
	//Setters------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------------------------------------------
	
	public void setBalance(double balance) {
		savingsBalance = this.decimalPlaces(balance);
	}
	
	public static void setInterestRate(double interest) {
		annualInterestRate = interest/100;
	}
	
	public void setAccountID(String x) {
		accountID = x;
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------
	//Getters	
	//------------------------------------------------------------------------------------------------------------------------------------

	public double getBalance() {
		return this.decimalPlaces(savingsBalance);
	}
	
	public String getID() {
		return accountID;
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------
	//To String------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------------------------------------------
	
	public String toString() {
		return "\r\n" +
			   "Account number    :: " + accountNumber + "\r\n" +
		       "Total Deposits    :: €" + totalDeposited + "\r\n" +
		       "Total Withdrawals :: €" + totalWithdrawal + "\r\n" +
		       "Interest earned   :: €" + interestEarned + "\r\n" +
		       "Savings total     :: €" + this.getBalance();
	}
	
	
	
	
	
	
	
	//------------------------------------------------------------------------------------------------------------------------------------
	//Misc methods------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------------------------------------------
	
	//Adds a deposit to account
	public void makeDeposit(double deposit) {
		deposit = this.decimalPlaces(deposit);
		totalDeposited = totalDeposited + deposit;
		savingsBalance = savingsBalance + deposit;
	}
	
	//Deducts a withdrawal from account 
	 public void makeWithdrawal(double withdraw) {
		 withdraw = this.decimalPlaces(withdraw);
		 savingsBalance = savingsBalance - withdraw;
		 totalWithdrawal = totalWithdrawal + withdraw;
	 }
	 
	 //Calculates Monthly Interest
	 public void calculateMonthlyInterest() {
		 double interest = (annualInterestRate/12)*savingsBalance;
		 interest = this.decimalPlaces(interest);
		 savingsBalance = savingsBalance + interest;
		 interestEarned = interestEarned + interest;
	 }
	 
	 //Transforms double into two decimal places
	 public double decimalPlaces(double value) {  // Hack-job method to keep output to two decimal places
		 double y = (value*100)%1;
		 double x = ((value*100)-y)/100;
		 return x;
	 }
	 

	 
	 
	 
	 
	 
	 
	
}
