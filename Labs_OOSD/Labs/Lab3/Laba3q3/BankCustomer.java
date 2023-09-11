package Laba3q3;

public class BankCustomer {
	
	//------------------------------------------------------------------------------------------------------------------------------------
	//Variables
	//------------------------------------------------------------------------------------------------------------------------------------
	

	private String name;
	private String address;
	private SavingsAccount[] accounts = new SavingsAccount[3];
	private 
	int accountsOwned = 0;
	private double savingsTotal;
	private double currentTotal;
	
	
	//------------------------------------------------------------------------------------------------------------------------------------
	// Constructors
	//------------------------------------------------------------------------------------------------------------------------------------
	
	public BankCustomer() {
		this.setName("");
		this.setAddress("");
	}
	
	public BankCustomer(String name) {
		this.setName(name);
		this.setAddress("");
	}
	
	public BankCustomer(String name, String address) {
		this.setName(name);
		this.setAddress(address);
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------
	// Setter methods
	//------------------------------------------------------------------------------------------------------------------------------------
	
	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------
	// Getter methods 
	//------------------------------------------------------------------------------------------------------------------------------------
	
	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}
	
	public int getAccountsOwned() {
		return accountsOwned;
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------
	// To String
	//------------------------------------------------------------------------------------------------------------------------------------
	
	public String toString() {
		return "Customer: Name     :: " + this.getName() + "\r\n" +
			   "          Adress   :: " + this.getAddress() + "\r\n" +
			   "          Accounts :: " + this.getAccountsOwned() + "\r\n";
 			   }
	
	
	
	
	
	
	//------------------------------------------------------------------------------------------------------------------------------------
	//Misc methods
	//------------------------------------------------------------------------------------------------------------------------------------
	
	
	
	// Adds an account instance to the arrayu accounts 
	public void addAccount(SavingsAccount x) {
		accounts[accountsOwned] = x;
		accountsOwned++;
	}
	
	//Calculates the balance of all accounts in array accounts for instance of customer object and returns the total
	public double balance() {
		for(int i  = 0; i < accountsOwned; i++ ) {
			currentTotal = currentTotal + accounts[i].getBalance();
		}
		savingsTotal = currentTotal;
		currentTotal = 0;
		return savingsTotal;
	}
	
	//Calls deposit method from SavingsAccount class on account from array accounts 
	public void deposit(int i, double x) {
		accounts[i].makeDeposit(x);
	}
	
	//Calls withdraw method from SavingsAccount class in account from array accounts
	public void withdraw(int i, double x) {
		accounts[i].makeWithdrawal(x);
	}
	
	//return balance of chosen account from array accounts 
	public double balanceAccount(int i) {
		return accounts[i].getBalance();
	}
	
	//return ID of account from array accounts
	public String accountID(int i) {
		return accounts[i].getID();
	}
	
	// Print Main Menu
	public static String mainMenu() {
		return "\r\n" +
			   "Bank Systems LTD" + "\r\n" + "\r\n" +
			   "\r\n" + 
			   "o:=: SELECT AN OPTION BELOW :=:o" + "\r\n" +
			   "\r\n" +
			   "| 1 |" + "= CREATE NEW CUSTOMER" + "\r\n" +
			   "| 2 |" + "= CHOOSE EXISTING CUSTOMER" + "\r\n" +
			   "| 3 |" + "= VIEW CURRENT CUSTOMERS" + "\r\n" +

			   "| 0 |" + "= EXIT" + "\r\n";
	}
	
	//Print Customer Menu
	public static String customerMenu() {
		return "Bank Systems LTD" + "\r\n" + "\r\n" +
			   "\r\n" + 
			   "o:=: SELECT AN OPTION BELOW :=:o" + "\r\n" +
			   "\r\n" +
			   "| 1 |" + "= ADD NEW SAVINGS ACCOUNT" + "\r\n" +
			   "| 2 |" + "= VIEW BALANCE" + "\r\n" +
			   "| 3 |" + "= MAKE DEPOSIT" + "\r\n" +
			   "| 4 |" + "= MAKE WITHDRAWAL" + "\r\n" +
			   "| 5 |" + "= VEIW CURRENT ACCOUNTS" + "\r\n" +
			   "| 0 |" + "= EXIT" + "\r\n";
	}
	
}
