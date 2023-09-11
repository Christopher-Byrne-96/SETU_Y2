package Lab8aq2;

public class TestPerson {

	public static void main(String[] args) {

	 Person[] people = new Person[] {new Student("Chris", "Computers"), new Employee("John", 40000)};
	 
	 System.out.println(people[0].getName());
	 System.out.println(people[0].getDescription());
	 System.out.println(people[1].getName());
	 System.out.println(people[1].getDescription());

	}

}
