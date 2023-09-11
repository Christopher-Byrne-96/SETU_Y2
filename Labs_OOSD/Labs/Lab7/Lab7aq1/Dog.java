package Lab7aq1;

public class Dog extends Animal {

	public Dog(String type, int age, char gender) {
		super(type,age,gender);
	}

   //Other Methods
   
   public void eat() {
	   System.out.println("Dog is eating");
   }
   
   public void sleep() {
	   System.out.println("Dog is sleeping");
   }
   
   public void makeSound() {
	   System.out.println("Woof Woof");
   }
}
