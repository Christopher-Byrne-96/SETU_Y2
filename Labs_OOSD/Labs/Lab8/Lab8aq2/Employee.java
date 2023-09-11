package Lab8aq2;

public class Employee extends Person{

	private double salary;
	
	public Employee(String name, double salary) {
		super(name);
		this.salary = salary;
	}

	@Override
	public String getDescription() {
		return ("An employee with a salary of " + salary);
	}
	

}
