package Lab7aq1;

public class Animal {

	// Variables
	   private int age;
	   private String type;
	   private char gender;
	   
	   // Constructors
	   public Animal(String type, int age, char gender) {
		   this.type = type;
		   this.age = age;
		   this.gender =gender;
	   }
	   
	   // Getters & Setters
	   public String getType() {
		   return type;
	   }
	   
	   public int getAge() {
		   return age;
	   }
	   
	   public char getGender( ) {
		   return gender;
	   }
	   
	   //Other Methods
	   
	   public void eat() {
		   System.out.println("Animal is eating");
	   }
	   
	   public void sleep() {
		   System.out.println("Animal is sleeping");
	   }
	   
	   public void makeSound() {
		   System.out.println("A generic animal sound");
	   }
	   //toString
	   public String toString() {
	      return "Type : " + type + ", Age : " + age + ", Gender : " + gender;
	   }
}
