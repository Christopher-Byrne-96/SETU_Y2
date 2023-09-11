package Lab3aq2;



public class SavingsAccount {
	
	
	static double annualInterestRate = 0.0;
	static int accountsCreated = 0;
	private int accountNumber;
	private double savingsBalance;
	private double interestEarned;
	private double totalDeposited;
	private double totalWithdrawal;
	
	//Constructor methods------------------------------------------------------------------------------
	
	public SavingsAccount() {
		accountsCreated++;
		accountNumber = accountsCreated;
		savingsBalance = 0.0;
		totalDeposited = 0.0;
		interestEarned = 0.0;
	}
	
	public SavingsAccount(double balance) {
		accountsCreated++;
		accountNumber = accountsCreated;
		this.setBalance(balance);
		totalDeposited = 0;
		interestEarned = 0;
	}
	
	//Setters------------------------------------------------------------------------------
	
	public void setBalance(double balance) {
		savingsBalance = this.decimalPlaces(balance);
	}
	
	public static void setInterestRate(double interest) {
		annualInterestRate = interest/100;
	}
	//Getters	

	public double getBalance() {
		return this.decimalPlaces(savingsBalance);
	}
	
	//To String------------------------------------------------------------------------------
	
	public String toString() {
		return "\r\n" +
			   "Account number    :: " + accountNumber + "\r\n" +
		       "Total Deposits    :: €" + totalDeposited + "\r\n" +
		       "Total Withdrawals :: €" + totalWithdrawal + "\r\n" +
		       "Interest earned   :: €" + interestEarned + "\r\n" +
		       "Savings total     :: €" + this.getBalance();
	}
	
	//Misc methods------------------------------------------------------------------------------
	
	public void makeDeposit(double deposit) {
		deposit = this.decimalPlaces(deposit);
		totalDeposited = totalDeposited + deposit;
		savingsBalance = savingsBalance + deposit;
	}
	 public void makeWithdrawal(double withdraw) {
		 withdraw = this.decimalPlaces(withdraw);
		 savingsBalance = savingsBalance - withdraw;
		 totalWithdrawal = totalWithdrawal + withdraw;
	 }
	 
	 public void calculateMonthlyInterest() {
		 double interest = (annualInterestRate/12)*savingsBalance;
		 interest = this.decimalPlaces(interest);
		 savingsBalance = savingsBalance + interest;
		 interestEarned = interestEarned + interest;
	 }
	 
	 public double decimalPlaces(double value) {  // Hack-job method to keep output to decimal places
		 double y = (value*100)%1;
		 double x = ((value*100)-y)/100;
		 return x;
	 }
	
}
